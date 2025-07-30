import { Routes } from '@angular/router';
import { authRoutes } from './auth/auth.routes';
import { HomeComponent } from './home/home.component';
import { authGuard } from './auth/auth.guard';
import { AddAuthorComponent } from './home/add-author/add-author.component';

export const routes: Routes = [
  { path: '', component: HomeComponent, canActivate: [authGuard] },
  {
    path: 'auth',
    children: authRoutes
  },
  { path: 'add-author', component: AddAuthorComponent }
];