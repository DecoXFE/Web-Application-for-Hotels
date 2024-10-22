import { Component } from '@angular/core';
import { ClienteApiRestService } from '../shared/client-api-rest.service';
import { FormsModule } from '@angular/forms';
import { User } from '../shared/user.model';
import { NgIf } from '@angular/common';

@Component({
  selector: 'app-form-booking',
  standalone: true,
  imports: [FormsModule, NgIf],
  templateUrl: './form-booking.component.html',
  styleUrl: './form-booking.component.css'
})
export class FormBookingComponent{
  users!:User[]
  errorMessage = ""



  constructor(private clientApiRest: ClienteApiRestService){}

  onNewBooking(){
    
  }


}
