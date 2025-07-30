import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { BookDto } from '../../model/BookDto';

@Injectable({ providedIn: 'root' })
export class BookService {
  private baseUrl = 'http://localhost:8080/api/books';

  constructor(private http: HttpClient) {}

  addBook(book: BookDto): Observable<any> {
    return this.http.post(this.baseUrl, book);
  }

  getBooks(): Observable<BookDto[]> {
  return this.http.get<BookDto[]>(this.baseUrl);
}

updateBook(book: BookDto): Observable<BookDto> {
  return this.http.put<BookDto>(`${this.baseUrl}/${book.id}`, book);
}
}