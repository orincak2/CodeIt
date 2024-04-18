package Parser


import Turtle.Farba
import Turtle.Fd
import Turtle.Kruh
import Turtle.Lt
import Turtle.Position
import Turtle.Rt
import Turtle.Stvorec
import Turtle.Trojuholnik
import android.widget.TextView
import com.rel.codeit.Playground
import com.rel.codeit.TextHelper
import com.rel.codeit.Turtle
import com.google.android.material.textview.MaterialTextView


import java.io.IOException
import kotlin.collections.List

fun Boolean.toFloat() = if (this) 1.toFloat() else 0.toFloat()
fun Float.toBoolean() = if (this == 1.toFloat()) true else false

class Parser(npg: Playground, nprint: MaterialTextView, texH: TextHelper):Syntax() {
    var pg = npg
    var print = nprint
    var turtle = Turtle(pg!!)
    var tabindex = 0
    var tabcount = 0
    var bolEnter = false
    var txH = texH
    class MojaTrieda(val hodnota: Any) {
        operator fun plus(inyObjekt: MojaTrieda): Any {
            if(this.hodnota is Float){
                if(inyObjekt.hodnota is Float){
                    return (this.hodnota as Float).toFloat() + (inyObjekt.hodnota as Float).toFloat()
                }
                if(inyObjekt.hodnota is Int){
                    return (this.hodnota as Float).toFloat() + (inyObjekt.hodnota as Int).toInt()
                }
                if(inyObjekt.hodnota is String){
                    return (this.hodnota as Float).toString() + (inyObjekt.hodnota as String)
                }
            }
            if(this.hodnota is Int){
                if(inyObjekt.hodnota is Float){
                    return (this.hodnota as Int).toInt() + (inyObjekt.hodnota as Float).toFloat()
                }
                if(inyObjekt.hodnota is Int){
                    return (this.hodnota as Int).toInt() + (inyObjekt.hodnota as Int).toInt()
                }
                if(inyObjekt.hodnota is String){
                    return (this.hodnota as Int).toString() + (inyObjekt.hodnota as String)
                }
            }
            if(this.hodnota is String){
                if(inyObjekt.hodnota is Float){
                    return (this.hodnota as String) + (inyObjekt.hodnota as Float).toString()
                }
                if(inyObjekt.hodnota is  Int){
                    return (this.hodnota as String) + (inyObjekt.hodnota as Int).toString()
                }
                if(inyObjekt.hodnota is String){
                    return (this.hodnota as String) + (inyObjekt.hodnota as String)
                }
            }
            return 99999998
        }

        operator fun times(inyObjekt: MojaTrieda): Any {
            if(this.hodnota is Float){
                if(inyObjekt.hodnota is Float){
                    return (this.hodnota as Float).toFloat() * (inyObjekt.hodnota as Float).toFloat()
                }
                if(inyObjekt.hodnota is Int){
                    return (this.hodnota as Float).toFloat() * (inyObjekt.hodnota as Int).toInt()
                }
                if(inyObjekt.hodnota is String){
                    val opakujuciSaRetazec = (inyObjekt.hodnota as String).toString().repeat((this.hodnota as Float).toInt())
                    return opakujuciSaRetazec
                }
            }

            if(this.hodnota is Int){
                if(inyObjekt.hodnota is Float){
                    return (this.hodnota as Int).toInt() * (inyObjekt.hodnota as Float).toFloat()
                }
                if(inyObjekt.hodnota is Int){
                    return (this.hodnota as Int).toInt() * (inyObjekt.hodnota as Int).toInt()
                }
                if(inyObjekt.hodnota is String){
                    val opakujuciSaRetazec = (inyObjekt.hodnota as String).toString().repeat((this.hodnota as Int).toInt())
                    return opakujuciSaRetazec
                }
            }
            if(this.hodnota is String){
                if(inyObjekt.hodnota is Float){
                    val opakujuciSaRetazec = (this.hodnota as String).toString().repeat((inyObjekt.hodnota as Float).toInt())
                    return opakujuciSaRetazec
                }
                if(inyObjekt.hodnota is Int){
                    val opakujuciSaRetazec = (this.hodnota as String).toString().repeat((inyObjekt.hodnota as Int).toInt())
                    return opakujuciSaRetazec
                }

            }
            return 99999998
        }

        fun equals(inyObjekt: MojaTrieda): Boolean {
            if(this.hodnota is Float){
                if(inyObjekt.hodnota is Float){
                    return (this.hodnota as Float).toFloat() != (inyObjekt.hodnota as Float).toFloat()
                }
            }
            if(this.hodnota is MutableList<*>){
                if(inyObjekt.hodnota is MutableList<*>){
                    return (this.hodnota as MutableList<*>) != (inyObjekt.hodnota as MutableList<*>)
                }
            }
            if(this.hodnota is Array<*>){
                if(inyObjekt.hodnota is Array<*>){
                    return (this.hodnota as Array<*>) != (inyObjekt.hodnota as Array<*>)
                }
            }
            if(this.hodnota is Int){
                if(inyObjekt.hodnota is Int){
                    return (this.hodnota as Int).toInt() != (inyObjekt.hodnota as Int).toInt()
                }
            }
            if(this.hodnota is String){
                if(inyObjekt.hodnota is String){
                    return (this.hodnota as String).toString() != (inyObjekt.hodnota as String).toString()
                }

            }
            return false
        }
    }

    fun run(txt:TextView){
        turtle.reset()
        reset()
        var leeeen = "def len(x){}"
        input = txt!!.text.toString().trim()
        //input.replace("   ", "\t")
        tabindex = 0
        tabcount = 0
        index = 0
        pomindex = 0
        bolEnter = false
        next()
        scan()
        globals = mutableMapOf<String, Identifier>()
        locals = mutableMapOf<String, Variable>()
        globalvaradr = 2
        localvardelta = 0
        //variables = mutableMapOf<String, Int>()
        //subroutines = mutableMapOf<String, Subroutine>()
        var program = parse(0)
        //check(NOTHING)
        counter_adr = 500
        mem = MutableList(1000) { Any() }
        //mem.addAll(List(1000) { Any() })
        adr = 0
        poke(INSTRUCTION_JUMP)
        poke(globalvaradr)
        adr = globalvaradr
        //poke(2 + variables.size)
        //adr = adr + variables.size
        program.generate()
        reset()
        while(terminated != true){
            executeP()
        }

    }
    fun fromAnyToFloat(anyFloatValue: Any):Float{
        if(anyFloatValue is Float){
            return anyFloatValue.toFloat()
        }

        return 99999999.toFloat()
    }

