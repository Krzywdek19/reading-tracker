import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { CreateAuthorDto } from "./model/CreateAuthorDto";
import { Observable } from "rxjs";

@Injectable({providedIn: 'root'})
export class AuthorService {
    private baseUrl = 'http://localhost:8080/api/authors'

    constructor(private http: HttpClient) {

    }

    createAuthor(dto: CreateAuthorDto): Observable<any> {
        return this.http.post(this.baseUrl, dto);
    }
 }