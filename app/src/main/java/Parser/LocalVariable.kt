package Parser

class LocalVariable(nname:String, nPos: Float):Variable(nname,nPos){

    override fun generate() {
        poke(INSTRUCTION_GETLOCAL)
        poke(pos)
    }
    override fun generate_set(){
        poke(INSTRUCTION_SETLOCAL)
        poke(pos)
    }
}