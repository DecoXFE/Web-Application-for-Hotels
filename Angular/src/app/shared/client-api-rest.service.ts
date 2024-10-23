import { HttpClient, HttpResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { User } from './user.model';
import { Observable } from 'rxjs';
import { Hotel } from './hotel.model';
import { Room } from './room.model';

@Injectable({
  providedIn: 'root'
})
export class ClienteApiRestService {

  private static readonly BASE_URI_USERS = "http://localhost:8080/RoomBooking/users";
  private static readonly BASE_URI_HOTELS = "http://localhost:8080/RoomBooking/hotels";

  constructor(private http: HttpClient) { }

  /*
  * Creates a new user
  */
  createUser(user : User) : Observable<HttpResponse<any>>{
    console.log("Entrando en createUser");
    let url = ClienteApiRestService.BASE_URI_USERS;
    return this.http.post(url, user, {observe: 'response', responseType:'text'})
  }

  /*
  * Gets all Users
  */
  getUsers() : Observable<HttpResponse<any>>{
    console.log("Entrando en getUsers");
    let url = ClienteApiRestService.BASE_URI_USERS;
    return this.http.get<User[]>(url, {observe: 'response'});
  }

  /*
  * Gets all Hotels
  */
    getHotels() : Observable<HttpResponse<any>>{
      console.log("Entrando en getHotels");
      let url = ClienteApiRestService.BASE_URI_HOTELS;
      return this.http.get<Hotel[]>(url, {observe: 'response'});
    }

  /*
  * Deletes a hotel
  */
    deleteHotel(id : Number) : Observable<HttpResponse<any>>{
      console.log("Entrando en deleteHotel");
      let url = ClienteApiRestService.BASE_URI_HOTELS + "/" + id;
      return this.http.delete(url, { observe: 'response', responseType: 'text'});
    }

    /*
    * Gets all avalible rooms of a hotel
    */
    getRooms(id : Number) : Observable<HttpResponse<any>>{
      console.log("Entrando en getHotels");
      let url = ClienteApiRestService.BASE_URI_HOTELS + "/" + id + "/rooms";
      return this.http.get<Room[]>(url, {observe: 'response'});
    }
}
