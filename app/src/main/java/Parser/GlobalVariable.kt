package Parser

class GlobalVariable(nname:String, nPos: Float):Variable(nname,nPos){

    override fun generate() {
        poke(INSTRUCTION_GET)
        poke(pos)
    }
    override fun generate_set(){
        poke(INSTRUCTION_SET)
        poke(pos)
    }
}