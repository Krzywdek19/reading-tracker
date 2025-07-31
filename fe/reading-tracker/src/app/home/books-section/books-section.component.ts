import { Component, OnInit } from '@angular/core';
import { BookService } from './book.service';
import { BookDto } from '../../model/BookDto';
import { Router } from '@angular/router';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { RouterModule } from '@angular/router';

@Component({
  selector: 'app-books-section',
  imports: [CommonModule, FormsModule, RouterModule],
  templateUrl: './books-section.component.html',
  styleUrls: ['./books-section.component.scss']
})
export class BooksListComponent implements OnInit {
  readBooks: BookDto[] = [];
  unreadBooks: BookDto[] = [];
  editReadPages: { [bookId: number]: number } = {};

  constructor(private bookService: BookService, private router: Router) {}

 ngOnInit(): void {
  this.loadBooks();
}

 loadBooks(): void {
  this.bookService.getBooks().subscribe({
    next: (data) => {
      this.readBooks = data.filter(book => book.read);
      this.unreadBooks = data.filter(book => !book.read);
    },
    error: (err) => console.error('Error occurs', err)
  });
}

  goToAddBook(): void {
    this.router.navigate(['/add-book']);
  }

saveReadPages(book: BookDto): void {
  const newReadPages = this.editReadPages[book.id];
  if (newReadPages != null && newReadPages >= 0 && newReadPages <= book.pages) {
    const updatedBook = { ...book, readPages: book.readPages + newReadPages };
    this.bookService.updateBook(updatedBook).subscribe({
      next: () => {
        this.loadBooks();
        this.editReadPages[book.id] = 0; 
      },
      error: (err) => console.error('error', err)
    });
  }
}
}