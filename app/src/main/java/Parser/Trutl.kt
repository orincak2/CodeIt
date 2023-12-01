package Parser

import com.example.dp11.Turtle

class Trutl(nturtle:Turtle):Syntax() {
    var turtle = nturtle

    override fun generate() {
        poke(INSTRUCTION_PUSH)
        poke(turtle)
    }
}