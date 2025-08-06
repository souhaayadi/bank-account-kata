import {Component, OnInit} from '@angular/core';
import {AccountService} from '../../services/account.service';
import {Router} from '@angular/router';
import {MatSnackBar} from "@angular/material/snack-bar";
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {User} from "../../models/user.model";

@Component({
  selector: 'app-create-account',
  templateUrl: './create-account.component.html',
  styleUrls: ['./create-account.component.scss']
})
export class CreateAccountComponent implements OnInit{
  accountId!: string
  form!: FormGroup;

  constructor(private accountService: AccountService,
              private router: Router,
              private snackBar: MatSnackBar,
              private fb: FormBuilder) {

  }
  ngOnInit(): void {
    this.form = this.fb.group({
      email: ['', [Validators.required, Validators.email]],
      password: ['', [Validators.required, Validators.minLength(6)]]
    });
  }
  onCreateAccount(): void {
    const user: User = {
      email: this.form.value.email,
      password: this.form.value.password
    };
    this.accountService.createAccount(user).subscribe(response => {
      const locationHeader = response.headers.get('Location');
      if (locationHeader) {
        this.accountId = locationHeader.split('/').pop();

        this.snackBar.open('Compte créé avec succès 🎉', 'Fermer', {
          duration: 1500,
          panelClass: ['success-snackbar']
        });
        setTimeout(() => {
          this.router.navigate(['/statement', this.accountId]);
          localStorage.setItem('accountId', this.accountId);
        }, 1500);
      }
    });

  }
}

