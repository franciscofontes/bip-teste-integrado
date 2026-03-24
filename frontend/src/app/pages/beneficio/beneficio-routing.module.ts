import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {BeneficioListComponent} from './beneficio-list/beneficio-list.component';
import {BeneficioFormComponent} from './beneficio-form/beneficio-form.component';
import {BeneficioTransferComponent} from './beneficio-transfer/beneficio-transfer.component';

const routes: Routes = [
  { path: '', component: BeneficioListComponent },
  { path: 'novo', component: BeneficioFormComponent },
  { path: 'editar/:id', component: BeneficioFormComponent },
  { path: 'transfer/:fromId', component: BeneficioTransferComponent }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class BeneficioRoutingModule { }
