import { Component } from '@angular/core';
import { RouterOutlet, RouterModule } from '@angular/router';
import { ClienteApiRestService } from './shared/client-api-rest.service';
import { Status, User } from './shared/user.model';
import { FormsModule } from '@angular/forms';
import { NgIf } from '@angular/common';

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [RouterOutlet, FormsModule, NgIf, RouterModule],
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css'],
})

//TODO ADD ROUTING
export class AppComponent {
  user: User = {
    name: "",
    email: "",
    status: Status.NOBOOKINGS
  };
  emailExistsMessage: string = "";

  constructor(private clientApiRest: ClienteApiRestService) { }

  onSignIn() {
    console.log("Entrando en submit");
    this.clientApiRest.createUser(this.user).subscribe({
      next: (response) => {
          console.log("Usuario creado con éxito:", response);
          this.resetForm();
          // ! Revisar
          window.location.reload();
      },
      error: (error) => {
          console.error("Error al crear el usuario:", error);
          this.emailExistsMessage = error.error;
      }
  });

  }
  onCancelSignIn() {  
    this.resetForm();
  }

  resetForm() {
    this.emailExistsMessage = "";
    this.user = {
      name: '',
      email: '',
      status: Status.NOBOOKINGS
    }
  }

}

