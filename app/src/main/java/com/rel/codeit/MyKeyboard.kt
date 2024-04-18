package com.rel.codeit

import android.content.Context
import android.text.TextUtils
import android.util.AttributeSet
import android.util.SparseArray
import android.view.LayoutInflater
import android.view.View
import android.view.inputmethod.ExtractedText
import android.view.inputmethod.ExtractedTextRequest
import android.view.inputmethod.InputConnection
import android.widget.Button
import android.widget.LinearLayout


class MyKeyboard(internal var context: Context, attrs: AttributeSet): LinearLayout(context,attrs), View.OnClickListener{

    var keyValues = SparseArray<String>()
    var inputConection : InputConnection?= null
    var ac : MainActivity?= null

    var button1 : Button? = null
    var button0 : Button? = null
    var button2 : Button? = null
    var button3 : Button? = null
    var button4 : Button? = null
    var button5 : Button? = null
    var button6 : Button? = null
    var button7 : Button? = null
    var button8 : Button? = null
    var button9 : Button? = null
    var buttonmaz : Button? = null
    var buttonq : Button? = null
    var buttonw : Button? = null
    var buttone : Button? = null
    var buttonr : Button? = null
    var buttont : Button? = null
    var buttonz : Button? = null
    var buttonu : Button? = null
    var buttoni : Button? = null
    var buttono : Button? = null
    var buttonp : Button? = null
    var buttona : Button? = null
    var buttons : Button? = null
    var buttond : Button? = null
    var buttonf : Button? = null
    var buttong : Button? = null
    var buttonh : Button? = null
    var buttonj : Button? = null
    var buttonk : Button? = null
    var buttonl : Button? = null
    var buttony : Button? = null
    var buttonx : Button? = null
    var buttonc : Button? = null
    var buttonv : Button? = null
    var buttonb : Button? = null
    var buttonn : Button? = null
    var buttonm : Button? = null
    var buttonlava : Button? = null
    var buttonprava : Button? = null
    var buttonmed : Button? = null
    var buttonent : Button? = null
    var buttonrov : Button? = null

    var buttonLavaN : Button? = null
    var buttonLavaK : Button? = null
    var buttonPravaK : Button? = null
    var buttonPravaN : Button? = null
    var buttonbodka : Button? = null
    var buttondvojbodka : Button? = null
    var buttonUvodz : Button? = null
    var buttonPlus : Button? = null
    var buttonMinus : Button? = null
    var buttonKrat : Button? = null
    var buttonDeleno : Button? = null
    var buttonrovna : Button? = null
    var buttonMensie : Button? = null
    var buttonVacsie : Button? = null
    var buttonCiarka : Button? = null

    init {
        LayoutInflater.from(context).inflate(R.layout.keyboard, this, true)
    }
    fun setCursor(){
        val sData: CharSequence = inputConection!!.getExtractedText(ExtractedTextRequest(), 0).text
        inputConection!!.commitText("",sData.count()+1)
    }

    override fun onClick(v: View?) {
        if(inputConection == null){
            return
        }
        if(v!!.getId() == R.id.button_maz) {
            var st = inputConection!!.getSelectedText(0)
            if (TextUtils.isEmpty(st)) {
                inputConection!!.deleteSurroundingText(1, 0)
            } else {
                inputConection!!.commitText("", 1)
            }
            val et: ExtractedText = inputConection!!.getExtractedText(ExtractedTextRequest(), 0)
            val selectionStart = et.selectionStart
            ac!!.parseColor()
            inputConection!!.commitText("", selectionStart+1)
            ac!!.setWords()
        }else if (v!!.getId() == R.id.button_med){
            var value = keyValues.get(v!!.getId())
            inputConection!!.commitText(value, 1)

            //val sData: CharSequence = inputConection!!.getExtractedText(ExtractedTextRequest(), 0).text
           // inputConection!!.commitText("",sData.count() +1)//ac!!.txt2!!.text.count()+1)*/
            val et: ExtractedText = inputConection!!.getExtractedText(ExtractedTextRequest(), 0)
            val selectionStart = et.selectionStart
           // val sData: CharSequence? = inputConection!!.getTextBeforeCursor(0, 0)
            ac!!.parseColor(0, true)
            inputConection!!.commitText("", selectionStart+1)

            ac!!.setWords()


        }
        else if (v!!.getId() == R.id.button_ent){
            var value = keyValues.get(v!!.getId())
            inputConection!!.commitText(value,1)
            val et: ExtractedText = inputConection!!.getExtractedText(ExtractedTextRequest(), 0)
            val selectionStart = et.selectionStart
            ac!!.parseColor()
            inputConection!!.commitText("", selectionStart+1)
            ac!!.setWords()
            ac!!.addline()
        }else{
            var value = keyValues.get(v!!.getId())
            inputConection!!.commitText(value,1)
            val et: ExtractedText = inputConection!!.getExtractedText(ExtractedTextRequest(), 0)
            val selectionStart = et.selectionStart
            if(v!!.getId() == R.id.button_dvojbodka || v!!.getId() == R.id.button_rovna || v!!.getId() == R.id.button_lavaK)
                ac!!.parseColor(0,true)
            else
                ac!!.parseColor()
            inputConection!!.commitText("", selectionStart+1)
            ac!!.setWords()

        }

    }

