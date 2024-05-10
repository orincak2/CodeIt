package Compiler

import Compiler.Parser.Parser
import Compiler.Parser.Syntax
import android.widget.EditText

class Compiler(nprint: EditText): Syntax() {
    var myParser = Parser()
    var myVirtualMachine = VirtualMachine(nprint)

    fun run(txt:String):Boolean{

        var parssTree = myParser.run(txt)

        generateReset()
        parssTree.generate()

        return myVirtualMachine.run()
    }


    fun generateReset(){
        //check(NOTHING)
        counter_adr = 500
        mem = MutableList(1000) { Any() }
        //mem.addAll(List(1000) { Any() })
        adr = 0
        poke(INSTRUCTION_JUMP)
        poke(globalvaradr)
        adr = globalvaradr
        //poke(2 + variables.size)
        //adr = adr + variables.size
    }


}