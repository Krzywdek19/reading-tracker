import { Component } from '@angular/core';
import { AuthService } from '../auth/auth.service';
import { NavComponent } from './nav/nav.component';
import { CommonModule } from '@angular/common';
import { Router } from '@angular/router';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrl: './header.component.scss',
  imports: [CommonModule, NavComponent]
})
export class HeaderComponent {
  isAuthenticated: boolean = false;

  constructor(private authService: AuthService, private router: Router) {}

  ngOnInit(): void {
    this.authService.isLoggedIn$.subscribe(
      (loggedIn) => {
        this.isAuthenticated = loggedIn;
      }
    );
  }

  logout(): void {
    this.authService.logout();
    this.router.navigate(['/auth/login']);
  }
}