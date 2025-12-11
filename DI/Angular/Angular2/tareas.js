class tarea{
    //atributos
    nombre;
    fecha;
    prioridad;
    //constructores
    constructor(nombre,fecha,prioridad){
        this.nombre=nombre;
        this.fecha=fecha;
        this.prioridad=prioridad;
    }
    //metodos
    mostrarDatos(){
        console.log(`El nombre de la tarea es ${this.nombre}`);
        console.log(`La fecha de la tarea es ${this.fecha}`);
        console.log(`La prioridad de la tarea es ${this.prioridad}`);
    }
    //getter-setter
    getNombre(){
        return this.nombre;
    }
    setNombre(nombre){
        this.nombre=nombre;
    }
}

let tarea1=new tarea("Tarea1",new Date(),"Alta");
tarea1.setNombre("Tarea prioritaria");
tarea1.getNombre();
tarea1.mostrarDatos();