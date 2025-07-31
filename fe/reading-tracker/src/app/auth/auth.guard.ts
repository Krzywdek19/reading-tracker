import { inject } from '@angular/core';
import { CanActivateFn, Router } from '@angular/router';
import { AuthService } from './auth.service';
import { map } from 'rxjs/operators';

export const authGuard: CanActivateFn = () => {
  const auth = inject(AuthService);
  const router = inject(Router);

  return auth.validateToken().pipe(
    map(isValid => {
      if (isValid) {
        return true;
      } else {
        auth.logout(); // Clear the token if validation fails
        // Redirect to login page
        return router.parseUrl('/auth/login');
      }
    })
  );
};