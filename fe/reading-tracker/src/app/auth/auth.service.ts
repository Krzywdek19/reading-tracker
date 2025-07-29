import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { LoginRequest } from "./model/LoginRequest";
import { Observable } from "rxjs";
import { JwtResponse } from "./model/JwtResponse";
import { RegisterRequest } from "./model/RegisterRequest";

import { BehaviorSubject } from "rxjs";
import { Router } from '@angular/router';
@Injectable({
  providedIn: 'root'
})
export class AuthService {
  private baseUrl = 'http://localhost:8080/auth';

  // stan logowania (domyślnie false)
  private loggedIn = new BehaviorSubject<boolean>(this.hasToken());
  isLoggedIn$ = this.loggedIn.asObservable();

  constructor(private http: HttpClient) {}

  login(data: LoginRequest): Observable<JwtResponse> {
    return this.http.post<JwtResponse>(`${this.baseUrl}/login`, data);
  }

  register(data: RegisterRequest): Observable<JwtResponse> {
    return this.http.post<JwtResponse>(`${this.baseUrl}/signup`, data);
  }

  saveToken(token: string): void {
    localStorage.setItem('jwt', token);
    this.loggedIn.next(true);
  }

  getToken(): string | null {
    return localStorage.getItem('jwt');
  }

  logout(): void {
    localStorage.removeItem('jwt');
    this.loggedIn.next(false);
  }

  isAuthenticated(): boolean {
    return this.hasToken();
  }

  private hasToken(): boolean {
    return !!localStorage.getItem('jwt');
  }
}