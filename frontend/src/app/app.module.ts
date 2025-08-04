import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { CreateAccountComponent } from './components/create-account/create-account.component';
import {HttpClientModule} from "@angular/common/http";
import { AccountStatementComponent } from './components/account-statement/account-statement.component';
import { DepositComponent } from './components/deposit/deposit.component';
import {FormsModule} from '@angular/forms';
import { WithdrawComponent } from './components/withdraw/withdraw.component';

@NgModule({
  declarations: [
    AppComponent,
    CreateAccountComponent,
    AccountStatementComponent,
    DepositComponent,
    WithdrawComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    FormsModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