    fun len(obj: Any): Int {
        return when (obj) {
            is String -> obj.length
            is Array<*> -> obj.size
            is Collection<*> -> obj.size
            else -> throw IllegalArgumentException("Unsupported type")
        }
    }
    fun fromAnyToInt(anyFloatValue: Any):Int{
        if(anyFloatValue is Int){
            return anyFloatValue.toInt()
        }
        throw Exception()
    }
    fun fromAnyTo(anyFloatValue: Any):Any{
        if(anyFloatValue is String){
            return anyFloatValue.toString()
        }
        if(anyFloatValue is Char){
            return anyFloatValue.toString()
        }
        if(anyFloatValue is Float){
            return anyFloatValue.toFloat()
        }
        if(anyFloatValue is Number){
            return anyFloatValue.toInt()
        }

        return 99999999.toFloat()
    }

    fun executeP(){
        if(fromAnyToFloat(mem[pc]) == INSTRUCTION_FD.toFloat()){
            pc += 1
            var turtlee = mem[top + 1] as Turtle
            turtlee.dopredu(pg.pwidth.toFloat() * fromAnyToFloat(mem[top])/1000)
            top += 2
        }else if (fromAnyToFloat(mem[pc]) == INSTRUCTION_MINUS.toFloat()){
            pc += 1
            mem[top] = -fromAnyToFloat(mem[top])
        }else if (fromAnyToFloat(mem[pc]) == INSTRUCTION_STVOREC.toFloat()){
            pc += 1
            var turtlee = mem[top + 4] as Turtle
            var x = pg.pwidth.toFloat() * fromAnyToFloat(mem[top+3])/1000
            var y = pg.pwidth.toFloat() * fromAnyToFloat(mem[top+2])/1000
            var v = pg.pwidth.toFloat() * fromAnyToFloat(mem[top+1])/1000
            turtlee.stvorec(x,y,v,fromAnyTo(mem[top]).toString())
            top += 5
        }else if (fromAnyToFloat(mem[pc]) == INSTRUCTION_KRUH.toFloat()){
            pc += 1
            var turtlee = mem[top + 4] as Turtle
            var x = pg.pwidth.toFloat() * fromAnyToFloat(mem[top+3])/1000
            var y = pg.pwidth.toFloat() * fromAnyToFloat(mem[top+2])/1000
            var v = pg.pwidth.toFloat() * fromAnyToFloat(mem[top+1])/1000
            turtlee.kruh(x,y,v,fromAnyTo(mem[top]).toString())
            top += 5
        }else if (fromAnyToFloat(mem[pc]) == INSTRUCTION_TROJUHOLNIK.toFloat()){
            pc += 1
            var turtlee = mem[top + 4] as Turtle
            var x = pg.pwidth.toFloat() * fromAnyToFloat(mem[top+3])/1000
            var y = pg.pwidth.toFloat() * fromAnyToFloat(mem[top+2])/1000
            var v = pg.pwidth.toFloat() * fromAnyToFloat(mem[top+1])/1000
            turtlee.trojuholnik(x,y,v,fromAnyTo(mem[top]).toString())
            top += 5
        }else if (fromAnyToFloat(mem[pc]) == INSTRUCTION_ADD.toFloat()){
            pc += 1
            mem[top + 1] = MojaTrieda(fromAnyTo(mem[top + 1])) + MojaTrieda(fromAnyTo(mem[top]))
            top = top + 1
        }else if (fromAnyToFloat(mem[pc]) == INSTRUCTION_SUB.toFloat()){
            pc += 1
            mem[top + 1] = fromAnyToFloat(mem[top + 1]) - fromAnyToFloat(mem[top])
            top = top + 1
        }else if (fromAnyToFloat(mem[pc]) == INSTRUCTION_MUL.toFloat()){
            pc += 1
            mem[top + 1] = MojaTrieda(fromAnyTo(mem[top + 1])) * MojaTrieda(fromAnyTo(mem[top]))
            top = top + 1
        }else if (fromAnyToFloat(mem[pc]) == INSTRUCTION_NOT.toFloat()){
            pc += 1
            if(mem[top] == 1.toFloat()){
                mem[top] = 0.toFloat()
            }else{
                mem[top] = 1.toFloat()
            }
        }else if (fromAnyToFloat(mem[pc]) == INSTRUCTION_DIV.toFloat()){
            pc += 1
            mem[top + 1] = fromAnyToFloat(mem[top + 1]) / fromAnyToFloat(mem[top])
            top = top + 1
        }
        else if (fromAnyToFloat(mem[pc]) == INSTRUCTION_AND.toFloat()){
            pc += 1
            mem[top + 1] = (fromAnyToFloat(mem[top + 1]).toBoolean() && fromAnyToFloat(mem[top]).toBoolean()).toFloat()
            top = top + 1
        } else if (fromAnyToFloat(mem[pc]) == INSTRUCTION_OR.toFloat()){
            pc += 1
            mem[top + 1] = (fromAnyToFloat(mem[top + 1]).toBoolean() || fromAnyToFloat(mem[top]).toBoolean()).toFloat()
            top = top + 1
        } else if (fromAnyToFloat(mem[pc]) == INSTRUCTION_LESS.toFloat()){
            pc += 1
            var pom1 = mem[top + 1]
            var pom2 = mem[top]
            if(!(pom1 is Number)){
               pom1 = len(mem[top + 1]).toFloat()
            }else{
                pom1 = fromAnyToFloat(mem[top + 1])
            }
            if(!(pom2 is Number)){
                pom2 = len(mem[top]).toFloat()
            }else{
                pom2 = fromAnyToFloat(mem[top])
            }
            mem[top + 1] = (pom1 < pom2).toFloat()
            top = top + 1
        } else if (fromAnyToFloat(mem[pc]) == INSTRUCTION_GREATER.toFloat()){
            pc += 1
            mem[top + 1] = (fromAnyToFloat(mem[top + 1]) > fromAnyToFloat(mem[top])).toFloat()
            top = top + 1
        }else if (fromAnyToFloat(mem[pc]) == INSTRUCTION_NOTEQUAL.toFloat()){
            pc += 1
            mem[top + 1] = (MojaTrieda(mem[top + 1]).equals(MojaTrieda(mem[top]))).toFloat()
            top = top + 1
        }else if (fromAnyToFloat(mem[pc]) == INSTRUCTION_CHOSE.toFloat()){
            //pc += 1
            var pom1 = fromAnyToFloat(mem[top + 1])
            var pom2 = fromAnyToFloat(mem[top])
            if(pom1 < pom2){
                mem[pc] = INSTRUCTION_LESS.toFloat()
            }else{
                mem[pc] = INSTRUCTION_GREATER.toFloat()
            }
        } else if (fromAnyToFloat(mem[pc]) == INSTRUCTION_LESSEQUAL.toFloat()){
            pc += 1
            mem[top + 1] = (fromAnyToFloat(mem[top + 1]) <= fromAnyToFloat(mem[top])).toFloat()
            top = top + 1
        } else if (fromAnyToFloat(mem[pc]) == INSTRUCTION_GREATEREQUAL.toFloat()){
            pc += 1
            mem[top + 1] = (fromAnyToFloat(mem[top + 1]) >= fromAnyToFloat(mem[top])).toFloat()
            top = top + 1
        } else if (fromAnyToFloat(mem[pc]) == INSTRUCTION_IN.toFloat()){
            pc += 1
            mem[top + 1] = (mem[top + 1].toString() in mem[top].toString()).toFloat()
            top = top + 1
        } else if (fromAnyToFloat(mem[pc]) == INSTRUCTION_EQUAL.toFloat()){
            pc += 1
            mem[top + 1] = (mem[top + 1].toString() == mem[top].toString()).toFloat()
            top = top + 1
        }else if (fromAnyToFloat(mem[pc]) == INSTRUCTION_LT.toFloat()){
            pc += 1
            var turtlee = mem[top + 1] as Turtle
            turtlee.vlavo(fromAnyToFloat(mem[top]))
            top += 2
        }
        else if (fromAnyToFloat(mem[pc]) == INSTRUCTION_RT.toFloat()){
            pc += 1
            var turtlee = mem[top + 1] as Turtle
            turtlee.vpravo(fromAnyToFloat(mem[top]))
            top += 2
        }
        else if (fromAnyToFloat(mem[pc]) == INSTRUCTION_LEN.toFloat()){
            pc += 1
            mem[top] = len((mem[top]))
        }
        else if (fromAnyToFloat(mem[pc]) == INSTRUCTION_SET_COLOR.toFloat()){
            pc += 1
            var turtlee = mem[top + 1] as Turtle
            turtlee.farba(fromAnyTo(mem[top]).toString())
            top += 2
        }
        else if (fromAnyToFloat(mem[pc]) == INSTRUCTION_SET_POSITION.toFloat()){
            pc += 1
            var turtlee = mem[top + 2] as Turtle
            turtlee.x =pg.pwidth.toFloat() * fromAnyToFloat(mem[top+1])/100
            turtlee.y =pg.pwidth.toFloat() * fromAnyToFloat(mem[top])/100
            top += 3
        }
        else if (fromAnyToFloat(mem[pc]) == INSTRUCTION_SET.toFloat()){
            pc += 1
            index = fromAnyToFloat(mem[pc]).toInt()
            pc = pc + 1
            mem[index] = mem[top]
            top = top + 1
        }
        else if (fromAnyToFloat(mem[pc]) == INSTRUCTION_GET.toFloat()){
            pc += 1
            index = fromAnyToFloat(mem[pc]).toInt()
            pc = pc + 1
            top = top - 1
            mem[top] = mem[index]
        }else if (fromAnyToFloat(mem[pc]) == INSTRUCTION_PRINT.toFloat()){
            pc += 1
            print.setText(print.text.toString() + "\n> " + fromAnyTo(mem[top]))
            top = top + 1
        }else if (fromAnyToFloat(mem[pc]) == INSTRUCTION_PUSH.toFloat()){
            pc += 1
            top = top - 1
            mem[top] = mem[pc]
            pc = pc + 1
        }else if (fromAnyToFloat(mem[pc]) == INSTRUCTION_JUMP.toFloat()){
            pc = fromAnyToFloat(mem[pc + 1]).toInt()
        }else if (fromAnyToFloat(mem[pc]) == INSTRUCTION_JUMPIFFALSE.toFloat()){
            pc = pc + 1
            if(mem[top] == 0.toFloat()){
                pc = fromAnyToFloat(mem[pc]).toInt()
            }else{
                pc += 1
            }
            top += 1
        }else if (fromAnyToFloat(mem[pc]) == INSTRUCTION_CALL.toFloat()){
            pc = pc + 1
            top -= 1
            mem[top] = (pc + 1).toFloat()
            /////
            top = top - 1
            mem[top] = frame
            frame = top
            //////
            pc = fromAnyToFloat(mem[pc]).toInt()
        }else if (fromAnyToFloat(mem[pc]) == INSTRUCTION_RETURN_VALUE.toFloat()){
            var value = mem[top]
            top +=1
            pc = pc + 2
            top = frame + 2 + fromAnyToFloat(mem[pc]).toInt()
            pc = fromAnyToFloat(mem[frame + 1]).toInt()
            frame = fromAnyTo(mem[frame]) as Int
            top -= 1
            mem[top] = value

            /*pc = fromAnyToFloat(mem[top]).toInt()
            top = top + 1*/
        }else if (fromAnyToFloat(mem[pc]) == INSTRUCTION_RETURN.toFloat()){
            pc = pc + 1
            top = frame + 2 + fromAnyToFloat(mem[pc]).toInt()
            pc = fromAnyToFloat(mem[frame + 1]).toInt()
            frame = fromAnyTo(mem[frame]) as Int
            /*pc = fromAnyToFloat(mem[top]).toInt()
            top = top + 1*/
        }
        else if (fromAnyToFloat(mem[pc]) == INSTRUCTION_LOOP.toFloat()){
            pc += 1
            index = fromAnyToFloat(mem[pc]).toInt()
            pc = pc + 1
            mem[index] = fromAnyToFloat(mem[index]) - 1
            if(fromAnyToFloat(mem[index]) > 0){
                pc = fromAnyToFloat(mem[pc]).toInt()
            }else{
                pc += 1
            }
        }else if (fromAnyToFloat(mem[pc]) == INSTRUCTION_GETLOCAL.toFloat()){
            pc += 1
            index = frame + fromAnyToFloat(mem[pc]).toInt()
            pc = pc + 1
            top = top - 1
            mem[top] = mem[index]
        }else if (fromAnyToFloat(mem[pc]) == INSTRUCTION_SETLOCAL.toFloat()){
            pc += 1
            index = frame + fromAnyToFloat(mem[pc]).toInt()
            pc = pc + 1
            mem[index] = mem[top]
            top = top + 1
        }else if (fromAnyToFloat(mem[pc]) == INSTRUCTION_SET_ELEMENT.toFloat()){
            pc += 1
            (mem[top+1] as MutableList<Syntax>)[fromAnyToFloat(mem[top]).toInt()] = mem[pc] as Syntax
            top = top + 1
            pc += 1
        }else if (fromAnyToFloat(mem[pc]) == INSTRUCTION_GET_ELEMENT.toFloat()){
            if(mem[top+1] is String) {
                pc += 1
                var element = (mem[top + 1] as String)[fromAnyToFloat(mem[top]).toInt()]
                mem[top+1] = element
                top += 1
                pc +=2
            }
            else{
                pc += 1
                var element =
                    (mem[top + 1] as MutableList<Syntax>)[fromAnyToFloat(mem[top]).toInt()]
                top += 2
                adr = pc
                (element as Syntax).generate()
            }
        }else if (fromAnyToFloat(mem[pc]) == INSTRUCTION_ADD_ELEMENT.toFloat()){
            pc += 1
            (mem[top] as MutableList<Syntax>).add(mem[pc] as Syntax)
            top += 1
            pc += 1
        }
        else {
            terminated = true
        }
    }


