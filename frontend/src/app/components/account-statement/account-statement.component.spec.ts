import { ComponentFixture, TestBed } from '@angular/core/testing';
import { RouterTestingModule } from '@angular/router/testing';
import { MatSnackBarModule } from '@angular/material/snack-bar';
import { MatTableModule } from '@angular/material/table';
import { MatPaginatorModule } from '@angular/material/paginator';
import { MatSortModule } from '@angular/material/sort';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatButtonModule } from '@angular/material/button';
import { By } from '@angular/platform-browser';
import { of } from 'rxjs';
import localeFr from "@angular/common/locales/fr";

import { AccountStatementComponent } from './account-statement.component';
import { AccountService } from '../../services/account.service';
import {AccountStatement} from "../../models/accountStatement.model";
import {BrowserAnimationsModule} from "@angular/platform-browser/animations";
import {registerLocaleData} from "@angular/common";
import {LOCALE_ID} from "@angular/core";
import {HeaderComponent} from "../header/header.component";

class AccountServiceMock {
  getStatement(accountId: string) {
    const mockStatement: AccountStatement = {
      accountId: accountId,
      balance: 1200,
      transactions: [
        { id: '1', type: 'DEPOSIT', amount: 500, date: new Date('2025-08-01T10:00:00').toISOString() },
        { id: '2', type: 'WITHDRAW', amount: 200, date: new Date('2025-08-02T14:00:00').toISOString() }
      ]
    };
    return of(mockStatement);
  }
}

describe('AccountStatementComponent', () => {
  let component: AccountStatementComponent;
  let fixture: ComponentFixture<AccountStatementComponent>;
  beforeAll(() =>registerLocaleData(localeFr))
  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [AccountStatementComponent, HeaderComponent],
      imports: [
        RouterTestingModule,
        MatSnackBarModule,
        MatTableModule,
        MatPaginatorModule,
        MatSortModule,
        MatFormFieldModule,
        MatInputModule,
        MatButtonModule,
        BrowserAnimationsModule
      ],
      providers: [
        { provide: AccountService, useClass: AccountServiceMock },
        {
          provide: LOCALE_ID,
          useValue: 'fr'
        }
      ]
    }).compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(AccountStatementComponent);
    component = fixture.componentInstance;
    component.accountId = 'abc123';
    fixture.detectChanges();
  });

  it('should create component', () => {
    expect(component).toBeTruthy();
  });

  it('should have two action buttons for deposit and withdraw', () => {
    const buttons = fixture.debugElement.queryAll(By.css('.actions button'));
    expect(buttons.length).toBeGreaterThanOrEqual(2);
    expect(buttons[0].nativeElement.textContent).toContain('Faire un dépôt');
    expect(buttons[1].nativeElement.textContent).toContain('Faire un retrait');
  });

  it('should display empty message when no transactions', () => {
    component.dataSource.data = [];
    fixture.detectChanges();

    const emptyMsg = fixture.debugElement.query(By.css('.empty'));
    expect(emptyMsg).toBeTruthy();
    expect(emptyMsg.nativeElement.textContent).toContain('Aucune transaction');
  });
});
