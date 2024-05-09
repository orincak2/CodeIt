package Compiler.Parser

open class BinaryOperation(nl: Syntax, nr: Syntax):
    Syntax(){
    var l = nl
    var r = nr
}