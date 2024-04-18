package Parser

class Len(nargs :Syntax):Syntax(){
    var args = nargs
    override fun generate() {
        args.generate()
        poke(INSTRUCTION_LEN)
    }
}