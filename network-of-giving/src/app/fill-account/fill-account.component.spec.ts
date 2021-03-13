import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { FillAccountComponent } from './fill-account.component';

describe('FillAccountComponent', () => {
  let component: FillAccountComponent;
  let fixture: ComponentFixture<FillAccountComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ FillAccountComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(FillAccountComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
