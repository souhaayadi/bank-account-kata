import {Component} from '@angular/core';
import {AccountService} from '../../services/account.service';
import {Router} from '@angular/router';
import {error} from 'protractor';

@Component({
  selector: 'app-create-account',
  templateUrl: './create-account.component.html',
  styleUrls: ['./create-account.component.scss']
})
export class CreateAccountComponent {
  accountId: string | null = null;

  constructor(private accountService: AccountService, private router: Router) {
  }

  onCreateAccount(): void {
    this.accountService.createAccount().subscribe(response => {
      const locationHeader = response.headers.get('Location');
      if (locationHeader) {
        this.accountId = locationHeader.split('/').pop() ?? null;
      }
      setTimeout(() => {
        this.router.navigate(['/statement', this.accountId]);
      }, 1500);
    });
  }
}

