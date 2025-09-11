fun main(args: Array<String>) {
    val nombre = "arp -a"
    val proceso = ProcessBuilder("cmd.exe","/c","arp -a >fichero.txt")
    proceso.start()
    print("Se ha ejecutado $nombre")
}