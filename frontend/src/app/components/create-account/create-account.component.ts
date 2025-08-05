import {Component} from '@angular/core';
import {AccountService} from '../../services/account.service';
import {Router} from '@angular/router';
import {error} from 'protractor';
import {MatSnackBar} from "@angular/material/snack-bar";

@Component({
  selector: 'app-create-account',
  templateUrl: './create-account.component.html',
  styleUrls: ['./create-account.component.scss']
})
export class CreateAccountComponent {
  accountId: string = '';

  constructor(private accountService: AccountService,
              private router: Router,
              private snackBar: MatSnackBar,) {
  }

  onCreateAccount(): void {
    this.accountService.createAccount().subscribe(response => {
      const locationHeader = response.headers.get('Location');
      if (locationHeader) {
        this.accountId = locationHeader.split('/').pop();
        localStorage.setItem('accountId', this.accountId);
      }
      this.snackBar.open('Compte créé avec succès 🎉', 'Fermer', {
        duration: 1500,
        panelClass: ['success-snackbar']
      });
      setTimeout(() => {
        this.router.navigate(['/statement', this.accountId]);
      }, 1500);
    });
  }
}

