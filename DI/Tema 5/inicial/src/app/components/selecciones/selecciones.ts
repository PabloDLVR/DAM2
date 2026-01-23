import { Component, ChangeDetectionStrategy } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { signal } from '@angular/core';
import Swal from 'sweetalert2';

type PaymentType = 'ordinario' | 'bizum' | 'transferencia';

interface PurchaseResult {
  userName: string;
  cost: number;
  address: string;
  paymentData: string;
}

@Component({
  selector: 'app-selecciones',
  standalone: false,
  templateUrl: './selecciones.html',
  styleUrl: './selecciones.css',
  changeDetection: ChangeDetectionStrategy.OnPush,
})
export class Selecciones {
  seleccion = '1';
  form: FormGroup;
  paymentType = signal<PaymentType>('ordinario');

  constructor(private fb: FormBuilder) {
    this.form = this.fb.group({
      nombre: ['', [Validators.required, Validators.minLength(2)]],
      direccionEnvio: ['', [Validators.required, Validators.minLength(5)]],
      direccionFacturacion: ['', [Validators.required, Validators.minLength(5)]],
      coste: ['', [Validators.required, Validators.min(0)]],
      tipoPago: ['ordinario', Validators.required],
      // Campos condicionales
      numeroTarjeta: [''],
      cvv: [''],
      mesAnio: [''],
      telefono: [''],
      numeroCuenta: [''],
    });

    // Suscribirse a cambios en el tipo de pago
    this.form.get('tipoPago')?.valueChanges.subscribe((tipo) => {
      this.paymentType.set(tipo);
      this.updateValidators(tipo);
    });
  }

  private updateValidators(tipo: PaymentType): void {
    const numeroTarjeta = this.form.get('numeroTarjeta');
    const cvv = this.form.get('cvv');
    const mesAnio = this.form.get('mesAnio');
    const telefono = this.form.get('telefono');
    const numeroCuenta = this.form.get('numeroCuenta');

    // Remover validadores de todos
    [numeroTarjeta, cvv, mesAnio, telefono, numeroCuenta].forEach((ctrl) => {
      ctrl?.clearValidators();
      ctrl?.updateValueAndValidity();
    });

    // Agregar validadores según el tipo de pago
    if (tipo === 'ordinario') {
      numeroTarjeta?.setValidators([Validators.required, Validators.minLength(13)]);
      cvv?.setValidators([Validators.required, Validators.minLength(3)]);
      mesAnio?.setValidators([Validators.required]);
    } else if (tipo === 'bizum') {
      telefono?.setValidators([Validators.required, Validators.minLength(9)]);
    } else if (tipo === 'transferencia') {
      numeroCuenta?.setValidators([Validators.required, Validators.minLength(10)]);
    }

    // Actualizar validez
    numeroTarjeta?.updateValueAndValidity();
    cvv?.updateValueAndValidity();
    mesAnio?.updateValueAndValidity();
    telefono?.updateValueAndValidity();
    numeroCuenta?.updateValueAndValidity();
  }

  validatePurchase(): void {
    if (this.form.invalid) {
      Swal.fire({
        icon: 'error',
        title: 'Error de validación',
        text: 'Por favor, complete correctamente todos los campos',
        confirmButtonColor: '#3085d6',
      });
      return;
    }

    const { nombre, direccionFacturacion, coste, tipoPago, numeroTarjeta, cvv, mesAnio, telefono, numeroCuenta } = this.form.value;
    const costNum = Number(coste);
    let finalCost = costNum;
    let paymentData = '';

    if (tipoPago === 'ordinario') {
      const lastDigits = numeroTarjeta.slice(-4);
      paymentData = `Tarjeta ${lastDigits} ${mesAnio}`;
    } else if (tipoPago === 'bizum') {
      const lastDigits = telefono.slice(-4);
      paymentData = `Teléfono ${lastDigits}`;
    } else if (tipoPago === 'transferencia') {
      const lastDigits = numeroCuenta.slice(-4);
      paymentData = `Cuenta ${lastDigits}`;
      finalCost = costNum * 1.05; // Agregar 5% si es transferencia
    }

    const surcharge = tipoPago === 'transferencia' ? ` (+5%)` : '';

    Swal.fire({
      icon: 'success',
      title: '¡Compra confirmada!',
      html: `
        <div style="text-align: left;">
          <p><strong>Nombre:</strong> ${nombre}</p>
          <p><strong>Producto enviado a:</strong> ${direccionFacturacion}</p>
          <p><strong>Coste:</strong> €${finalCost.toFixed(2)}${surcharge}</p>
          <hr/>
          <p><strong>Datos de pago:</strong></p>
          <p>${paymentData}</p>
        </div>
      `,
      confirmButtonColor: '#28a745',
      confirmButtonText: 'Aceptar',
    });
  }
}
