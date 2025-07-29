import { Routes } from '@angular/router';
import { authRoutes } from './auth/auth.routes';
import { HomeComponent } from './home/home.component';
import { authGuard } from './auth/auth.guard';

export const routes: Routes = [
  { path: '', component: HomeComponent, canActivate: [authGuard] },
  {
    path: 'auth',
    children: authRoutes
  }
];