console.log("Esto se ejecuta desde la carpeta JS")
/*let datos= "Pagina JS"
var asignatura="DI"//Variable que se puede acceder desde cualquier ámbito
if(true){
    const DNI="1234A" //Variable constante valor definidio no variable
    let nombre= "Pablo"; //Variable que guarda el ambito
    console.log(nombre)    
    console.log(DNI)    
}
console.log(datos)
console.log(asignatura)*/
/* let nombre="Pablo";//Strinf
let edad=24;//Number == Int
let altura=1.85;//Number
let experiencia=false;//Boolean
let elementos=[];//Object -> Array
let cosa=null;//Object -> null
let dato;//Undefined */
//NaN -> not a Number -> isNaN()
/*console.log(typeof nombre);// El typeoff te muestra de que tipo es la variable
console.log(typeof edad);
console.log(typeof altura);
console.log(typeof experiencia);
console.log(typeof elementos);
console.log(typeof cosa);
console.log(typeof dato);*/
/* console.log(`Mi nombre es ${nombre} y tengo experencia en el sector ${experiencia}`);
console.log(`Mi nombre es ${nombre}`); */
/* alert("Guille es un virgen")//Alert sirve para que te haga un pop-up la pagina
let respuesta= confirm("Estas seguro de que Oscar es un domado?")//TE obliga el poop-up a que aceptes si quiere ver la pagina
if(respuesta==true){
    alert("El usuario ha pulsado aceptar")
}else{
    alert("El usuario ha pulsado cancelar")
} */
let nombre = prompt("Porfavor itroduce tu nombre");
let respuesta = true

while(respuesta){
    let op1 = prompt("Porfavor itroduce el pimer operando");
    let op2 = prompt("Porfavor itroduce el segundo operando");

    if(isNaN(op1) && isNaN(op2)){
        
    }else{
        let suma = op1+op2;
        let resta = op1-op2;
        let multiplicar = op1*op2;
        let division = op1/op2;
        console.log(`El resultado de suma es ${suma}`);
        console.log(`El resultado de suma es ${resta}`);
        console.log(`El resultado de suma es ${multiplicar}`);
        console.log(`El resultado de suma es ${division}`);
        
    }
}