    fun parse(poradie: Int): Block {
        var result = Block()
        if(poradie== 0) {
            var basick = GlobalVariable("basicKoritnacka", globalvaradr.toFloat())
            result.add(
                Assign(
                    basick,
                    Trutl(
                        Turtle(
                            pg,
                            pg.pwidth.toFloat() * 0.5.toFloat(),
                            pg.pheight.toFloat() * 0.5.toFloat(),
                            "black"
                        )
                    )
                )
            )
            globals["basicKoritnacka"] = basick
            globalvaradr += 1
        }

        while (kind == WORD && tabindex <= tabcount ){
            if(token == "ak" || token == "if") {
                var navysetab = tabcount - tabindex
                scan()
                var test = Syntax()
                if (token == "(") {
                    scan()
                    test = expr()
                    scan()
                } else {
                    test = expr()
                }
                var boltab = false
                if (token == ":") {
                    tabindex += 1 + navysetab
                    boltab = true
                }
                check(arrayOf(SYMBOL), arrayOf("{", ":"))
                scan()
                var ifelse = IfElse(test, parse(1), null)

                if (!boltab)
                    check(arrayOf(SYMBOL), arrayOf("}"))

                if (token == "}") {
                    scan()
                }

                if (token == "inak" || token == "else") {
                    if (boltab) {
                        if (tabindex - 1 != tabcount) {
                            throw Exception("Zle odsadenie" + "?" + index)
                        }
                    }
                    scan()
                    check(arrayOf(SYMBOL), arrayOf("{", ":"))


                if (token == "{" || token == ":") {
                    if (token == ":" && !boltab) {
                        navysetab = tabcount - tabindex
                        tabindex += 1 + navysetab
                        boltab = true
                    }
                    scan()
                    ifelse.bodyfalse = parse(1)

                    if (!boltab)
                        check(arrayOf(SYMBOL), arrayOf("}"))

                    if (token == "}") {
                        scan()
                    }
                }
            }
                if(boltab){
                    tabindex -= (1 + navysetab)
                }
                result.add(ifelse)
            }
            else if(token == "definuj" || token == "def" || token == "fun" || token == "metoda"|| token == "funkcia"){
                if(tabindex != 0 || tabcount !=0){
                    throw Exception("Metodu nieje mozne definovat vo vnutri ineho tela." + "?" + index)
                }
                scan()
                check(arrayOf(WORD), emptyArray())
                var name = token
                if(name in globals){
                    throw IOException(token + " sa už používa." + "?" +index)
                }
                scan()
                var pom = Subroutine(name, params(), null)
                globals[name] = pom
                check(arrayOf(SYMBOL), arrayOf("{", ":"))
                //check(SYMBOL, "{")
                var boltab = false
                if(token == ":"){
                    tabindex += 1
                    boltab = true
                }
                scan()
                locals = pom.vars
                localvardelta = -1
                pom.body = parse(1)
                //pom.vars = locals
                locals = mutableMapOf<String, Variable>()
                //check(SYMBOL, "}")
                if(!boltab)
                    check(arrayOf(SYMBOL), arrayOf("}"))

                if(token == "}"){
                    scan()
                }
                if(boltab){
                    tabindex -= (1)
                }
                result.add(pom)
            }
            else if (token == "for" || token == "cyklus" || token == "forEach") {
                var navysetab = tabcount - tabindex
                scan()
                if(token == "("){
                    scan()
                }
                var name = token
                scan()
                if(token == "in" || token == "="){
                    scan()
                }


                var pom :Assign? = null
                var rangeEnd = Syntax()
                var test = Syntax()
                var pom2: Assign? = null
                var pom3: Assign? = null
                var prve = expr()
                if(token == "." || token == "," || token == ";" || token == name){
                    scan()
                    if(token == name){
                       scan()
                    }
                    if(token == "." || token == "<" || token == ">") {
                        scan()
                    }
                    rangeEnd = expr()
                    var pomL = GlobalVariable(name, globalvaradr.toFloat())
                    pom = Assign(pomL, prve)
                    globals[name] = pomL
                    globalvaradr += 1
                    test = Less(pomL, rangeEnd)
                }else{
                    if(prve is MyList || prve is Strings) {
                        var pomL3 = GlobalVariable(name+"FL", globalvaradr.toFloat())
                        pom3 = Assign(pomL3, prve)
                        globals[name+"FL"] = pomL3
                        globalvaradr += 1

                        var pomL = GlobalVariable(name+"FI", globalvaradr.toFloat())
                        pom = Assign(pomL, Const((0).toFloat()))
                        globals[name+"FI"] = pomL
                        globalvaradr += 1

                        var pomL2 = GlobalVariable(name, globalvaradr.toFloat())
                        pom2 = Assign(pomL2, ListElement(pomL3, pomL))
                        globals[name] = pomL2
                        globalvaradr += 1
                        test = Less(pomL, pomL3)
                    }
                    else {
                        var pomL = GlobalVariable(name+"FL", globalvaradr.toFloat())
                        pom = Assign(pomL, Const((0).toFloat()))
                        globals[name+"FL"] = pomL
                        globalvaradr += 1

                        var pomL2 = GlobalVariable(name, globalvaradr.toFloat())
                        pom2 = Assign(pomL2, ListElement(prve as Variable, pomL))
                        globals[name] = pomL2
                        globalvaradr += 1
                        test = Less(pomL, prve)
                    }

                }
               /* scan()
               // check(SYMBOL, "in")
                scan()
                var rangeStart = expr()*/
                //check(SYMBOL, "..")
                //check(SYMBOL, "[")
                if(token == ")"){
                    scan()
                }
                var boltab = false
                if(token == ":"){
                    tabindex += 1 + navysetab
                    boltab = true
                }
                scan()
                var loopBody = parse(1)
                locals = mutableMapOf<String, Variable>()
                //check(SYMBOL, "]")
                if(boltab){
                    tabindex -= (1 + navysetab)
                }
                if(token == "}"){
                    scan()
                }
                result.add(ForLoop(pom!!, test, loopBody, pom2, pom3))
            }
            else if(token == "opakuj" || token == "repeat"){
                var navysetab = tabcount - tabindex
                scan()
                var n = addsub()
                var boltab = false
                if(token == ":"){
                    tabindex += 1 + navysetab
                    boltab = true
                }
                scan()
                result.add(Repeat(n, parse(1)))
                if(boltab){
                    tabindex -= (1 + navysetab)
                }
                if(token == "}"){
                    scan()
                }
            }
            else if(token == "vrat" || token == "return"){
                scan()
                if(token == "("){
                    scan()
                }
                result.add(Return(expr()))
                if(token == ")"){
                    scan()
                }
            }
            else if(token == "vypis" || token == "print" || token == "printf"|| token == "println"){
                scan()
                if(token == "("){
                    scan()
                }
                result.add(Print(addsub()))
                if(token == ")"){
                    scan()
                }
            }
            else if(token == "kym" || token == "while"){
                var navysetab = tabcount - tabindex
                scan()
                var test = Syntax()
                if(token == "("){
                    scan()
                    test = expr()
                    scan()
                }else{
                    test = expr()
                }
                //check(SYMBOL, "{")
                var boltab = false
                check(arrayOf(SYMBOL), arrayOf("{", ":"))
                if(token == ":"){
                    tabindex += 1 + navysetab
                    boltab = true
                }
                scan()
                result.add(While(test, parse(1)))
                if(boltab){
                    tabindex -= (1 + navysetab)
                }
                //check(SYMBOL, "}")
                if(!boltab)
                    check(arrayOf(SYMBOL), arrayOf("}"))

                if(token == "}"){
                    scan()
                }
            }
            else if (token == "dopredu" || token == "forward" || token == "vpred") {
                scan()
                if(token == "(" || token == "=") {
                    scan()
                    result.add(Fd(globals["basicKoritnacka"] as Variable, addsub()))
                    if(token == ")")
                        scan()
                }else{
                    result.add(Fd(globals["basicKoritnacka"] as Variable, addsub()))
                }
            }
            else if (token == "vpravo" || token == "right") {
                scan()
                if(token == "(") {
                    scan()
                    result.add(Rt(globals["basicKoritnacka"] as Variable, addsub()))
                    scan()
                }else{
                    result.add(Rt(globals["basicKoritnacka"] as Variable, addsub()))
                }

            }
            else if (token == "vlavo" || token == "left") {
                scan()
                if(token == "(") {
                    scan()
                    result.add(Lt(globals["basicKoritnacka"] as Variable, addsub()))
                    scan()
                }else{
                    result.add(Lt(globals["basicKoritnacka"] as Variable, addsub()))
                }
            }
            else if (token == "stvorec" || token == "square") {
                scan()
                var Color= Syntax()
                 Color = Strings("black")
                var X= Syntax()
                var Y= Syntax()
                var velk = Syntax()
                if(token == "(") {
                    scan()
                }
                check(arrayOf(NUMBER,WORD), emptyArray())
                X = addsub()
                if(token == ",") {
                    scan() //,
                }
                check(arrayOf(NUMBER,WORD), emptyArray())
                Y = addsub()
                if(token == ",") {
                    scan() //,
                }
                check(arrayOf(NUMBER,WORD), emptyArray())
                velk = addsub()
                if(token == ","){
                        scan()
                        Color = operand()
                        scan()
                    }
                if(token == ")") {
                    scan()
                }

                result.add(Stvorec(globals["basicKoritnacka"] as Variable,X,Y,velk,Color))
            }
            else if (token == "kruh") {
                scan()
                var Color= Syntax()
                Color = Strings("black")
                var X= Syntax()
                var Y= Syntax()
                var velk = Syntax()
                if(token == "(") {
                    scan()
                }
                check(arrayOf(NUMBER,WORD), emptyArray())
                X = addsub()
                if(token == ",") {
                    scan() //,
                }
                check(arrayOf(NUMBER,WORD), emptyArray())
                Y = addsub()
                if(token == ",") {
                    scan() //,
                }
                check(arrayOf(NUMBER,WORD), emptyArray())
                velk = addsub()
                if(token == ","){
                    scan()
                    Color = operand()
                    scan()
                }
                if(token == ")") {
                    scan()
                }

                result.add(Kruh(globals["basicKoritnacka"] as Variable,X,Y,velk,Color))

            }
            else if (token == "trojuholnik") {
                scan()
                var Color= Syntax()
                Color = Strings("black")
                var X= Syntax()
                var Y= Syntax()
                var velk = Syntax()
                if(token == "(") {
                    scan()
                }
                check(arrayOf(NUMBER,WORD), emptyArray())
                X = addsub()
                if(token == ",") {
                    scan() //,
                }
                check(arrayOf(NUMBER,WORD), emptyArray())
                Y = addsub()
                if(token == ",") {
                    scan() //,
                }
                check(arrayOf(NUMBER,WORD), emptyArray())
                velk = addsub()
                if(token == ","){
                    scan()
                    Color = operand()
                    scan()
                }
                if(token == ")") {
                    scan()
                }

                result.add(Trojuholnik(globals["basicKoritnacka"] as Variable,X,Y,velk,Color))

            }
            else if (token == "farba" || token == "color" || token == "setColor" || token == "nastavFarbu") {
                scan()
                if(token == "(" || token == "=") {
                    scan()
                    result.add(Farba(globals["basicKoritnacka"] as Variable, addsub()))
                    if(token == ")"){
                        scan()
                    }
                }else{
                    result.add(Farba(globals["basicKoritnacka"] as Variable, addsub()))
                }
            }
            else if (token == "position" || token == "pozicia" || token == "poloha" || token == "setPosition") {
                scan()
                if(token == "(" || token == "=") {
                    scan()
                    var xx = addsub()
                    scan()
                    var yy = addsub()
                    result.add(Position(globals["basicKoritnacka"] as Variable, xx, yy))
                    if(token == ")"){
                        scan()
                    }
                }else{
                    var xx = addsub()
                    scan()
                    var yy = addsub()
                    result.add(Position(globals["basicKoritnacka"] as Variable, xx, yy))
                }
            }
            else{
                var name = token
                scan()
                var pomIndex:Syntax? = null
                if(token == "["){
                    scan()
                    pomIndex = addsub()
                    check(arrayOf(SYMBOL), arrayOf("]"))
                    scan()
                }
                if(token != "="){
                    if((name in globals)) {
                        //throw Exception("Neznámy príkaz" + name)

                        /* if(!(globals[name] is Subroutine) && !(globals[name] is Trutl) ){
                        throw Exception(name + " nie je podprogram")
                    }*/
                        if (token == ".") {
                            var nieco = globals[name]
                            scan()
                            if (token == "dopredu" || token == "forward" || token == "vpred") {
                                scan()
                                if(token == "(" || token == "=") {
                                    scan()
                                    result.add(Fd(nieco as Variable, addsub()))
                                    if(token == ")")
                                        scan()
                                }else{
                                    result.add(Fd(nieco as Variable, addsub()))
                                }
                            }
                            if (token == "vpravo" || token == "right") {
                                scan()
                                if(token == "(") {
                                    scan()
                                    result.add(Rt(nieco as Variable, addsub()))
                                    scan()
                                }else{
                                    result.add(Rt(nieco as Variable, addsub()))
                                }

                            }
                            if (token == "vlavo" || token == "left") {
                                scan()
                                if(token == "(") {
                                    scan()
                                    result.add(Lt(nieco as Variable, addsub()))
                                    scan()
                                }else{
                                    result.add(Lt(nieco as Variable, addsub()))
                                }
                            }
                            if (token == "farba" || token == "color" || token == "setColor" || token == "nastavFarbu") {
                                scan()
                                if(token == "(" || token == "=") {
                                    scan()
                                    result.add(Farba(nieco as Variable, addsub()))
                                    if(token == ")"){
                                        scan()
                                    }
                                }else{
                                    result.add(Farba(nieco as Variable, addsub()))
                                }
                            }
                            if (token == "position" || token == "pozicia" || token == "poloha" || token == "setPosition") {
                                scan()
                                if(token == "(" || token == "=") {
                                    scan()
                                    var xx = addsub()
                                    scan()
                                    var yy = addsub()
                                    result.add(Position(nieco as Variable, xx, yy))
                                    if(token == ")"){
                                        scan()
                                    }
                                }else{
                                    var xx = addsub()
                                    scan()
                                    var yy = addsub()
                                    result.add(Position(nieco as Variable, xx, yy))
                                }
                            }
                            if (token == "add" || token == "pridaj" || token == "vloz"){
                                scan()
                                if(token == "(" || token == "=") {
                                    scan()
                                    var xx = addsub()
                                    result.add(AddElement(nieco as Variable, xx))
                                    if(token == ")"){
                                        scan()
                                    }
                                }
                            }
                        } else {
                            var subr = globals[name] as Subroutine
                            var agrs = Block()
                            if (token == "(") {
                                scan()
                                if (token != ")") {
                                    agrs.add(expr())
                                    while (token == ",") {
                                        scan()
                                        agrs.add(expr())
                                    }
                                }
                                check(arrayOf(SYMBOL), arrayOf(")"))
                                scan()
                            }
                            if (agrs.items.count() != subr.paramcount) {
                                throw Exception("Nesprávny počet parametrov")
                            }
                            result.add(Call(subr, agrs))
                        }
                    }else if(name == "len"){
                        if (token == "(") {
                            scan()
                            var agrs = expr()
                            check(arrayOf(SYMBOL), arrayOf(")"))
                            scan()
                            result.add(Len(agrs))
                        }else{
                            result.add(Len(expr()))
                        }

                    }
                    else if (name in locals){
                        if (token == ".") {
                            var nieco = locals[name]
                            scan()
                            if (token == "dopredu" || token == "forward" || token == "vpred") {
                                scan()
                                if(token == "(") {
                                    scan()
                                    result.add(Fd(nieco as Variable, addsub()))
                                    scan()
                                }else{
                                    result.add(Fd(nieco as Variable, addsub()))
                                }
                            }
                            if (token == "vpravo" || token == "right") {
                                scan()
                                if(token == "(") {
                                    scan()
                                    result.add(Rt(nieco as Variable, addsub()))
                                    scan()
                                }else{
                                    result.add(Rt(nieco as Variable, addsub()))
                                }

                            }
                            if (token == "vlavo" || token == "left") {
                                scan()
                                if(token == "(") {
                                    scan()
                                    result.add(Lt(nieco as Variable, addsub()))
                                    scan()
                                }else{
                                    result.add(Lt(nieco as Variable, addsub()))
                                }
                            }
                            if (token == "farba" || token == "color" || token == "setColor" || token == "nastavFarbu") {
                                scan()
                                if(token == "(" || token == "=") {
                                    scan()
                                    result.add(Farba(nieco as Variable, addsub()))
                                    if(token == ")"){
                                        scan()
                                    }
                                }else{
                                    result.add(Farba(nieco as Variable, addsub()))
                                }
                            }
                            if (token == "position" || token == "pozicia" || token == "poloha" || token == "setPosition") {
                                scan()
                                if(token == "(" || token == "=") {
                                    scan()
                                    var xx = addsub()
                                    scan()
                                    var yy = addsub()
                                    result.add(Position(nieco as Variable, xx, yy))
                                    if(token == ")"){
                                        scan()
                                    }
                                }else{
                                    var xx = addsub()
                                    scan()
                                    var yy = addsub()
                                    result.add(Position(nieco as Variable, xx, yy))
                                }
                            }
                            if (token == "add" || token == "pridaj" || token == "vloz"){
                                scan()
                                if(token == "(" || token == "=") {
                                    scan()
                                    var xx = addsub()
                                    result.add(AddElement(nieco as Variable, xx))
                                    if(token == ")"){
                                        scan()
                                    }
                                }
                            }
                        }
                    }else{
                        throw Exception("Neznama premenna?" + pomindex)
                    }
                }
                else{
                    scan()
                    var pom = Syntax()

                    if(locals.count() > 0){
                        if(name in globals){
                            if(token == "["){
                                pom = Assign(globals[name]!!, elements())
                            }else {
                                pom = Assign(globals[name]!!, expr())
                            }
                        }
                        else if(name in locals){
                            if(token == "["){
                                pom = Assign(locals[name]!!, elements())
                            }else {
                                pom = Assign(locals[name]!!, expr())
                            }
                        }else{
                            var pomL = LocalVariable(name, localvardelta.toFloat())
                            if(token == "["){
                                pom = Assign(pomL, elements())
                            }else {
                                pom = Assign(pomL, expr())
                            }
                            locals[name] = pomL
                            localvardelta -= 1
                        }
                    }else{
                        if(name in globals){
                            if(!(globals[name] is Variable)){
                                throw Exception(name + "nie je premenna" + "?" + index)
                            }
                            if(token == "["){
                                if(pomIndex != null){
                                    pom = AssignElement(globals[name]!!, elements(), pomIndex)
                                }else {
                                    pom = Assign(globals[name]!!, elements())
                                }
                            }else {
                                if(pomIndex != null){
                                    pom = AssignElement(globals[name]!!, expr(), pomIndex)
                                }else {
                                    pom = Assign(globals[name]!!, expr())
                                }
                            }
                        }else{

                            var pomL = GlobalVariable(name, globalvaradr.toFloat())
                            if(token == "["){
                                if(pomIndex != null){
                                    pom = AssignElement(pomL, elements(), pomIndex)
                                }else {
                                    pom = Assign(pomL, elements())
                                }
                            }else {
                                if(pomIndex != null){
                                    pom = AssignElement(pomL, expr(), pomIndex)
                                }else {
                                    pom = Assign(pomL, expr())
                                }
                            }
                            globals[name] = pomL
                            globalvaradr += 1
                        }
                    }
                    result.add(pom)
                }
            }
        }
        //tabindex = 0
        return result
    }

