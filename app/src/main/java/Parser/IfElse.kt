package Parser

class IfElse(ntest: Syntax, nbodytrue: Syntax, nbodyfalse :Syntax? = null):Syntax(){
    var test = ntest
    var bodytrue = nbodytrue
    var bodyfalse = nbodyfalse
    override fun generate() {
        test.generate()
        poke(INSTRUCTION_JUMPIFFALSE)
        var jumpfalse_ins = adr
        adr += 1
        bodytrue.generate()
        if(bodyfalse == null){
            mem[jumpfalse_ins] = adr.toFloat()
        }else{
            poke(INSTRUCTION_JUMP)
            var jump_ins = adr
            adr += 1
            mem[jumpfalse_ins] = adr.toFloat()
            bodyfalse!!.generate()
            mem[jump_ins] = adr.toFloat()
        }
    }
}