import { HttpClient, HttpResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { User } from './user.model';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class ClienteApiRestService {

  private static readonly BASE_URI_USERS = "http://localhost:8080/RoomBooking/users";

  constructor(private http: HttpClient) { }

  /*
  * Creates a new user
  */
  createUser(user : User) : Observable<HttpResponse<any>>{
    console.log("Entrando en createUser");
    let url = ClienteApiRestService.BASE_URI_USERS;
    return this.http.post(url,user,{observe: 'response', responseType:'text'})
  }

  /*
  * Gets all Users
  */
  getUsers() : Observable<HttpResponse<any>>{
    console.log("Entrando en createUser");
    let url = ClienteApiRestService.BASE_URI_USERS;
    return this.http.get<User[]>(url, {observe: 'response'});
  }

}
