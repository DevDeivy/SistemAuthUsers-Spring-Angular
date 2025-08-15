import { CommonModule } from '@angular/common';
import { Component, inject } from '@angular/core';
import { FormControl, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { RouterLink } from '@angular/router';
import { RegexValidationsService } from '../../services/regex-validations.service';

@Component({
  selector: 'app-login',
  imports: [ReactiveFormsModule, CommonModule, RouterLink],
  templateUrl: './login.component.html',
  styleUrl: './login.component.css'
})
export class LoginComponent {

  private _regexValidations = inject(RegexValidationsService);

  login = new FormGroup({
    email: new FormControl('', [Validators.required, Validators.pattern(this._regexValidations.email), Validators.email]),
    password: new FormControl('', [Validators.required, Validators.pattern(this._regexValidations.password)])
  })

}
