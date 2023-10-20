import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.os.Environment
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import java.io.File
import java.io.FileOutputStream
import java.io.FileWriter
import java.io.IOException

class FileUtility(var context: Context) {
    //to be delted
    fun writetofile(
        filename: String?,
        surahId: Int,
        verseId: Int,
        wordno: Int,
        wordsAr: String
    ): Int {
        val ammended = ArrayList<String>()
        ammended.add("$surahId|$verseId|$wordno|$wordsAr|")
        val state = Environment.getExternalStorageState()
        var status = 0
        if (Environment.MEDIA_MOUNTED == state) {
            var myWriter: FileWriter
            var s: String
            if (checkPermission()) {
                val sdcard = Environment.getExternalStorageDirectory()
                val dir = File(sdcard.absolutePath + "/text/")
                dir.mkdir()
                val file = File(dir, filename)
                val os: FileOutputStream
                try {
                    os = FileOutputStream(file, true)
                    //      os.write(harfNasbIndexArrayList.toString().getBytes());
                    for (str in ammended) {
                        os.write(str.toByteArray())
                        val newline = "\n"
                        os.write(newline.toByteArray())
                    }
                    os.close()
                    status = 1
                } catch (e: IOException) {
                    e.printStackTrace()
                }
            } else {
                requestPermission() // Code for permission
            }
        }
        return status
    }

    private fun requestPermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(
                (context as Activity),
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            )
        ) {
            Toast.makeText(
                context as Activity,
                "Write External Storage permission allows us to create files. Please allow this permission in App Settings.",
                Toast.LENGTH_LONG
            ).show()
        } else {
            ActivityCompat.requestPermissions(
                (context as Activity),
                arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),
                100
            )
        }
    }

    private fun checkPermission(): Boolean {
        val result = ContextCompat.checkSelfPermission(
            (context as Activity),
            Manifest.permission.WRITE_EXTERNAL_STORAGE
        )
        return result == PackageManager.PERMISSION_GRANTED
    }
}