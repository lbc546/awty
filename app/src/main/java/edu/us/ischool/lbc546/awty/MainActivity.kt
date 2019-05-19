package edu.us.ischool.lbc546.awty

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val message : EditText = findViewById(R.id.message)
        val number : EditText = findViewById(R.id.number)
        val nagInterval : EditText = findViewById(R.id.interval)
        val button : Button = findViewById(R.id.button)
        val intent = Intent(this, AlarmReceiver::class.java)

        val alarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager
        button.setOnClickListener {
            when (button.text.toString()) {
                "Start" -> {
                    if (message.text.isNotEmpty() && number.text.isNotEmpty() &&
                        nagInterval.text.isNotEmpty() && number.length() == 10 &&
                        nagInterval.text.toString().toInt() > 0) {
                        button.setBackgroundColor(Color.YELLOW);
                        button.text = getString(R.string.stop)
                        val interval = nagInterval.text.toString().toLong()
                        intent.putExtra("number", number.text.toString())
                        intent.putExtra("message", message.text.toString())
                        val pendingIntent = PendingIntent.getBroadcast(this, 0, intent,  PendingIntent.FLAG_UPDATE_CURRENT)
                        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(), 1000 * 60 * interval, pendingIntent)

                    } else {
                        var error = ""
                        if (number.text.isEmpty()) {
                            error += "Please enter a number to nag"
                        }
                        if (number.length() != 10) {
                            if (error.isNotEmpty()) error += "\n"
                            error += "Please type a 10 digit number"
                        }
                        if (nagInterval.text.isEmpty()) {
                            if (error.isNotEmpty()) error += "\n"
                            error += "Please enter a nagging interval"
                        } else if (nagInterval.text.toString().toInt() <= 0) {
                            if (error.isNotEmpty()) error += "\n"
                            error += "Please type a integral interval greater than 0"
                        }
                        if (message.text.isEmpty()) {
                            if (error.isNotEmpty()) error += "\n"
                            error += "Please type a message to send"
                        }
                        Toast.makeText(this, error, Toast.LENGTH_LONG).show()
                    }
                }
                "Stop" -> {
                    button.text = getString(R.string.start)
                    button.setBackgroundColor(Color.GREEN);
                    Toast.makeText(this, "Alarm Stopped", Toast.LENGTH_SHORT).show()
                    val pendingIntent = PendingIntent.getBroadcast(this, 0, intent, 0)
                    alarmManager.cancel(pendingIntent)
                }
            }
        }
    }
}
