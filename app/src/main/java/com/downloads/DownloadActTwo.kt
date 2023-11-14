package com.downloads

import android.app.DownloadManager
import android.content.Context
import android.database.ContentObserver
import android.database.Cursor
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.ActivityResultLauncher
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import com.appscreens.downloadLink
import com.appscreens.readerID
import com.downloads.ui.theme.MushafApplicationTheme
import com.example.mushafconsolidated.Entities.Qari
import com.example.mushafconsolidated.Entities.QuranEntity
import com.example.mushafconsolidated.Utils
import com.example.mushafconsolidated.receiversimport.AudioAppConstants
import com.example.mushafconsolidated.receiversimport.QuranValidateSources
import com.example.utility.QuranGrammarApplication
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.File

class DownloadActTwo : ComponentActivity() {

    private lateinit var datas: DownloadActTwo.File



    private lateinit var data: MutableState<File>
    private lateinit var testLists: ArrayList<DownloadActTwo.File>
    private lateinit var requestMultiplePermission: ActivityResultLauncher<Array<String>>
    private lateinit var permissionHelper : PermissionHelper
    private val url: String = "https://i.imgur.com/ayo3pHA.mp4"
    var scanUri = "content://downloads/my_downloads"
    var seekBarValue: MutableState<Float> = mutableStateOf(0F)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val Links: List<String>? =           createDownloadLinks()
        val links = Links
       val arr= listOf<File>()


        setContent {
            setContent {
                val scope: CoroutineScope = rememberCoroutineScope()
                MushafApplicationTheme {
                    seekBarValue = remember {
                        mutableStateOf(0F)
                    }

                    // A surface container using the 'background' color from the theme
                    Column(
                        modifier = Modifier.fillMaxSize(),

                        ) {


                        Slider(value = (seekBarValue.value)/100, onValueChange = {
                            seekBarValue.value = it
                        } )

                        Button(onClick = {

                                for (ln in testLists) {
                                    downLoadMedia(scope,ln.url,ln.name)
                                }



                        }) {
                            Text(text="Download")
                        }
                    }
                }
            }
            }
        }
