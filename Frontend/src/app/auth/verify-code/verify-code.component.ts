import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';
import { FormControl, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { ActivatedRoute, RouterLink } from '@angular/router';

@Component({
  selector: 'app-verify-code',
  imports: [ReactiveFormsModule, RouterLink, CommonModule],
  templateUrl: './verify-code.component.html',
  styleUrl: './verify-code.component.css'
})
export class VerifyCodeComponent {
  
  params?: string;

  form = new FormGroup({
    code: new FormControl('', [Validators.required, Validators.minLength(6), Validators.maxLength(6)])
  })

  constructor(private param: ActivatedRoute){}

  ngOnInit():void{
    this.params = this.param.snapshot.paramMap.get('params')!;
  }

  

}
