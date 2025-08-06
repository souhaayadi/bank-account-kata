import { Component, OnInit, ViewChild } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { MatSnackBar } from '@angular/material/snack-bar';
import { MatTableDataSource } from '@angular/material/table';
import { MatPaginator } from '@angular/material/paginator';
import { MatSort } from '@angular/material/sort';

import { AccountService } from '../../services/account.service'; // adapte chemin
import { Transaction } from '../../models/transaction.model';
import { User } from '../../models/user.model';
import {AccountStatement} from "../../models/accountStatement.model";

@Component({
  selector: 'app-account-statement',
  templateUrl: './account-statement.component.html',
  styleUrls: ['./account-statement.component.scss']
})
export class AccountStatementComponent implements OnInit {
  accountId = '';
  user?: User;

  balance = 0;
  displayedColumns: string[] = ['type', 'amount', 'date'];
  dataSource = new MatTableDataSource<Transaction>([]);

  @ViewChild(MatPaginator) paginator!: MatPaginator;
  @ViewChild(MatSort) sort!: MatSort;

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private accountService: AccountService,
    private snackBar: MatSnackBar
  ) {}

  ngOnInit(): void {
    this.accountId = this.route.snapshot.paramMap.get('id') || localStorage.getItem('accountId') || '';
    this.loadStatement();
  }

  ngAfterViewInit(): void {
    this.dataSource.paginator = this.paginator;
    this.dataSource.sort = this.sort;

    if (this.sort) {
      this.sort.active = 'date';
      this.sort.direction = 'desc';
      this.sort.sortChange.emit({ active: 'date', direction: 'desc' });
    }
  }

  loadStatement(): void {
    this.accountService.getStatement(this.accountId).subscribe((st: AccountStatement) => {
      this.balance = st.balance;
      this.dataSource.data = st.transactions ?? [];
    });
  }

  goDeposit(): void { this.router.navigate(['/deposit', this.accountId]); }
  goWithdraw(): void { this.router.navigate(['/withdraw', this.accountId]); }
}