    fun params(): MutableMap<String, Variable>{
        var result = mutableMapOf<String, Variable>()
        if(token == "(" ){
            scan()
            if(kind == WORD){
                result[token] = LocalVariable(token,(0).toFloat())
                scan()
                while(token == ","){
                    scan()
                    check(arrayOf(WORD), emptyArray())
                    if(token in result){
                        throw Exception("Duplicitný názov parametra" + "?" + index)
                    }
                    result[token] = LocalVariable(token, (result.count()).toFloat())
                    scan()
                }
            }
            check(arrayOf(SYMBOL), arrayOf(")"))
            scan()
            var n = 1 + result.count()
            for(prvok in result.values){
                prvok.pos = n - prvok.pos
            }
        }
        return result
    }

    fun elements():MyList{
        var agrs = mutableListOf<Syntax>()
        if(token == "["){
            scan()
            if(token != "]"){
                agrs.add(expr())
                while (token == ","){
                    scan()
                    agrs.add(expr())
                }
            }
            check(arrayOf(SYMBOL), arrayOf("]"))
            scan()
        }
        return MyList(agrs)
    }

fun elementsO():MyList{
    var agrs = mutableListOf<Syntax>()
    if(token == "["){
        scan()
        if(token != "]"){
            agrs.add(expr())
            while (token == ","){
                scan()
                agrs.add(expr())
            }
        }
        check(arrayOf(SYMBOL), arrayOf("]"))
    }
    return MyList(agrs)
}

