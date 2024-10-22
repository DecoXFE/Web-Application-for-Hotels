import { HttpClient, HttpResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { User } from './user.model';
import { Observable } from 'rxjs';
import { Hotel } from './hotel.model';
import { Booking } from './booking.model';
import { RoomType } from './room.model';

@Injectable({
  providedIn: 'root'
})
export class ClienteApiRestService {

  private static readonly BASE_URI_USERS = "http://localhost:8080/RoomBooking/users";
  private static readonly BASE_URI_HOTELS = "http://localhost:8080/RoomBooking/hotels";
  private static readonly BASE_URI_BOOKINGS = "http://localhost:8080/RoomBooking/bookings";


  constructor(private http: HttpClient) { }

  /*
  * Creates a new user
  */
  createUser(user: User): Observable<HttpResponse<any>> {
    console.log("Entrando en createUser");
    let url = ClienteApiRestService.BASE_URI_USERS;
    return this.http.post(url, user, { observe: 'response', responseType: 'text' })
  }

  /*
  * Gets all Users
  */
  getUsers(): Observable<HttpResponse<any>> {
    console.log("Entrando en getUsers");
    let url = ClienteApiRestService.BASE_URI_USERS;
    return this.http.get<User[]>(url, { observe: 'response' });
  }

  /*
  * Gets all Hotels
  */
  getHotels(): Observable<HttpResponse<any>> {
    console.log("Entrando en getHotels");
    let url = ClienteApiRestService.BASE_URI_HOTELS;
    return this.http.get<Hotel[]>(url, { observe: 'response' });
  }

  /*
  * Delete an hotel
  */
  deleteHotel(id: Number): Observable<HttpResponse<any>> {
    console.log("Entrando en deleteHotel");
    let url = ClienteApiRestService.BASE_URI_HOTELS + "/" + id;
    return this.http.delete(url, { observe: 'response', responseType: 'text' });
  }

  /*
  * Create new booking
  */
  // ? Insert userId and roomId in booking model
  createBooking(booking: Booking,
    email: String,
    roomType: RoomType): Observable<HttpResponse<any>> {
    console.log("Entering createBooking")
    let url = ClienteApiRestService.BASE_URI_BOOKINGS + "?userEmail=" + email + "&roomType=" + roomType
    return this.http.post(url, booking, { observe: 'response', responseType: 'text' })
  }

}
