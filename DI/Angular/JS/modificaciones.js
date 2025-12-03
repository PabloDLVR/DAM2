/* let botonAgregar=document.querySelector("#btnAgregar")//Buscar elementos del html
 *//* let botones=document.querySelectorAll("button")//Buscar todos los elementos con la etiqueta que elijas
console.log(botonAgregar);
console.log(botones); */
/* botonAgregar.addEventListener("click",()=>{console.log("Boton pulsado");
}) */
//Al pulsar agregar, saco por consola: Nombre, apellido y fecha pero si hay campos vacios que salte un alert
//Al pulsar limpiar, que se limpien los campos input
let botonAgregar=document.querySelector("button")
let inputNombre=document.querySelector("#inputNombre")
let inputApellido=document.querySelector("#inputApellido")
let inputFecha=document.querySelector("#inputFecha")
 botonAgregar=addEventListener("click",(ev)=>{
    let Nombre=inputNombre.value;
    let Apellido=inputApellido.value;
    let Fecha=inputFecha.value;
    console.log(`nombre ${Nombre}`);
    console.log(`Apellido ${Apellido}`);
    console.log(`Fecha ${Fecha}`);
    
})