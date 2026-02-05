import { Component, OnInit } from '@angular/core';
import { Tareas, Tarea } from '../services/tareas';

@Component({
  selector: 'app-buscar',
  standalone: false,
  templateUrl: './buscar.html',
  styleUrl: './buscar.css',
})
export class Buscar implements OnInit {
  tareas: Tarea[] = [];
  termino: string = '';
  prioridades: number[] = [];
  prioridadSeleccionada: string = '';
  tipoFiltro: string = 'nombre';

  constructor(private tareasServicio: Tareas) {}

  ngOnInit(): void {
    this.cargarPrioridades();
  }

  cargarPrioridades(): void {
    this.prioridades = this.tareasServicio.obtenerPrioridades();
  }

  buscar(): void {
    if (this.tipoFiltro === 'nombre') {
      this.tareas = this.tareasServicio.buscarTareas(this.termino);
    } else if (this.tipoFiltro === 'prioridad' && this.prioridadSeleccionada) {
      this.tareas = this.tareasServicio.filtrarPorPrioridad(
        Number(this.prioridadSeleccionada)
      );
    }
  }

  limpiar(): void {
    this.termino = '';
    this.prioridadSeleccionada = '';
    this.tareas = [];
  }
}
