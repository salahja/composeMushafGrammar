package com.downloadmanager

import android.app.DownloadManager
import android.app.NotificationManager
import android.content.Context
import android.database.Cursor
import android.database.CursorIndexOutOfBoundsException
import android.net.Uri
import android.os.Environment
import android.util.Log
import android.widget.Toast
import androidx.compose.runtime.mutableStateOf
import androidx.core.app.NotificationCompat
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alorma.compose.settings.storage.preferences.BooleanPreferenceSettingValueState
import com.appscreensn.readerID
import com.downloads.DownloadActThree
import com.example.mushafconsolidated.Entities.Qari
import com.example.mushafconsolidated.Entities.QuranEntity
import com.example.mushafconsolidated.Utils
import com.example.mushafconsolidated.receiversimport.AudioAppConstants
import com.example.mushafconsolidated.receiversimport.FileManager
import com.example.mushafconsolidated.receiversimport.QuranValidateSources
import com.example.utility.QuranGrammarApplication
import com.example.utility.QuranGrammarApplication.Companion.context
import com.google.android.exoplayer2.MediaItem
import com.viewmodels.FIleDownloadParam
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.io.File
var downloadlists: List<FIleDownloadParam>?=null
class DownloaderViewModel(chapterid: Int, isdark: BooleanPreferenceSettingValueState) : ViewModel() {


    private val _marray = MutableStateFlow(listOf<MediaItem>())


    val marray: StateFlow<List<MediaItem>> get() = _marray


   // var marray: MutableList<MediaItem> = arrayListOf()




    private lateinit var downloadUri: Uri
    private var notificationManager: NotificationManager? = null
    private var builder: NotificationCompat.Builder? = null
    private var notificationDownloaderFlag: Boolean?=null
    private var notificationDivider = 0
    // This will be updated on the basis of the
    private val _status: MutableLiveData<String> = MutableLiveData()
    val status: LiveData<String> = _status
    val loading = mutableStateOf(false)

    // LiveData for progress
    private val _progress: MutableLiveData<List<Int>> = MutableLiveData()
    val progress: LiveData<List<Int>> = _progress

    // This is just to keep track of statuses when the download is running
    private var lastMsg: String = ""

    private lateinit var directory: File
    private lateinit var url: String
    private var downloadId: Long = -1L

    val ayaLocations: MutableList<String> = ArrayList()


    init {
     downLoads(chapterid,isdark)
 }


     fun downLoads(chapterid: Int, isdark: BooleanPreferenceSettingValueState) {
         viewModelScope.launch {
             val repository = Utils(context)

             downloadlists = arrayListOf<FIleDownloadParam>()
             val quranbySurah: List<QuranEntity?>? = repository.getQuranbySurah(
                 chapterid
             )
             //get files from dabase to play or create links to download
             val testList = arrayListOf<MediaItem>()
             val Links: List<String>? = getAudioLinksOrDownloadlinks(chapterid)
             if (Links!!.isEmpty()) {
                 for (ayaItem in quranbySurah!!) {

                     val location = FileManager.createAyaAudioLinkLocation(
                         QuranGrammarApplication.context,
                         readerID,
                         ayaItem!!.ayah,
                         ayaItem!!.surah
                     )
                     testList.add(MediaItem.fromUri(location))
                     _marray.emit(testList)
                 }

             } else {
                 for (ln in downloadlists!!) {
                     //     downLoadMedia(scope,ln.url,ln.name)


                     downloadMedia(downloadlists!!, ln.url, 0)

                 }
                 for (ayaItem in quranbySurah!!) {

                     val location = FileManager.createAyaAudioLinkLocation(
                         QuranGrammarApplication.context,
                         readerID,
                         ayaItem!!.ayah,
                         ayaItem!!.surah
                     )
                     testList.add(MediaItem.fromUri(location))
                     _marray.emit(testList)
                 }


             }

         }

    }

