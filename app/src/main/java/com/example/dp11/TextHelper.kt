package com.example.dp11

import android.graphics.Color
import android.text.Html
import android.util.Log
import android.widget.Button
import android.widget.EditText
import java.io.IOException
import java.util.Collections.list
import java.util.Dictionary
import java.util.PriorityQueue

class TextHelper(text : EditText?, but1 : Button?, but2 : Button?, but3 : Button?,but4 : Button?,but5 : Button?, nwordHelper: MutableList<WordHelper>) {
    var but11 = but1
    var but12 = but2
    var but13 = but3
    var but14 = but4
    var but15 = but5
    var eText = text
    var input = ""
    var index = 0
    var look = '0'
    var token = ""
    var kind = 0
    var NOTHING = 0
    var NUMBER = 1
    var WORD = 2
    var SYMBOL = 3
    var wordHelper = mutableListOf<WordHelper>()

    init {
        wordHelper = nwordHelper

    }

    fun setWords(){
        var lstWords = eText!!.text.toString().split(" ")
        var sLastWord = lstWords[lstWords.size-1].replace("[","").replace("]","").replace("=","").replace(">","").replace("<","")
        for (sWord in wordHelper){
            if(sWord.iMainPrio > 100 && sWord.myEqual(sLastWord)){
                var iIndex = wordHelper.indexOf(sWord)
                wordHelper[iIndex] = WordHelper(sWord.sText, 0, 100)
            }
            else if(sWord.iMainPrio > 100 && sWord.sText.contains(sLastWord)){
                var iIndex = wordHelper.indexOf(sWord)
                wordHelper[iIndex] = WordHelper(sWord.sText, 1, 101)
            }
            else if(sWord.iMainPrio < 100 && sWord.myEqual(sLastWord)){
                var iIndex = wordHelper.indexOf(sWord)
                wordHelper[iIndex] = WordHelper(sWord.sText, 0, sWord.iMainPrio)
            }
            else if(sWord.iMainPrio < 100 && sWord.sText.contains(sLastWord)){
                var iIndex = wordHelper.indexOf(sWord)
                wordHelper[iIndex] = WordHelper(sWord.sText, 1, sWord.iMainPrio)
            }
            else if((sWord.iMainPrio == 0 || sWord.iMainPrio == 1) && !sWord.sText.contains(sLastWord) && sWord.iSecPrio > 50){
                var iIndex = wordHelper.indexOf(sWord)
                wordHelper[iIndex] = WordHelper(sWord.sText, 200, 0)
            }
            else if((sWord.iMainPrio == 0 || sWord.iMainPrio == 1) && !sWord.sText.contains(sLastWord) && sWord.iSecPrio < 50){
                var iIndex = wordHelper.indexOf(sWord)
                wordHelper[iIndex] = WordHelper(sWord.sText, sWord.iSecPrio, 1)
            }
        }
        wordHelper.sort()
        but11!!.text = wordHelper[0].sText
        but12!!.text = wordHelper[1].sText
        but13!!.text = wordHelper[2].sText
        but14!!.text = wordHelper[3].sText
        but15!!.text = wordHelper[4].sText
    }
    
    fun setColors(){

        input = eText!!.text.toString()
        index = 0
        look = '0'
        token = ""
        kind = 0
        var pom = ""
        next()
        pom = scan(pom)
        while (kind != NOTHING){
            if(token == "definuj" || token == "def"){
                pom += getColoredText(token, Color.rgb(255, 165, 0).toString())
                pom = scan(pom)
                pom += getColoredText(token, Color.BLUE.toString())
            }else if(token == "opakuj" || token == "kym" || token == "ak"){
                pom += getColoredText(token, Color.rgb(255,105,180).toString())
            }else
            {
                pom += getColoredText(token, Color.BLACK.toString())
            }
            pom = scan(pom)
            Log.d("aaa", token)
        }
        eText!!.setText(Html.fromHtml(pom))
        
    }


    fun scan(nResult: String): String{
        var result = nResult
        while(look == ' ' || look == '\n'){
            if(look == ' ' ){
                result += look
            }else{
                result += "<br>"
            }

            next()
        }
        token = ""
        if(look.isDigit()){
            while(look.isDigit()){
                token += look
                next()
            }
            kind = NUMBER
        }else if(look.isLetter()){
            while(look.isLetter()){
                token += look
                next()
            }
            kind = WORD
            if(token != "definuj" && token != "def" && token != "opakuj" && token != "kym" && token != "ak"){
                for(x in wordHelper){
                    if(x.sText == token){
                        return result
                    }
                }
                wordHelper.add(WordHelper(token, 200, 0))
            }
        }else if(look == '<' || look == '>'){
            if(look == '<'){
                token += "&lt"
            }else{
                token += "&gt"
            }
            token += look
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
        if(index >= input.length){
            val asciiValue = 0
            look = asciiValue.toChar()
        }else{
            look = input[index]
            index += 1
        }
    }

    fun getColoredText(text: String, color: String): String? {
        return "<font color=$color>$text</font>"
    }

    fun setText(text: EditText?){
        eText = text
    }




}