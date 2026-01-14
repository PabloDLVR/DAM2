let boton = document.querySelector("#btnAgregar")
let inputNombre=document.querySelector("#inputnombre")
let inputFecha=document.querySelector("#inputFecha")
let selectPrio=document.querySelector("#selectPrio")
let listaTareas=document.querySelector("#selectPrio")

let tareas=[];

boton.addEventListener("click", () => {
    let nombre=inputNombre.value;
    let fecha=inputFecha.value;

    if (nombre.length==0||fecha.length==0) {
        alert("No hay datos");
    }else{
    let tarea1=new tarea(inputNombre.value,new Date(inputFecha.value),selectPrio.value);
    tareas.push(tarea1)
    inputNombre.value="";
    inputFecha.value="";
    selectPrio.value="0";
    }
})