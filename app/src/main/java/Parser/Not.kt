package Parser

class Not(ne:Syntax):UnaryOperation(ne){
    override fun generate() {
        e.generate()
        poke(INSTRUCTION_NOT)
    }
}