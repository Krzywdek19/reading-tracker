import { Routes } from '@angular/router';
import { authRoutes } from './auth/auth.routes';
import { HomeComponent } from './home/home.component';
import { authGuard } from './auth/auth.guard';
import { AddAuthorComponent } from './home/authors-section/add-author/add-author.component';
import { AuthorsSectionComponent } from './home/authors-section/authors-section.component';
import { AddBookComponent } from './home/books-section/add-book/add-book.component';
import { BooksListComponent } from './home/books-section/books-section.component';


export const routes: Routes = [
  {
    path: 'auth',
    children: authRoutes 
  },
  {
    path: '',
    canActivate: [authGuard],
    children: [
      { path: '', component: HomeComponent },
      { path: 'authors', component: AuthorsSectionComponent },
      { path: 'add-author', component: AddAuthorComponent },
      { path: 'books', component: BooksListComponent },
      { path: 'add-book', component: AddBookComponent }
    ]
  },
  { path: '**', redirectTo: '' }
];