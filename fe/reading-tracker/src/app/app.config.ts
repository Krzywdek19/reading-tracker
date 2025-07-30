import { ApplicationConfig, provideZoneChangeDetection } from '@angular/core';
import { provideHttpClient, withInterceptors} from '@angular/common/http';
import { provideRouter } from '@angular/router';
import { routes } from './app.routes';
import { jwtInterceptor } from './auth/jwt-interceptor.service';

export const appConfig: ApplicationConfig = {
  providers: [
    provideZoneChangeDetection({ eventCoalescing: true }),
    provideRouter(routes),
    provideHttpClient(withInterceptors([jwtInterceptor]))
  ]
};