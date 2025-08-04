import { Component } from '@angular/core';
import { AccountService } from '../../services/account.service';

@Component({
  selector: 'app-create-account',
  templateUrl: './create-account.component.html',
  styleUrls: ['./create-account.component.scss']
})
export class CreateAccountComponent {
  accountId: string | null = null;

  constructor(private accountService: AccountService) {}

  onCreateAccount(): void {
    this.accountService.createAccount().subscribe(response => {
      const locationHeader = response.headers.get('Location');
      if (locationHeader) {
        this.accountId = locationHeader.split('/').pop() ?? null;
      }
    });
  }
}
