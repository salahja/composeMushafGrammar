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
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Column

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material.Button
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.app.NotificationCompat
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import com.appscreens.downloadLink
import com.appscreens.readerID
import com.downloadmanager.DownloaderViewModel
import com.downloads.ui.theme.MushafApplicationTheme
import com.example.compose.WordOccuranceLoading
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

class DownloadActThree : ComponentActivity() {

    private lateinit var datas: DownloadActThree.File



    private lateinit var data: MutableState<File>
    private lateinit var testLists: ArrayList<DownloadActThree.File>
    private lateinit var requestMultiplePermission: ActivityResultLauncher<Array<String>>
    private lateinit var permissionHelper : PermissionHelper
    private val url: String = "https://i.imgur.com/ayo3pHA.mp4"
    var scanUri = "content://downloads/my_downloads"
    var seekBarValue: MutableState<Float> = mutableStateOf(0F)

    var loading = mutableStateOf(true)
    private var builder: NotificationCompat.Builder? = null
    private var notificationDownloaderFlag: Boolean=false
    /*
     if (notificationDownloaderFlag) {
            //remoteViews.setProgressBar(R.id.progressBar2, , , false);
            builder!!.setProgress(values[1]!!.toInt(), values[0]!!.toInt(), false)
            notificationManager!!.notify(0, builder!!.build())
        }
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
         val downloaderViewModel by viewModels<DownloaderViewModel>()
        var loadings = downloaderViewModel.loading.value
        //var loading = downloaderViewModel.loading.value
        val Links: List<String>? =           createDownloadLinks()
        val links = Links
       val arr= listOf<File>()


        setContent {
            setContent {
                val localContext = LocalContext.current
                val scope: CoroutineScope = rememberCoroutineScope()
                displaybar()
                MushafApplicationTheme {
                    seekBarValue = remember {
                        mutableStateOf(0F)
                    }

                    // A surface container using the 'background' color from the theme
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.fillMaxWidth(),
                        ) {
                        loadings = downloaderViewModel.loading.value
                        WordOccuranceLoading(isDisplayed = loadings)

                        Slider(value = (seekBarValue.value)/100, onValueChange = {
                            seekBarValue.value = it
                        } )

                        Button(onClick = {

                                for (ln in testLists) {
                               //     downLoadMedia(scope,ln.url,ln.name)
                                   loading.value=true

                                    downloaderViewModel.downloadMedia(testLists, ln.url, 0)
                                    loading.value=false
                                }

                            downloaderViewModel.status.observe(this@DownloadActThree, Observer {
                                if(!it.isNullOrEmpty()) {
                                    Toast.makeText(localContext, "Selected Item: $it", Toast.LENGTH_SHORT)
                                        .show()

                                }
                            })

                            downloaderViewModel.progress.observe(this@DownloadActThree, {
                                it?.let {
                                    val percentage=(it[1]/100)
                                    println(it)
                                 //   val currentProgress: Int = downloaded.toInt()
                                    seekBarValue.value=percentage.toFloat()
                                    Log.d("Crying", "Progress is being set")
                                  //  binding.progressBar.progress = it[0]
                                  //  binding.progressBar.max = it[1]
                                }
                            })


                        }) {
                            Text(text="Download")
                        }


                    }
                }
            }
            }
        }

    @Composable
    private fun displaybar() {
        if (loading.value) {
            CircularProgressIndicator(
                color = Color.Blue,
                modifier = Modifier
                    .size(32.dp)

            )
        }
    }

    ///




    fun createDownloadLinks(): List<String> {
        lateinit var readersList: List<Qari>
         var files: ArrayList<File> = ArrayList<File>()
        testLists = arrayListOf<File>()


        val lists: ArrayList<String> = ArrayList<String>()
   //     val testList = arrayListOf<DownloadFileListArray>()
        val repository= Utils(QuranGrammarApplication.context)
        readersList = repository.qaris
        val chap = repository.getSingleChapter(60)
        val quranbySurah: List<QuranEntity?>? = repository.getQuranbySurah(60)
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