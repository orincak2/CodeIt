package Parser

class Return(nHodnota:Syntax):Syntax(){
    var hodnota =  nHodnota

    override fun generate() {

        hodnota.generate()

        poke(INSTRUCTION_RETURN_VALUE)
    }
    open fun generate_set(){
        /* poke(INSTRUCTION_SET)
         poke(variables[name]!!)*/
    }
}