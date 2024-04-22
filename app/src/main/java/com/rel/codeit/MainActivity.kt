package com.rel.codeit


import Parser.Parser
import android.R.id.button3
import android.content.pm.ActivityInfo
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.Html
import android.text.InputType
import android.text.method.ScrollingMovementMethod
import android.view.GestureDetector
import android.view.MotionEvent
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
import java.util.Timer
import kotlin.concurrent.schedule


class MainActivity : AppCompatActivity() {
    var lincounter = 1
    var bInput :Boolean? = false
    var term = false
    var pomTerString:String = ""
    private lateinit var gestureDetector: GestureDetector

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
    var print: EditText? = null
    var pg: Playground? = null
    var parser: Parser? = null
    var dvoj = false
    var runButton: ImageView? = null
    var neue: ImageView? = null
    var openCloseTerminal: ImageView? = null
    var keyboardMain: MyKeyboard? = null
    var keyboardMain2: MyKeyboard? = null
    var keyboardFullScreen: MyKeyboard? = null
    var openFullScreen: ImageView? = null
    var closeFullScreen: ImageView? = null
    var mainLayout: LinearLayout? = null
    var vypisl: LinearLayout? = null
    var TerminalLayout: LinearLayout? = null
    var fullScreenLayout: ConstraintLayout? = null
    var tr: ConstraintLayout? = null
    var fullScreenLayoutKey: ConstraintLayout? = null
    var spodok: ConstraintLayout? = null
    var vrch: ConstraintLayout? = null
    var startX = 0f
    var startY = 0f
    private val MAX_EXECUTION_TIME = 5000 // Maximálny čas vykonávania v milisekundách