    fun operand():Syntax{
        var result = Syntax()
        if(kind == WORD){
            if(token == "false" || token == "False"){
                result = Const(0.toFloat())
            }else if (token == "cervena" || token == "modra" || token == "red" || token == "blue"){
                result = Strings(token.replace("\"",""))
            }else if (token == "true" || token == "True"){
                result = Const(1.toFloat())
            }else if (token == "turtle" || token == "korytnacka" || token == "pero" || token == "pen"){
                scan()
                var turtleX = 0F
                var turtleY = 0F
                var turtleColor = "red"
                if(token == "("){
                    scan()
                    turtleX = token.toFloat()
                    scan() //,
                    scan()
                    turtleY = token.toFloat()
                    scan()
                    if(token == ","){
                        scan()
                        turtleColor = token.replace("\"","")
                        scan()
                    }
                    scan()
                }
                return Trutl(Turtle(pg,turtleX, turtleY,turtleColor))
            }else if (token.count() > 0 && token[0] == '\"'){
                result = Strings(token.replace("\"",""))
            }else {
                if ((token in locals)) {
                    result = locals[token]!!

                }else if (token in globals){
                    if (!(globals[token] is Variable)){
                        var subr = globals[token] as Subroutine
                        var agrs = Block()
                        scan()
                        if(token == "("){
                            scan()
                            if(token != ")"){
                                agrs.add(expr())
                                while (token == ","){
                                    scan()
                                    agrs.add(expr())
                                }
                            }
                            //check(SYMBOL, ")")
                        }
                        if(agrs.items.count() != subr.paramcount){
                            throw Exception("Nesprávny počet parametrov")
                        }
                        result = Call(subr, agrs)
                    }else{
                        result = globals[token]!!
                    }
                }else if(token == "len"){
                    scan()
                    if (token == "(") {
                        scan()
                        var agrs = expr()
                        check(arrayOf(SYMBOL), arrayOf(")"))
                        result = Len(agrs)
                    }else{
                        result = Len(expr())
                    }

                }else{
                    throw Exception("Neznáma premenna" + "?" + index)
                }
            }
        }else if(kind == SYMBOL){
            //scan()
            result = elementsO() // AK je nove pole( napr. [1,4,6] vo for cykle
        }else{
            //check(NUMBER)
            result = Const(token.toFloat())
        }
        scan()
        if(token == "[" && result is Variable){
            scan()
            var indexx = addsub()
            var variab = result
            result = ListElement(variab,indexx)
            scan()
        }
       // scan()
        return result
    }

