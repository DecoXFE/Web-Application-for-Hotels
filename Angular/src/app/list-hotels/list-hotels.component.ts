import { NgFor } from '@angular/common';
import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { Address, Hotel } from '../shared/hotel.model';
import { ClienteApiRestService } from '../shared/client-api-rest.service';
import { RouterLink } from '@angular/router';
import { Room, RoomType } from '../shared/room.model';

@Component({
  selector: 'app-list-hotels',
  standalone: true,
  imports: [NgFor, FormsModule, RouterLink],
  templateUrl: './list-hotels.component.html',
  styleUrl: './list-hotels.component.css'
})
export class ListHotelsComponent {
  hotels!: Hotel[];
  selectedHotelId: Number = -1;

  newHotel = {
    name: '',
    streetKind: '',
    streetName: '',
    number: 0,
    postCode: '',
    otherInfo: '',
    rooms: [] as Room[]
  };

  constructor(private clientApiRest: ClienteApiRestService) { }

  ngOnInit() {
    this.getHotels();
  }

  getHotels() {
    this.clientApiRest.getHotels().subscribe({
      next: (response) => {
        this.hotels = response.body;
      },
      error: (error) => {
        console.error("Error al conseguir los hoteles:", error);
      }
    });
  }

  openDeleteModal(id: Number){
    this.selectedHotelId = id;
  }

  onSubmit(){

    const address: Address = {
      streetKind: this.newHotel.streetKind,
      streetName: this.newHotel.streetName,
      number: this.newHotel.number,
      postCode: this.newHotel.postCode,
      otherInfo: this.newHotel.otherInfo
    };

    const hotel: Hotel = {
      id: 0,
      name: this.newHotel.name,
      address: address,
      roomCollection: this.newHotel.rooms
    };

    this.clientApiRest.createHotel(hotel).subscribe({
      next: (response) => {
        this.resetForm();
        // ! Revisar
        window.location.reload();
        
      },
      error: (error) => {
        console.error("Error al crear el hotel:", error);
      }
    })
  }

  deleteHotel() {
    this.clientApiRest.deleteHotel(this.selectedHotelId).subscribe({
      next: (response) => {
        this.hotels = this.hotels.filter(hotel => hotel.id !== this.selectedHotelId);
      },
      error: (error) => {
        console.error("Error al borrar el hotel:", error);
      }
    })
  }

  addRoom() {
    this.newHotel.rooms.push({
      id : (this.newHotel.rooms.length+1),
      roomNumber: (this.newHotel.rooms.length + 1).toString(),
      roomType: RoomType.SINGLE,
      available: false
    });
  }

  removeRoom(i: number){
    this.newHotel.rooms.splice(i, 1);
  }

  onCancel(){
    this.resetForm();
  }

  resetForm(){
    this.newHotel = {
      name: '',
      streetKind: '',
      streetName: '',
      number: 0,
      postCode: '',
      otherInfo: '',
      rooms: [] as Room[]
    };
  }
}