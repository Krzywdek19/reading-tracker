import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { BookService } from '../book.service';
import { BookDto } from '../../../model/BookDto';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';
import { ReactiveFormsModule } from '@angular/forms';
import { AuthorDto } from '../../../model/AuthorDto';
import { AuthorService } from '../../authors-section/author.service';

@Component({
  selector: 'app-book-details',
  templateUrl: './book-details.component.html',
  imports: [CommonModule, RouterModule, ReactiveFormsModule],
  styleUrls: ['./book-details.component.scss']
})
export class BookDetailsComponent implements OnInit {
  bookForm!: FormGroup;
  bookId!: number;
  authors: AuthorDto[] = [];

  constructor(
    private route: ActivatedRoute,
    private bookService: BookService,
    private fb: FormBuilder,
    private router: Router,
    private authorService: AuthorService 
  ) {}

  ngOnInit(): void {
    this.bookId = Number(this.route.snapshot.paramMap.get('id'));

    this.authorService.getAuthors().subscribe({
      next: (data) => this.authors = data,
      error: (err) => console.error('Błąd pobierania autorów', err)
    });

    this.bookService.getBook(this.bookId).subscribe({
      next: (book) => {
        this.bookForm = this.fb.group({
          title: [book.title, Validators.required],
          description: [book.description],
          pages: [book.pages, [Validators.required, Validators.min(1)]],
          readPages: [book.readPages, [Validators.min(0)]],
          authorId: [book.authorDto?.id, Validators.required]
        });
      },
      error: (err) => {
        alert('Book has not been found!');
        this.router.navigate(['/books']);
      }
    });
  }

  goBack(): void {
    this.router.navigate(['/books']);
  }

  onSubmit(): void {
    if (this.bookForm.valid) {
      const updatedBook: BookDto = {
        ...this.bookForm.value,
        id: this.bookId,
        read: this.bookForm.value.readPages === this.bookForm.value.pages
      };
      this.bookService.updateBook(updatedBook).subscribe({
        next: () => {
          this.router.navigate(['/books']);
        },
        error: (err) => alert('Error occurs!')
      });
    }
  }
}