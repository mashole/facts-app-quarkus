import { ComponentFixture, TestBed } from '@angular/core/testing';

import { RandomFactComponent } from './random-fact.component';

describe('RandomFactComponent', () => {
  let component: RandomFactComponent;
  let fixture: ComponentFixture<RandomFactComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [RandomFactComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(RandomFactComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
