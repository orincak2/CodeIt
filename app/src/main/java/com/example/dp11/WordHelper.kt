package com.example.dp11

class WordHelper(val sText: String, val iMainPrio: Int, val iSecPrio: Int) : Comparable<WordHelper> {

    override fun compareTo(other: WordHelper): Int {
        if(iMainPrio < other.iMainPrio) return -1
        if(iMainPrio > other.iMainPrio) return 1
        if(iSecPrio < other.iSecPrio) return -1
        if(iSecPrio > other.iSecPrio) return 1
        return 0

    }

    override fun equals(other: Any?): Boolean {
        return (other is WordHelper) && sText == other.sText
    }

    fun myEqual(sWord : String): Boolean{
        if(sText.count() >= sWord.count()){
            for (x in 0..(sWord.count()-1)){
                if(sText[x] != sWord[x]){
                    return false
                }
            }
            return true
        }
        return false
    }
}