    override fun onFinishInflate() {
        super.onFinishInflate()
        button1 = findViewById(R.id.button_1)
        button0 = findViewById(R.id.button_0)
        button2 = findViewById(R.id.button_2)
        button3 = findViewById(R.id.button_3)
        button4 = findViewById(R.id.button_4)
        button5 = findViewById(R.id.button_5)
        button6 = findViewById(R.id.button_6)
        button7 = findViewById(R.id.button_7)
        button8 = findViewById(R.id.button_8)
        button9 = findViewById(R.id.button_9)
        buttonmaz = findViewById(R.id.button_maz)
        buttonp = findViewById(R.id.button_p)
        buttona = findViewById(R.id.button_a)
        buttonq = findViewById(R.id.button_q)
        buttonw = findViewById(R.id.button_w)
        buttone = findViewById(R.id.button_e)
        buttonr = findViewById(R.id.button_r)
        buttont = findViewById(R.id.button_t)
        buttonz = findViewById(R.id.button_z)
        buttonu = findViewById(R.id.button_u)
        buttoni = findViewById(R.id.button_i)
        buttono = findViewById(R.id.button_o)
        buttonp = findViewById(R.id.button_p)
        buttona = findViewById(R.id.button_a)
        buttons = findViewById(R.id.button_s)
        buttond = findViewById(R.id.button_d)
        buttonf = findViewById(R.id.button_f)
        buttong = findViewById(R.id.button_g)
        buttonh = findViewById(R.id.button_h)
        buttonj = findViewById(R.id.button_j)
        buttonk = findViewById(R.id.button_k)
        buttonl = findViewById(R.id.button_l)
        buttony = findViewById(R.id.button_y)
        buttonx = findViewById(R.id.button_x)
        buttonc = findViewById(R.id.button_c)
        buttonv = findViewById(R.id.button_v)
        buttonb = findViewById(R.id.button_b)
        buttonn = findViewById(R.id.button_n)
        buttonm = findViewById(R.id.button_m)
        buttonlava = findViewById(R.id.button_lava)
        buttonprava = findViewById(R.id.button_prava)
        buttonmed = findViewById(R.id.button_med)
        buttonent = findViewById(R.id.button_ent)
        buttonrov = findViewById(R.id.button_tab)
        buttonLavaK = findViewById(R.id.button_lavaK)
        buttonLavaN = findViewById(R.id.button_lavaN)
        buttonPravaK = findViewById(R.id.button_pravaK)
        buttonPravaN = findViewById(R.id.button_pravaN)
        buttonbodka = findViewById(R.id.button_bodka)
        buttondvojbodka = findViewById(R.id.button_dvojbodka)
        buttonUvodz = findViewById(R.id.button_uvodzovka)
        buttonPlus = findViewById(R.id.button_plus)
        buttonMinus = findViewById(R.id.button_minus)
        buttonKrat = findViewById(R.id.button_krat)
        buttonDeleno = findViewById(R.id.button_deleno)
        buttonrovna = findViewById(R.id.button_rovna)
        buttonMensie = findViewById(R.id.button_mensi)
        buttonVacsie = findViewById(R.id.button_vacsi)
        buttonCiarka = findViewById(R.id.button_ciarka)
        button1 !!.setOnClickListener(this)
        button2 !!.setOnClickListener(this)
        button3 !!.setOnClickListener(this)
        button4 !!.setOnClickListener(this)
        button5 !!.setOnClickListener(this)
        button6 !!.setOnClickListener(this)
        button7 !!.setOnClickListener(this)
        button8 !!.setOnClickListener(this)
        button9 !!.setOnClickListener(this)
        button0 !!.setOnClickListener(this)
        buttonmaz !!.setOnClickListener(this)
        buttona !!.setOnClickListener(this)
        buttonq !!.setOnClickListener(this)
        buttonw !!.setOnClickListener(this)
        buttone !!.setOnClickListener(this)
        buttonr !!.setOnClickListener(this)
        buttont !!.setOnClickListener(this)
        buttonz !!.setOnClickListener(this)
        buttonu !!.setOnClickListener(this)
        buttoni !!.setOnClickListener(this)
        buttono !!.setOnClickListener(this)
        buttonp !!.setOnClickListener(this)
        buttona !!.setOnClickListener(this)
        buttons !!.setOnClickListener(this)
        buttond !!.setOnClickListener(this)
        buttonf !!.setOnClickListener(this)
        buttong !!.setOnClickListener(this)
        buttonh !!.setOnClickListener(this)
        buttonj !!.setOnClickListener(this)
        buttonk !!.setOnClickListener(this)
        buttonl !!.setOnClickListener(this)
        buttony !!.setOnClickListener(this)
        buttonx !!.setOnClickListener(this)
        buttonc !!.setOnClickListener(this)
        buttonv !!.setOnClickListener(this)
        buttonb !!.setOnClickListener(this)
        buttonn !!.setOnClickListener(this)
        buttonm !!.setOnClickListener(this)
        buttonlava !!.setOnClickListener(this)
        buttonprava !!.setOnClickListener(this)
        buttonmed !!.setOnClickListener(this)
        buttonent !!.setOnClickListener(this)
        buttonrov !!.setOnClickListener(this)
        buttonPravaK !!.setOnClickListener(this)
        buttonPravaN !!.setOnClickListener(this)
        buttonLavaK !!.setOnClickListener(this)
        buttonLavaN !!.setOnClickListener(this)
        buttonbodka !!.setOnClickListener(this)
        buttondvojbodka !!.setOnClickListener(this)
        buttonUvodz !!.setOnClickListener(this)
        buttonPlus !!.setOnClickListener(this)
        buttonMinus !!.setOnClickListener(this)
        buttonKrat !!.setOnClickListener(this)
        buttonDeleno !!.setOnClickListener(this)
        buttonrovna !!.setOnClickListener(this)
        buttonMensie !!.setOnClickListener(this)
        buttonVacsie !!.setOnClickListener(this)
        buttonCiarka !!.setOnClickListener(this)
        keyValues.put(R.id.button_1, "1")
        keyValues.put(R.id.button_2, "2")
        keyValues.put(R.id.button_3, "3")
        keyValues.put(R.id.button_4, "4")
        keyValues.put(R.id.button_5, "5")
        keyValues.put(R.id.button_6, "6")
        keyValues.put(R.id.button_7, "7")
        keyValues.put(R.id.button_8, "8")
        keyValues.put(R.id.button_9, "9")
        keyValues.put(R.id.button_0, "0")
        keyValues.put(R.id.button_q, "q")
        keyValues.put(R.id.button_w, "w")
        keyValues.put(R.id.button_e, "e")
        keyValues.put(R.id.button_r, "r")
        keyValues.put(R.id.button_t, "t")
        keyValues.put(R.id.button_z, "z")
        keyValues.put(R.id.button_u, "u")
        keyValues.put(R.id.button_i, "i")
        keyValues.put(R.id.button_o, "o")
        keyValues.put(R.id.button_p, "p")
        keyValues.put(R.id.button_a, "a")
        keyValues.put(R.id.button_s, "s")
        keyValues.put(R.id.button_d, "d")
        keyValues.put(R.id.button_f, "f")
        keyValues.put(R.id.button_g, "g")
        keyValues.put(R.id.button_h, "h")
        keyValues.put(R.id.button_j, "j")
        keyValues.put(R.id.button_k, "k")
        keyValues.put(R.id.button_l, "l")
        keyValues.put(R.id.button_y, "y")
        keyValues.put(R.id.button_x, "x")
        keyValues.put(R.id.button_c, "c")
        keyValues.put(R.id.button_v, "v")
        keyValues.put(R.id.button_b, "b")
        keyValues.put(R.id.button_n, "n")
        keyValues.put(R.id.button_m, "m")
        keyValues.put(R.id.button_lava, "[")
        keyValues.put(R.id.button_prava, "]")
        keyValues.put(R.id.button_med, " ")
        keyValues.put(R.id.button_ent, "\n")
        keyValues.put(R.id.button_tab, "\t")
        keyValues.put(R.id.button_pravaN, ")")
        keyValues.put(R.id.button_pravaK, "}")
        keyValues.put(R.id.button_lavaN, "(")
        keyValues.put(R.id.button_lavaK, "{")
        keyValues.put(R.id.button_bodka, ".")
        keyValues.put(R.id.button_dvojbodka, ":")
        keyValues.put(R.id.button_uvodzovka, "\"")
        keyValues.put(R.id.button_plus, "+")
        keyValues.put(R.id.button_minus, "-")
        keyValues.put(R.id.button_krat, "*")
        keyValues.put(R.id.button_deleno, "/")
        keyValues.put(R.id.button_rovna, "=")
        keyValues.put(R.id.button_mensi, "<")
        keyValues.put(R.id.button_vacsi, ">")
        keyValues.put(R.id.button_ciarka, ",")
    }

     fun setsInputConection(ic: InputConnection, nac: MainActivity){
        inputConection = ic
         ac = nac
    }



}