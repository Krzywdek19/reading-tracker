import { Component, OnInit } from '@angular/core';
import { AuthorService } from '../../authors-section/author.service';
import { BookService } from '../book.service';
import { AuthorDto } from '../../../model/AuthorDto';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { ReactiveFormsModule } from '@angular/forms';
import { Router } from '@angular/router';
@Component({
  selector: 'app-add-book',
  imports: [CommonModule, ReactiveFormsModule],
  templateUrl: './add-book.component.html',
  styleUrls: ['./add-book.component.scss']
})
export class AddBookComponent implements OnInit {
  bookForm: FormGroup;
  authors: AuthorDto[] = [];

  constructor(
    private fb: FormBuilder,
    private authorService: AuthorService,
    private bookService: BookService,
    private router: Router
  ) {
    this.bookForm = this.fb.group({
      title: ['', Validators.required],
      authorId: ['', Validators.required],
      pages: [null, [Validators.required, Validators.min(1)]],
    });
  }

  ngOnInit(): void {
    this.authorService.getAuthors().subscribe({
      next: (data) => this.authors = data,
      error: (err) => console.error('Error ocours', err)
    });
  }

  onSubmit(): void {
    if (this.bookForm.valid) {
      this.bookService.addBook(this.bookForm.value).subscribe({
        next: () => {
          this.router.navigate(['/books'])
        },
        error: (err) => {
        }
      });
    }
  }
}