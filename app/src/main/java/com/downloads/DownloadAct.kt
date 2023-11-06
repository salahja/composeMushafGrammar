package com.downloads

import android.app.DownloadManager
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.database.ContentObserver
import android.database.Cursor
import android.database.CursorIndexOutOfBoundsException
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.os.Handler
import android.os.Looper
import android.provider.Settings.Global.putString
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
import androidx.compose.material.Button
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Surface
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
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
import com.downloadmanager.FileDownloadWorker
import com.downloads.ui.theme.MushafApplicationTheme

import com.skyyo.expandablelist.theme.AppTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.File

class DownloadAct : ComponentActivity() {

    private lateinit var requestMultiplePermission: ActivityResultLauncher<Array<String>>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        requestMultiplePermission = registerForActivityResult(
            ActivityResultContracts.RequestMultiplePermissions()
        ){
            var isGranted = false
            it.forEach { s, b ->
                isGranted = b
            }

            if (!isGranted){
                Toast.makeText(this, "Permission Not Granted", Toast.LENGTH_SHORT).show()
            }
        }

        setContent {
            AppTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    requestMultiplePermission.launch(
                        /* input = */ arrayOf(
                           // int result = ContextCompat.checkSelfPermission(this, android.Manifest.permission.READ_EXTERNAL_STORAGE)
                          android.   Manifest.permission.READ_EXTERNAL_STORAGE,
                          android.  Manifest.permission.WRITE_EXTERNAL_STORAGE
                        )
                    )

                    Home()
                }
            }
        }


    }
    data class File(
        val id:String,
        val name:String,
        val type:String,
        val url:String,
        var downloadedUri:String?=null,
        var isDownloading:Boolean = false,
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
            val data = remember {
                mutableStateOf(
                    File(
                        id = "10",
                        name = "Pdf File 10 MB",
                        type = "PDF",
                        url = "https://www.learningcontainer.com/wp-content/uploads/2019/09/sample-pdf-download-10-mb.pdf",
                        downloadedUri = null
                    )
                )
            }

            ItemFile(
                file = data.value,
                startDownload = {
                    startDownloadingFile(
                        file = data.value,
                        success = {
                            data.value = data.value.copy().apply {
                                isDownloading = false
                                downloadedUri = it
                            }
                        },
                        failed = {

                            data.value = data.value.copy().apply {
                                isDownloading = false
                                downloadedUri = null
                            }
                        },
                        running = {
                            data.value = data.value.copy().apply {
                                isDownloading = true
                            }
                        }
                    )
                },
                openFile = {
                    val intent = Intent(Intent.ACTION_VIEW)
                    intent.setDataAndType(it.downloadedUri?.toUri(),"application/pdf")
                    intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
                    try {
                        startActivity(intent)
                    }catch (e: ActivityNotFoundException){
                        Toast.makeText(
                            this@DownloadAct,
                            "Can't open Pdf",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            )
        }
    }

    @Composable
    fun ItemFile(
        file: File,
        startDownload:(File) -> Unit,
        openFile:(File) -> Unit
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .fillMaxWidth()
                .background(color = Color.White)
                .border(width = 2.dp, color = Color.Blue, shape = RoundedCornerShape(16.dp))
                .clickable {
                    if (!file.isDownloading){
                        if (file.downloadedUri.isNullOrEmpty()){
                            startDownload(file)
                        }else{
                            openFile(file)
                        }
                    }
                }
                .padding(16.dp)
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
                        val description = if (file.isDownloading){
                            "Downloading..."
                        }else{
                            if (file.downloadedUri.isNullOrEmpty()) "Tap to download the file" else "Tap to open file"
                        }
                        Text(
                            text = description,
                         //   style = Typography.body2,
                            color = Color.DarkGray
                        )
                    }

                }

                if (file.isDownloading){
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
        success:(String) -> Unit,
        failed:(String) -> Unit,
        running:() -> Unit
    ) {
        val data = Data.Builder()
        val workManager = WorkManager.getInstance(applicationContext)
        data.apply {
            putString(FileDownloadWorker.FileParams.KEY_FILE_NAME, file.name)
            putString(FileDownloadWorker.FileParams.KEY_FILE_URL, file.url)
            putString(FileDownloadWorker.FileParams.KEY_FILE_TYPE, file.type)
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

        workManager.enqueueUniqueWork(
            "oneFileDownloadWork_${System.currentTimeMillis()}",
            ExistingWorkPolicy.KEEP,
            fileDownloadWorker
        )


        workManager.getWorkInfoByIdLiveData(fileDownloadWorker.id)
            .observe(this){ info->
                info?.let {
                    when (it.state) {
                        WorkInfo.State.SUCCEEDED -> {
                            success(it.outputData.getString(FileDownloadWorker.FileParams.KEY_FILE_URI) ?: "")
                        }
                        WorkInfo.State.FAILED -> {
                            failed("Downloading failed!")
                        }
                        WorkInfo.State.RUNNING -> {
                            running()
                        }
                        else -> {
                            failed("Something went wrong")
                        }
                    }
                }
            }
    }


}





object FileParams{
    const val KEY_FILE_URL = "key_file_url"
    const val KEY_FILE_TYPE = "key_file_type"
    const val KEY_FILE_NAME = "key_file_name"
    const val KEY_FILE_URI = "key_file_uri"
}

object NotificationConstants{
    const val CHANNEL_NAME = "download_file_worker_demo_channel"
    const val CHANNEL_DESCRIPTION = "download_file_worker_demo_description"
    const val CHANNEL_ID = "download_file_worker_demo_channel_123456"
    const val NOTIFICATION_ID = 1
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





