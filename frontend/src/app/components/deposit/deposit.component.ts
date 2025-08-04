import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { AccountService } from '../../services/account.service';

@Component({
  selector: 'app-deposit',
  templateUrl: './deposit.component.html',
  styleUrls:['./deposit.component.scss']
})
export class DepositComponent implements OnInit {
  accountId!: string;
  amount!: number;
  success = false;

  constructor(
    private route: ActivatedRoute,
    private accountService: AccountService,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.accountId = this.route.snapshot.paramMap.get('id')!;
  }

  onDeposit(): void {
    this.accountService.deposit(this.accountId, this.amount).subscribe(() => {
      this.success = true;
      setTimeout(() => {
        this.router.navigate(['/statement', this.accountId]);
      }, 1500);
    });
  }
}
