import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { ReactiveFormsModule } from '@angular/forms';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { Router } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { MatSnackBar, MatSnackBarModule } from '@angular/material/snack-bar';
import { By } from '@angular/platform-browser';
import { of } from 'rxjs';

import { CreateAccountComponent } from './create-account.component';
import {AccountService} from "../../services/account.service";
import {BrowserAnimationsModule} from "@angular/platform-browser/animations";


describe('CreateAccountComponent', () => {
  let component: CreateAccountComponent;
  let fixture: ComponentFixture<CreateAccountComponent>;
  let router: Router;
  let snackBar: MatSnackBar;
  let accountService: AccountServiceMock;
  class AccountServiceMock {createAccount = jasmine.createSpy('createAccount');
  }

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [CreateAccountComponent],
      imports: [
        ReactiveFormsModule,
        HttpClientTestingModule,
        RouterTestingModule.withRoutes([]),
        MatSnackBarModule,
        BrowserAnimationsModule
      ],
      providers: [
        { provide: Router, useValue: jasmine.createSpyObj('Router', ['navigate']) },
        { provide: AccountService, useClass: AccountServiceMock }
      ]
    }).compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(CreateAccountComponent);
    component = fixture.componentInstance;
    router = TestBed.inject(Router);
    snackBar = TestBed.inject(MatSnackBar);
    accountService = TestBed.inject(AccountService) as unknown as AccountServiceMock;

    fixture.detectChanges();
  });

  it('should create and show the submit button label', () => {
    expect(component).toBeTruthy();
    const btn = fixture.debugElement.query(By.css('button[type="submit"]'));
    expect(btn.nativeElement.textContent).toContain('Créer un compte');
  });

  it('should have invalid form initially and keep button disabled', () => {
    expect(component.form.invalid).toBeTrue();
    const btn: HTMLButtonElement = fixture.debugElement.query(By.css('button[type="submit"]')).nativeElement;
    expect(btn.disabled).toBeTrue();
  });

  it('should enable button when form becomes valid', () => {
    component.form.setValue({ email: 'john@doe.com', password: 'secret1' });
    fixture.detectChanges();

    const btn: HTMLButtonElement = fixture.debugElement.query(By.css('button[type="submit"]')).nativeElement;
    expect(component.form.valid).toBeTrue();
    expect(btn.disabled).toBeFalse();
  });

  it('should call service, open snackbar and navigate when Location header is present', fakeAsync(() => {
    const headers = new HttpHeaders({ Location: '/accounts/abc-123' });
    const response = new HttpResponse({ status: 201, headers });
    accountService.createAccount.and.returnValue(of(response));

    const snackSpy = spyOn(snackBar, 'open').and.callThrough();
    const navSpy = (router.navigate as jasmine.Spy).and.resolveTo(true);

    component.form.setValue({ email: 'john@doe.com', password: 'secret1' });

    component.onCreateAccount();
    tick(1600);

    expect(accountService.createAccount).toHaveBeenCalledWith({
      email: 'john@doe.com',
      password: 'secret1'
    });
    expect(component.accountId).toBe('abc-123');
    expect(localStorage.getItem('accountId')).toBe('abc-123');
    expect(snackSpy).toHaveBeenCalled();
    expect(navSpy).toHaveBeenCalledWith(['/statement', 'abc-123']);
  }));

  it('should not navigate when Location header is missing', fakeAsync(() => {
    const response = new HttpResponse({ status: 201 });
    accountService.createAccount.and.returnValue(of(response));

    const navSpy = (router.navigate as jasmine.Spy).and.resolveTo(true);

    component.form.setValue({ email: 'john@doe.com', password: 'secret1' });
    component.onCreateAccount();
    tick(1600);

    expect(component.accountId).toBeUndefined();
    expect(navSpy).not.toHaveBeenCalled();
  }));
});
