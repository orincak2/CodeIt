package Compiler.Parser

import Compiler.INSTRUCTION_PUSH

class Strings(nValue : String):Syntax(){
    var value = nValue
    override fun generate(){
        poke(INSTRUCTION_PUSH)
        poke(value)
    }
}