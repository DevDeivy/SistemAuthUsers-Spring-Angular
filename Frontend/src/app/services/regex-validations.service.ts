import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class RegexValidationsService {

  public email: RegExp = /^[a-z0-9._%+-]+@[a-z0-9.-]+\.[a-z]{2,4}$/;
  public letters: RegExp = /^[a-zA-Z]+$/;
  public number: RegExp = /^[0-9]$/;
  public simbols: RegExp = /^[[^a-zA-Z0-9\s]$/;
  public password: RegExp = /^(?=.[A-Z])(?=.[a-z])(?=.*\\d).{8,}$/;

}
