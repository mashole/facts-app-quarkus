import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CachedFactsComponent } from './cached-facts.component';

describe('FactListComponent', () => {
  let component: CachedFactsComponent;
  let fixture: ComponentFixture<CachedFactsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [CachedFactsComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(CachedFactsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