///
private fun downLoadMedia(scope: CoroutineScope, url: String, filename: String) {
    val app_folder_path =
        Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
            .toString() + "/audio/" + readerID
    val f = File(app_folder_path)
    val path = f.absolutePath
    val files = File(path)
    if (!files.exists()){ files.mkdirs()}


    val downloadManager =getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
    val request = DownloadManager.Request(Uri.parse(url)).apply {
        setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI or DownloadManager.Request.NETWORK_MOBILE)
            .setAllowedOverRoaming(false)
            .setTitle(url.substring(url.lastIndexOf("/") + 1))
            .setDescription("")
            .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
            // Notifications for DownloadManager are optional and can be removed
            .setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS,"afasy/"+filename

            )
    }

    val  downloadId = downloadManager.enqueue(request)
    val query = DownloadManager.Query().setFilterById(downloadId)
    val observer=object : ContentObserver(
        Handler(Looper.getMainLooper() ) ){

        override fun onChange(selfChange: Boolean) {
            super.onChange(selfChange)

            val cursor: Cursor = downloadManager.query(query)
            cursor.moveToFirst()

            if(cursor!=null && cursor.moveToFirst()){
                val total  = cursor.getLong(cursor.getColumnIndexOrThrow(DownloadManager.COLUMN_TOTAL_SIZE_BYTES));
                // Progress ->

                val downloaded  = cursor.getLong(cursor.getColumnIndexOrThrow(DownloadManager.COLUMN_BYTES_DOWNLOADED_SO_FAR));
                val currentProgress: Int = downloaded.toInt()
                seekBarValue.value=currentProgress.toFloat()
            }

        }
    }
    contentResolver.registerContentObserver(
        Uri.parse(scanUri),true,observer
    )

            scope.launch(Dispatchers.IO) {

                    var downloading = true





                    // The following is to set statuses for the present download

                        val cursor: Cursor = downloadManager.query(query)
                        cursor.moveToFirst()

                        if(cursor!=null && cursor.moveToFirst()){
                            val total  = cursor.getLong(cursor.getColumnIndexOrThrow(DownloadManager.COLUMN_TOTAL_SIZE_BYTES));
                            // Progress ->

                            val downloaded  = cursor.getLong(cursor.getColumnIndexOrThrow(DownloadManager.COLUMN_BYTES_DOWNLOADED_SO_FAR));
                            val currentProgress: Int = downloaded.toInt()
                            seekBarValue.value=currentProgress.toFloat()
                        }


                        // Download finished
                        if (cursor.getInt(cursor.getColumnIndexOrThrow(DownloadManager.COLUMN_STATUS)) == DownloadManager.STATUS_SUCCESSFUL) {
                            downloading = false
                        }

                        // Changing statuses ->




            }



    //
}

    private fun createPath() {
        ///storage/emulated/0/Documents/audio/20
        /*
        file = {File@26060} "/storage/emulated/0/Documents/audio/20"
 0 = {File@26201} "/storage/emulated/0/Documents/audio/20/020001.mp3"
 1 = {File@26202} "/storage/emulated/0/Documents/audio/20/020002.mp3"
 2 = {File@26203} "/storage/emulated/0/Documents/audio/20/020003.mp3"
 3 = {File@26204} "/storage/emulated/0/Documents/audio/20/020004.mp3"
         */
        val app_folder_path =
            Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS)
                .toString() + "/audio/" + readerID
        val f = File(app_folder_path)
        val path = f.absolutePath
        val file = File(path)
        if (!file.exists()) file.mkdirs()

    }

    fun createDownloadLinks(): List<String> {
        lateinit var readersList: List<Qari>
         var files: ArrayList<File> = ArrayList<File>()
        testLists = arrayListOf<File>()


        val lists: ArrayList<String> = ArrayList<String>()
   //     val testList = arrayListOf<DownloadFileListArray>()
        val repository= Utils(QuranGrammarApplication.context)
        readersList = repository.qaris
        val chap = repository.getSingleChapter(100)
        val quranbySurah: List<QuranEntity?>? = repository.getQuranbySurah(100)
        //   surahselected = surah
        //   int ayaID=0;
        var counter = 0
        //   quranbySurah.add(0, new QuranEntity(1, 1, 1));
        val downloadLinks: MutableList<String> = java.util.ArrayList()
        //   ayaList.add(0, new Aya(1, 1, 1));
        //loop for all page ayat
//check if readerID is 0
        if (readerID == 0) {

            for (qari in readersList) {
                val selectedqari = "Mishary Rashed Al-Afasy"
                if (qari.name_english == selectedqari) {
                    readerID = qari.id
                //    downloadLink = qari.url
                    downloadLink =    "https://mirrors.quranicaudio.com/everyayah/Alafasy_128kbps/"
                    break
                }
            }
        }
        downloadLink =    "https://mirrors.quranicaudio.com/everyayah/Alafasy_128kbps/"
        if (quranbySurah != null) {
            for (ayaItem in quranbySurah) {
                //validate if aya download or not
                if (!QuranValidateSources.validateAyaAudio(
                        applicationContext,
                        readerID,
                        ayaItem!!.ayah,
                        ayaItem.surah
                    )
                ) {

                    //create aya link


                    //create aya link
                    val suraLength: Int =
                        chap!![0]!!.chapterid.toString().trim { it <= ' ' }.length
                    var suraID: String = chap[0]!!.chapterid.toString() + ""


                    val ayaLength = ayaItem.ayah.toString().trim { it <= ' ' }.length
                    //   int ayaLength = String.valueOf(ayaItem.ayaID).trim().length();
                    //   int ayaLength = String.valueOf(ayaItem.ayaID).trim().length();
                    var ayaID = java.lang.StringBuilder(
                        java.lang.StringBuilder().append(ayaItem.ayah).append("").toString()
                    )
                    if (suraLength == 1) suraID =
                        "00" + ayaItem.surah else if (suraLength == 2) suraID = "0" + ayaItem.surah

                    if (ayaLength == 1) {
                        ayaID = java.lang.StringBuilder("00" + ayaItem.ayah)
                    } else if (ayaLength == 2) {
                        ayaID = java.lang.StringBuilder("0" + ayaItem.ayah)
                    }
                    counter++
                    //add aya link to list
                    //chec
                    downloadLinks.add(downloadLink + suraID + ayaID + AudioAppConstants.Extensions.MP3)
                   /*
                           val id: String,
        val name: String,
        val type: String,
        val url: String,
        var downloadedUri: String? = null,
        var isDownloading: Boolean = false,
            id = "10",
                        name = "Pdf File 10 MB",
                        type = "PDF",
                        url = "https://www.learningcontainer.com/wp-content/uploads/2019/09/sample-pdf-download-10-mb.pdf",
                        downloadedUri = null
                    */
                    val string = counter.toString()
                    val linkItem = downloadLink + suraID + ayaID + AudioAppConstants.Extensions.MP3
                val    fileName = linkItem.substring(linkItem.lastIndexOf('/') + 1, linkItem.length)
                    var filePath = downloadLink///storage/emulated/0/Documents/audio/20

                    testLists+=File(
                      string,fileName,"MP3", downloadLink + suraID + ayaID + AudioAppConstants.Extensions.MP3

                    )
                    Log.d(
                        "DownloadLinks",
                        downloadLink + suraID + ayaID + AudioAppConstants.Extensions.MP3
                    )
                }
            }
        }
        return downloadLinks
    }
    data class File(
        val id: String,
        val name: String,
        val type: String,
        val url: String,
        var downloadedUri: String? = null,
        var isDownloading: Boolean = false,
    )





}