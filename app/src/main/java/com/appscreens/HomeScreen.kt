
package com.appscreens
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.activities.NavigationType


import com.example.mushafconsolidated.R
import com.example.utility.QuranGrammarApplication
import com.google.accompanist.adaptive.HorizontalTwoPaneStrategy
import com.google.accompanist.adaptive.TwoPane


@Composable
fun HomeScreen(
    userId: String?,
    isOnlyDetailScreen: Boolean,
    navigationType: NavigationType,
    goToUserDetail: (String?) -> Unit,
    onDetailBackPressed: () -> Unit,
    navController: NavHostController,
) {

    //custom back handler
    BackHandler {
        println("BackHandler")
        onDetailBackPressed()
    }

    if (navigationType == NavigationType.PERMANENT_NAV_DRAWER) {

        TwoPane(
            first = { HomeSingleScreen(goToUserDetail = goToUserDetail) },
            second = {
                RootDetailScreen(userId = userId, isOnlyDetailScreen = isOnlyDetailScreen,navController) {

                }
            },
            strategy = HorizontalTwoPaneStrategy(splitFraction = 0.45f, gapWidth = 16.dp),
            displayFeatures = mutableListOf()
        )

    } else {

        if(userId != null && isOnlyDetailScreen && navigationType == NavigationType.NAV_RAIL) {
            RootDetailScreen(userId = userId, isOnlyDetailScreen = isOnlyDetailScreen,navController) {
                onDetailBackPressed()
            }
        } else {
            HomeSingleScreen(goToUserDetail = goToUserDetail)
        }

    }

}

@Composable
fun HomeSingleScreen(
    goToUserDetail: (String) -> Unit,
) {
    LazyColumn(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally,
        contentPadding = PaddingValues(8.dp)
    ) {
        val array: Array<String> = QuranGrammarApplication.context!!.resources
            .getStringArray(R.array.arabicletters)
        items(items = array) { index ->
            Card(shape = RoundedCornerShape(15.dp), modifier = Modifier
                .padding(start = 8.dp, end = 8.dp, top = 8.dp, bottom = 8.dp)
                .fillMaxWidth()
                .clickable {
                    println("userID=$index")
                    goToUserDetail(index)
                }) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Image(
                        painter = painterResource(id = R.drawable.ic_makkah_48),
                        contentDescription = "",
                        modifier = Modifier.padding(16.dp)
                    )
                    Text(text = "User $index")
                }
            }
        }
    }
}