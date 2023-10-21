package com.example.compose



import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.fragment.app.DialogFragment

class WordDialogFragment : DialogFragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        dialog!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
        return ComposeView(requireContext()).apply {
            setContent {
                Column(modifier = Modifier.fillMaxWidth()) {
                    Text(
                        text = "溫馨提示",
                        textAlign = TextAlign.Center,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(30.dp),
                    )
                    Text(
                        text = "回家吃饭啦",
                        textAlign = TextAlign.Center,
                        fontSize = 18.sp,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 30.dp, end = 30.dp, bottom = 30.dp),
                    )

                    Row(
                        modifier = Modifier
                            .background(Color.Gray)
                            .padding(top = 1.dp)
                            .background(Color.White)
                            .height(48.dp)
                    ) {
                        TextButton(
                            shape = RoundedCornerShape(0.dp),
                            modifier = Modifier
                                .fillMaxHeight()
                                .weight(1F),
                            onClick = {
                                Toast.makeText(requireContext(), "onCancelClick", Toast.LENGTH_SHORT).show()
                            },
                        ) {
                            Text(text = "取消", color = Color.Gray)
                        }
                        Spacer(
                            modifier = Modifier
                                .width(1.dp)
                                .fillMaxHeight()
                                .background(Color.Gray)
                        )
                        TextButton(
                            shape = RoundedCornerShape(0.dp),
                            modifier = Modifier
                                .fillMaxHeight()
                                .weight(1F),
                            onClick = {
                                Toast.makeText(requireContext(), "onConfirmClick", Toast.LENGTH_SHORT).show()
                            },
                        ) {
                            Text(text = "确定")
                        }
                    }
                }
            }
        }
    }
}