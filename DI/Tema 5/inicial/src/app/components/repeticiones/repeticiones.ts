import { Component } from '@angular/core';
import Swal from 'sweetalert2';
import { usuario } from '../../model/usuario';
import { usuarioif } from '../../model/usuarioif';

@Component({
  selector: 'app-repeticiones',
  standalone: false,
  templateUrl: './repeticiones.html',
  styleUrl: './repeticiones.css',
})
export class Repeticiones {
  usuarioif:usuarioif[] = [{nombre: 'Ana', apellido: 'Gomez', edad: 50, hobbies: ['leer', 'viajar'], addHobby(hobby: string) { this.hobbies.push(hobby); } },
  {nombre: 'Luis', apellido: 'Martinez', edad: 30, hobbies: ['futbol', 'cine'], addHobby(hobby: string) { this.hobbies.push(hobby); } },
  {nombre: 'Carlos', apellido: 'Lopez', edad: 25, hobbies: ['musica', 'videojuegos'], addHobby(hobby: string) { this.hobbies.push(hobby); } }]
  usuarios: usuario[] = [
    new usuario('Ana', 'Gomez', 50),
    new usuario('Luis', 'Martinez', 30),
    new usuario('Carlos', 'Lopez', 25),
  ];
  nombreInput: string = '';
  apellidoInput: string = '';
  edadInput: string = '';
  hobbyInput: string = '';
  hobbyNuevoInput: string = '';
  usuarioSeleccionado: usuario | null = null;

  agregarUsuario(): void {
    if (!this.nombreInput || !this.apellidoInput || !this.edadInput || !this.hobbyInput) {
      Swal.fire({
        title: 'Advertencia',
        text: 'Completa todos los campos',
      });
      return;
    }

    let existe = false;
    for (let i = 0; i < this.usuarios.length; i++) {
      if (this.usuarios[i].getNombre().toLowerCase() === this.nombreInput.toLowerCase()) {
        existe = true;
      }
    }

    if (existe) {
      Swal.fire({
        title: 'Error',
        text: 'Este usuario ya existe',
      });
      return;
    }

    let nuevoUsuario = new usuario(this.nombreInput, this.apellidoInput, Number(this.edadInput));
    nuevoUsuario.agregarHobby(this.hobbyInput);
    this.usuarios.push(nuevoUsuario);
    this.nombreInput = '';
    this.apellidoInput = '';
    this.edadInput = '';
    this.hobbyInput = '';
  }

  agregarHobbyAUsuario(): void {
    if (!this.usuarioSeleccionado || !this.hobbyNuevoInput) {
      Swal.fire({
        title: 'Advertencia',
        text: 'Selecciona un usuario e introduce un hobby',
      });
      return;
    }

    this.usuarioSeleccionado.agregarHobby(this.hobbyNuevoInput);
    this.hobbyNuevoInput = '';
    this.usuarioSeleccionado = null;
  }
}