    var handler: Handler? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main)
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        init()
        handler = Handler(Looper.getMainLooper());
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

        fun otvorter(){
            openCloseTerminal!!.setVisibility(View.VISIBLE)
            openFullScreen!!.setVisibility(View.VISIBLE)

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
            val param3 = LinearLayout.LayoutParams(
                ConstraintLayout.LayoutParams.MATCH_PARENT,
                0,
                50.0f
            )
            val param4 = LinearLayout.LayoutParams(
                ConstraintLayout.LayoutParams.MATCH_PARENT,
                0,
                3.0f
            )
            val param5 = LinearLayout.LayoutParams(
                ConstraintLayout.LayoutParams.MATCH_PARENT,
                0,
                0.0f
            )
            openCloseTerminal!!.setImageResource(com.google.android.material.R.drawable.mtrl_ic_arrow_drop_down)
            vypisl!!.setLayoutParams(param2)
            spodok!!.setLayoutParams(param)
            keyboardMain2!!.setLayoutParams(param5)
            tr!!.setLayoutParams(param4)
            vrch!!.setLayoutParams(param3)
            vypisl!!.setVisibility(View.VISIBLE)
            keyboardMain2!!.setVisibility(View.INVISIBLE)
            term = false
        }

        openCloseTerminal!!.setOnClickListener {
            openCloseTerminal!!.setVisibility(View.VISIBLE)
            openFullScreen!!.setVisibility(View.VISIBLE)
            if(vypisl!!.visibility == View.INVISIBLE) {
                otvorter()
            }else{
                if(term){
                    otvorter()
                }else {
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
                    val param3 = LinearLayout.LayoutParams(
                        ConstraintLayout.LayoutParams.MATCH_PARENT,
                        0,
                        50.0f
                    )

                    openCloseTerminal!!.setImageResource(com.google.android.material.R.drawable.mtrl_ic_arrow_drop_up)
                    vypisl!!.setLayoutParams(param2)
                    spodok!!.setLayoutParams(param)
                    vrch!!.setLayoutParams(param3)
                    vypisl!!.setVisibility(View.INVISIBLE)
                    keyboardMain2!!.setVisibility(View.INVISIBLE)
                    keyboardMain2!!.setLayoutParams(param2)
                    term = false
                }
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

        gestureDetector = GestureDetector(this, object : GestureDetector.SimpleOnGestureListener() {
            override fun onDoubleTap(e: MotionEvent): Boolean {
                if(keyboardMain!!.visibility == View.INVISIBLE){
                }else{
                     zavriklavesnicu()
                    dvoj = true
                }
                return true
            }
        })
        txt!!.setOnTouchListener { _, event ->
            gestureDetector.onTouchEvent(event)
            if(dvoj){
                dvoj = false
                true
            }else{
                dvoj = false

                false
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
               // zavriklavesnicu()
            }
        }


        fun klav2(){
            if(keyboardMain2!!.visibility == View.INVISIBLE){
                val param = LinearLayout.LayoutParams(
                    ConstraintLayout.LayoutParams.MATCH_PARENT,
                    0,
                    55.0f
                )
                val param2 = LinearLayout.LayoutParams(
                    ConstraintLayout.LayoutParams.MATCH_PARENT,
                    0,
                    0.0f
                )
                val param3 = LinearLayout.LayoutParams(
                    ConstraintLayout.LayoutParams.MATCH_PARENT,
                    0,
                    1.0f
                )
                val param4 = LinearLayout.LayoutParams(
                    ConstraintLayout.LayoutParams.MATCH_PARENT,
                    0,
                    7.0f
                )
                val param5 = LinearLayout.LayoutParams(
                    ConstraintLayout.LayoutParams.MATCH_PARENT,
                    0,
                    45.0f
                )
                spodok!!.setLayoutParams(param3)
                vrch!!.setLayoutParams(param2)
                vypisl!!.setLayoutParams(param5)
                keyboardMain2!!.setLayoutParams(param)
                tr!!.setLayoutParams(param4)
                tr!!.setVisibility(View.VISIBLE)
                vypisl!!.setVisibility(View.VISIBLE)
                keyboardMain2!!.setVisibility(View.VISIBLE)
                print!!.setSelection(print!!.text.length)

            }else{
                print!!.setSelection(print!!.text.length)
            }
        }
        print!!.setOnClickListener{

            //klav2()
            //term = true
        }

        fun isAClick(startX: Float, startY: Float, endX: Float, endY: Float): Boolean {
            val dx = startX - endX
            val dy = startY - endY
            val distance = Math.sqrt((dx * dx + dy * dy).toDouble())
            return distance < 10 // Prahová hodnota pre určenie kliknutia, môžeš ju prispôsobiť podľa potreby
        }

        print!!.setOnTouchListener { _, event ->
            // Zabraňuje ďalšiemu spracovaniu udalosti, ktorá spôsobila ťahanie (drag)
            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    startX = event.x
                    startY = event.y
                    false
                }
                MotionEvent.ACTION_UP -> {
                    val endX = event.x
                    val endY = event.y
                    if (isAClick(startX, startY, endX, endY)) {
                        //print!!.performClick()
                        klav2()
                        term = true
                        print!!.setSelection(print!!.text.length)
                        false

                    }else {
                        true
                    }
                }

                else -> false
            }
        }

        but1!!.setOnClickListener {
          /* txt!!.setText("a = turtle(300,300, \"modra\")" +
                   "\nd = 1" +
                   "\n far = [\"modra\",\"cervena\",\"zlta\",\"oranzova\",\"zelena\"]" +
                   "\n vyber = 3" +
                   "\n" +
                   "\ndefinuj krok(kor, dlzka):\n\tkor.dopredu(dlzka)\n" +
                   "\ndefinuj toc(kor, uhol):\n\tkor.vpravo(uhol)\n" +
                   "\ndefinuj zmenfarbu(kor):\n" +
                   "\tkor.farba(far[vyber]) \n" +
                   "\tvyber = vyber + 1 \n" +
                   "\tif(vyber > 4):\n" +
                   "\t\tvyber = 0\n" +
                   "\n" +
                   "kym(d < 250):\n" +
                   "\tkrok(a, d) \n" +
                   "\ttoc(a, 65) \n" +
                   "\tzmenfarbu(a) \n" +
                   "\td = d + 10")*/



               /*"d = 1 a = turtle(200,200,\"orange\") \n b = turtle(450,200,\"zlta\")\n" +
                    "definuj krok(kora, korb){kora.dopredu d korb.dopredu d}\n" +
                    "definuj toc(){a.vpravo 70 b.vpravo 70}\n" +
                   "definuj zvec(h) {vrat d + h}\n" +
                    "kym d < 150 {krok(a, b) toc() d = zvec(7)}")*/
            wordHelpButttonClicked(but1)
        }
        but2!!.setOnClickListener {
           /* txt!!.setText("a = turtle(300,300, \"modra\")" +
                    "\nd = 1" +
                    "\n far = [\"modra\",\"cervena\",\"zlta\",\"oranzova\",\"zelena\"]" +
                    "\n vyber = 0" +
                    "\ndefinuj krok(kor, dlzka){dopredu(dlzka)}\n" +
                    "definuj toc(kor, uhol){vpravo(uhol)}\n" +
                    "definuj zmenfarbu(kor){farba(far[vyber]) vyber = vyber + 1 if(vyber > 4){vyber = 0}}\n" +
                    "kym(d < 25) {krok(a, d) toc(a, 70) zmenfarbu(a) d = d + 1}")*/
            wordHelpButttonClicked(but2)
        }
        but3!!.setOnClickListener {
           /* txt!!.setText("a = turtle(300,300, \"modra\")" +
                    "\nd = 1" +
                    "\n far = [\"modra\",\"cervena\",\"zlta\",\"oranzova\",\"zelena\"]" +
                    "\n vyber = 2" +
                    "\n" +
                    "\ndefinuj krok(kor, dlzka)\n{\n\tkor.dopredu(dlzka)\n}\n" +
                    "\ndefinuj toc(kor, uhol)\n{\n\tkor.vpravo(uhol)\n}\n" +
                    "\ndefinuj zmenfarbu(kor)\n{\n" +
                    "\tkor.farba(far[vyber]) \n" +
                    "\tvyber = vyber + 1 \n" +
                    "\tif(vyber > 4)\n" +
                    "\t{\n\t\tvyber = 0\n\t}\n" +
                    "}\n\n" +
                    "kym(d < 250) \n{\n" +
                    "\tkrok(a, d) \n" +
                    "\ttoc(a, 75) \n" +
                    "\tzmenfarbu(a) \n" +
                    "\td = d + 10\n}")*/
            wordHelpButttonClicked(but3)
        }
        but4!!.setOnClickListener {
            /*txt!!.setText("pole = [1, 2, 3, 4]" +
                    "\npole.add(6)" +
                    "\nres = 0" +
                    "\nfor i in 0..5:" +
                    "\n\tres = res + pole[i]" +
                    "\nvypis(\"1.sucet pola je: \" + res)" +
                    "" +
                    "\n" +
                    "\nres = 0" +
                    "\nfor j = 0; j < 5:" +
                    "\n\tres = res + pole[j]" +
                    "\nvypis(\"2.sucet pola je: \" + res)" +
                    "" +
                    "\n" +
                    "\nres = 0" +
                    "\nfor x in pole:" +
                    "\n\tres = res + x" +
                    "\nvypis(\"3.sucet pola je: \" + res)"+
                    "" +
                    "\n" +
                    "\nres = 0" +
                    "\n pom = 0" +
                    "\nopakuj 5:" +
                    "\n\tres = res + pole[pom]" +
                    "\n\tpom = pom + 1" +
                    "\nvypis(\"4.sucet pola je: \" + res)"+
                    "" +
                    "\n" +
                    "\nfor y in pole:" +
                    "\n\tvypis(y)")*/
            wordHelpButttonClicked(but4)
        }
        but5!!.setOnClickListener {
            if(txt!!.text.toString() == "1"){
                txt!!.setText("a = turtle(300,300, \"modra\")" +
                        "\nd = 1" +
                        "\nfar = [\"modra\",\"cervena\",\"zlta\",\"oranzova\",\"zelena\"]" +
                        "\nvyber = 3" +
                        "\n" +
                        "\ndefinuj krok(kor, dlzka):\n\tkor.dopredu(dlzka)\n" +
                        "\ndefinuj toc(kor, uhol):\n\tkor.vpravo(uhol)\n" +
                        "\ndefinuj zmenfarbu(kor):\n" +
                        "\tkor.farba(far[vyber]) \n" +
                        "\tvyber = vyber + 1 \n" +
                        "\tif(vyber > 4):\n" +
                        "\t\tvyber = 0\n" +
                        "\n" +
                        "kym(d < 150):\n" +
                        "\tkrok(a, d) \n" +
                        "\ttoc(a, 65) \n" +
                        "\tzmenfarbu(a) \n" +
                        "\td = d + 5")
            }
            if(txt!!.text.toString() == "2"){
                txt!!.setText("a = turtle(300,300, \"modra\")" +
                        "\nd = 1" +
                        "\n far = [\"modra\",\"cervena\",\"zlta\",\"oranzova\",\"zelena\"]" +
                        "\n vyber = 0" +
                        "\ndefinuj krok(kor, dlzka){dopredu(dlzka)}\n" +
                        "definuj toc(kor, uhol){vpravo(uhol)}\n" +
                        "definuj zmenfarbu(kor){farba(far[vyber]) vyber = vyber + 1 if(vyber > 4){vyber = 0}}\n" +
                        "kym(d < 125) {krok(a, d) toc(a, 70) zmenfarbu(a) d = d + 5}")
            }
            if(txt!!.text.toString() == "3"){
                txt!!.setText("a = turtle(300,300, \"modra\")" +
                        "\nd = 1" +
                        "\n far = [\"modra\",\"cervena\",\"zlta\",\"oranzova\",\"zelena\"]" +
                        "\n vyber = 2" +
                        "\n" +
                        "\ndefinuj krok(kor, dlzka)\n{\n\tkor.dopredu(dlzka)\n}\n" +
                        "\ndefinuj toc(kor, uhol)\n{\n\tkor.vpravo(uhol)\n}\n" +
                        "\ndefinuj zmenfarbu(kor)\n{\n" +
                        "\tkor.farba(far[vyber]) \n" +
                        "\tvyber = vyber + 1 \n" +
                        "\tif(vyber > 4)\n" +
                                "\t{\n\t\tvyber = 0\n\t}\n" +
                        "}\n\n" +
                        "kym(d < 150) \n{\n" +
                        "\tkrok(a, d) \n" +
                        "\ttoc(a, 75) \n" +
                        "\tzmenfarbu(a) \n" +
                        "\td = d + 5\n}")
            }
            if(txt!!.text.toString() == "4"){
                txt!!.setText("pole = [1, 2, 3, 4]" +
                        "\npole.add(6)" +
                        "\nres = 0" +
                        "\nfor i in 0..5:" +
                        "\n\tres = res + pole[i]" +
                        "\nvypis(\"1.sucet pola je: \" + res)" +
                        "" +
                        "\n" +
                        "\nres = 0" +
                        "\nfor j = 0; j < 5:" +
                        "\n\tres = res + pole[j]" +
                        "\nvypis(\"2.sucet pola je: \" + res)" +
                        "" +
                        "\n" +
                        "\nres = 0" +
                        "\nfor x in pole:" +
                        "\n\tres = res + x" +
                        "\nvypis(\"3.sucet pola je: \" + res)"+
                        "" +
                        "\n" +
                        "\nres = 0" +
                        "\npom = 0" +
                        "\nopakuj 5:" +
                        "\n\tres = res + pole[pom]" +
                        "\n\tpom = pom + 1" +
                        "\nvypis(\"4.sucet pola je: \" + res)"+
                        "" +
                        "\n" +
                        "\nfor y in pole:" +
                        "\n\tvypis(y)")
            }
            if(txt!!.text.toString() == "5"){
                txt!!.setText("def sucet(hodnotaA, hodnotaB):" +
                        "\n\tvrat hodnotaA + hodnotaB" +
                        "\nd = 1")
            }
            if(txt!!.text.toString() == "6"){
                txt!!.setText("stvorec(500,200,100,\"red\")" +
                        "\ntrojuholnik(500,100,100,\"blue\")")
            }
            if(txt!!.text.toString() == "7"){
                txt!!.setText("hslovo=\"optimista\"" +
                        "\ntslovo = len(hslovo)*\"_\"" +
                        "\n" +
                        "\nneuspesne = 0" +
                        "\nwhile tslovo != hslovo:" +
                        "\n\tznak=input(\"Zadaj znak:\")" +
                        "\n\tif znak in hslovo:" +
                        "\n\t\tnove=\"\"" +
                        "\n\t\tfor i in hslovo:" +
                        "\n\t\t\tif(i==znak or i in tslovo):" +
                        "\n\t\t\t\tnove=nove+i" +
                        "\n\t\t\telse:" +
                        "\n\t\t\t\tnove=nove+\"_\"" +
                        "\n\t\ttslovo=nove" +
                        "\n\tprint(\"Zatial mas slovo: \" + tslovo)")
            }
            if(txt!!.text.toString() == "8"){
                txt!!.setText("a = 0" +
                        "\nwhile(a<3):" +
                        "\n\tprint(a)" +
                        "\n\tb = input(\"Zadaj cislo:\")" +
                        "\n\ta=a+1" +
                        "\n\tprint(b)")
            }


            //wordHelpButttonClicked(but5)
        }




        print!!.setMovementMethod(ScrollingMovementMethod())
        runButton!!.setOnClickListener {
            pomTerString = ""
            zavriklavesnicu()
            try {


                bInput = parser!!.run(txt!!.text.toString())



                // Spracovanie úspešného výsledku
                if(bInput!!){
                    klav2()
                    openCloseTerminal!!.setVisibility(View.INVISIBLE)
                    openFullScreen!!.setVisibility(View.INVISIBLE)
                    print!!.performClick()
                    print!!.requestFocus()
                }


                //bInput = parser!!.run(txt!!.text.toString())



            } catch (e: Exception) {
                print!!.setText(Html.fromHtml(getColoredText(print!!.text.toString() + "<br>", Color.BLACK.toString()) + getColoredText(e.message.toString().split('?')[0], Color.RED.toString()) + "<br>") )
                var pom = e.message.toString().split('?')
                if (pom.size > 1)
                    parseColor(e.message.toString().split('?')[1].toInt())
            }

        }

        neue!!.setOnClickListener {
            txt!!.setText("")
            print!!.setText("")

        }
    }

    fun otvorter(){
        openCloseTerminal!!.setVisibility(View.VISIBLE)
        openFullScreen!!.setVisibility(View.VISIBLE)

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
        val param3 = LinearLayout.LayoutParams(
            ConstraintLayout.LayoutParams.MATCH_PARENT,
            0,
            50.0f
        )
        val param4 = LinearLayout.LayoutParams(
            ConstraintLayout.LayoutParams.MATCH_PARENT,
            0,
            3.0f
        )
        val param5 = LinearLayout.LayoutParams(
            ConstraintLayout.LayoutParams.MATCH_PARENT,
            0,
            0.0f
        )
        openCloseTerminal!!.setImageResource(com.google.android.material.R.drawable.mtrl_ic_arrow_drop_down)
        vypisl!!.setLayoutParams(param2)
        spodok!!.setLayoutParams(param)
        keyboardMain2!!.setLayoutParams(param5)
        tr!!.setLayoutParams(param4)
        vrch!!.setLayoutParams(param3)
        vypisl!!.setVisibility(View.VISIBLE)
        keyboardMain2!!.setVisibility(View.INVISIBLE)
        term = false
    }

    fun zatvorter(){
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
        val param3 = LinearLayout.LayoutParams(
            ConstraintLayout.LayoutParams.MATCH_PARENT,
            0,
            50.0f

        )

        openCloseTerminal!!.setImageResource(com.google.android.material.R.drawable.mtrl_ic_arrow_drop_up)
        vypisl!!.setLayoutParams(param2)
        spodok!!.setLayoutParams(param)
        vrch!!.setLayoutParams(param3)
        vypisl!!.setVisibility(View.INVISIBLE)
        keyboardMain2!!.setVisibility(View.INVISIBLE)
        keyboardMain2!!.setLayoutParams(param2)
        term = false

    }
    fun opakujsInputom(){
            var pole = print!!.text.split("\n")
            var ind = pole.lastIndex
            var inputt = print!!.text.split("\n")[ind-1].replace("\"","")
            bInput = parser!!.run(txt!!.text.toString(), true,inputt)
        if(bInput!!){
            openCloseTerminal!!.setVisibility(View.INVISIBLE)
            openFullScreen!!.setVisibility(View.INVISIBLE)
            print!!.performClick()
            print!!.requestFocus()
        }else{
            otvorter()
        }
    }

    fun cezterminal(){
        var pole = print!!.text.split("\n")
        var ind = pole.lastIndex
        var inputt = print!!.text.split("\n")[ind-1].replace("\"","")

        if(inputt.contains("=") && (!inputt.contains("==") && !inputt.contains("!=") && !inputt.contains("<=") && !inputt.contains(">="))){
            pomTerString += inputt + "\n"
        }
        else{
            if(inputt == "\n" || inputt == "" || inputt.contains("False") || inputt.contains("True")){}else{
            inputt = "print "+inputt}
        }
        if(inputt == "\n" || inputt == "" || inputt.contains("False") || inputt.contains("True")){}else{
            try {

                bInput = parser!!.run(pomTerString + inputt)
            } catch (e: Exception) {
                print!!.setText(Html.fromHtml(getColoredText(print!!.text.toString() + "<br>", Color.BLACK.toString()) + getColoredText(e.message.toString().split('?')[0], Color.RED.toString()) + "<br>"))
                var pom = e.message.toString().split('?')
                if (pom.size > 1)
                    parseColor(e.message.toString().split('?')[1].toInt())
            }
            print!!.performClick()
            print!!.requestFocus()




        }

    }
    fun getColoredText(text: String, color: String): String? {
        return "<font color=$color>$text</font>"
    }
    
    fun parseColor(ind: Int = 0, boolean: Boolean = false){
        th!!.setColors(ind, boolean)
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

    fun zavriklavesnicu(){
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
        keyboardMain2!!.setVisibility(View.INVISIBLE)
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
        //wordsHelper.add(WordHelper("definuj", 4, 1))
        wordsHelper.add(WordHelper("for", 5,1))
        wordsHelper.add(WordHelper("print", 6,1))
        //wordsHelper.add(WordHelper("vypis", 6,1))
        //wordsHelper.add(WordHelper("inak", 7,1))
        wordsHelper.add(WordHelper("else", 7,1))
        wordsHelper.add(WordHelper("turtle", 8,1))
        //wordsHelper.add(WordHelper("korytnacka", 8,1))
        wordsHelper.add(WordHelper("while", 9,1))
        wordsHelper.add(WordHelper("input", 9,1))
        wordsHelper.add(WordHelper("range", 9,1))
       // wordsHelper.add(WordHelper("kym", 9,1))
        wordsHelper.add(WordHelper("return", 10,1))
        wordsHelper.add(WordHelper("len", 10,1))
        //wordsHelper.add(WordHelper("vrat", 10,1))
        //wordsHelper.add(WordHelper("dopredu", 13,1))
        //wordsHelper.add(WordHelper("vpravo", 14,1))
        //wordsHelper.add(WordHelper("vlavo", 15,1))
        wordsHelper.add(WordHelper("stvorec", 16,1))
        wordsHelper.add(WordHelper("trojuholnik", 17,1))
        wordsHelper.add(WordHelper("kruh", 18,1))
        //wordsHelper.add(WordHelper("farba", 18,1))
       // wordsHelper.add(WordHelper("pozicia", 18,1))
        th = TextHelper(txt, but1, but2, but3, but4, but5, wordsHelper)

        if (Build.VERSION.SDK_INT >= 21) {
            txt!!.showSoftInputOnFocus = false
            txt2!!.showSoftInputOnFocus = false
            print!!.showSoftInputOnFocus = false
        } else if (Build.VERSION.SDK_INT >= 11) {
            txt!!.setRawInputType(InputType.TYPE_CLASS_TEXT)
            txt!!.setTextIsSelectable(true)
            print!!.setRawInputType(InputType.TYPE_CLASS_TEXT)
            print!!.setTextIsSelectable(true)
            txt2!!.setRawInputType(InputType.TYPE_CLASS_TEXT)
            txt2!!.setTextIsSelectable(true)
            txt3!!.setRawInputType(InputType.TYPE_CLASS_TEXT)
            txt3!!.setTextIsSelectable(false)
            txt4!!.setRawInputType(InputType.TYPE_CLASS_TEXT)
            txt4!!.setTextIsSelectable(false)
        } else {
            txt!!.setRawInputType(InputType.TYPE_NULL)
            txt!!.isFocusable = true
            print!!.setRawInputType(InputType.TYPE_NULL)
            print!!.isFocusable = true
            txt2!!.setRawInputType(InputType.TYPE_NULL)
            txt2!!.isFocusable = true
            txt3!!.setRawInputType(InputType.TYPE_NULL)
            txt3!!.isFocusable = false
            txt4!!.setRawInputType(InputType.TYPE_NULL)
            txt4!!.isFocusable = false
        }
        parser = Parser(pg!!,print!!,th!!)
        runButton = findViewById(R.id.imageView44)
        neue = findViewById(R.id.imageView4)
        openCloseTerminal = findViewById(R.id.imageView6)

        keyboardMain = findViewById(R.id.keyboard)
        keyboardMain2 = findViewById(R.id.keyboard3)
        var icM = txt!!.onCreateInputConnection(EditorInfo())
        keyboardMain!!.setsInputConection(icM, this)

        var icM2 = print!!.onCreateInputConnection(EditorInfo())
        keyboardMain2!!.setsInputConection(icM2, this)

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
        vrch = findViewById(R.id.vrch)
        tr = findViewById(R.id.TerminalLayout)
    }

    fun wordHelpButttonClicked(button: Button?){
        val cursorIndex = txt!!.selectionStart
        val prvaCast = txt!!.text.toString().substring(0, cursorIndex)
        val druhaCast = txt!!.text.toString().substring(cursorIndex)
        var lstWords = prvaCast.trim().split(" ", "&nbsp;", "(",",","\\t")
        var pppp = lstWords[lstWords.lastIndex].split("&nbsp;")
        var pppr = pppp[pppp.lastIndex].split(160.toChar())
        var sLastWordpom = pppr[pppr.size-1].split("\n")
        var sLastWordpom2 = sLastWordpom[sLastWordpom.size-1].split(".")
        var sLastWord = sLastWordpom2[sLastWordpom2.size-1].replace("[","").replace("]","").replace("=","").replace(">","").replace("<","")
        txt!!.setText(prvaCast.trim() + (button!!.text).removePrefix(sLastWord) +druhaCast)
        txt!!.setSelection(txt!!.length())
        parseColor()
        setWords()
        //keyboardMain!!.setCursor()
        var a = (cursorIndex+(button!!.text).removePrefix(sLastWord).length).toInt()
        if(a > txt!!.text.toString().length)
            a = txt!!.text.toString().length
        txt!!.setSelection(a)
    }

}































