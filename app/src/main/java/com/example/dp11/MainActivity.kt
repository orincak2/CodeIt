package com.example.dp11


import Parser.Parser
import android.content.pm.ActivityInfo
import android.os.Build
import android.os.Bundle
import android.text.InputType
import android.text.Spannable
import android.text.SpannableString
import android.text.method.ScrollingMovementMethod
import android.text.style.AbsoluteSizeSpan
import android.text.style.RelativeSizeSpan
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.view.inputmethod.EditorInfo
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import com.google.android.material.textview.MaterialTextView
import java.util.Timer
import kotlin.concurrent.schedule


class MainActivity : AppCompatActivity() {
    var lincounter = 1
    var txt: EditText?= null
    var txt2: EditText?= null
    var txt3: EditText?= null
    var txt4: EditText?= null
    var txtFullScreen: EditText?= null
    var but1: Button? = null
    var but2: Button? = null
    var but3: Button? = null
    var but4: Button? = null
    var but5: Button? = null
    var wordsHelper = mutableListOf<WordHelper>()
    var th: TextHelper? = null
    var turtle: Turtle? = null
    var print: MaterialTextView? = null
    var pg: Playground? = null
    var parser: Parser? = null
    var runButton: ImageView? = null
    var openCloseTerminal: ImageView? = null
    var keyboardMain: MyKeyboard? = null
    var keyboardFullScreen: MyKeyboard? = null
    var openFullScreen: ImageView? = null
    var closeFullScreen: ImageView? = null
    var mainLayout: LinearLayout? = null
    var vypisl: LinearLayout? = null
    var TerminalLayout: LinearLayout? = null
    var fullScreenLayout: ConstraintLayout? = null
    var fullScreenLayoutKey: ConstraintLayout? = null
    var spodok: ConstraintLayout? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main)
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        init()

        val timer = Timer().schedule(1000,20){
            runOnUiThread{
                pg!!.invalidate()
                if(pg!!.visibility == View.VISIBLE){
                    openFullScreen!!.setImageResource(com.google.android.material.R.drawable.ic_m3_chip_close)
                }
            }
        }
        timer.run()

        openFullScreen!!.setOnClickListener{
            if(pg!!.visibility == View.VISIBLE){
                pg!!.setVisibility(View.INVISIBLE)
                openFullScreen!!.setImageResource(androidx.appcompat.R.drawable.abc_ic_menu_copy_mtrl_am_alpha)
            }else {
                mainLayout!!.setVisibility(View.INVISIBLE)
                fullScreenLayout!!.setVisibility(View.VISIBLE)
                txt2!!.text = txt!!.text
                txt4!!.text = txt3!!.text
                th!!.setText(txt2)
            }
        }

        closeFullScreen!!.setOnClickListener{
            mainLayout!!.setVisibility(View.VISIBLE)
            fullScreenLayout!!.setVisibility(View.INVISIBLE)
            txt!!.text = txt2!!.text
            txt3!!.text = txt4!!.text
            th!!.setText(txt)
        }
        openCloseTerminal!!.setOnClickListener {
            if(vypisl!!.visibility == View.INVISIBLE) {
                val param = LinearLayout.LayoutParams(
                    ConstraintLayout.LayoutParams.MATCH_PARENT,
                    0,
                    40.0f
                )
                val param2 = LinearLayout.LayoutParams(
                    ConstraintLayout.LayoutParams.MATCH_PARENT,
                    0,
                    16.0f
                )
                openCloseTerminal!!.setImageResource(com.google.android.material.R.drawable.mtrl_ic_arrow_drop_down)
                vypisl!!.setLayoutParams(param2)
                spodok!!.setLayoutParams(param)
                vypisl!!.setVisibility(View.VISIBLE)
            }else{
                val param = LinearLayout.LayoutParams(
                    ConstraintLayout.LayoutParams.MATCH_PARENT,
                    0,
                    4.0f
                )
                val param2 = LinearLayout.LayoutParams(
                    ConstraintLayout.LayoutParams.MATCH_PARENT,
                    0,
                    0.0f
                )
                openCloseTerminal!!.setImageResource(com.google.android.material.R.drawable.mtrl_ic_arrow_drop_up)
                vypisl!!.setLayoutParams(param2)
                spodok!!.setLayoutParams(param)
                vypisl!!.setVisibility(View.INVISIBLE)
            }
        }

        txtFullScreen!!.setOnClickListener {
            if(keyboardFullScreen!!.visibility == View.INVISIBLE){
                keyboardFullScreen!!.setVisibility(View.VISIBLE)
                val param = LinearLayout.LayoutParams(
                    ConstraintLayout.LayoutParams.MATCH_PARENT,
                    0,
                    40.0f
                )
                fullScreenLayoutKey!!.setLayoutParams(param)
            }else{
                val param = LinearLayout.LayoutParams(
                    ConstraintLayout.LayoutParams.MATCH_PARENT,
                    0,
                    0.0f
                )
                fullScreenLayoutKey!!.setLayoutParams(param)
                keyboardFullScreen!!.setVisibility(View.INVISIBLE)
            }
        }


        txt!!.setOnClickListener{
            if(keyboardMain!!.visibility == View.INVISIBLE){
                val param = LinearLayout.LayoutParams(
                    ConstraintLayout.LayoutParams.MATCH_PARENT,
                    0,
                    50.0f
                )
                spodok!!.setLayoutParams(param)
                TerminalLayout!!.setVisibility(View.INVISIBLE)
                keyboardMain!!.setVisibility(View.VISIBLE)
            }else{
                if(vypisl!!.visibility == View.VISIBLE) {
                    val param = LinearLayout.LayoutParams(
                        ConstraintLayout.LayoutParams.MATCH_PARENT,
                        0,
                        50.0f
                    )
                    val param2 = LinearLayout.LayoutParams(
                        ConstraintLayout.LayoutParams.MATCH_PARENT,
                        0,
                        16.0f
                    )
                    openCloseTerminal!!.setImageResource(com.google.android.material.R.drawable.mtrl_ic_arrow_drop_down)
                    vypisl!!.setLayoutParams(param2)
                    spodok!!.setLayoutParams(param)
                    vypisl!!.setVisibility(View.VISIBLE)
                }else{
                    val param = LinearLayout.LayoutParams(
                        ConstraintLayout.LayoutParams.MATCH_PARENT,
                        0,
                        4.0f
                    )
                    val param2 = LinearLayout.LayoutParams(
                        ConstraintLayout.LayoutParams.MATCH_PARENT,
                        0,
                        0.0f
                    )
                    openCloseTerminal!!.setImageResource(com.google.android.material.R.drawable.mtrl_ic_arrow_drop_up)
                    vypisl!!.setLayoutParams(param2)
                    spodok!!.setLayoutParams(param)
                    vypisl!!.setVisibility(View.INVISIBLE)
                }




                TerminalLayout!!.setVisibility(View.VISIBLE)
                keyboardMain!!.setVisibility(View.INVISIBLE)
            }
        }

        but1!!.setOnClickListener {
          /* txt!!.setText("d = 1 a = turtle b = turtle\n" +
                    "definuj krok(){a.dopredu d b.dopredu d}\n" +
                    "definuj toc(){a.vpravo 70 b.vpravo 70}\n" +
                   "definuj zvec(h) {vrat d + h}\n" +
                    "kym d < 150 {krok() toc() d = zvec(7)}")*/
            wordHelpButttonClicked(but1)
        }
        but2!!.setOnClickListener {
           /* txt!!.setText("d = true\n" +
                    "opakuj 2 \n{\n\tak d: \n\t\tvypis 100" +
                    "\n\tinak:\n\t\tvypis 200 \nd = not d}")*/
            wordHelpButttonClicked(but2)
        }
        but3!!.setOnClickListener {
          /*  txt!!.setText("d = \"aaa\" " +
                    "\nak d:" +
                    "\n\ta=1" +
                    "\n\tkym a<3:" +
                    "\n\t\tvypis 100" +
                    "\n\t\ta = a+1"+
                    "\nelse:" +
                    "\n\tvypis 200" +
                    "\nvypis 300")*/
            wordHelpButttonClicked(but3)
        }
        but4!!.setOnClickListener {
               /* txt!!.setText("c = 4 b = [7,2,1,\"nieco\"] a = 1 " +
                        "\nfor x = 0 x < c:" +
                        "\n\ta = a+ b[x]" +
                        "\n\tvypis a")*/
            wordHelpButttonClicked(but4)
        }
        but5!!.setOnClickListener {
           /* txt!!.setText("a = true" +
                    "\n definuj krok(b):" +
                    "" +
                    "\n\tak (a):" +
                    "" +
                    "\n\t\tvypis 100" +
                    "" +
                    "\n\tinak:" +
                    "" +
                    "\n\t\tvypis 200" +
                    "\n\treturn b" +
                    "" +
                    "\n krok(4)")*/

            wordHelpButttonClicked(but5)
        }
        print!!.setMovementMethod(ScrollingMovementMethod())
        runButton!!.setOnClickListener {
           parser!!.run(txt!!)
        }
    }

    fun parseColor(){
        th!!.setColors()
    }
    fun setWords(){
        th!!.setWords()
    }

    fun addline(){
        lincounter++
        txt3!!.setText(txt3!!.text.toString()+"\n"+lincounter)
        txt4!!.setText(txt4!!.text.toString()+"\n"+lincounter)
       /* val spannableString = SpannableString(txt2!!.text.toString()+"\n"+lincounter.toString())

        // Nastavte novú veľkosť písma len pre konkrétny rozsah textu
        spannableString.setSpan(
            AbsoluteSizeSpan(10, true),
            0,
            txt2!!.text.length,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        txt2!!.setText(spannableString)*/

    }

    fun init(){
        pg = findViewById<Playground>(R.id.playground)
        print = findViewById(R.id.Vypis)
        //turtle = Turtle(pg!!)
        txt2 = findViewById(R.id.editTextText5)
        txt3 = findViewById(R.id.textView3)
        txt4 = findViewById(R.id.textView5)
        txtFullScreen = findViewById(R.id.editTextText5)
        txt = findViewById(R.id.editTextText)
        but1 = findViewById(R.id.button3)
        but2 = findViewById(R.id.button4)
        but3 = findViewById(R.id.button5)
        but4 = findViewById(R.id.button)
        but5 = findViewById(R.id.button2)

        wordsHelper.add(WordHelper("def", 4, 1))
        wordsHelper.add(WordHelper("definuj", 4, 1))
        wordsHelper.add(WordHelper("for", 5,1))
        wordsHelper.add(WordHelper("print", 6,1))
        wordsHelper.add(WordHelper("vypis", 6,1))
        wordsHelper.add(WordHelper("inak", 7,1))
        wordsHelper.add(WordHelper("turtle", 8,1))
        wordsHelper.add(WordHelper("while", 9,1))
        wordsHelper.add(WordHelper("kym", 9,1))
        wordsHelper.add(WordHelper("return", 10,1))
        wordsHelper.add(WordHelper("vrat", 10,1))
        wordsHelper.add(WordHelper("dopredu", 13,1))
        wordsHelper.add(WordHelper("vpravo", 14,1))
        wordsHelper.add(WordHelper("vlavo", 15,1))
        th = TextHelper(txt, but1, but2, but3, but4, but5, wordsHelper)

        if (Build.VERSION.SDK_INT >= 21) {
            txt!!.showSoftInputOnFocus = false
            txt2!!.showSoftInputOnFocus = false
        } else if (Build.VERSION.SDK_INT >= 11) {
            txt!!.setRawInputType(InputType.TYPE_CLASS_TEXT)
            txt!!.setTextIsSelectable(true)
            txt2!!.setRawInputType(InputType.TYPE_CLASS_TEXT)
            txt2!!.setTextIsSelectable(true)
            txt3!!.setRawInputType(InputType.TYPE_CLASS_TEXT)
            txt3!!.setTextIsSelectable(false)
            txt4!!.setRawInputType(InputType.TYPE_CLASS_TEXT)
            txt4!!.setTextIsSelectable(false)
        } else {
            txt!!.setRawInputType(InputType.TYPE_NULL)
            txt!!.isFocusable = true
            txt2!!.setRawInputType(InputType.TYPE_NULL)
            txt2!!.isFocusable = true
            txt3!!.setRawInputType(InputType.TYPE_NULL)
            txt3!!.isFocusable = false
            txt4!!.setRawInputType(InputType.TYPE_NULL)
            txt4!!.isFocusable = false
        }
        parser = Parser(pg!!,print!!)
        runButton = findViewById(R.id.imageView44)
        openCloseTerminal = findViewById(R.id.imageView6)

        keyboardMain = findViewById(R.id.keyboard)
        var icM = txt!!.onCreateInputConnection(EditorInfo())
        keyboardMain!!.setsInputConection(icM, this)

        keyboardFullScreen = findViewById(R.id.keyboard2)
        var icF = txt2!!.onCreateInputConnection(EditorInfo())
        keyboardFullScreen!!.setsInputConection(icF, this)

        openFullScreen = findViewById(R.id.imageView5)
        closeFullScreen = findViewById(R.id.imageView7)
        mainLayout = findViewById(R.id.main)
        vypisl = findViewById(R.id.vypisl)
        TerminalLayout = findViewById(R.id.linearLayout2)
        fullScreenLayout = findViewById(R.id.fulls)
        fullScreenLayoutKey = findViewById(R.id.layFullKey)
        spodok = findViewById(R.id.spodok)
    }

    fun wordHelpButttonClicked(button: Button?){
        var lstWords = txt!!.text.toString().split(" ")
        var sLastWordpom = lstWords[lstWords.size-1].split("\n")
        var sLastWordpom2 = sLastWordpom[sLastWordpom.size-1].split(".")
        var sLastWord = sLastWordpom2[sLastWordpom2.size-1].replace("[","").replace("]","").replace("=","").replace(">","").replace("<","")
        txt!!.setText(txt!!.text.toString() + (button!!.text).removePrefix(sLastWord))
        txt!!.setSelection(txt!!.length())
        parseColor()
        setWords()
        keyboardMain!!.setCursor()
    }

}































