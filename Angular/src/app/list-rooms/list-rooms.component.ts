import { NgClass, NgFor, NgIf } from '@angular/common';
import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { Room } from '../shared/room.model';
import { ClientApiRestService } from '../shared/client-api-rest.service';
import { ActivatedRoute } from '@angular/router';

@Component({
  selector: 'app-list-rooms',
  standalone: true,
  imports: [NgFor, FormsModule, NgClass, NgIf],
  templateUrl: './list-rooms.component.html',
  styleUrl: './list-rooms.component.css'
})
export class ListRoomsComponent {
  rooms!: Room[];
  hotelId!: Number;

  constructor(private clientApiRest: ClientApiRestService, private route: ActivatedRoute) { }

  ngOnInit() {
    this.route.paramMap.subscribe(params => {
      this.hotelId = +params.get('id')!;
      this.getRooms(this.hotelId);
    });
  }

  getRooms(id: Number) {
    this.clientApiRest.getRooms(id).subscribe({
      next: (response) => {
        this.rooms = response.body;
      },
      error: (error) => {
        console.error("Error getting rooms:", error);
      }
    });
  }

  changeAvailable(idr: Number){
    this.clientApiRest.editRoomAvailability(this.hotelId, idr).subscribe({
      next: (response) => {
        // ! revisar
        window.location.reload();
      },
      error: (error) => {
        console.error("Error changing availability:", error);
      }
    });
  }
}
