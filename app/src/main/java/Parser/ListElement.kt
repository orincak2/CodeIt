package Parser

class ListElement(varr: Variable, nindex: Syntax):Syntax() {
    var vars = varr
    var index = nindex
    override fun generate() {
        vars.generate()
        index.generate()
        poke(INSTRUCTION_GET_ELEMENT)
        adr += 2
    }
}