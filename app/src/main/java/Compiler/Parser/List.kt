package Compiler.Parser

import Compiler.INSTRUCTION_PUSH

class List(nvars: MutableList<Syntax>):Syntax() {
    var value = nvars
    override fun generate() {
        poke(INSTRUCTION_PUSH)
        poke(value)
    }
}