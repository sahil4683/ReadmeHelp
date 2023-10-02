import { ComponentFixture, TestBed } from '@angular/core/testing';

import { BgCompComponent } from './bg-comp.component';

describe('BgCompComponent', () => {
  let component: BgCompComponent;
  let fixture: ComponentFixture<BgCompComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ BgCompComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(BgCompComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
