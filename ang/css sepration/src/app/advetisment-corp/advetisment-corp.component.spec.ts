import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AdvetismentCorpComponent } from './advetisment-corp.component';

describe('AdvetismentCorpComponent', () => {
  let component: AdvetismentCorpComponent;
  let fixture: ComponentFixture<AdvetismentCorpComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ AdvetismentCorpComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(AdvetismentCorpComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
