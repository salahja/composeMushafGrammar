package com.example.mushafconsolidated.model




import android.text.SpannableString
import androidx.compose.ui.text.AnnotatedString
import androidx.room.Embedded
import androidx.room.Ignore
import androidx.room.Relation
import com.example.mushafconsolidated.Entities.CorpusEntity
import com.example.mushafconsolidated.Entities.wbwentity


 class NewQuranCorpusWbw(var str :String="") {
    @Ignore
    var spannableverse: SpannableString? = null

    @Ignore
    var annotatedVerse: AnnotatedString? = null
    @Embedded
    var corpus: CorpusEntity? = null

    @Relation(parentColumn = "id", entityColumn = "id")
    var wbw: wbwentity? = null

    constructor(corpus: CorpusEntity?, wbw: wbwentity?) : this() {
        this.corpus = corpus
        this.wbw = wbw
    }

     fun getAnnotateStr(): AnnotatedString? {
        return annotatedVerse
    }

    fun setAnootedStr(annotatedVerse: AnnotatedString?) {
        this.annotatedVerse = annotatedVerse
    }
}

