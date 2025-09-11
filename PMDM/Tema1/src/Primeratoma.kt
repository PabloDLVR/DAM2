fun main(){
    // comentario de linea
    println("Este es mi primer programa kotlin")

    // var -> variables mutables  puede cambiar su valor
    // val -> variables no mutables no pueden cambiar de valor

    var nombre:String = "Oscar" // String
    var edad:Int = 24 // Int
    val DNI:String = "123154a"
    lateinit var direcion: String
    var correo:String? = null

    println("Hola mi nombre es $nombre tengo $edad y mi DNI es $DNI")
    println("Mi nombre tiene ${nombre.length} letras")
    correo="correo1@cesjp.com"
    println("Mi correo es ${correo?.length ?: "Sin asisgnar"}")
    direcion = "Alcorcon"
    println("Mi direccion es ${direcion}")



}

