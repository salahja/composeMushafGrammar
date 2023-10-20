package com.example.mushafconsolidated.Activity


import android.annotation.SuppressLint
import android.app.ActivityManager
import android.app.Dialog
import android.content.ActivityNotFoundException
import android.content.DialogInterface
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.text.SpannableStringBuilder
import android.text.Spanned
import android.text.format.DateFormat
import android.text.style.ForegroundColorSpan
import android.util.Log
import android.view.Gravity
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import android.window.OnBackInvokedDispatcher
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.view.menu.MenuBuilder
import androidx.appcompat.view.menu.MenuPopupHelper
import androidx.appcompat.widget.SearchView
import androidx.appcompat.widget.SwitchCompat
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.fragment.app.commit
import androidx.fragment.app.replace
import androidx.lifecycle.ViewModelProvider
import androidx.preference.PreferenceManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.Constant
import com.example.Constant.SURAHFRAGTAG


import com.example.mushafconsolidated.Activityimport.BaseActivity

import com.example.mushafconsolidated.Entities.BadalErabNotesEnt
import com.example.mushafconsolidated.Entities.BookMarks
import com.example.mushafconsolidated.Entities.ChaptersAnaEntity
import com.example.mushafconsolidated.Entities.HalEnt
import com.example.mushafconsolidated.Entities.LiajlihiEnt
import com.example.mushafconsolidated.Entities.MafoolBihi
import com.example.mushafconsolidated.Entities.MafoolMutlaqEnt
import com.example.mushafconsolidated.Entities.QuranEntity
import com.example.mushafconsolidated.Entities.TameezEnt
import com.example.mushafconsolidated.R

import com.example.mushafconsolidated.databinding.NewFragmentReadingBinding

import com.example.mushafconsolidated.intrfaceimport.OnItemClickListenerOnLong
import com.example.mushafconsolidated.model.NewQuranCorpusWbw
import com.example.mushafconsolidated.model.QuranCorpusWbw
import com.example.mushafconsolidated.quranrepo.QuranVIewModel
import com.example.mushafconsolidated.settingsimport.Constants

import com.example.utility.CorpusUtilityorig
import com.example.utility.QuranGrammarApplication

import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.navigation.NavigationView
import com.google.android.material.snackbar.Snackbar

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.ahocorasick.trie.Trie

import wheel.OnWheelChangedListener
import wheel.WheelView
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.util.Date

//import com.example.mushafconsolidated.Entities.JoinVersesTranslationDataTranslation;
class QuranGrammarAct : BaseActivity(), OnItemClickListenerOnLong {
    private var bundle: Intent? = null
    private var bundles: Bundle? = null
    private lateinit var mainViewModel: QuranVIewModel
    private var corpusSurahWord: List<QuranCorpusWbw>? = null

    private var newnewadapterlist = LinkedHashMap<Int, ArrayList<NewQuranCorpusWbw>>()

    lateinit var binding: NewFragmentReadingBinding
    private lateinit var surahWheelDisplayData: Array<String>
    private lateinit var ayahWheelDisplayData: Array<String>
    private lateinit var btnBottomSheet: FloatingActionButton
    lateinit var surahArabicName: String
    private var jumptostatus = false
    var surah_id = 0
    var verseNumber = 0
    var suraNumber = 0
    private var rukucount = 0
    var surahname: String? = null
    private var mudhafColoragainstBlack = 0
    private var mausofColoragainstBlack = 0
    private var sifatColoragainstBlack = 0
    private var brokenPlurarColoragainstBlack = 0
    private var shartagainstback = 0
    private var surahorpart = 0

    // TextView tvsurah, tvayah, tvrukus;
    private var currentSelectSurah = 0

    // --Commented out by Inspection (13/08/23, 4:31 pm):RelativeLayout layoutBottomSheet;
    var versescount = 0
    private var chapterorpart = false

    // --Commented out by Inspection (14/08/21, 7:26 PM):ChipNavigationBar chipNavigationBar;
    private var verseNo = 0
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var bottomNavigationView: BottomNavigationView
    private lateinit var navigationView: NavigationView

