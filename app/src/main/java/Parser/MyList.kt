package Parser

class MyList(nvars: MutableList<Syntax>):Syntax() {
    var value = nvars
    override fun generate() {
        poke(INSTRUCTION_PUSH)
        poke(value)
    }
}