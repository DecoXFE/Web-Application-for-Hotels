import { Component, OnInit } from '@angular/core';
import { NgFor } from '@angular/common';
import { User } from '../shared/user.model';
import { ClienteApiRestService } from '../shared/client-api-rest.service';

@Component({
  selector: 'app-list-users-component',
  standalone: true,
  imports: [NgFor],
  templateUrl: './list-users-component.component.html',
  styleUrl: './list-users-component.component.css'
})
export class ListUsersComponentComponent implements OnInit {
  users!: User[];


  constructor(private clientApiRest: ClienteApiRestService) { }

  ngOnInit() {
    this.getUsers();
  }

  getUsers(){
    this.clientApiRest.getUsers().subscribe({
      next: (response) => {
          this.users = response.body;
      },
      error: (error) => {
          console.error("Error al conseguir los usuarios:", error);
      }
  });
  }
}
