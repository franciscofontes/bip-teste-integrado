import {Routes} from '@angular/router';

export const routes: Routes = [
  {
    path: 'beneficios',
    loadChildren: () => import('./pages/beneficio/beneficio.module').then(m => m.BeneficioModule)
  },
  {path: '', redirectTo: '/beneficios', pathMatch: 'full'}
];
