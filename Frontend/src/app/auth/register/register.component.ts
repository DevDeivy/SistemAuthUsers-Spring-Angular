import { CommonModule } from '@angular/common';
import { Component, inject, OnInit } from '@angular/core';
import { FormControl, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { Router, RouterLink } from '@angular/router';
import { UserService } from '../../services/user.service';
import { User } from '../../entities/User';
import { RegexValidationsService } from '../../services/regex-validations.service';

@Component({
  selector: 'app-register',
  imports: [ReactiveFormsModule, CommonModule, RouterLink],
  templateUrl: './register.component.html',
  styleUrl: './register.component.css'
})
export class RegisterComponent implements OnInit {

  public user!: User;
  public registered: boolean = false;
  private _registerUser = inject(UserService);
  private _regexValidations = inject(RegexValidationsService)
  register!: FormGroup;
  
  constructor(private rout: Router){

  }
  
  ngOnInit(): void{
    this.register = new FormGroup({
      email: new FormControl('', [Validators.required, Validators.pattern(this._regexValidations.email), Validators.email]),
      password: new FormControl('', [Validators.required, Validators.pattern(this._regexValidations.password)]),
      name: new FormControl('', [Validators.required, Validators.pattern(this._regexValidations.letters)]),
      lastname: new FormControl('', [Validators.required, Validators.pattern(this._regexValidations.letters)]),
      confirm: new FormControl('', Validators.required)
    });
  }


  confirmPassword(){
    return this.register?.get('password')?.value !== this.register?.get('confirm')?.value;
  }

  registerUserNew(){
    const {email, password, lastname, name} = this.register.value;
    const newUser: User = {
      email,
      password,
      firstName: name,
      lastName: lastname
    }

    if(this.register?.invalid){
      this.register.markAllAsTouched();
      return
    }

    this._registerUser.createUser(newUser).subscribe(data => {
      console.log(data)  
      this.registered = true;
      this.rout.navigateByUrl('/auth/login')
    }, error => {
      console.log("error", error)
    })
  }
}
