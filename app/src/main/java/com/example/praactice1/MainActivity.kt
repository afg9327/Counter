package com.example.praactice1

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import java.lang.Math.abs
import java.lang.System.exit
import java.util.*

class MainActivity : AppCompatActivity() {
    var k = 1
    var p_num = 0
    val score = mutableListOf<Float>()
    var isBlind = true

    fun start(){
        setContentView(R.layout.activity_start)
        val acbtn: Button = findViewById(R.id.accept)
        val psbtn: Button = findViewById(R.id.plus)
        val msbtn: Button = findViewById(R.id.minus)
        val ps: TextView = findViewById(R.id.people2)
        val pn: TextView = findViewById(R.id.pn)
        val btn_blind: Button = findViewById(R.id.btn_blind)

        pn.text= p_num.toString()

        btn_blind.setOnClickListener {
            isBlind =!isBlind
            if(isBlind==true)
            {
                btn_blind.text = "Blind 모드 On"
            }
            else if(isBlind==false)
            {
                btn_blind.text = "Blind 모드 Off"
            }
        }
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
        val btn_back: Button = findViewById(R.id.btn_back)
        val random_box = Random()
        val num = random_box.nextInt(1000)
        val bg_main : ConstraintLayout = findViewById(R.id.bg_main)
        val color_list = mutableListOf<String>("#FBEEEE","#D8E6CB","#D3EAE1","#D3E1EA","#D4D3EA","#DDD3EA","#E5D3EA","#EAD3E3")
        var color_index = k%8-1
        if(color_index == -1)
        {
            color_index= 7
        }
        val color_select =color_list.get(color_index)
        bg_main.setBackgroundColor(Color.parseColor(color_select))

        tg.text = (num.toFloat()/100).toString()
        tv.text = ""
        pp.text = "${k}번 참가자"

        btn_back.setOnClickListener {
            k=1
            score.clear()
            p_num = 3
            start()
        }

        btn.setOnClickListener {
            stage++
            if (stage==2) {
                btn.text = "멈춤"
                timerTask = kotlin.concurrent.timer(period = 10) {
                    sec++
                    runOnUiThread {
                        if(isBlind==false){
                            tv.text = (sec.toFloat()/100).toString()
                        }
                        else if(isBlind==true && stage ==2)
                        {
                            tv.text = "???"
                        }
                    }
                }
            }
            else if (stage==3) {
                btn.text = "다음"
                tv.text = (sec.toFloat()/100).toString()
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