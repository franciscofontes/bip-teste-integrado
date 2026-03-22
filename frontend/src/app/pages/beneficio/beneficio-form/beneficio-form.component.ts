import {Component, inject, OnInit} from '@angular/core';
import {FormBuilder, FormGroup, ReactiveFormsModule, Validators} from '@angular/forms';
import {ActivatedRoute, Router, RouterLink} from '@angular/router';
import {BeneficioService} from '../../../services/beneficio.service';
import {HeaderComponent} from '../../../shared/header/header.component';

@Component({
  selector: 'app-beneficio-form',
  imports: [
    ReactiveFormsModule,
    RouterLink,
    HeaderComponent
  ],
  templateUrl: './beneficio-form.component.html'
})
export class BeneficioFormComponent implements OnInit {
  fb = inject(FormBuilder);
  beneficioService = inject(BeneficioService);
  router = inject(Router);
  route = inject(ActivatedRoute);

  form!: FormGroup;
  isEdit = false;
  beneficioId?: number;

  ngOnInit() {
    this.form = this.fb.group({
      nome: ['', Validators.required],
      descricao: [''],
      valor: ['', [Validators.required, Validators.min(0)]],
      ativo: [true],
    });

    const id = this.route.snapshot.paramMap.get('id');
    if (id) {
      this.isEdit = true;
      this.beneficioId = +id;
      const beneficio = this.beneficioService.pageBeneficios().content.find(p => p.id === this.beneficioId);
      if (beneficio) this.form.patchValue(beneficio);
    }
  }

  save() {
    if (this.form.invalid) return;
    const beneficio = {...this.form.value, id: this.beneficioId};
    if (this.isEdit) {
      this.beneficioService.update(beneficio);
    } else {
      this.beneficioService.add(beneficio);
    }
    this.router.navigate(['/beneficios']);
  }
}
