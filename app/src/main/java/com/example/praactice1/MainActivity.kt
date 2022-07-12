package com.example.praactice1

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import java.lang.Math.abs
import java.util.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var timerTask : Timer? = null
        var isRunning = false
        val tv: TextView = findViewById(R.id.counter)
        var sec: Int = 0
        val tg: TextView = findViewById(R.id.target)
        val btn: Button = findViewById(R.id.tvbutton)
        val sc: TextView = findViewById(R.id.score)
        val random_box = Random()
        val num = random_box.nextInt(1000)
        tg.text = (num.toFloat()/100).toString()
        tv.text = ""

        btn.setOnClickListener {
            isRunning = !isRunning
            if (isRunning == true) {
                btn.text = "멈춤"
                timerTask = kotlin.concurrent.timer(period = 10) {
                    sec++
                    runOnUiThread {
                        tv.text = (sec.toFloat()/100).toString()
                    }
                }
            } else {
                btn.text = "시작"
                timerTask?.cancel()
                sc.text= (abs(sec-num).toFloat()/100).toString()
            }

        }
    }
}