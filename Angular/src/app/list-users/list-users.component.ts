import { Component, OnInit } from '@angular/core';
import { NgFor } from '@angular/common';
import { User } from '../shared/user.model';
import { ClientApiRestService } from '../shared/client-api-rest.service';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-list-users-component',
  standalone: true,
  imports: [NgFor, FormsModule],
  templateUrl: './list-users.component.html',
  styleUrl: './list-users.component.css'
})

export class ListUsersComponent implements OnInit {
  users!: User[];
  statusFilter = "";


  constructor(private clientApiRest: ClientApiRestService) { }

  ngOnInit() {
    this.getUsers();
  }

  getUsers(){
    this.clientApiRest.getUsers().subscribe({
      next: (response) => {
          this.users = response.body;
          if(this.statusFilter!=""){
          this.users = this.users.filter(user => user.status==this.statusFilter);
      }
      },
      error: (error) => {
          console.error("Error getting users:", error);
      }
  });
  }
}

