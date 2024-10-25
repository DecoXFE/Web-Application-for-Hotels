import { NgFor, NgIf } from '@angular/common';
import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { Address, Hotel } from '../shared/hotel.model';
import { ClientApiRestService } from '../shared/client-api-rest.service';
import { RouterLink } from '@angular/router';
import { Room, RoomType } from '../shared/room.model';

@Component({
  selector: 'app-list-hotels',
  standalone: true,
  imports: [NgFor, FormsModule, RouterLink, NgIf],
  templateUrl: './list-hotels.component.html',
  styleUrl: './list-hotels.component.css'
})
export class ListHotelsComponent {
  hotels!: Hotel[];
  selectedHotelId: Number = -1;

  errorExistMessage = "";

  newHotel = {
    name: '',
    streetKind: '',
    streetName: '',
    number: 0,
    postCode: '',
    otherInfo: '',
    rooms: [] as Room[]
  };

  constructor(private clientApiRest: ClientApiRestService) { }

  ngOnInit() {
    this.getHotels();
  }

  getHotels() {
    this.clientApiRest.getHotels().subscribe({
      next: (response) => {
        this.hotels = response.body;
      },
      error: (error) => {
        console.error("Error getting hotels:", error);
      }
    });
  }

  openDeleteModal(id: Number) {
    this.selectedHotelId = id;
  }

  onSubmit() {
    
    if (isNaN(this.newHotel.number)) {
      this.errorExistMessage = "Hotel number must be a valid number"
    } else {
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
          console.error("Error creating hotel:", error)
          this.errorExistMessage = error.error
        }
      })
    }
  }

  deleteHotel() {
    this.clientApiRest.deleteHotel(this.selectedHotelId).subscribe({
      next: (response) => {
        // ! Hacer esto con routing
        this.hotels = this.hotels.filter(hotel => hotel.id !== this.selectedHotelId);
      },
      error: (error) => {
        console.error("Error deleting hotel:", error)
      }
    })
  }

  // ! Error al añadir habitaciones
  addRoom() {
    const maxId = this.newHotel.rooms.length > 0 
    ? Math.max(...this.newHotel.rooms.map(room => room.id.valueOf())) : 0;
    this.newHotel.rooms.push({
      id: maxId+1,
      roomNumber: (maxId+1).toString(),
      roomType: RoomType.SINGLE,
      available: false
    });
  }

  removeRoom(i: number) {
    this.newHotel.rooms.splice(i, 1);
  }

  onCancel() {
    this.resetForm();
  }

  resetForm() {
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