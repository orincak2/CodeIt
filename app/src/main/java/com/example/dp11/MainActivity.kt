package com.example.dp11


import Parser.Parser
import android.content.Intent
import android.content.pm.ActivityInfo
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.text.Html
import android.text.InputType
import android.text.Layout
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.view.inputmethod.EditorInfo
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import com.google.android.material.textview.MaterialTextView
import java.io.IOException
import java.util.Timer
import kotlin.concurrent.schedule

class MainActivity : AppCompatActivity() {

    var txt: EditText?= null
    var txt2: EditText?= null
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
    var keyboardMain: MyKeyboard? = null
    var keyboardFullScreen: MyKeyboard? = null
    var openFullScreen: ImageView? = null
    var closeFullScreen: ImageView? = null
    var mainLayout: LinearLayout? = null
    var fullScreenLayout: ConstraintLayout? = null

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
            }
        }
        timer.run()

        openFullScreen!!.setOnClickListener{
            mainLayout!!.setVisibility(View.INVISIBLE)
            fullScreenLayout!!.setVisibility(View.VISIBLE)
            txt2!!.text = txt!!.text
            th!!.setText(txt2)
        }

        closeFullScreen!!.setOnClickListener{
            mainLayout!!.setVisibility(View.VISIBLE)
            fullScreenLayout!!.setVisibility(View.INVISIBLE)
            txt!!.text = txt2!!.text
            th!!.setText(txt)
        }

        txt!!.setOnClickListener{
            if(keyboardMain!!.visibility == View.INVISIBLE){
                keyboardMain!!.setVisibility(View.VISIBLE)
            }else{
                keyboardMain!!.setVisibility(View.INVISIBLE)
            }
        }

        but1!!.setOnClickListener {
           txt!!.setText("d = 1\n" +
                    "definuj krok() [dopredu d]\n" +
                    "definuj toc() [vpravo 70]\n" +
                   "definuj zvec(h) [vrat d + h]\n" +
                    "kym d < 150 [krok() toc() d = zvec(7)]")
            //wordHelpButttonClicked(but1)
        }
        but2!!.setOnClickListener {
            txt!!.setText("d = true\n" +
                    "opakuj 2 [ak d [vypis 100][vypis 200] d = not d")
            //wordHelpButttonClicked(but2)
        }
        but3!!.setOnClickListener {
            txt!!.setText("a = turtle b = turtle a.dopredu 300 b.dopredu 100")
            //wordHelpButttonClicked(but3)
        }
        but4!!.setOnClickListener {
            txt!!.setText("c = 4 b = [7,2,1,\"nieco\"] a = 1 for x in 0..c [a = a+ b[x]] vypis a")
            //wordHelpButttonClicked(but4)
        }
        but5!!.setOnClickListener {
            wordHelpButttonClicked(but5)
        }

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

    fun init(){
        pg = findViewById<Playground>(R.id.playground)
        print = findViewById(R.id.Vypis)
        //turtle = Turtle(pg!!)
        txt2 = findViewById(R.id.editTextText3)
        txt = findViewById(R.id.editTextText)
        but1 = findViewById(R.id.button3)
        but2 = findViewById(R.id.button4)
        but3 = findViewById(R.id.button5)
        but4 = findViewById(R.id.button)
        but5 = findViewById(R.id.button2)

        wordsHelper.add(WordHelper("definuj", 4, 1))
        wordsHelper.add(WordHelper("opakuj", 5,1))
        wordsHelper.add(WordHelper("vypis", 6,1))
        wordsHelper.add(WordHelper("dopredu", 7,1))
        wordsHelper.add(WordHelper("vpravo", 8,1))
        wordsHelper.add(WordHelper("vlavo", 9,1))
        th = TextHelper(txt, but1, but2, but3, but4, but5, wordsHelper)

        if (Build.VERSION.SDK_INT >= 21) {
            txt!!.showSoftInputOnFocus = false
            txt2!!.showSoftInputOnFocus = false
        } else if (Build.VERSION.SDK_INT >= 11) {
            txt!!.setRawInputType(InputType.TYPE_CLASS_TEXT)
            txt!!.setTextIsSelectable(true)
            txt2!!.setRawInputType(InputType.TYPE_CLASS_TEXT)
            txt2!!.setTextIsSelectable(true)
        } else {
            txt!!.setRawInputType(InputType.TYPE_NULL)
            txt!!.isFocusable = true
            txt2!!.setRawInputType(InputType.TYPE_NULL)
            txt2!!.isFocusable = true
        }
        parser = Parser(pg!!,print!!)
        runButton = findViewById(R.id.imageView44)

        keyboardMain = findViewById(R.id.keyboard)
        var icM = txt!!.onCreateInputConnection(EditorInfo())
        keyboardMain!!.setsInputConection(icM, this)

        keyboardFullScreen = findViewById(R.id.keyboard2)
        var icF = txt2!!.onCreateInputConnection(EditorInfo())
        keyboardFullScreen!!.setsInputConection(icF, this)

        openFullScreen = findViewById(R.id.imageView5)
        closeFullScreen = findViewById(R.id.imageView7)
        mainLayout = findViewById(R.id.main)
        fullScreenLayout = findViewById(R.id.fulls)

    }

    fun wordHelpButttonClicked(button: Button?){
        var lstWords = txt!!.text.toString().split(" ")
        var sLastWord = lstWords[lstWords.size-1].replace("[","").replace("]","").replace("=","").replace(">","").replace("<","")
        txt!!.setText(txt!!.text.toString() + (button!!.text).removePrefix(sLastWord))
        txt!!.setSelection(txt!!.length())
        parseColor()
        setWords()
        keyboardMain!!.setCursor()
    }

}































