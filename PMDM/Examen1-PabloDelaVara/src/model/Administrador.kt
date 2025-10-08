package model

class Administrador(var id: Int, var nombre: String, var clave: Int=1234) {

    fun iniciarLiga(clave: Int) {
        if (this.clave == clave) {
            println("Liga iniciada")
        } else {
            println("Clave incorrecta")
        }
    }

    override fun toString(): String {
        return "Administrador(id=$id, nombre='$nombre', clave=$clave)"
    }
}