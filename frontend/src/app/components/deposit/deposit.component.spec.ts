import { ComponentFixture, TestBed } from '@angular/core/testing';
import { DepositComponent } from './deposit.component';
import { AccountService } from '../../services/account.service';
import { ActivatedRoute, Router } from '@angular/router';
import { of } from 'rxjs';

describe('DepositComponent', () => {
  let component: DepositComponent;
  let fixture: ComponentFixture<DepositComponent>;
  let mockAccountService: jasmine.SpyObj<AccountService>;
  let mockRouter: jasmine.SpyObj<Router>;

  beforeEach(async () => {
    mockAccountService = jasmine.createSpyObj('AccountService', ['deposit']);
    mockRouter = jasmine.createSpyObj('Router', ['navigate']);

    await TestBed.configureTestingModule({
      declarations: [DepositComponent],
      providers: [
        { provide: AccountService, useValue: mockAccountService },
        { provide: Router, useValue: mockRouter },
        {
          provide: ActivatedRoute,
          useValue: {
            snapshot: { paramMap: new Map([['id', 'abc123']]) }
          }
        }
      ]
    }).compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(DepositComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create the component', () => {
    expect(component).toBeTruthy();
  });

  it('should call deposit and navigate after deposit', () => {
    component.amount = 100;
    mockAccountService.deposit.and.returnValue(of());

    component.onDeposit();

    expect(mockAccountService.deposit).toHaveBeenCalledWith('abc123', 100);
// Optionally test navigation after timeout if needed
  });
});
