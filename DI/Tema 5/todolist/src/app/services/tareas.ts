import { Injectable } from '@angular/core';

export interface Tarea {
  nombre?: string;
  responsable?: string;
  items?: string[];
  fecha?: string;
  prioridad?: number;
  descripcion?: string;
}

@Injectable({
  providedIn: 'root',
})
export class Tareas {
  tareas: Tarea[] = [];

  // Agrega una nueva tarea a la lista
  agregarTarea(tarea: Tarea): void {
    this.tareas.push(tarea);
  }

  // Elimina una tarea de la lista por su índice
  eliminarTarea(indice: number): void {
    if (indice >= 0 && indice < this.tareas.length) {
      this.tareas.splice(indice, 1);
    }
  }

  // Obtiene la lista completa de tareas
  obtenerTareas(): Tarea[] {
    return this.tareas;
  }

  // Busca tareas por nombre
  buscarTareas(termino: string): Tarea[] {
    if (!termino.trim()) {
      return this.tareas;
    }
    return this.tareas.filter(tarea =>
      tarea.nombre?.toLowerCase().includes(termino.toLowerCase())
    );
  }

  // Filtra tareas por prioridad
  filtrarPorPrioridad(prioridad: number): Tarea[] {
    return this.tareas.filter(tarea => tarea.prioridad === prioridad);
  }

  // Obtiene todas las prioridades únicas en las tareas
  obtenerPrioridades(): number[] {
    const prioridades = new Set<number>();
    this.tareas.forEach(tarea => {
      if (tarea.prioridad !== undefined) {
        prioridades.add(tarea.prioridad);
      }
    });
    return Array.from(prioridades).sort((a, b) => a - b);
  }
}
