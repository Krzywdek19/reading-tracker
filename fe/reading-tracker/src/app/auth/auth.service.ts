import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { LoginRequest } from "./model/LoginRequest";
import { Observable } from "rxjs";
import { JwtResponse } from "./model/JwtResponse";
import { RegisterRequest } from "./model/RegisterRequest";

@Injectable({
    providedIn: 'root'
})
export class AuthService {
    private baseUrl = 'http://localhost:8080/auth';

    constructor(private http: HttpClient) {}

    login(data: LoginRequest) : Observable<JwtResponse> {
        return this.http.post<JwtResponse>(`${this.baseUrl}/login`, data);
    }

     register(data: RegisterRequest) : Observable<JwtResponse> {
        return this.http.post<JwtResponse>(`${this.baseUrl}/signup`, data);
    }

    saveToken(token: string): void {
        localStorage.setItem('jwt', token);
    }

    getToken(): string | null {
        return localStorage.getItem('jwt');
    }

    logout(): void {
        localStorage.removeItem('jwt');
    }

    isAuthenticated(): boolean {
        return !!this.getToken();
    }
}