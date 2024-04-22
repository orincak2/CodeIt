package Parser

class Input(nargs :Syntax):Syntax(){
    var args = nargs
    override fun generate() {
        args.generate()
        poke(INSTRUCTION_PRINT)
        poke(INSTRUCTION_INPUT)
    }
}