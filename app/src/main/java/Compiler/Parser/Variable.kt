package Compiler.Parser

open class Variable(nname:String, nPos: Float):Identifier(nname){
    var pos = nPos

    /*override fun generate() {
        poke(INSTRUCTION_GET)
        poke(variables[name]!!)
    }*/
    open fun generate_set(){
       /* poke(INSTRUCTION_SET)
        poke(variables[name]!!)*/
    }
}