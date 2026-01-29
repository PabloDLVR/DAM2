import { Component } from '@angular/core';
import { Tareas, Tarea } from '../services/tareas';

@Component({
  selector: 'app-agregar',
  standalone: false,
  templateUrl: './agregar.html',
  styleUrls: ['./agregar.css'],
})
export class Agregar {
  items: string[] = [];
  nombre?: string;
  fecha?: string;
  responsable?: string;
  prioridad?: string;
  descripcion?: string;
  item?: string;
  tareas: Tarea[] = [];

  constructor(private tareasServicio: Tareas) {
    // lógica
  }

  agregarItem() {
    if (this.item != undefined) {
      this.items.push(this.item);
    }
  }

  agregarTarea() {
    const tarea: Tarea = {
      nombre: this.nombre,
      responsable: this.responsable,
      items: this.items,
      fecha: this.fecha,
      prioridad: this.prioridad ? Number(this.prioridad) : undefined,
      descripcion: this.descripcion,
    };

    // llamar al metodo agregarTarea del servicio
    this.tareasServicio.agregarTarea(tarea);
  }
}
