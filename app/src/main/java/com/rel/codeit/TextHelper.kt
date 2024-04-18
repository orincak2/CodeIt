package com.rel.codeit

import android.graphics.Color
import android.text.Html
import android.util.Log
import android.widget.Button
import android.widget.EditText

class TextHelper(text : EditText?, but1 : Button?, but2 : Button?, but3 : Button?,but4 : Button?,but5 : Button?, nwordHelper: MutableList<WordHelper>) {
    var but11 = but1
    var but12 = but2
    var but13 = but3
    var but14 = but4
    var but15 = but5
    var eText = text
    var inputt = ""
    var tokenPred = ""
    var index = 0
    var look = '0'
    var token = ""
    var kind = 0
    var NOTHING = 0
    var NUMBER = 1
    var WORD = 2
    var SYMBOL = 3
    var wordHelper = mutableListOf<WordHelper>()
    var subHelper = mutableMapOf<String,String>()
    var subHName = ""
    var subHText = ""
    var helpuj = false

    init {
        wordHelper = nwordHelper

    }

    fun setWords(){
        var lstWords = eText!!.text.toString().trim().split(" ", "&nbsp;", "(",",","\\t")
        var pppp = lstWords[lstWords.lastIndex].split("&nbsp;")
        var pppr = pppp[pppp.lastIndex].split(160.toChar())
        var sLastWordpom = pppr[pppr.size-1].split("\n")
        var sLastWordpom2 = sLastWordpom[sLastWordpom.size-1].split(".")
        var sLastWord = sLastWordpom2[sLastWordpom2.size-1].replace("[","").replace("]","").replace("=","").replace(">","").replace("<","")
        for (sWord in wordHelper){
            if(sWord.iMainPrio > 100 && sWord.myEqual(sLastWord)){
                var iIndex = wordHelper.indexOf(sWord)
                wordHelper[iIndex] = WordHelper(sWord.sText, 0, 100, sWord.isSUb)
            }
            else if(sWord.iMainPrio > 100 && sWord.sText.contains(sLastWord)){
                var iIndex = wordHelper.indexOf(sWord)
                wordHelper[iIndex] = WordHelper(sWord.sText, 1, 101, sWord.isSUb)
            }
            else if(sWord.iMainPrio < 100 && sWord.myEqual(sLastWord)){
                var iIndex = wordHelper.indexOf(sWord)
                wordHelper[iIndex] = WordHelper(sWord.sText, 0, sWord.iMainPrio, sWord.isSUb)
            }
            else if(sWord.iMainPrio < 100 && sWord.sText.contains(sLastWord)){
                var iIndex = wordHelper.indexOf(sWord)
                wordHelper[iIndex] = WordHelper(sWord.sText, 1, sWord.iMainPrio, sWord.isSUb)
            }
            else if((sWord.iMainPrio == 0 || sWord.iMainPrio == 1) && !sWord.sText.contains(sLastWord) && sWord.iSecPrio > 50){
                var iIndex = wordHelper.indexOf(sWord)
                wordHelper[iIndex] = WordHelper(sWord.sText, 200, 0, sWord.isSUb)
            }
            else if((sWord.iMainPrio == 0 || sWord.iMainPrio == 1) && !sWord.sText.contains(sLastWord) && sWord.iSecPrio < 50){
                var iIndex = wordHelper.indexOf(sWord)
                wordHelper[iIndex] = WordHelper(sWord.sText, sWord.iSecPrio, 1, sWord.isSUb)
            }
        }
        wordHelper.sort()
        but11!!.text = wordHelper[0].sText
        but12!!.text = wordHelper[1].sText
        but13!!.text = wordHelper[2].sText
        but14!!.text = wordHelper[3].sText
        but15!!.text = wordHelper[4].sText
    }
    
