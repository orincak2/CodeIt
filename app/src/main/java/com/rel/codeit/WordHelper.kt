package com.rel.codeit

class WordHelper(val sText: String, val iMainPrio: Int, val iSecPrio: Int, val isSUb :Boolean = false) : Comparable<WordHelper> {

    override fun compareTo(other: WordHelper): Int {
        if(iMainPrio < other.iMainPrio) return -1
        if(iMainPrio > other.iMainPrio) return 1
        if(iSecPrio < other.iSecPrio) return -1
        if(iSecPrio > other.iSecPrio) return 1
        return 0

    }

    override fun equals(other: Any?): Boolean {
        if(other is WordHelper) {return  sText == other.sText}
        return other.toString() == sText
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