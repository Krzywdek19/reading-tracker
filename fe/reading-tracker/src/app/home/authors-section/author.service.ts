import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { CreateAuthorDto } from "./add-author/model/CreateAuthorDto";
import { Observable } from "rxjs";
import { Author } from "./model/Author";

@Injectable({providedIn: 'root'})
export class AuthorService {
    private baseUrl = 'http://localhost:8080/api/authors'

    constructor(private http: HttpClient) {

    }

    createAuthor(dto: CreateAuthorDto): Observable<any> {
        return this.http.post(this.baseUrl, dto);
    }

    getAuthors(): Observable<Author[]> {
        return this.http.get<Author[]>(this.baseUrl);
    }
 }