    private fun getAudioLinksOrDownloadlinks( chapterid: Int): List<String>? {
        lateinit var readersList: List<Qari>
        var files: ArrayList<DownloadActThree.File> = ArrayList<DownloadActThree.File>()

      downloadlists = arrayListOf<FIleDownloadParam>()

        val lists: ArrayList<String> = ArrayList<String>()
        //     val testList = arrayListOf<DownloadFileListArray>()
        val repository= Utils(QuranGrammarApplication.context)
        readersList = repository.qaris
        val chap = repository.getSingleChapter(chapterid)
        val quranbySurah: List<QuranEntity?>? = repository.getQuranbySurah(chapterid)
        //   surahselected = surah
        //   int ayaID=0;
        var counter = 0
        //   quranbySurah.add(0, new QuranEntity(1, 1, 1));
        val downloadLinks: MutableList<String> = java.util.ArrayList()
        //   ayaList.add(0, new Aya(1, 1, 1));
        //loop for all page ayat
//check if readerID is 0
        if (com.appscreens.readerID == 0) {

            for (qari in readersList) {
                val selectedqari = "Mishary Rashed Al-Afasy"
                if (qari.name_english == selectedqari) {
                    com.appscreens.readerID = qari.id
                    //    downloadLink = qari.url
                    com.appscreens.downloadLink =    "https://mirrors.quranicaudio.com/everyayah/Alafasy_128kbps/"
                    break
                }
            }
        }
        com.appscreens.downloadLink =    "https://mirrors.quranicaudio.com/everyayah/Alafasy_128kbps/"
        if (quranbySurah != null) {
            for (ayaItem in quranbySurah) {
                //validate if aya download or not
                if (!QuranValidateSources.validateAyaAudio(
                        QuranGrammarApplication.context!!,
                        com.appscreens.readerID,
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
                    downloadLinks.add(com.appscreens.downloadLink + suraID + ayaID + AudioAppConstants.Extensions.MP3)
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
                    val linkItem = com.appscreens.downloadLink + suraID + ayaID + AudioAppConstants.Extensions.MP3
                    val    fileName = linkItem.substring(linkItem.lastIndexOf('/') + 1, linkItem.length)
                    var filePath = com.appscreens.downloadLink///storage/emulated/0/Documents/audio/20

                    downloadlists as ArrayList<FIleDownloadParam> += FIleDownloadParam(
                        string,
                        fileName,
                        "MP3",
                        com.appscreens.downloadLink + suraID + ayaID + AudioAppConstants.Extensions.MP3

                    )
                    Log.d(
                        "DownloadLinks",
                        com.appscreens.downloadLink + suraID + ayaID + AudioAppConstants.Extensions.MP3
                    )
                }
            }
        }
        return downloadLinks
    }


    fun downloadMedia(list: List<FIleDownloadParam>, url: String, requestCode: Int) {
        loading.value = true


        // setting the url as top level property for later use ->
        this.url = url
        val app_folder_path =
            Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
                .toString() + "/audio/" + 20
        val f = File(app_folder_path)
        val path = f.absolutePath
        val files = File(path)
        if (!files.exists()) {
            files.mkdirs()
        }
        // Setting up the target Directory ->
        directory = File(Environment.DIRECTORY_DOWNLOADS, "/20/")
        if (!directory.exists()) {
            directory.mkdirs()
        }

     //   for (ln in list) {

            downloadUri = Uri.parse(url)
     //   }

        // parse url to uri

        when (requestCode) {
            0 -> {
                // Creating a request with all necessary methods for the details
                val request = DownloadManager.Request(downloadUri).apply {
                    setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI or DownloadManager.Request.NETWORK_MOBILE)
                        .setAllowedOverRoaming(false)
                        .setTitle(url.substring(url.lastIndexOf("/") + 1))
                        .setDescription("")
                        .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
                        // Notifications for DownloadManager are optional and can be removed
                        .setDestinationInExternalPublicDir(
                            Environment.DIRECTORY_DOWNLOADS,
                            "20/" + url.substring(url.lastIndexOf("/") + 1)

                        )
                }
                startDownload(request)
            }

            1 -> {
                cancelDownload()
            }
        }
    }

    /**
     * Cancels the download after checking if the download ID is not still the
     * default value set above (-1)
     * @param context is used for Toasts right from the viewModel & getting the DownloadManager
     * **/
    private fun cancelDownload() {
        // Calling the DownloadManagerService
        val downloadManager =
            context!!.getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
        if (downloadId != -1L) {
            downloadManager.remove(downloadId)
        } else {
            Toast.makeText(context, "Cancel Failed", Toast.LENGTH_SHORT).show()
        }
    }

    /**
     * Has everything to start the request formed in the downloadMedia() function
     * @param context to get the DownloadManager
     * @param request -> This is passed into the DownloadManager as this has all the file details
     */
    private fun startDownload(request: DownloadManager.Request) {
        // Calling the DownloadManagerService
        val downloadManager =
            context!!.getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager

        // starting the download
        downloadId = downloadManager.enqueue(request)
        val query = DownloadManager.Query().setFilterById(downloadId)
//      .setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS,"afasy/"+filename
        viewModelScope.launch(Dispatchers.IO) {
            try {
                var downloading = true
                // The following is to set statuses for the present download
                while (downloading) {
                    val cursor: Cursor = downloadManager.query(query)
                    cursor.moveToFirst()
                    val total: Long =
                        cursor.getLong(cursor.getColumnIndexOrThrow(DownloadManager.COLUMN_TOTAL_SIZE_BYTES));
                    // Progress ->
                    if (total >= 0) {
                        val downloaded: Long =
                            cursor.getLong(cursor.getColumnIndexOrThrow(DownloadManager.COLUMN_BYTES_DOWNLOADED_SO_FAR));
                        val currentProgress: Int = downloaded.toInt()
                        _progress.postValue(listOf(currentProgress, total.toInt()))
                    }

                    // Download finished
                    if (cursor.getInt(cursor.getColumnIndexOrThrow(DownloadManager.COLUMN_STATUS)) == DownloadManager.STATUS_SUCCESSFUL) {
                        downloading = false
                    }

                    // Changing statuses ->
                    val status =
                        cursor.getInt(cursor.getColumnIndexOrThrow(DownloadManager.COLUMN_STATUS))
                    val msg = statusMessage(url, directory, status)
                    if (msg != lastMsg) {
                        _status.postValue(msg!!)
                        lastMsg = msg ?: ""
                    }
                    cursor.close()
                }
            } catch (e: CursorIndexOutOfBoundsException) {
                _status.postValue("Download cancelled")
                _progress.postValue(listOf(0, 0))
            }
        }
        loading.value = false
    }

    /**
     * Sets Status Messages from the DownloadManager
     **/
    private fun statusMessage(url: String, directory: File, status: Int): String? {
        var msg = ""
        msg = when (status) {
            DownloadManager.STATUS_FAILED -> "Download has been failed, please try again"
            DownloadManager.STATUS_PAUSED -> "Paused"
            DownloadManager.STATUS_PENDING -> "Pending"
            DownloadManager.STATUS_RUNNING -> "Downloading..."
            DownloadManager.STATUS_SUCCESSFUL -> "Media downloaded successfully in $directory" + File.separator + url.substring(
                url.lastIndexOf("/") + 1
            )

            else -> "There's nothing to download"
        }
        return msg
    }

}