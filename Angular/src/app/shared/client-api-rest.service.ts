import { HttpClient, HttpResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { User } from './user.model';
import { Observable } from 'rxjs';
import { Hotel } from './hotel.model';
import { Room } from './room.model';
import { Booking } from './booking.model';
import { RoomType } from './room.model';

@Injectable({
  providedIn: 'root'
})
export class ClientApiRestService {

  private static readonly BASE_URI_USERS = "http://localhost:8080/RoomBooking/users";
  private static readonly BASE_URI_HOTELS = "http://localhost:8080/RoomBooking/hotels";
  private static readonly BASE_URI_BOOKINGS = "http://localhost:8080/RoomBooking/bookings";


  constructor(private http: HttpClient) { }

  /*
  * Creates a new user
  */
  createUser(user: User): Observable<HttpResponse<any>> {
    console.log("Entering createUser");
    let url = ClientApiRestService.BASE_URI_USERS;
    return this.http.post(url, user, { observe: 'response', responseType: 'text' })
  }

  /*
  * Gets all Users
  */
  getUsers(): Observable<HttpResponse<any>> {
    console.log("Entering getUsers");
    let url = ClientApiRestService.BASE_URI_USERS;
    return this.http.get<User[]>(url, { observe: 'response' });
  }

  /*
  * Gets all Hotels
  */
  getHotels(): Observable<HttpResponse<any>> {
    console.log("Entering getHotels");
    let url = ClientApiRestService.BASE_URI_HOTELS;
    return this.http.get<Hotel[]>(url, { observe: 'response' });
  }

  /*
  * Delete an hotel
  */
  deleteHotel(id: Number): Observable<HttpResponse<any>> {
    console.log("Entering deleteHotel");
    let url = ClientApiRestService.BASE_URI_HOTELS + "/" + id;
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
    let url = ClientApiRestService.BASE_URI_BOOKINGS + "?userEmail=" + email + "&roomType=" + roomType
    return this.http.post(url, booking, { observe: 'response', responseType: 'text' })
  }

    /*
    * Gets all avalible rooms of a hotel
    */
    getRooms(id : Number) : Observable<HttpResponse<any>>{
      console.log("Entering getHotels");
      let url = ClientApiRestService.BASE_URI_HOTELS + "/" + id + "/rooms?available=false";
      return this.http.get<Room[]>(url, {observe: 'response'});
    }

    /*
    * Creates a new hotel
    */
    createHotel(hotel : Hotel) : Observable<HttpResponse<any>>{
      console.log("Entering createHotel");
      let url = ClientApiRestService.BASE_URI_HOTELS;
      return this.http.post(url, hotel, {observe: 'response', responseType:'text'})
    }

    /*
    * Edits the availability of a room
    */
    editRoomAvailability(idh : Number, idr : Number) : Observable<any>{
      console.log("Entering editRoomAvailability");
      let url = ClientApiRestService.BASE_URI_HOTELS + "/" + idh + "/rooms/" + idr;
      return this.http.patch(url, {observe: 'response', responseType:'text'});
    }
}
