import org.apache.commons.net.ftp.FTP
import org.apache.commons.net.ftp.FTPClient
import java.io.FileInputStream
import java.io.FileOutputStream

fun main() {
    val ftp = FTPClient()
    try {
        ftp.connect("127.0.0.1", 21)
        ftp.login("DAM2026", "")
        ftp.enterLocalPassiveMode()
        ftp.setFileType(FTP.BINARY_FILE_TYPE)


        val output = FileOutputStream("C:\\Users\\pablo\\Desktop\\FTP\\Osky.exe")
        ftp.retrieveFile("/MONOS/Virus.exe", output)
        output.close()


    } catch (e: Exception) {
        e.printStackTrace()
    } finally {
        ftp.logout()
        ftp.disconnect()
    }
}