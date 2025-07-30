import { Component, OnInit } from '@angular/core';
import { AuthorDto } from '../../model/AuthorDto';
import { AuthorService } from './author.service';
import { Router } from '@angular/router';
import { CommonModule } from '@angular/common';
@Component({
  selector: 'app-authors-section',
  imports: [CommonModule],
  templateUrl: './authors-section.component.html',
  styleUrl: './authors-section.component.scss'
})
export class AuthorsSectionComponent implements OnInit {
  authors: AuthorDto[] = [];

  constructor(private authortService: AuthorService, private router: Router){}
  ngOnInit(): void {
    this.authortService.getAuthors().subscribe({
      next: (data) => this.authors = data,
      error: (err) => console.error(err)
    });
  }
  goToAddAuthor(): void {
    this.router.navigate(['/add-author']);
  }
}