    fun braces():Syntax{
        if(token !="("){
            return operand()
        }
        scan()
        check(arrayOf(WORD, SYMBOL), emptyArray(), pomindex)
        var result = expr()
       // check(SYMBOL, ")")
        scan()
        return result
    }

    fun minus():Syntax{
        if(token != "-"){
            return braces()
        }
        scan()
        check(arrayOf(WORD, NUMBER), emptyArray(), pomindex)
        return Minus(braces())
    }


    fun multdiv():Syntax{
        var result = minus()
        while(true){
            if(token == "*"){
                scan()
                check(arrayOf(WORD, NUMBER), emptyArray(), pomindex)
                result = Mul(result, minus())
            }else if(token == "/"){
                scan()
                check(arrayOf(WORD, NUMBER), emptyArray(), pomindex)
                result = Div(result, minus())
            }else{
                return result
            }
        }
    }

    fun addsub():Syntax{
        var result = multdiv()
        while(true){
            if(token == "+"){
                scan()
                check(arrayOf(WORD, NUMBER), emptyArray(), pomindex)
                result = Add(result, multdiv())
            }else if(token == "-"){
                scan()
                check(arrayOf(WORD, NUMBER), emptyArray(), pomindex)
                result = Sub(result, multdiv())
            }else{
                return result
            }
        }
    }

