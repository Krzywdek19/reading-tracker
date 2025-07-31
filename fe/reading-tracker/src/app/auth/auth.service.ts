import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { LoginRequest } from "../model/LoginRequest";
import { Observable, of} from "rxjs";
import { JwtResponse } from "../model/JwtResponse";
import { RegisterRequest } from "../model/RegisterRequest";
import { catchError, map } from "rxjs/operators";
import { BehaviorSubject } from "rxjs";


@Injectable({
  providedIn: 'root'
})
export class AuthService {
  private baseUrl = 'http://localhost:8080/auth';

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

  isAuthenticated(): Observable<boolean> {
    return this.validateToken();
  }

  private hasToken(): boolean {
    return !!localStorage.getItem('jwt');
  }

  validateToken(): Observable<boolean> {
  const token = this.getToken();
  if (!token) return of(false);

  return this.http.get(`${this.baseUrl}/validate`, {
    headers: {
      Authorization: `Bearer ${token}`
    }
  }).pipe(
    map(() => true),
    catchError(() => {
      this.logout(); // Clear the token if validation fails
      return of(false);
    })
  );
  }
}