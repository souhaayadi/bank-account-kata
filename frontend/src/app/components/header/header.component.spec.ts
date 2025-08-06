import { ComponentFixture, TestBed } from '@angular/core/testing';
import { CommonModule } from '@angular/common'; // pour *ngIf
import { Router } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';

import { HeaderComponent } from './header.component';

describe('HeaderComponent', () => {
  let fixture: ComponentFixture<HeaderComponent>;
  let component: HeaderComponent;
  let router: Router;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [HeaderComponent],
      imports: [
        CommonModule,
        RouterTestingModule.withRoutes([])
      ]
    }).compileComponents();

    localStorage.setItem('accountId', 'abc123');

    fixture = TestBed.createComponent(HeaderComponent);
    component = fixture.componentInstance;
    router = TestBed.inject(Router);

    fixture.detectChanges();
  });

  afterEach(() => {
    localStorage.clear();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should show the logout button when user is logged in', () => {
    const btn: HTMLButtonElement | null = fixture.nativeElement.querySelector('button');
    expect(btn).not.toBeNull();
    expect(btn!.textContent).toContain('Déconnexion');
  });

  it('should hide the logout button when user is not logged in', () => {
    localStorage.removeItem('accountId');
    fixture.detectChanges();

    const btn: HTMLButtonElement | null = fixture.nativeElement.querySelector('button');
    expect(btn).toBeNull();
  });

  it('should clear storage and navigate to /create-account on logout', async () => {
    const navSpy = spyOn(router, 'navigate').and.resolveTo(true);

    const btn: HTMLButtonElement = fixture.nativeElement.querySelector('button');
    btn.click();
    fixture.detectChanges();

    expect(localStorage.getItem('accountId')).toBeNull();
    expect(navSpy).toHaveBeenCalledWith(['/create-account']);
  });
});
