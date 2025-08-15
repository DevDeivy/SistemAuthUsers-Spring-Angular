import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { User } from '../entities/User';

@Injectable({
  providedIn: 'root'
})
export class UserService {

  private api: string = "http://localhost:8080/api/auth"

  constructor(private http: HttpClient) {}


  createUser(user: User): Observable<Object>{
    return this.http.post(`${this.api}/create`, user)
  }
}
