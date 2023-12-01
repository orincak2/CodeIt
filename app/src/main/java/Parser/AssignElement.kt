package Parser

class AssignElement(nvar:Identifier?, nexp:Syntax, nindex: Syntax):Syntax(){
    var varr = nvar
    var exp = nexp
    var index = nindex

    override fun generate() {
        (varr as Variable).generate()
        index.generate()
        poke(INSTRUCTION_SET_ELEMENT)
        poke(exp)
        if(varr is Variable) {
            (varr as Variable).generate_set()
        }
    }
}