package Parser

class Strings(nValue : String):Syntax(){
    var value = nValue
    override fun generate(){
        poke(INSTRUCTION_PUSH)
        poke(value)
    }
}