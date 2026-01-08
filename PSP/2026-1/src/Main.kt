import org.apache.commons.net.ftp.FTP
import org.apache.commons.net.ftp.FTPClient

fun main() {
    val ftp = FTPClient()
    try {
        ftp.connect("ftp.rediris.es", 21)
        ftp.login("Javier", "123456")
        ftp.enterLocalPassiveMode()
        ftp.setFileType(FTP.BINARY_FILE_TYPE)
        //ftp.logout()

        val ficheros = ftp.listFiles("/sites")
        for (fichero in ficheros) {
            if (fichero.isFile) {
                println("Fichero :${fichero.name} Tamaño: ${fichero.size} bytes")
            } else if (fichero.isDirectory) {
                println("Directorio: ${fichero.name}")
            }
        }
    } catch (e: Exception) {
        e.printStackTrace()
    }
}