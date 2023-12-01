package Parser

class Block(vararg nItems : Syntax):Syntax(){
    var items = ArrayList<Syntax>()
    init {
        nItems.forEach { items.add(it) }
    }
    fun add(item : Syntax){
        items.add(item)
    }
    override fun execute() {
        items.forEach{it.execute()}
    }

    override fun generate() {
        items.forEach{it.generate()}
    }
}