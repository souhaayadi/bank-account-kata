import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { AccountService } from '../../services/account.service';

@Component({
  selector: 'app-account-statement',
  templateUrl: './account-statement.component.html',
  styleUrls: ['./account-statement.component.scss']
})
export class AccountStatementComponent implements OnInit {
  accountId = '';
  balance = 0;
  transactions: any[] = [];

  constructor(private route: ActivatedRoute, private accountService: AccountService) {}

  ngOnInit(): void {
    this.accountId = this.route.snapshot.paramMap.get('id')!;
    this.accountService.getStatement(this.accountId).subscribe(data => {
      this.balance = data.balance;
      this.transactions = data.transactions;
    });
  }
}
