/* /* let botonAgregar=document.querySelector("#btnAgregar")//Buscar elementos del html
 *//* let botones=document.querySelectorAll("button")//Buscar todos los elementos con la etiqueta que elijas
console.log(botonAgregar);
console.log(botones); */
/* botonAgregar.addEventListener("click",()=>{console.log("Boton pulsado");
}) */
//Al pulsar agregar, saco por consola: Nombre, apellido y fecha pero si hay campos vacios que salte un alert
//Al pulsar limpiar, que se limpien los campos input
/* let botonAgregar=document.querySelector("button")
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
    
}) */

/*     function lanzarDialogo(parms) {
        Swal.fire({
  title: title,
  text: text,
  icon: icon
});
    } */ 

let botonAgregar = document.querySelector("#btnAgregar");
let botonLimpiar = document.querySelector("#btnLimpiar");
let inputNombre = document.querySelector("#inputNombre");
let inputApellido = document.querySelector("#inputApellido");
let inputFecha = document.querySelector("#inputFecha");
let listaAgregados = document.querySelector("#divAgregados ul");
botonAgregar.addEventListener("click", (ev) => {
  let nombre = inputNombre.value;
  let apellido = inputApellido.value;
  let fecha = inputFecha.value;

  let cosa = "";
  if (nombre.length == 0 || apellido.length == 0 || fecha.length == 0) {
    lanzarDialogo("Error", "Faltan datos", "warning");
  } else {
    agregarLi(nombre,apellido,fecha);
  }
});

botonLimpiar.addEventListener("click", (ev) => {
  limpiarCampos(inputNombre, inputApellido, inputFecha);
});

function limpiarCampos() {
  for (let index = 0; index < arguments.length; index++) {
    arguments[index].value = "";
  }
}

function lanzarDialogo(title, text, icon) {
  Swal.fire({
    title: title,
    text: text,
    icon: icon,
  });
}

function agregarLi(nombre, apellido, fecha) {
    listaAgregados.innerHTML='<h1 class="animate__animated animate__bounce">An animated element</h1>'
  lanzarDialogo(
    "Agregado correctamente",
    `Usuario con nombre ${nombre} agregado correctamente`,
    "success"
  );
}