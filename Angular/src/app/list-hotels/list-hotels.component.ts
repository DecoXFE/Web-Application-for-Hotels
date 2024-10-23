import { NgFor } from '@angular/common';
import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { Hotel } from '../shared/hotel.model';
import { ClienteApiRestService } from '../shared/client-api-rest.service';
import { RouterLink } from '@angular/router';

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
}