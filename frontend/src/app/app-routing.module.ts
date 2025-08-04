import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import {CreateAccountComponent} from './components/create-account/create-account.component';
import {AccountStatementComponent} from './components/account-statement/account-statement.component';

const routes: Routes = [
  { path: '', redirectTo: 'create-account', pathMatch: 'full' },
  { path: 'create-account', component: CreateAccountComponent },
  { path: 'statement/:id', component: AccountStatementComponent } // 👈
];
@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule {
}
