import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ListUsersComponentComponent } from './list-users-component.component';

describe('ListUsersComponentComponent', () => {
  let component: ListUsersComponentComponent;
  let fixture: ComponentFixture<ListUsersComponentComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ListUsersComponentComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ListUsersComponentComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
