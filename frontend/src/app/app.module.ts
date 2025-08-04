import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { CreateAccountComponent } from './components/create-account/create-account.component';
import {HttpClientModule} from "@angular/common/http";
import { AccountStatementComponent } from './components/account-statement/account-statement.component';

@NgModule({
  declarations: [
    AppComponent,
    CreateAccountComponent,
    AccountStatementComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
