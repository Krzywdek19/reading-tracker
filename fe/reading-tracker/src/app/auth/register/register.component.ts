import { Component } from '@angular/core';
import { AuthService } from '../auth.service';
import { Router } from '@angular/router';
import { CommonModule } from '@angular/common';
import { ReactiveFormsModule, FormBuilder, FormGroup } from '@angular/forms';
import { RouterLink } from '@angular/router';

@Component({
  selector: 'app-register',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule, RouterLink],
  templateUrl: './register.component.html'
})
export class RegisterComponent {
  form: FormGroup;

  constructor(private fb: FormBuilder, private auth: AuthService, private router: Router) {
    this.form = this.fb.group({
      email: [''],
      password: [''],
      confirmPassword: ['']
    });
  }

  onSubmit(): void {
    this.auth.register(this.form.value).subscribe({
      next: (response) => {
        this.auth.saveToken(response.token);
        this.router.navigate(['/']); 
      },
      error: (err) => {
        alert('Register error!');
      }
    });
  }
}