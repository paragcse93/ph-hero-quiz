package com.programminghero.quiz

import android.annotation.SuppressLint
import android.content.Context
import android.net.ConnectivityManager
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.programminghero.quiz.helper.StorageManager

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Dashboard(navController: NavController) {
    val context = LocalContext.current
    val storageManager = remember { StorageManager(context) }

    var highScore by remember { mutableStateOf("0") }
    val showDialog = remember { mutableStateOf(false) }
    // Retrieve data from SharedPreferences when the composable is first created
    DisposableEffect(Unit) {
        val maxScore = storageManager.getHighScore()
        highScore = "$maxScore Point"
        onDispose { }
    }

    Scaffold(
        content = {
            if (showDialog.value) {
                AlertDialog(
                    onDismissRequest = { showDialog.value = false },
                    title = { Text("Opps") },
                    text = { Text("No Internet Connection.") },
                    confirmButton = {
                        Button(
                            onClick = { showDialog.value = false },
                        ) {
                            Text("OK")
                        }
                    }
                )
            }

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(colorResource(id = R.color.primary))
            ) {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.ic_ph_logo),
                        contentDescription = null, // Set a proper content description
                        modifier = Modifier
                            .size(200.dp)
                    )

                    Row {
                        Text(
                            text = "Programming ",
                            fontSize = 32.sp,
                            color = Color.White,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(top = 1.dp)
                        )
                        Text(
                            text = "Hero",
                            fontSize = 32.sp,
                            color = Color.White,
                            fontWeight = FontWeight.ExtraLight,
                            modifier = Modifier.padding(top = 1.dp)
                        )
                    }

                    Text(
                        text = "Quiz",
                        fontSize = 36.sp,
                        color = Color.White,
                        fontWeight = FontWeight.ExtraBold,
                        modifier = Modifier.padding(top = 1.dp)
                    )
                    Spacer(modifier = Modifier.height(10.dp))

                    Text(
                        text = "High Score",
                        fontSize = 22.sp,
                        color = Color.White,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(top = 28.dp)
                    )

                    Text(
                        text = highScore,
                        fontSize = 22.sp,
                        color = Color.White,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(top = 5.dp)
                    )
                    Spacer(modifier = Modifier.height(10.dp))

                    Button(
                        onClick = {
                            if (isInternetAvailable(context)) {
                                navController.navigate("QuizScreen")
                            } else {
                                showDialog.value = true
                            }

                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(30.dp),
                        shape = MaterialTheme.shapes.small,
                        colors = ButtonDefaults.buttonColors(Color.White)
                    ) {
                        Text(
                            text = "Start",
                            color = Color.Black,
                            fontSize = 22.sp,
                            fontWeight = FontWeight.ExtraBold
                        )
                    }
                }
            }
        }
    )
}

fun isInternetAvailable(context: Context): Boolean {
    val connectivityManager =
        context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    val network = connectivityManager.activeNetwork
    val networkCapabilities = connectivityManager.getNetworkCapabilities(network)

    // Check if network capabilities exist, indicating any internet connection
    return networkCapabilities != null
}