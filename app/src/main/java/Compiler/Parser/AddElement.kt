package Compiler.Parser

import Compiler.INSTRUCTION_ADD_ELEMENT

class AddElement(varr: Variable, nelemtn: Syntax):
    Syntax() {
    var vars = varr
    var elemtn = nelemtn
    override fun generate() {
        vars.generate()
        poke(INSTRUCTION_ADD_ELEMENT)
        poke(elemtn)

    }
}