import { ComponentFixture, TestBed } from '@angular/core/testing';
import { AccountStatementComponent } from './account-statement.component';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { By } from '@angular/platform-browser';

describe('AccountStatementComponent', () => {
  let component: AccountStatementComponent;
  let fixture: ComponentFixture<AccountStatementComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [AccountStatementComponent],
      imports: [HttpClientTestingModule]
    }).compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(AccountStatementComponent);
    component = fixture.componentInstance;

    component.accountId = 'abc123';
    component.balance = 1200;
    component.transactions = [
      { type: 'DEPOSIT', amount: 500, date: new Date() },
      { type: 'WITHDRAWAL', amount: 200, date: new Date() }
    ];

    fixture.detectChanges();
  });

  it('should display account ID and balance', () => {
    const compiled = fixture.nativeElement;
    expect(compiled.textContent).toContain('abc123');
    expect(compiled.textContent).toContain('1200');
  });

  it('should render a list of transactions', () => {
    const txList = fixture.debugElement.queryAll(By.css('.transaction-list li'));
    expect(txList.length).toBe(2);
  });

  it('should display a message when there are no transactions', () => {
    component.transactions = [];
    fixture.detectChanges();

    const msg = fixture.debugElement.query(By.css('.no-transaction'));
    expect(msg.nativeElement.textContent).toContain('Aucune transaction');
  });
});
