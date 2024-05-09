package Compiler.Parser

class Assign(nvar: Identifier?, nexp: Syntax):
    Syntax(){
    var varr = nvar
    var exp = nexp

    override fun generate() {
        exp.generate()
        if(varr is Variable) {
            (varr as Variable).generate_set()
        }
    }
}