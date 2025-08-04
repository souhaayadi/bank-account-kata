import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { AccountService } from 'src/app/services/account.service';

@Component({
  selector: 'app-withdraw',
  templateUrl: './withdraw.component.html',
  styleUrls: ['./withdraw.component.scss']
})
export class WithdrawComponent implements OnInit {
  accountId!: string;
  amount = 0;
  successMessage = '';
  errorMessage = '';

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private accountService: AccountService
  ) {}

  ngOnInit(): void {
    this.accountId = this.route.snapshot.paramMap.get('id')!;
  }

  onWithdraw(): void {
    this.accountService.withdraw(this.accountId, this.amount).subscribe({
      next: () => {
        this.successMessage = 'Retrait effectué avec succès.';
        this.errorMessage = '';
        setTimeout(() => {
          this.router.navigate(['/statement', this.accountId]);
        }, 1500);
      },
      error: () => {
        this.successMessage = '';
        this.errorMessage = 'Erreur lors du retrait.';
      }
    });
  }
}
