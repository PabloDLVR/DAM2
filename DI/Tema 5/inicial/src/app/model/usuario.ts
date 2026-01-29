export class usuario {
  hobbies: string[];

  constructor(private nombre: string, private apellido: string, private edad: number) {
    this.hobbies = [];
  }

  getNombre(): string {
    return this.nombre;
  }

  getApellido(): string {
    return this.apellido;
  }

  getEdad(): number {
    return this.edad;
  }

  agregarHobby(hobby: string): void {
    this.hobbies.push(hobby);
  }
}   