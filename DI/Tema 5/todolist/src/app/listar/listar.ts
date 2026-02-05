import { Component, OnInit, DoCheck } from '@angular/core';
import { Tareas, Tarea } from '../services/tareas';

@Component({
  selector: 'app-listar',
  standalone: false,
  templateUrl: './listar.html',
  styleUrl: './listar.css',
})
export class Listar implements OnInit, DoCheck {
  tareas: Tarea[] = [];
  prioridades: number[] = [];
  prioridadSeleccionada: string = '';

  constructor(private tareasServicio: Tareas) {}

  ngOnInit(): void {
    this.cargarTareas();
    this.cargarPrioridades();
  }

  ngDoCheck(): void {
    this.cargarTareas();
  }

  cargarTareas(): void {
    if (this.prioridadSeleccionada) {
      this.tareas = this.tareasServicio.filtrarPorPrioridad(
        Number(this.prioridadSeleccionada)
      );
    } else {
      this.tareas = this.tareasServicio.obtenerTareas();
    }
  }

  cargarPrioridades(): void {
    this.prioridades = this.tareasServicio.obtenerPrioridades();
  }

  filtrarPorPrioridad(): void {
    this.cargarTareas();
  }

  limpiarFiltro(): void {
    this.prioridadSeleccionada = '';
    this.cargarTareas();
  }

  eliminarTarea(indice: number): void {
    this.tareasServicio.eliminarTarea(indice);
    this.cargarTareas();
    this.cargarPrioridades();
  }
}
