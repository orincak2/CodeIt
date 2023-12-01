package Parser

class Const(nValue : Float):Syntax(){
    var value = nValue
    override fun generate(){
        poke(INSTRUCTION_PUSH)
        poke(value)
    }
}