import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AccountYearManagementComponent } from './account-year-management.component';

describe('AccountYearManagementComponent', () => {
  let component: AccountYearManagementComponent;
  let fixture: ComponentFixture<AccountYearManagementComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ AccountYearManagementComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(AccountYearManagementComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