    fun compare():Syntax{
        var result = addsub()
        while(true){
            if(token == "<"){
                scan()
                check(arrayOf(WORD, NUMBER), emptyArray(), pomindex)
                result = Less(result, addsub())
            }else if(token == ">"){
                scan()
                check(arrayOf(WORD, NUMBER), emptyArray(), pomindex)
                result = Greater(result, addsub())
            }else if(token == "==" || token == "="){
                scan()
                check(arrayOf(WORD, NUMBER), emptyArray(), pomindex)
                result = Equal(result, addsub())
            }else if(token == "<="){
                scan()
                check(arrayOf(WORD, NUMBER), emptyArray(), pomindex)
                result = LessEqual(result, addsub())
            }else if(token == "!="){
                scan()
                result = NotEqual(result, addsub())
            }else if(token == ">="){
                scan()
                check(arrayOf(WORD, NUMBER), emptyArray(), pomindex)
                result = GreaterEqual(result, addsub())
            }else if(token == "in"){
                scan()
                //check(arrayOf(WORD, NUMBER), emptyArray(), pomindex)
                result = In(result, addsub())
            }else{
                return result
            }
        }
    }

    fun not():Syntax{
        if(token !="not"){
            return compare()
        }
        scan()
        check(arrayOf(WORD, NUMBER), emptyArray(), pomindex)
        return Not(compare())
    }

