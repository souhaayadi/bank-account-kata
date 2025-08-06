import {Component, OnInit, ViewChild} from '@angular/core';
import {ActivatedRoute, Router} from '@angular/router';
import {MatTableDataSource} from '@angular/material/table';
import {MatPaginator} from '@angular/material/paginator';
import {MatSort} from '@angular/material/sort';

import {AccountService} from '../../services/account.service';
import {Transaction} from '../../models/transaction.model';
import {AccountStatement} from "../../models/accountStatement.model";

@Component({
  selector: 'app-account-statement',
  templateUrl: './account-statement.component.html',
  styleUrls: ['./account-statement.component.scss']
})
export class AccountStatementComponent implements OnInit {
  accountId = '';
  balance = 0;
  displayedColumns: string[] = ['type', 'amount', 'date'];
  dataSource = new MatTableDataSource<Transaction>([]);

  @ViewChild(MatPaginator)
  set matPaginator(p: MatPaginator){
    if (p){
      this.dataSource.paginator = p;
    }
  }
  @ViewChild(MatSort) sort!: MatSort;

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private accountService: AccountService) {}

  ngOnInit(): void {
    this.accountId = this.route.snapshot.paramMap.get('id') || localStorage.getItem('accountId') || '';
    this.loadStatement();
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
