package com.activities

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.crashlytics.buildtools.reloc.javax.annotation.Nullable
import com.tonyodev.fetch2.AbstractFetchListener
import com.tonyodev.fetch2.Download
import com.tonyodev.fetch2.Error
import com.tonyodev.fetch2.Fetch
import com.tonyodev.fetch2.Fetch.Impl.getInstance
import com.tonyodev.fetch2.FetchConfiguration
import com.tonyodev.fetch2.FetchListener
import com.tonyodev.fetch2.NetworkType
import com.tonyodev.fetch2.Priority
import com.tonyodev.fetch2.Request
import org.jetbrains.annotations.NotNull


public class MainActivity : AppCompatActivity() {

    var fetch: Fetch? = null

    override fun onCreate(@Nullable savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val fetchConfiguration: FetchConfiguration = FetchConfiguration.Builder(this)
            .setDownloadConcurrentLimit(3)
            .build()

        fetch = getInstance(fetchConfiguration)
        fetch = Fetch.Impl.getInstance(fetchConfiguration);
        val url = "http:www.example.com/test.txt"
        val file = "/downloads/test.txt"

        val request = Request(url, file)
        request.priority=Priority.HIGH
        request.networkType=NetworkType.ALL

      request!!.addHeader("clientKey", "SD78DF93_3947&MVNGHE1WONG")

        fetch!!.enqueue(request,
            { updatedRequest: Request? -> }
        ) { error: Error? -> }






    }

    override fun onResume() {
        super.onResume()
        fetch!!.addListener(fetchListener)
    }

    override fun onPause() {
        super.onPause()
        fetch!!.removeListener(fetchListener)
    }

    override fun onDestroy() {
        super.onDestroy()
        fetch!!.close()
    }

    private val fetchListener: FetchListener = object : AbstractFetchListener() {
        override fun onProgress(
            @NotNull download: Download,
            etaInMilliSeconds: Long,
            downloadedBytesPerSecond: Long
        ) {
            super.onProgress(download, etaInMilliSeconds, downloadedBytesPerSecond)
            Log.d("TestActivity", "Progress: " + download.progress)
        }


    }
}
