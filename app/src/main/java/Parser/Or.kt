package Parser

class Or(nl:Syntax, nr:Syntax):BinaryOperation(nl,nr){
    override fun generate() {
        l.generate()
        r.generate()
        poke(INSTRUCTION_OR)
    }
}