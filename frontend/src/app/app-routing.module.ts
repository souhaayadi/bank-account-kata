import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import {CreateAccountComponent} from './components/create-account/create-account.component';
import {AccountStatementComponent} from './components/account-statement/account-statement.component';
import {DepositComponent} from './components/deposit/deposit.component';
import {WithdrawComponent} from './components/withdraw/withdraw.component';

const routes: Routes = [
  { path: '', redirectTo: 'create-account', pathMatch: 'full' },
  { path: 'create-account', component: CreateAccountComponent },
  { path: 'statement/:id', component: AccountStatementComponent },
  { path: 'deposit/:id', component: DepositComponent },
  { path: 'withdraw/:id', component: WithdrawComponent }
];
@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule {
}
