import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import {BeneficioListComponent} from './beneficio-list/beneficio-list.component';
import {BeneficioFormComponent} from './beneficio-form/beneficio-form.component';

const routes: Routes = [
  { path: '', component: BeneficioListComponent },
  { path: 'novo', component: BeneficioFormComponent },
  { path: 'editar/:id', component: BeneficioFormComponent },
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class BeneficioRoutingModule { }
