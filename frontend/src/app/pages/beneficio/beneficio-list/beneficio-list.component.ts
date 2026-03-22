import {Component, inject} from '@angular/core';
import {CurrencyPipe} from '@angular/common';
import {RouterLink} from '@angular/router';
import {BeneficioService} from '../../../services/beneficio.service';
import {HeaderComponent} from '../../../shared/header/header.component';

@Component({
  selector: 'app-beneficio-list',
  imports: [
    CurrencyPipe,
    RouterLink,
    HeaderComponent
  ],
  templateUrl: './beneficio-list.component.html'
})
export class BeneficioListComponent {
  beneficioService = inject(BeneficioService);
  pageBeneficios = this.beneficioService.pageBeneficios;
  beneficios = this.beneficioService.beneficios;

  ngOnInit(): void {
    this.beneficioService.findByPage();
  }

  delete(id?: number) {
    if (id && confirm('Tem certeza?')) {
      this.beneficioService.delete(id);
    }
  }
}
