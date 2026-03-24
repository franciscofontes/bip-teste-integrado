import {Component, inject, OnInit} from '@angular/core';
import {FormBuilder, FormGroup, ReactiveFormsModule, Validators} from '@angular/forms';
import {ActivatedRoute, Router, RouterLink} from '@angular/router';
import {BeneficioService} from '../../../services/beneficio.service';
import {AlertService} from '../../../shared/alert/alert.service';
import {LoadingService} from '../../../services/loading.service';

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
  loadingService = inject(LoadingService);
  router = inject(Router);
  route = inject(ActivatedRoute);
  loading = this.loadingService.loading;
  form!: FormGroup;
  isEdit = false;
  beneficioId?: number;
  successAlertOptions = {autoClose: true, keepAfterRouteChange: true};

  ngOnInit() {
    this.form = this.fb.group({
      nome: ['', [Validators.required, Validators.minLength(2), Validators.maxLength(100)]],
      descricao: ['', [Validators.maxLength(255)]],
      valor: ['', [Validators.required, Validators.min(0)]],
      ativo: [true],
    });
    this.fill();
  }

  private fill() {
    const id = this.route.snapshot.paramMap.get('id');
    if (id) {
      this.isEdit = true;
      this.beneficioId = +id;
      this.findById(this.beneficioId);
    }
  }

  private findById(id: number) {
    this.beneficioService.findById(id).subscribe((response) => {
      this.form.patchValue(response);
    });
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
        }
      });
    } else {
      this.beneficioService.create(beneficio).subscribe({
        next: (response) => {
          this.alertService.success("Beneficio cadastrado com sucesso", [], this.successAlertOptions);
          this.router.navigate(['..'], {relativeTo: this.route}).then(r => '');
        },
        error: (err) => {
        }
      });
    }
  }
}
