package com.example.compose.activity

import CardsScreen
import android.app.Application
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.compose.CardsViewModel
import com.skyyo.expandablelist.theme.AppTheme


class ComposeAct : AppCompatActivity() {


    // val darkTheme = themeViewModel.darkTheme.observeAsState(initial = true)
    private val cardsViewModel by viewModels<CardsViewModel>()

    var root: String? = null

    //   private val viewModel by viewModels<ExpandableListViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {

            val bundle: Bundle? = intent.extras
            //    root = bundle?.getString(Constant.QURAN_VERB_ROOT)
            //  val defArgs = bundleOf("root" to root)

            root = "نصر"

            //  occurances = utils.getNounOccuranceBreakVerses(root);
            val hamzaindex = root!!.indexOf("ء")
            var nounroot: String? = ""
            val verbindex = root!!.indexOf("ا")

            var verbroot: String? = ""
            nounroot = if (hamzaindex != -1) {
                root!!.replace("ء", "ا")
            } else {
                root
            }
            verbroot = if (verbindex != -1) {
                root!!.replace("ا", "ء")
            } else {
                root
            }




            AppTheme {
                Surface(color = MaterialTheme.colors.background) {
                    if (root == "ACC" || root == "LOC" || root == "T") {
                        val viewModel: CardsViewModel by viewModels {
                            MyViewModelFactory(
                                application, verbroot!!,
                                nounroot!!, true

                            )
                        }
                        nounroot = root

                        CardsScreen(viewModel)
                    } else {
                        val viewModel: CardsViewModel by viewModels {
                            MyViewModelFactory(
                                application, verbroot!!,
                                nounroot!!, false
                            )
                        }
                        nounroot = root
                        CardsScreen(viewModel)

                    }


                }
            }


        }


    }

    class MyViewModelFactory(
        private val mApplication: Application,
        private val verbroot: String,
        private val nounroot: String,
        private val isharf: Boolean
    ) :
        ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {

            return CardsViewModel( verbroot, nounroot, isharf) as T
        }
    }

}