    // ChipNavigationBar chipNavigationBar;
    private lateinit var materialToolbar: Toolbar
    //goes with extracttwothree
    //   private lateinit var refflowAyahWordAdapter: refFlowAyahWordAdapter
    // private lateinit var flowAyahWordAdapterpassage: FlowAyahWordAdapterPassage
    // private UpdateMafoolFlowAyahWordAdapter flowAyahWordAdapter;
    private var mausoof = false
    private var mudhaf = false
    private var harfnasb = false
    private var shart = false
    private lateinit var soraList: ArrayList<ChaptersAnaEntity>
    private var kana = false
    private var allofQuran: List<QuranEntity>? = null
    private lateinit var shared: SharedPreferences
    //  private OnClickListener onClickListener;
    //  private val newadapterlist = LinkedHashMap<Int, ArrayList<NewCorpusAyahWord>>()
    private var mafoolbihiwords: List<MafoolBihi?>? = null
    private var Jumlahaliya: List<HalEnt?>? = null
    private var Tammezent: List<TameezEnt?>? = null
    private var Mutlaqent: List<MafoolMutlaqEnt?>? = null
    private var Liajlihient: List<LiajlihiEnt?>? = null
    private var BadalErabNotesEnt: List<BadalErabNotesEnt?>? = null
    private var isMakkiMadani = 0
    var chapterno = 0
    private lateinit var parentRecyclerView: RecyclerView
    private var mushafview = false



    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_dua_group, menu)
        val searchView = menu.findItem(R.id.search).actionView as SearchView?
        searchView!!.queryHint = "Type something…"
        val sear = ContextCompat.getDrawable(this, R.drawable.custom_search_box)
        searchView.clipToOutline = true
        searchView.setBackgroundDrawable(sear)
        searchView.gravity = View.TEXT_ALIGNMENT_CENTER
        searchView.maxWidth = Int.MAX_VALUE
        return true
    }

    override fun onStop() {
        super.onStop()
    }

    override fun onDestroy() {
        super.onDestroy()
    }

    override fun onPrepareOptionsMenu(menu: Menu): Boolean {
        return true
    }

    @RequiresApi(api = Build.VERSION_CODES.Q)
    override fun onCreate(savedInstanceState: Bundle?) {
        this.shared = PreferenceManager.getDefaultSharedPreferences(this@QuranGrammarAct)
        val preferences = shared.getString("themepref", "dark")
        super.onCreate(savedInstanceState)
        binding = NewFragmentReadingBinding.inflate(layoutInflater)
        mainViewModel = ViewModelProvider(this)[QuranVIewModel::class.java]
        val view = binding.root
        setContentView(view)
        // Get a reference to the ViewModel scoped to this Fragment
        // Get a reference to the ViewModel scoped to its Activity
        //    setContentView(R.layout.new_fragment_reading)
        materialToolbar = binding.toolbarmain

        initnavigation()


        setSupportActionBar(materialToolbar)
        val prefs =
            android.preference.PreferenceManager.getDefaultSharedPreferences(QuranGrammarApplication.context)
        if (preferences == "dark" || preferences == "blue" || preferences == "green") {
            shartagainstback = prefs.getInt("shartback", Color.GREEN)
            mausofColoragainstBlack = prefs.getInt("mausoofblack", Color.RED)
            mudhafColoragainstBlack = prefs.getInt("mudhafblack", Color.CYAN)
            sifatColoragainstBlack = prefs.getInt("sifatblack", Color.YELLOW)
            brokenPlurarColoragainstBlack = prefs.getInt("brokenblack", Color.GREEN)
        } else {
            shartagainstback = prefs.getInt("shartback", Constant.INDIGO)
            mudhafColoragainstBlack = prefs.getInt("mausoofwhite", Color.GREEN)
            mausofColoragainstBlack = prefs.getInt("mudhafwhite", Constant.MIDNIGHTBLUE)
            sifatColoragainstBlack = prefs.getInt("sifatwhite", Constant.ORANGE400)
            brokenPlurarColoragainstBlack = prefs.getInt("brokenwhite", Constant.DARKMAGENTA)
        }

        android.preference.PreferenceManager.setDefaultValues(this, R.xml.preferences, false)
        mausoof = shared.getBoolean("mausoof", true)
        mudhaf = shared.getBoolean("mudhaf", true)
        harfnasb = shared.getBoolean("harfnasb", true)
        shart = shared.getBoolean("shart", true)
        kana = shared.getBoolean("kana", true)
        getpreferences()
        bundle = intent
        bundles = intent.extras

    }


    private fun initnavigation() {
        btnBottomSheet = binding.fab
        btnBottomSheet = binding.fab
        drawerLayout = binding.drawer
        navigationView = binding.navigationView
        bottomNavigationView = binding.bottomNavView
        val toggle = ActionBarDrawerToggle(
            this,
            drawerLayout,
            materialToolbar,
            R.string.drawer_open,
            R.string.drawer_close
                                          )
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()
        btnBottomSheet.setOnClickListener { v: View? -> toggleBottomSheets() }
        //  bottomNavigationView.setOnItemSelectedListener { new  }OnItemReselectedListener
        bottomNavigationView.setOnItemReselectedListener { item: MenuItem ->



        }
        navigationView.setNavigationItemSelectedListener { item: MenuItem ->






            false
        }
    }// first time

    ////////////////
    private val isFirstTime: Boolean
        get() {
            val preferences = getPreferences(MODE_PRIVATE)
            val ranBefore = preferences.getBoolean("RanBefore", false)
            if (!ranBefore) {
                // first time
                val editor = preferences.edit()
                editor.putBoolean("RanBefore", true)
                editor.apply()
            }
            return !ranBefore
        }

    private fun getpreferences() {
        val pref = applicationContext.getSharedPreferences("lastread", MODE_PRIVATE)
        chapterno = pref.getInt(Constant.SURAH_ID, 1)
        verseNo = pref.getInt(Constant.AYAH_ID, 1)
        surahname = pref.getString(Constant.SURAH_ARABIC_NAME, "")
        surahArabicName = surahname.toString()
    }

    override fun onItemClick(v: View, position: Int) {
        TODO("Not yet implemented")
    }


    override fun onItemLongClick(position: Int, v: View) {
        //    Toast.makeText(this, "longclick", Toast.LENGTH_SHORT).show();
    }

    private fun initView() {
        val linearLayoutManager = LinearLayoutManager(applicationContext)
        btnBottomSheet = binding.fab
        val verlayViewRecyclerView = findViewById<RecyclerView>(R.id.overlayViewRecyclerView)
        verlayViewRecyclerView.layoutManager = linearLayoutManager
        // bookmarkchip.setOnClickListener(v -> CheckStringLENGTHS());
    }

    private fun toggleBottomSheets() {
        if (bottomNavigationView.visibility == View.VISIBLE) {
            bottomNavigationView.visibility = View.GONE
            //    btnBottomSheet.setText("Close sheet");
        } else {
            bottomNavigationView.visibility = View.VISIBLE
            //    btnBottomSheet.setText("Expand sheet");
        }
    }
    /*    override fun ondatarecevied(
            chapterno: Int,
            partname: String?,
            totalverses: Int,
            rukucount: Int,
            makkimadani: Int,
                                   ) {
            Log.e(TAG, "onClick called")
            val intent =
                intent.putExtra("chapter", chapterno).putExtra("chapterorpart", chapterorpart).putExtra(
                    Constant.SURAH_ARABIC_NAME, partname
                                                                                                       )
                    .putExtra(Constant.VERSESCOUNT, totalverses).putExtra(Constant.RUKUCOUNT, rukucount)
                    .putExtra(
                        Constant.MAKKI_MADANI, makkimadani
                             )
            overridePendingTransition(0, 0)
            startActivity(intent)
            finish()
            overridePendingTransition(android.R.anim.slide_out_right, android.R.anim.slide_in_left)
        }*/
    companion object {
        private const val TAG = "fragment"
    }
}


