package Parser

class Print(nexp: Syntax):Syntax(){
    var exp = nexp
    override fun generate() {
        exp.generate()
        poke(INSTRUCTION_PRINT)
    }
}