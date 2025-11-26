//nominales - flecha 
//Nominal -> function (denttro de una clase) (parametros){}
function realizarCalculo(op1,op2) {
    console.log(`La suma de los dos parametros es: ${op1+op2}`);
}
function realizarCalculoRetorno(op1,op2) {
    return op1+op2;
}
function realizarCalculoDefecto(op1,op2=7) {
    return op1+op2
}
function realizarCalculoFantasma(op1) {
    console.log("Calculo fantasma");
    console.log(op1+" Es el parametro requerido");
    //arguments -> argumentos fanstama que son pasados adicionalmete..[]
    console.log(`El numero de argumentos fantasma es ${arguments.length}`);
    //hagais la suma de todos los argumentos que me pasan por parametros, indicalos
    //cuantos son paados y cuantos son fantasma
    //hay 4 argumentos, 3 fantasmas
    //la suma de todos es 10
    suma=0
    
    for (let i = 0; i < arguments.length; i++) {
        console.log(i);
    }
}
realizarCalculoFantasma(1,2,3,4)
/* console.log(`La suma con retorn y default es: ${realizarCalculoDefecto(5)}`); */
/* console.log(`El resultado de la operacion con retorno es: ${realizarCalculoRetorno(4,5)}`); */
