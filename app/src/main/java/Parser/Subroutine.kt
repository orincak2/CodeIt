package Parser

class Subroutine(nname: String, nvars: MutableMap<String, Variable>, nbody: Syntax?):Identifier(nname){
    var vars = nvars
    var paramcount = vars.count()
    var body = nbody
    var bodyadr = 0
    override fun generate() {
        poke(INSTRUCTION_JUMP)
        adr += 1
        bodyadr = adr

        var n = vars.count() - paramcount
        for(x in 0..n-1){
            poke(INSTRUCTION_PUSH)
            poke(0)
        }

        body!!.generate()
        poke(INSTRUCTION_RETURN)
        poke(paramcount)///
        mem[bodyadr - 1] = adr.toFloat()
    }
}