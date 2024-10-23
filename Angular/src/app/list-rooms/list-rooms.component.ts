import { NgFor } from '@angular/common';
import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { Room } from '../shared/room.model';
import { ClienteApiRestService } from '../shared/client-api-rest.service';
import { ActivatedRoute } from '@angular/router';

@Component({
  selector: 'app-list-rooms',
  standalone: true,
  imports: [NgFor, FormsModule],
  templateUrl: './list-rooms.component.html',
  styleUrl: './list-rooms.component.css'
})
export class ListRoomsComponent {
  rooms!: Room[];

  constructor(private clientApiRest: ClienteApiRestService, private route: ActivatedRoute) { }

  ngOnInit() {
    this.route.paramMap.subscribe(params => {
      this.getRooms(+params.get('id')!);
    });
  }

  getRooms(id: Number) {
    this.clientApiRest.getRooms(id).subscribe({
      next: (response) => {
        this.rooms = response.body;
      },
      error: (error) => {
        console.error("Error al conseguir las habitaciones:", error);
      }
    });
  }
}
