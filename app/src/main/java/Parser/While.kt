package Parser

class While(ntest: Syntax, nbody: Syntax ):Syntax(){
    var test = ntest
    var body = nbody
    override fun generate() {
        var test_adr = adr
        test.generate()
        poke(INSTRUCTION_JUMPIFFALSE)
        var jump_ins = adr
        adr += 1
        body.generate()
        poke(INSTRUCTION_JUMP)
        poke(test_adr)
        mem[jump_ins] = adr.toFloat()
    }
}