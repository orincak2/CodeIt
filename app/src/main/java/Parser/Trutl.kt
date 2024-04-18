package Parser

import com.rel.codeit.Turtle

class Trutl(nturtle: Turtle):Syntax() {
    var turtle = nturtle

    override fun generate() {
        poke(INSTRUCTION_PUSH)
        poke(turtle)
    }
}