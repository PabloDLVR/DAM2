import { Component, input, signal } from '@angular/core';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-root',
  templateUrl: './app.html',
  standalone: false,
  styleUrl: './app.css'
})
export class App {
  protected readonly title = signal('inicial');
  nombre:string = 'Pablo';
  apellido:string= 'De La Vara';
  curso:string= 'DAM2';
  nombreCurso:string= 'Desarrollo de Aplicaciones Multiplataforma';
  nota:number|string=0;


pulsarComenzar(inputNota:string){
  //alert(`¡Bienvenido ${this.nombre} ${this.apellido} al curso de ${this.nombreCurso}!`);
  this.nota=inputNota;
  Swal.fire({
    title: '¡Bienvenido '+this.nombre+' '+this.apellido+' al curso de '+this.nombreCurso+'!',
    text: 'Tu nota es: '+this.nota,
    icon: 'success',
    confirmButtonText: 'Aceptar'
  });
}
}
