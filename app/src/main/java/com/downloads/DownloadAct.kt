package com.downloads

import android.Manifest
import android.content.ActivityNotFoundException
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Surface
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

import androidx.compose.ui.unit.dp
import androidx.core.net.toUri
import androidx.work.Constraints
import androidx.work.Data
import androidx.work.ExistingWorkPolicy
import androidx.work.NetworkType
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkInfo
import androidx.work.WorkManager
import com.appscreens.downloadLink
import com.appscreens.readerID
import com.downloadmanager.FileDownloadWorker
import com.example.mushafconsolidated.Entities.Qari
import com.example.mushafconsolidated.Entities.QuranEntity
import com.example.mushafconsolidated.Utils
import com.example.mushafconsolidated.receiversimport.AudioAppConstants
import com.example.mushafconsolidated.receiversimport.QuranValidateSources
import com.example.utility.QuranGrammarApplication
import com.example.viewmodels.DownloadFileListArray
import com.example.viewmodels.ExpandableCardModelSpannableLists

import com.skyyo.expandablelist.theme.AppTheme
import java.io.File

class DownloadAct : ComponentActivity() {

    private lateinit var datas: DownloadAct.File
    private lateinit var data: MutableState<File>
    private lateinit var testList: ArrayList<DownloadAct.File>
    private lateinit var requestMultiplePermission: ActivityResultLauncher<Array<String>>
    private lateinit var permissionHelper : PermissionHelper
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val Links: List<String>? =           createDownloadLinks()
        val links = Links
       val arr= listOf<File>()
        handlePerms()

