import { ComponentFixture, TestBed } from '@angular/core/testing';

import { FgCompComponent } from './fg-comp.component';

describe('FgCompComponent', () => {
  let component: FgCompComponent;
  let fixture: ComponentFixture<FgCompComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ FgCompComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(FgCompComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
