package edu.us.ischool.lbc546.awty

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast

class AlarmReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        val extras : Bundle = intent.extras
        val number = extras.getString("number")
        val message = extras.getString("message")
        val formattedNumber = "(" + number.substring(0, 3) + ")" + number.substring(3, 6) + "-" + number.substring(6)
        Toast.makeText(context, "Texting " + formattedNumber + "\n" + message, Toast.LENGTH_SHORT).show()
    }
}