        setContent {
            AppTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                  if(Links!!.isNotEmpty()) {
                        createPath()
                        Home()
                    }else{

                    }
                }
            }
        }


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

    private fun handlePerms() {
        if(Build.VERSION.SDK_INT < Build.VERSION_CODES.Q) {
            permissionHelper = PermissionHelper(this,
                arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE), 100)

            permissionHelper.denied {
                if (it) {
                    Toast.makeText(this, "Permission denied by system", Toast.LENGTH_LONG).show()
                    permissionHelper.openAppDetailsActivity()
                } else {
                    Toast.makeText(this, "Permission denied", Toast.LENGTH_LONG).show()
                }
            }
            //Request all permission
            permissionHelper.requestAll {}
        }
    }
    fun createDownloadLinks(): List<String> {
        lateinit var readersList: List<Qari>
         var files: ArrayList<File> = ArrayList<File>()
          testList = arrayListOf<File>()


        val lists: ArrayList<String> = ArrayList<String>()
   //     val testList = arrayListOf<DownloadFileListArray>()
        val repository= Utils(QuranGrammarApplication.context)
        readersList = repository.qaris
        val chap = repository.getSingleChapter(1)
        val quranbySurah: List<QuranEntity?>? = repository.getQuranbySurah(1)
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

                    testList+=File(
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

    @Composable
    fun Home() {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(32.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            for (i in 0 until testList.size) {


          //  for (links in testList) {
             datas =testList[i]

          //  }
            ItemFile(
                file = datas,
                //   file=testList.get(0),
                startDownload = {
                    startDownloadingFile(
                        //   file=testList.get(0),
                        file = datas,
                       success = {

                           datas= datas.copy().apply {
                                isDownloading = false
                                downloadedUri = it
                            }
                        },
                        failed = {

                            datas = datas.copy().apply {
                                isDownloading = false
                                downloadedUri = null
                            }
                        },
                        running = {
                            datas = datas.copy().apply {
                                isDownloading = true
                            }
                        }
                    )
                },
                openFile = {
                    val intent = Intent(Intent.ACTION_VIEW)
                    intent.setDataAndType(it.downloadedUri?.toUri(), "application/mp3")
                    intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
                    try {
                        startActivity(intent)
                    } catch (e: ActivityNotFoundException) {
                        Toast.makeText(
                            this@DownloadAct,
                            "Can't open Pdf",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            )

        }//for
        }
    }

    @Composable
    fun ItemFile(
        file: File,
        startDownload: (File) -> Unit,
        openFile: (File) -> Unit
    ) {
        if (!file.isDownloading) {
            if (file.downloadedUri.isNullOrEmpty()) {
                startDownload(file)
            } else {
                openFile(file)
            }
        }
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .fillMaxWidth()
                .background(color = Color.White)
                .border(width = 2.dp, color = Color.Blue, shape = RoundedCornerShape(16.dp))

        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth(0.8f)
                ) {
                    Text(
                        text = file.name,
                        //   style = Typography.body1,
                        color = Color.Black
                    )

                    Row {
                      /*  val description = if (file.isDownloading) {
                            "Downloading..."
                        } else {
                            if (file.downloadedUri.isNullOrEmpty()) "Tap to download the file" else "Tap to open file"
                        }
                        Text(
                            text = description,
                            //   style = Typography.body2,
                            color = Color.DarkGray
                        )*/
                    }

                }

                if (file.isDownloading) {
                    CircularProgressIndicator(
                        color = Color.Blue,
                        modifier = Modifier
                            .size(32.dp)
                            .align(Alignment.CenterVertically)
                    )
                }

            }

        }
    }

    private fun startDownloadingFile(
        file: File,
        success: (String) -> Unit,
        failed: (String) -> Unit,
         running: () -> Unit
    ) {


    //    val workManager = WorkManager.getInstance(this)
     //   val workRequest = OneTimeWorkRequestBuilder<ForegroundWorker>().build()
        val app_folder_path =
            Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
                .toString() + "/audio/" + readerID
        val f = File(app_folder_path)
        val path = f.absolutePath
        val files = File(path)
        if (!files.exists()){ files.mkdirs()}

        val data = Data.Builder()
        val workManager = WorkManager.getInstance(applicationContext)
        data.apply {
            putString(FileDownloadWorker.FileParams.KEY_FILE_NAME, file.name)
            putString(FileDownloadWorker.FileParams.KEY_FILE_URL, file.url)
            putString(FileDownloadWorker.FileParams.KEY_FILE_TYPE, file.type)
            putString(FileDownloadWorker.FileParams.KEY_FILE_PATH,   "Download/audio/" + readerID)

//  put(MediaStore.MediaColumns.RELATIVE_PATH, "Download/Audio")
        }

        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .setRequiresStorageNotLow(true)
            .setRequiresBatteryNotLow(true)
            .build()

        val fileDownloadWorker = OneTimeWorkRequestBuilder<FileDownloadWorker>()
            .setConstraints(constraints)
            .setInputData(data.build())
            .build()

      //  workManager.enqueue(fileDownloadWorker)


        workManager.enqueueUniqueWork(
            "oneFileDownloadWork_${System.currentTimeMillis()}",
            ExistingWorkPolicy.KEEP,
            fileDownloadWorker
        )




        workManager.getWorkInfoByIdLiveData(fileDownloadWorker.id)
            .observe(this) { info ->
                info?.let {
                    when (it.state) {
                        WorkInfo.State.SUCCEEDED -> {
                            success(
                                it.outputData.getString(FileDownloadWorker.FileParams.KEY_FILE_URI)
                                    ?: ""
                            )
                        }

                        WorkInfo.State.FAILED -> {
                            failed("Downloading failed!")
                        }

                        WorkInfo.State.RUNNING -> {
                            running()
                        }
                        WorkInfo.State.FAILED->{
                            println("WorkInfo")
                        }
                        WorkInfo.State.BLOCKED->{
                            println("WorkInfo")
                        }
                        else -> {
                            failed("Something went wrong")
                        }
                    }
                }
            }
    }


}




/*  private val url: String = "https://i.imgur.com/ayo3pHA.mp4"
  var scanUri = "content://downloads/my_downloads"
  var seekBarValue: MutableState<Float> = mutableStateOf(0F)

  override fun onCreate(savedInstanceState: Bundle?) {
      super.onCreate(savedInstanceState)
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
                 downLoadMedia(scope)

                  }) {
                  Text(text="Download")
                  }
              }
          }
      }
  }

  private fun downLoadMedia(scope: CoroutineScope) {


      val downloadManager =getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
      val request = DownloadManager.Request(Uri.parse(url)).apply {
          setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI or DownloadManager.Request.NETWORK_MOBILE)
              .setAllowedOverRoaming(false)
              .setTitle(url.substring(url.lastIndexOf("/") + 1))
              .setDescription("")
              .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
              // Notifications for DownloadManager are optional and can be removed
              .setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS,"filename.mp4"

              )
      }

    val  downloadId = downloadManager.enqueue(request)
      val query = DownloadManager.Query().setFilterById(downloadId)
   val observer=object :ContentObserver(
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
      )*/
/*
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




        }*/





/* data = remember {
                  mutableStateOf(
                     links
                      *//*      File(
                        id = "10",
                        name = "Pdf File 10 MB",
                        type = "PDF",
                        url = "https://www.learningcontainer.com/wp-content/uploads/2019/09/sample-pdf-download-10-mb.pdf",
                        downloadedUri = null
                    )*//*
                    )
                }*/