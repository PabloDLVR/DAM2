import { Component, signal } from '@angular/core';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-repeticiones',
  standalone: false,
  templateUrl: './repeticiones.html',
  styleUrl: './repeticiones.css',
})
export class Repeticiones {

  usuarios = signal<string[]>(['Ana', 'Luis', 'Carlos', 'Marta', 'Sofía']);
  nombreInput = signal<string>('');

  agregarUsuario(): void {
    const nombre = this.nombreInput();

    if (!nombre) {
      Swal.fire({
        title: 'Advertencia',
        text: 'Introduce un nombre',
      });
      return;
    }

    if (this.usuarios().includes(nombre)) {
      Swal.fire({
        title: 'Error',
        text: 'El nombre ya está en la lista',
      });
      return;
    }

    this.usuarios.update((usuarios) => [...usuarios, nombre]);
    this.nombreInput.set('');
  }
}
