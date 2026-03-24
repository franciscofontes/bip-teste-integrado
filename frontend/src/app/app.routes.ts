import {Routes} from '@angular/router';

export const routes: Routes = [
  {path: 'home', loadChildren: () => import('./pages/home/home.module').then(m => m.HomeModule)},
  {path: 'beneficios', loadChildren: () => import('./pages/beneficio/beneficio.module').then(m => m.BeneficioModule)},
  {path: '', redirectTo: '/home', pathMatch: 'full'}
];
