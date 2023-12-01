package Parser

class Minus(ne:Syntax):UnaryOperation(ne){
    override fun generate() {
        e.generate()
        poke(INSTRUCTION_MINUS)
    }
}