    fun setColors(ind : Int = 0, jeMedzernik: Boolean = false){
        inputt = eText!!.text.toString()
        index = 0
        look = '0'
        token = ""
        kind = 0
        var chyba = false
        var pom = ""
        next()
        pom = scan(pom,jeMedzernik)
        while (kind != NOTHING){
            if(token == "definuj" || token == "def" || token == "fun" || token == "metoda"|| token == "funkcia"){
                pom += getColoredText(token, Color.rgb(255, 165, 0).toString())
                pom = scan(pom,jeMedzernik, true)
                pom += getColoredText(token, Color.BLUE.toString())
            }else if(ind != 0 && ind <= index && !chyba){
                pom += getColoredTextWithBackground(token, Color.rgb(0,0,0).toString(),Color.rgb(255,0,0).toString())
                chyba = true
            } else if(token == "for" || token == "cyklus" || token == "foreach" || token == "opakuj" || token == "repeat" || token == "kym" || token == "while" || token == "ak" || token == "if" || token == "inak" || token == "else"){
                pom += getColoredText(token, Color.rgb(255,105,180).toString())
            }else if(token == "vrat" || token == "return"){
                pom += getColoredText(token, Color.rgb(255, 165, 0).toString())
            }else if(token == "true" || token == "false"){
                pom += getColoredText(token, Color.rgb(255, 165, 0).toString())
            }else if(token == "vypis" || token == "print"){
            pom += getColoredText(token, Color.BLUE.toString())
            }else if(token[0] == '\"'){
                pom += getColoredText(token, Color.GREEN.toString())
            }else if(kind == NUMBER && tokenPred == "="){
                pom += getColoredText(token, Color.CYAN.toString())
            }else if((wordHelper.any { it.equals(token) })){
                val index = wordHelper.indexOfFirst { it.equals(token) }
                if(wordHelper[index].isSUb || token == "kruh" || token == "stvorec" || token == "trojuholnik" || token == "turtle" || token == "korytnacka" || token == "dopredu" || token == "forward" || token == "left" || token == "vlavo" || token == "vpravo" || token == "right") {
                    pom += getColoredText(token, Color.BLUE.toString())
                }else{

                    pom += getColoredText(token, Color.BLACK.toString())
                }
            }else
            {
                if(token == "=" && !wordHelper.any{ it.equals(tokenPred) }){
                    wordHelper.add(WordHelper(tokenPred, 200, 0))
                }
                if(token == "(" && (tokenPred=="stvorec" || tokenPred=="kruh" || tokenPred=="trojuholnik") && look == 0.toChar()){
                    pom += getColoredText(token, Color.BLACK.toString()) + getColoredText("&#8206;x,y,velkost,farba)&#8206;", Color.argb(100,210,210,210).toString())
                }else if(token == "(" && (tokenPred=="turtle" || tokenPred=="korytnacka") && look == 0.toChar()){
                    pom += getColoredText(token, Color.BLACK.toString()) + getColoredText("&#8206;x,y,farba)&#8206;", Color.argb(100,210,210,210).toString())
                }
                else if(token == "(" && (subHelper[tokenPred] != null) && look == 0.toChar()){
                    pom += getColoredText(token, Color.BLACK.toString()) + getColoredText("&#8206;"+ subHelper[tokenPred]+"&#8206;", Color.argb(100,210,210,210).toString())
                }else if(token == "(" && (wordHelper.any { it.equals(tokenPred) }) && (!subHelper.any(){it.equals(tokenPred)})){
                    subHName = tokenPred
                    helpuj = true
                    pom += getColoredText(token, Color.BLACK.toString())
                }


                else {
                    if(helpuj){
                        subHText += token
                        if(token == ")"){
                            subHelper.put(subHName,subHText)
                            helpuj = false
                            subHText = ""
                        }
                    }
                    pom += getColoredText(token, Color.BLACK.toString())
                }
            }
            tokenPred = token
            pom = scan(pom,jeMedzernik)
            Log.d("aaa", token + " ! " + tokenPred)

        }
        eText!!.setText(Html.fromHtml(pom))
        
    }


    fun scan(nResult: String,jeMedzernik:Boolean = false, sub:Boolean = false): String{
        var result = nResult
        while(look == ' ' || look == '\n' || look == '\t' || look == ';' || look == 160.toChar()){
            if(look == ' ' || look == 160.toChar()){
                result += "&nbsp;"
            }else if (look == '\n' ){
                result += "<br>"
            }else if (look == '\t' || look == 9.toChar()){
                result += "&nbsp;"
            }else{
                result += look
            }

            next()
        }
        token = ""
        if(look == 8206.toChar()){
            next()
            while (look != 8206.toChar())
                next()
            next()
        }
        if(look.isDigit()){
            while(look.isDigit()){
                token += look
                next()
            }
            kind = NUMBER
        }else if(look.isLetter()){
            while(look.isLetter() || look.isDigit()){
                token += look
                next()
            }
            kind = WORD
            if(token != "definuj" && token != "def" && token != "fun" && token != "metoda"&& token != "funkcia" && token != "for" && token != "cyklus" && token != "foreach" && token != "opakuj" && token != "repeat" && token != "kym" && token != "while" && token != "ak" && token != "if" && token != "inak" && token != "else" && token != "vrat" && token != "return" && token != "true" && token != "false" && token != "vypis" && token != "print"){
                for(x in wordHelper){
                    if(x.sText == token){
                        return result
                    }
                }
                if(sub){
                    if(jeMedzernik)
                        wordHelper.add(WordHelper(token, 200, 0, true))
                }else {
                    //if(jeMedzernik)
                        //wordHelper.add(WordHelper(token, 200, 0))
                }
            }
        }else if(look == '\"'){
            token += look
            next()
            while(look != '\"' && look != 0.toChar()){
                token += look
                next()
            }
            token += look
            next()
            kind = WORD
        }else if(look == '<' || look == '>'){
            if(look == '<'){
                token += "&lt"
            }else{
                token += "&gt"
            }
            next()
            if(look == '='){
                token += look
                next()
            }

            kind = SYMBOL
        }else if(look != 0.toChar()){
            token += look
            next()
            kind = SYMBOL
        }else{
            kind = NOTHING
        }
        return result
    }

    fun next(){
        if(index >= inputt.length){
            val asciiValue = 0
            look = asciiValue.toChar()
        }else{
            look = inputt[index]
            index += 1
        }
    }

    fun getColoredText(text: String, color: String): String? {
        return "<font color=$color>$text</font>"
    }

    fun getColoredTextWithBackground(text: String, textColor: String, backgroundColor: String): String {
        // Nastavíte farbu textu a pozadia pomocou štýlov CSS v značke <span>
        return "<span style='color: $textColor; background-color: $backgroundColor;'>$text</span>"
    }

    fun setText(text: EditText?){
        eText = text
    }




}