import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AuthorsSectionComponent } from './authors-section.component';

describe('AuthorsSectionComponent', () => {
  let component: AuthorsSectionComponent;
  let fixture: ComponentFixture<AuthorsSectionComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [AuthorsSectionComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(AuthorsSectionComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
