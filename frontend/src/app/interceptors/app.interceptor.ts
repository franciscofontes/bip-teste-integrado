import {HttpErrorResponse, HttpInterceptorFn} from '@angular/common/http';
import {catchError, finalize, throwError} from 'rxjs';
import {inject} from '@angular/core';
import {AlertService} from '../shared/alert/alert.service';
import {LoadingService} from '../services/loading.service';

export const appInterceptor: HttpInterceptorFn = (req, next) => {

  const loadingService = inject(LoadingService);
  const alertService = inject(AlertService);

  loadingService.show();

  return next(req).pipe(
    catchError((error: HttpErrorResponse) => {
      let errorMessage = '';
      switch (error.status) {
        case 0:
          errorMessage = "Falha ao conectar servidor";
          break;
        default:
          errorMessage = `${error.error}`;
          break;
      }
      alertService.error(errorMessage, [], {autoClose: true, keepAfterRouteChange: true});
      return throwError(() => new Error(errorMessage));
    }),
    finalize(() => {
      loadingService.hide();
    })
  );
};
