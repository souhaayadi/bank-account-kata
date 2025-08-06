import {LOCALE_ID, NgModule} from '@angular/core';
import {BrowserModule} from '@angular/platform-browser';

import {AppRoutingModule} from './app-routing.module';
import {AppComponent} from './app.component';
import {CreateAccountComponent} from './components/create-account/create-account.component';
import {HTTP_INTERCEPTORS, HttpClientModule} from '@angular/common/http';
import {AccountStatementComponent} from './components/account-statement/account-statement.component';
import {DepositComponent} from './components/deposit/deposit.component';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import {WithdrawComponent} from './components/withdraw/withdraw.component';
import {HttpErrorInterceptor} from './interceptors/http-error.interceptor';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import {MatSnackBarModule} from '@angular/material/snack-bar';
import { HeaderComponent } from './components/header/header.component';
import {MatTableModule} from "@angular/material/table";
import {MatPaginatorModule} from "@angular/material/paginator";
import {MatSortModule} from "@angular/material/sort";
import {MatFormFieldModule} from "@angular/material/form-field";
import {MatInputModule} from "@angular/material/input";
import {MatButtonModule} from "@angular/material/button";
import {registerLocaleData} from "@angular/common";
import localeFr from "@angular/common/locales/fr";

registerLocaleData(localeFr);

@NgModule({
  declarations: [
    AppComponent,
    CreateAccountComponent,
    AccountStatementComponent,
    DepositComponent,
    WithdrawComponent,
    HeaderComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    FormsModule,
    MatSnackBarModule,
    BrowserAnimationsModule,
    ReactiveFormsModule,
    MatTableModule,
    MatPaginatorModule,
    MatSortModule,
    MatFormFieldModule,
    MatInputModule,
    MatButtonModule
  ],
  providers: [
    {
      provide: HTTP_INTERCEPTORS,
      useClass: HttpErrorInterceptor,
      multi: true
    },
    {
      provide: LOCALE_ID,
      useValue: 'fr'
    }
  ],
  bootstrap: [AppComponent]
})
export class AppModule {
}
