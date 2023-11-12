package com.example.viewmodels

import androidx.compose.runtime.Immutable
import com.downloads.DownloadAct

@Immutable
data class DownloadFileListArray(

    val filelist: List<DownloadAct.File> =listOf<DownloadAct.File>(),

    )