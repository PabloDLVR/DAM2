let lista = ["Palabra1","Palabra2"];
lista.push("Palabra3","Palabra4")//Coloca las cosas al final del array
lista.unshift("Palabra5","Palabra6")//Coloca las cosas al principio del array
/* lista[2]="Gay"
lista[100]="Feo" */
lista.pop()//elimina elemento del final de la lista
lista.shift()//elimina el primer elemento de la lista

lista = lista.filter((item,index)=>{return item!="Palabra3"});//Item valor, index posicion.
lista = lista.find((item,index)=>{return item!="Palabra3"});//Te devuelve solo un objeto
console.log(lista);
