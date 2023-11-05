package com.downloads

import android.app.DownloadManager
import android.content.Context
import android.database.ContentObserver
import android.database.Cursor
import android.database.CursorIndexOutOfBoundsException
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.os.Handler
import android.os.Looper
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
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
import com.downloads.ui.theme.MushafApplicationTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DownloadAct : ComponentActivity() {
    private val url: String = "https://i.imgur.com/ayo3pHA.mp4"
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
        )
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

    }
}





