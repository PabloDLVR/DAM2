package controller

import model.*

class ShieldController {
    private val armas = mutableListOf<Arma>()
    private val heroes = mutableListOf<Superheroe>()

    // ------------------ CREAR ELEMENTOS ------------------

    fun crearArmaTradicional() {
        print("ID: "); val id = readLine()?.toIntOrNull() ?: return
        print("Nombre: "); val nombre = readLine() ?: ""
        print("Nivel de potencia: "); val pot = readLine()?.toIntOrNull() ?: 0
        print("Nivel de daño: "); val dano = readLine()?.toIntOrNull() ?: 0
        print("Peso: "); val peso = readLine()?.toDoubleOrNull() ?: 0.0

        armas.add(ArmaTradicional(id, nombre, pot, dano, peso))
        println("✅ Arma tradicional registrada.")
    }

    fun crearArmaBiologica() {
        print("ID: "); val id = readLine()?.toIntOrNull() ?: return
        print("Nombre: "); val nombre = readLine() ?: ""
        print("Nivel de potencia: "); val pot = readLine()?.toIntOrNull() ?: 0
        print("Nivel de daño: "); val dano = readLine()?.toIntOrNull() ?: 0
        print("Descripción: "); val desc = readLine() ?: ""

        armas.add(ArmaBiologica(id, nombre, pot, dano, desc))
        println("✅ Arma biológica registrada.")
    }

    fun crearHumano() {
        if (armas.none { it is ArmaTradicional }) {
            println("❌ No hay armas tradicionales registradas.")
            return
        }

        print("ID héroe: "); val id = readLine()?.toIntOrNull() ?: return
        print("Nombre: "); val nombre = readLine() ?: ""
        print("Nivel: "); val nivel = readLine()?.toIntOrNull() ?: 0
        print("Resistencia: "); val res = readLine()?.toIntOrNull() ?: 0

        listarArmas()
        print("ID del arma tradicional: "); val idArma = readLine()?.toIntOrNull() ?: return
        val arma = armas.find { it.id == idArma && it is ArmaTradicional } as? ArmaTradicional

        if (arma == null) {
            println("❌ Arma no válida.")
            return
        }

        heroes.add(Humano(id, nombre, nivel, res, arma))
        println("✅ Superhéroe humano registrado.")
    }

    fun crearMutante() {
        if (armas.none { it is ArmaBiologica }) {
            println("❌ No hay armas biológicas registradas.")
            return
        }

        print("ID héroe: "); val id = readLine()?.toIntOrNull() ?: return
        print("Nombre: "); val nombre = readLine() ?: ""
        print("Nivel: "); val nivel = readLine()?.toIntOrNull() ?: 0

        listarArmas()
        print("ID del arma biológica: "); val idArma = readLine()?.toIntOrNull() ?: return
        val arma = armas.find { it.id == idArma && it is ArmaBiologica } as? ArmaBiologica

        if (arma == null) {
            println("❌ Arma no válida.")
            return
        }

        heroes.add(Mutante(id, nombre, nivel, arma))
        println("✅ Superhéroe mutante registrado.")
    }

    // ------------------ LISTADOS ------------------

    fun listarArmas() {
        if (armas.isEmpty()) println("No hay armas registradas.")
        else armas.forEach { println(it) }
    }

    fun listarHeroes() {
        if (heroes.isEmpty()) println("No hay superhéroes registrados.")
        else heroes.forEach { println(it) }
    }

    // ------------------ COMBATE ------------------

    fun enfrentar() {
        if (heroes.size < 2 || armas.isEmpty()) {
            println("❌ Faltan héroes o armas para realizar un combate.")
            return
        }

        listarHeroes()
        print("ID del contrincante 1: "); val id1 = readLine()?.toIntOrNull() ?: return
        listarArmas()
        print("ID del arma 1: "); val arma1 = readLine()?.toIntOrNull() ?: return

        print("ID del contrincante 2: "); val id2 = readLine()?.toIntOrNull() ?: return
        listarArmas()
        print("ID del arma 2: "); val arma2 = readLine()?.toIntOrNull() ?: return

        val h1 = heroes.find { it.id == id1 }
        val h2 = heroes.find { it.id == id2 }
        val a1 = armas.find { it.id == arma1 }
        val a2 = armas.find { it.id == arma2 }

        if (h1 == null || h2 == null || a1 == null || a2 == null) {
            println("❌ IDs incorrectos.")
            return
        }

        val poder1 = h1.nivel + (a1.nivelPotencia * a1.nivelDanyo)
        val poder2 = h2.nivel + (a2.nivelPotencia * a2.nivelDanyo)

        println("\n⚔️ ${h1.nombre} (Poder: $poder1) VS ${h2.nombre} (Poder: $poder2) ⚔️")

        when {
            poder1 > poder2 -> println("🏆 Gana ${h1.nombre}")
            poder2 > poder1 -> println("🏆 Gana ${h2.nombre}")
            else -> println("🤝 Empate técnico")
        }
    }
}
