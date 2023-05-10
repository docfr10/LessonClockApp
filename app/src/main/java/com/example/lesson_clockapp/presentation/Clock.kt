package com.example.lesson_clockapp.presentation

import android.app.TimePickerDialog
import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.wear.compose.material.Button
import androidx.wear.compose.material.MaterialTheme
import androidx.wear.compose.material.Scaffold
import androidx.wear.compose.material.Text
import kotlinx.coroutines.delay
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

@Composable
fun Clock(context: Context) {
    val time = remember { mutableStateOf(Date()) }
    val alarmTime = remember { mutableStateOf(Date()) }

    LaunchedEffect(Unit) {
        while (true) {
            time.value = Date()

            if (alarmTime.value.hours == time.value.hours &&
                alarmTime.value.minutes == time.value.minutes
            ) {
                Toast.makeText(context, "Alarm", Toast.LENGTH_LONG).show()
            }
            delay(1000)
        }
    }

    Scaffold {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
                    .border(
                        BorderStroke(
                            width = 4.dp,
                            color = Color.Black
                        ),
                        shape = MaterialTheme.shapes.medium
                    ),
                contentAlignment = Alignment.Center
            ) {
                val formattedTime =
                    SimpleDateFormat("hh:mm:ss a", Locale.getDefault()).format(time.value)
                Text(
                    text = formattedTime,
                    fontSize = 36.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center,
                    color = Color.White
                )

                Button(
                    onClick = {
                        // Open time picker dialog and update alarmTime
                        TimePickerDialog(
                            context,
                            { _, hourOfDay, minute ->
                                val calendar = Calendar.getInstance()
                                calendar.set(Calendar.HOUR_OF_DAY, hourOfDay)
                                calendar.set(Calendar.MINUTE, minute)
                                alarmTime.value = calendar.time
                            },
                            alarmTime.value.hours,
                            alarmTime.value.minutes,
                            false
                        ).show()
                    },
                    modifier = Modifier.align(Alignment.BottomEnd)
                ) {
                    Text(text = "Set alarm")
                }
            }
        }
    }
}