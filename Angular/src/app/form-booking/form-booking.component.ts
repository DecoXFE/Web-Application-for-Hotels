import { Component } from '@angular/core';
import { ClientApiRestService } from '../shared/client-api-rest.service';
import { FormsModule } from '@angular/forms';
import { NgIf } from '@angular/common';
import { Booking } from '../shared/booking.model';
import { RoomType } from '../shared/room.model';

@Component({
  selector: 'app-form-booking',
  standalone: true,
  imports: [FormsModule, NgIf],
  templateUrl: './form-booking.component.html',
  styleUrl: './form-booking.component.css'
})
export class FormBookingComponent{
  errorMessage = ""
  userEmail = ""
  successMessage = ""

  allBookings!: Booking[]

  roomType = RoomType.SINGLE
  booking: Booking = {
    id: 0,
    startDate: null,
    endDate: null
  }


  constructor(private clientApiRest: ClientApiRestService){}

  onNewBooking(){
    console.log("Entering onNewBooking")
    this.clientApiRest.createBooking(this.booking,this.userEmail,this.roomType).subscribe({
      next: (response)=>{
        console.log("Booking created: ",response)
        this.resetForm()
      },
      error: (err)=>{
        console.error("Error creating booking:", err)
        this.errorMessage = err.error
      }
    })

    }

    resetForm(){
      this.userEmail = ""
      this.booking = {
        id: 0,
        startDate: null,
        endDate: null
      }
      this.errorMessage = ""
      this.successMessage = "Booking successfully created"
    }
  }

