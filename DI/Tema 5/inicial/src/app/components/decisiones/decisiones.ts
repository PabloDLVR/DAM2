import { Component, ChangeDetectionStrategy } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { signal } from '@angular/core';
import Swal from 'sweetalert2';

interface CandidateResult {
  status: 'IDONEO' | 'DESCARTADO' | null;
  message: string;
}

@Component({
  selector: 'app-decisiones',
  templateUrl: './decisiones.html',
  styleUrl: './decisiones.css',
  changeDetection: ChangeDetectionStrategy.OnPush,
  standalone: false,
})
export class Decisiones {
  mostrar = signal(true);
  expression = signal(true);

  form: FormGroup;
  result = signal<CandidateResult>({ status: null, message: '' });

  constructor(private fb: FormBuilder) {
    this.form = this.fb.group({
      nombre: ['', [Validators.required, Validators.minLength(2)]],
      apellido: ['', [Validators.required, Validators.minLength(2)]],
      edad: ['', [Validators.required, Validators.min(18)]],
      salario: ['', [Validators.required, Validators.min(0)]],
      experiencia: ['', [Validators.required, Validators.min(0)]],
    });
  }

  evaluateCandidate(): void {
    if (this.form.invalid) {
      Swal.fire({
        icon: 'error',
        title: 'Error de validación',
        text: 'Por favor, complete correctamente todos los campos',
        confirmButtonColor: '#3085d6',
      });
      return;
    }

    const { edad, salario, experiencia } = this.form.value;

    // Convertir a números para asegurar comparaciones correctas
    const edadNum = Number(edad);
    const salarioNum = Number(salario);
    const experienciaNum = Number(experiencia);

    let status: 'IDONEO' | 'DESCARTADO' = 'DESCARTADO';
    let message = '';

    // Condición 1: edad > 18, salario > 20000€, experiencia < 2 años
    if (edadNum > 18 && salarioNum > 20000 && experienciaNum < 2) {
      status = 'IDONEO';
      message = 'Perfil senior con salario alto';
    }
    // Condición 2: edad > 18, salario < 5000€, experiencia > 3 años
    else if (edadNum > 18 && salarioNum < 5000 && experienciaNum > 3) {
      status = 'IDONEO';
      message = 'Perfil económico con experiencia';
    }
    // Condición 3: experiencia > 10 años, edad > 18
    else if (experienciaNum > 10 && edadNum > 18) {
      status = 'IDONEO';
      message = 'Candidato muy experimentado';
    }
    // Resto de casos
    else {
      status = 'DESCARTADO';
      message = 'No cumple los requisitos necesarios';
    }

    this.showResult(status, message);
  }

  private showResult(status: 'IDONEO' | 'DESCARTADO', message: string): void {
    const isIdoneo = status === 'IDONEO';
    const { nombre, apellido } = this.form.value;

    Swal.fire({
      icon: isIdoneo ? 'success' : 'error',
      title: isIdoneo ? '✓ CANDIDATO IDÓNEO' : '✗ CANDIDATO DESCARTADO',
      html: `
        <div style="text-align: left;">
          <p><strong>Nombre:</strong> ${nombre}</p>
          <p><strong>Apellido:</strong> ${apellido}</p>
          <p><strong>Resultado:</strong> ${message}</p>
        </div>
      `,
      confirmButtonColor: isIdoneo ? '#28a745' : '#dc3545',
      confirmButtonText: 'Aceptar',
    });

    this.result.set({ status, message });
  }
}
