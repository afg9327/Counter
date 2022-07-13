package com.example.praactice1

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import java.lang.Math.abs
import java.lang.System.exit
import java.util.*

class MainActivity : AppCompatActivity() {
    var k = 1
    var p_num = 0
    val score = mutableListOf<Float>()

    fun start(){
        setContentView(R.layout.activity_start)
        val acbtn: Button = findViewById(R.id.accept)
        val psbtn: Button = findViewById(R.id.plus)
        val msbtn: Button = findViewById(R.id.minus)
        val ps: TextView = findViewById(R.id.people2)
        val pn: TextView = findViewById(R.id.pn)
        psbtn.setOnClickListener{
            p_num++
            pn.text = p_num.toString()
        }
        msbtn.setOnClickListener{
            if(p_num<1)
            {
                p_num = 0
            }
            else
            {
                p_num--
            }
            pn.text = p_num.toString()
        }
        acbtn.setOnClickListener{
            main()
        }

    }
    fun main(){
        setContentView(R.layout.activity_main)
        var timerTask : Timer? = null
        var stage = 1
        var sec: Int = 0
        val tv: TextView = findViewById(R.id.counter)
        val tg: TextView = findViewById(R.id.target)
        val btn: Button = findViewById(R.id.tvbutton)
        val sc: TextView = findViewById(R.id.score)
        val pp: TextView = findViewById(R.id.people)
        val random_box = Random()
        val num = random_box.nextInt(1000)

        tg.text = (num.toFloat()/100).toString()
        tv.text = ""
        pp.text = "${k}번 참가자"

        btn.setOnClickListener {
            stage++
            if (stage==2) {
                btn.text = "멈춤"
                timerTask = kotlin.concurrent.timer(period = 10) {
                    sec++
                    runOnUiThread {
                        tv.text = (sec.toFloat()/100).toString()
                    }
                }
            }
            else if (stage==3) {
                btn.text = "다음"
                timerTask?.cancel()
                val point = abs(sec-num).toFloat()/100
                score.add(point)
                sc.text=  point.toString()
                stage=0
            }
            else if (stage==1){
                if( k< p_num) {
                    k++
                    main()
                }
                else {
                    btn.text="마무리"
                    end()
                }
            }
        }
    }
    fun end(){
        setContentView(R.layout.activity_end)
        val lp: TextView = findViewById(R.id.l_people)
        val lsc: TextView = findViewById(R.id.l_score)
        val rsbtn: Button = findViewById(R.id.rs_button)
        lsc.text = (score.maxOrNull()).toString()
        lp.text=(score.indexOf(score.maxOrNull())+1).toString() + "번참가자"
        rsbtn.setOnClickListener {
            score.clear()
            k=1
            start()
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        start()

    }
}