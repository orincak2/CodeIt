package Parser

class Call(nsubr: Subroutine, nargs :Block):Syntax(){
    var subr = nsubr
    var args = nargs
    override fun generate() {
        args.generate()
        poke(INSTRUCTION_CALL)
        poke(subr.bodyadr)
    }
}