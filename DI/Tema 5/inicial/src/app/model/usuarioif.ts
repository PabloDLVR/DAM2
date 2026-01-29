export interface usuarioif {
    nombre: string;
    apellido: string;
    edad: number;
    hobbies: string[];
    addHobby(hobby: string): void;
}