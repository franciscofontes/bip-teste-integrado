import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import {RouterModule, Routes} from '@angular/router';
import {BeneficioListComponent} from '../beneficio/beneficio-list/beneficio-list.component';
import {BeneficioFormComponent} from '../beneficio/beneficio-form/beneficio-form.component';
import {BeneficioTransferComponent} from '../beneficio/beneficio-transfer/beneficio-transfer.component';
import {HomeComponent} from './home.component';

const routes: Routes = [
  { path: '', component: HomeComponent }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class HomeRoutingModule { }