    fun and():Syntax{
        var result = not()
        if(token !="and"){
            return result
        }
        scan()
        check(arrayOf(WORD, NUMBER), emptyArray(), pomindex)
        return And(result, not())
    }

    fun or():Syntax{
        var result = and()
        if(token !="or"){
            return result
        }
        scan()
        check(arrayOf(WORD, NUMBER), emptyArray(), pomindex)
        return Or(result, and())
    }

    fun expr():Syntax{
        var result = or()
        return result
    }

    enum class KindType() {
        nic,
        cislo,
        slovo,
        symbol
    }

    fun  check(expected_kind: Array<Int>, expected_token: Array<String>, ind: Int = index){
        if(expected_kind.size > 0 && !expected_kind.contains(kind)){
            throw IOException("zly typ ?" + ind)
        }
        if(expected_token.size > 0 && !expected_token.contains(token)){
            throw IOException("nesprávna syntax, očakával som: " + expected_token.joinToString(" alebo ") + ", dostal som: " + token+ "?" + ind)
        }
    }

    fun next(){
        if(index >= input.length){
            val asciiValue = 0
            look = asciiValue.toChar()
        }else{
            look = input[index]
            index += 1
        }
    }

    fun scan(){
        pomindex = index
        while(look == ' ' || look == '\n' || look == '\t' || look == ';' || look == 160.toChar()){
            if(look == '\n'){
                tabcount= 0
                bolEnter = true
            }
            next()
            while(look == ' ' || look == '\n' || look == ';'){
                if(look == '\n'){
                    tabcount= 0
                    bolEnter = true
                }
                next()
            }
            if(look == 160.toChar() && bolEnter){
                tabcount += 1
            }
            if(look == '\t'){
                tabcount += 1
            }
        }
        token = ""
        position = index - 1
        if(look.isDigit()){
            bolEnter = false
            while(look.isDigit()){
                token += look
                next()
            }
            if(look == '.'){
                token += look
                next()
                while(look.isDigit()){
                    token += look
                    next()
                }
            }
            kind = NUMBER
        }else if(look.isLetter()){
            bolEnter = false

            while(look.isLetter() || look.isDigit()){
                token += look
                next()
            }
            kind = WORD
        }else if(look == '\"'){
            bolEnter = false

            token += look
            next()
            while(look != '\"' && look != 0.toChar()){
                token += look
                next()
            }
            if(look == 0.toChar()){
                throw Exception("String nie je ukončený, očakával som \"?" + index)
            }
            token += look
            next()
            kind = WORD
        }else if(look == '<' || look == '>' || look == '=' || look == '!'){
            bolEnter = false

            token += look
            next()
            if(look == '='){
                token += look
                next()
            }
            kind = SYMBOL
        }else if(look != 0.toChar()){
            bolEnter = false

            token += look
            next()
            kind = SYMBOL
        }else{
            bolEnter = false

            kind = NOTHING
        }
    }
}