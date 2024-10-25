import { Component } from '@angular/core';
import { RouterOutlet, RouterModule } from '@angular/router';
import { ClientApiRestService } from './shared/client-api-rest.service';
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
  title = "SmartBooking"
  user: User = {
    name: "",
    email: "",
    status: Status.NOBOOKINGS
  };
  emailExistsMessage: string = "";

  constructor(private clientApiRest: ClientApiRestService) { }

  onSignIn() {
    console.log("Entering submit");
    this.clientApiRest.createUser(this.user).subscribe({
      next: (response) => {
          console.log("User created:", response);
          this.resetForm();
          // ! Revisar
          window.location.reload();
      },
      error: (error) => {
          console.error("Error creating user:", error);
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

