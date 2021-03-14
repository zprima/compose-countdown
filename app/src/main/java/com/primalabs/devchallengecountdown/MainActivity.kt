package com.primalabs.devchallengecountdown

import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Stop
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.primalabs.devchallengecountdown.ui.theme.DevChallengeCountdownTheme
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DevChallengeCountdownTheme {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background) {
                    App()
                }
            }
        }
    }
}

var ct: CountDownTimer? = null


@Composable
fun App(){
    var animationPlaying by remember { mutableStateOf(false) }

//    val miliSeconds: Int by animateIntAsState(
//        targetValue = if(animationPlaying) 0 else 60_000,
//        animationSpec = tween(60_000),
//        finishedListener = {
//            Log.d("mijagi", "done")
//        }
//    )

    var miliSeconds by remember { mutableStateOf(60_000)}

    fun makeTimer(){
        Log.d("mijagi", "play called")
        ct = object : CountDownTimer(miliSeconds.toLong(), 1_000) {
            override fun onTick(millisUntilFinished: Long) {
                miliSeconds -= 1_000
            }

            override fun onFinish() {
                Log.d("mijagi", "done")
                animationPlaying = false
            }
        }.start()
        animationPlaying = true
    }

    fun stopTimer(){
        Log.d("mijagi", "stop called")
        animationPlaying = false
        ct?.cancel()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ){

        Row(){
            IconButton(onClick = { miliSeconds += 1_000 }) {
                Icon(imageVector = Icons.Default.KeyboardArrowUp, contentDescription = "Add Second")
            }

            IconButton(onClick = { miliSeconds -= 55_000 }) {
                Icon(imageVector = Icons.Default.KeyboardArrowDown, contentDescription = "Remove Second")
            }
        }

        Countdowner(miliSeconds = miliSeconds, animationPlaying = animationPlaying, onPlay = {
            makeTimer()}, onStop = { stopTimer() }
        )
    }
}

@Composable
fun Countdowner(miliSeconds: Int, animationPlaying: Boolean, onPlay: () -> Unit, onStop: () -> Unit ) {
    val simpleFormater = SimpleDateFormat("mm:ss")
    val format = simpleFormater.format(Date(miliSeconds.toLong()))

    Text(text = "$format", fontSize = 32.sp)
    Text(text = "$miliSeconds")

    Spacer(modifier = Modifier.height(36.dp))

    if (animationPlaying) {
        IconButton(onClick = { onStop() }) {
            Icon(imageVector = Icons.Default.Stop, contentDescription = "Refresh")
        }
    } else {
        IconButton(onClick = { onPlay() }) {
            Icon(imageVector = Icons.Default.PlayArrow, contentDescription = "Play")
        }
    }
}
