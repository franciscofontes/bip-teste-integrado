import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {BeneficioRoutingModule} from './beneficio-routing.module';
import {ReactiveFormsModule} from '@angular/forms';
import {BeneficioListComponent} from './beneficio-list/beneficio-list.component';
import {BeneficioFormComponent} from './beneficio-form/beneficio-form.component';


@NgModule({
  declarations: [],
  imports: [
    CommonModule,
    BeneficioRoutingModule,
    ReactiveFormsModule,
    BeneficioListComponent,
    BeneficioFormComponent
  ]
})
export class BeneficioModule {
}
