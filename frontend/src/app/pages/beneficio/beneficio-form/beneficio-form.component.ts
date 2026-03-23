import {Component, inject, OnInit} from '@angular/core';
import {FormBuilder, FormGroup, ReactiveFormsModule, Validators} from '@angular/forms';
import {ActivatedRoute, Router, RouterLink} from '@angular/router';
import {BeneficioService} from '../../../services/beneficio.service';
import {AlertService} from '../../../shared/alert/alert.service';

@Component({
  selector: 'app-beneficio-form',
  imports: [
    ReactiveFormsModule,
    RouterLink
  ],
  templateUrl: './beneficio-form.component.html',
  styleUrl: './beneficio-form.component.css',
})
export class BeneficioFormComponent implements OnInit {
  fb = inject(FormBuilder);
  beneficioService = inject(BeneficioService);
  alertService = inject(AlertService);
  router = inject(Router);
  route = inject(ActivatedRoute);
  form!: FormGroup;
  isEdit = false;
  beneficioId?: number;
  successAlertOptions = {autoClose: true, keepAfterRouteChange: true};
  warnAlertOptions = {autoClose: true, keepAfterRouteChange: false};

  ngOnInit() {
    this.form = this.fb.group({
      nome: ['', [Validators.required, Validators.minLength(2), Validators.maxLength(100)]],
      descricao: ['', [Validators.maxLength(255)]],
      valor: ['', [Validators.required, Validators.min(0)]],
      ativo: [true],
    });
    const id = this.route.snapshot.paramMap.get('id');
    if (id) {
      this.isEdit = true;
      this.beneficioId = +id;
      const beneficio = this.beneficioService.pageBeneficios().content.find(b => b.id === this.beneficioId);
      if (beneficio) this.form.patchValue(beneficio);
    }
  }

  save() {
    if (this.form.invalid) return;
    const beneficio = {...this.form.value, id: this.beneficioId};
    if (this.isEdit) {
      this.beneficioService.update(beneficio).subscribe({
        next: (response) => {
          this.alertService.success("Beneficio atualizado com sucesso", [], this.successAlertOptions);
          this.router.navigate(['../..'], {relativeTo: this.route}).then(r => '');
        },
        error: (err) => {
          this.alertService.warn("Ocorreu um erro no servidor. Não foi possível atualizar", [], this.warnAlertOptions);
        }
      });
    } else {
      this.beneficioService.create(beneficio).subscribe({
        next: (response) => {
          this.alertService.success("Beneficio cadastrado com sucesso", [], this.successAlertOptions);
          this.router.navigate(['..'], {relativeTo: this.route}).then(r => '');
        },
        error: (err) => {
          this.alertService.warn("Ocorreu um erro no servidor. Não foi possível cadastrar", [], this.warnAlertOptions);
        }
      });
    }
  }
}
