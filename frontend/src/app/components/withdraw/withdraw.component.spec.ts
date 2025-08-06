import { ComponentFixture, TestBed } from '@angular/core/testing';
import { WithdrawComponent } from './withdraw.component';
import { FormsModule } from '@angular/forms';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRoute } from '@angular/router';
import { Router } from '@angular/router';
import {RouterTestingModule} from "@angular/router/testing";

describe('WithdrawComponent', () => {
  let component: WithdrawComponent;
  let fixture: ComponentFixture<WithdrawComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [WithdrawComponent],
      imports: [FormsModule, HttpClientTestingModule, RouterTestingModule],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: {
            snapshot: { paramMap: { get: () => 'test-account-id' } }
          }
        },
        {
          provide: Router,
          useValue: {
            snapshot: { navigate: jasmine.createSpy('navigate')}
          }
        }
      ]
    }).compileComponents();

    fixture = TestBed.createComponent(WithdrawComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create component', () => {
    expect(component).toBeTruthy();
  });

  it('should update amount value', () => {
    component.amount = 100;
    expect(component.amount).toBe(100);
  });

  it('should show success message after withdrawal', () => {
    component.successMessage = 'Retrait effectué avec succès !';
    fixture.detectChanges();
    const compiled = fixture.nativeElement as HTMLElement;
    expect(compiled.querySelector('.success-message')?.textContent).toContain('Retrait effectué');
  });
});

