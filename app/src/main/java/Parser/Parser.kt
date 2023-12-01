package Parser


import Turtle.Farba
import Turtle.Fd
import Turtle.Lt
import Turtle.Rt
import android.graphics.Color
import android.view.View
import android.widget.TextView
import com.example.dp11.Playground
import com.example.dp11.Turtle
import com.google.android.material.textview.MaterialTextView


import java.io.IOException
import kotlin.collections.List

fun Boolean.toFloat() = if (this) 1.toFloat() else 0.toFloat()
fun Float.toBoolean() = if (this == 1.toFloat()) true else false

class Parser(npg: Playground, nprint: MaterialTextView):Syntax() {
    var pg = npg
    var print = nprint
    var turtle = Turtle(pg!!)
    var tabindex = 0
    var tabcount = 0
    class MojaTrieda(val hodnota: Any) {
        operator fun plus(inyObjekt: MojaTrieda): Any {
            if(this.hodnota is Float){
                if(inyObjekt.hodnota is Float){
                    return (this.hodnota as Float) + (inyObjekt.hodnota as Float)
                }
                if(inyObjekt.hodnota is String){
                    return (this.hodnota as Float).toString() + (inyObjekt.hodnota as String)
                }
            }
            if(this.hodnota is String){
                if(inyObjekt.hodnota is Float){
                    return (this.hodnota as String) + (inyObjekt.hodnota as Float).toString()
                }
                if(inyObjekt.hodnota is String){
                    return (this.hodnota as String) + (inyObjekt.hodnota as String)
                }
            }
            return 99999998
        }
    }

    fun run(txt:TextView){
        turtle.reset()
        input = txt!!.text.toString()
        index = 0
        next()
        scan()
        globals = mutableMapOf<String, Identifier>()
        locals = mutableMapOf<String, Variable>()
        globalvaradr = 2
        localvardelta = 0
        //variables = mutableMapOf<String, Int>()
        //subroutines = mutableMapOf<String, Subroutine>()
        var program = parse()
        check(NOTHING)
        counter_adr = 70
        mem = mutableListOf<Any>(100)
        mem.addAll(List(100) { Any() })
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
        pg.cc = Color.GREEN
        pg.cc = Color.CYAN
    }
    fun fromAnyToFloat(anyFloatValue: Any):Float{
        if(anyFloatValue is Float){
            return anyFloatValue.toFloat()
        }

        return 99999999.toFloat()
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
            turtlee.dopredu(fromAnyToFloat(mem[top]))
            top += 2
        }else if (fromAnyToFloat(mem[pc]) == INSTRUCTION_MINUS.toFloat()){
            pc += 1
            mem[top] = -fromAnyToFloat(mem[top])
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
            mem[top + 1] = fromAnyToFloat(mem[top + 1]) * fromAnyToFloat(mem[top])
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
               pom1 = ((mem[top + 1] as MutableList<Syntax>).count()).toFloat()
            }else{
                pom1 = fromAnyToFloat(mem[top + 1])
            }
            if(!(pom2 is Number)){
                pom2 = ((mem[top] as MutableList<Syntax>).count()).toFloat()
            }else{
                pom2 = fromAnyToFloat(mem[top])
            }
            mem[top + 1] = (pom1 < pom2).toFloat()
            top = top + 1
        } else if (fromAnyToFloat(mem[pc]) == INSTRUCTION_GREATER.toFloat()){
            pc += 1
            mem[top + 1] = (fromAnyToFloat(mem[top + 1]) > fromAnyToFloat(mem[top])).toFloat()
            top = top + 1
        } else if (fromAnyToFloat(mem[pc]) == INSTRUCTION_LESSEQUAL.toFloat()){
            pc += 1
            mem[top + 1] = (fromAnyToFloat(mem[top + 1]) <= fromAnyToFloat(mem[top])).toFloat()
            top = top + 1
        } else if (fromAnyToFloat(mem[pc]) == INSTRUCTION_GREATEREQUAL.toFloat()){
            pc += 1
            mem[top + 1] = (fromAnyToFloat(mem[top + 1]) >= fromAnyToFloat(mem[top])).toFloat()
            top = top + 1
        } else if (fromAnyToFloat(mem[pc]) == INSTRUCTION_EQUAL.toFloat()){
            pc += 1
            mem[top + 1] = (mem[top + 1] == mem[top]).toFloat()
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
        else if (fromAnyToFloat(mem[pc]) == INSTRUCTION_SET_COLOR.toFloat()){
            pc += 1
            var turtlee = mem[top + 1] as Turtle
            turtlee.farba(fromAnyTo(mem[top]).toString())
            top += 2
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
            pc += 1
            var element = (mem[top+1] as MutableList<Syntax>)[fromAnyToFloat(mem[top]).toInt()]
            top += 2
            adr = pc
            (element as Syntax).generate()
        }
        else {
            terminated = true
        }
    }


    fun parse(): Block {
        var result = Block()

        while (kind == WORD && tabindex <= tabcount ){
            if(token == "ak" || token == "if"){
                var navysetab = tabcount
                scan()
                var test = Syntax()
                if(token == "("){
                    scan()
                    test = expr()
                    scan()
                }else{
                    test = expr()
                }
                var boltab = false
                if(token == ":"){
                    tabindex += 1 + navysetab
                    boltab = true
                }
                //check(SYMBOL, "{")
                scan()
                var ifelse = IfElse(test, parse(), null)
                //check(SYMBOL, "}")
                if(token == "}"){
                    scan()
                }

                if(token == "inak" || token == "else"){
                    if(boltab){
                        if(tabindex-1 != tabcount){
                            throw Exception()
                        }
                    }
                    scan()
                }
                if(token == "{" || token == ":"){
                    if(token == ":" && !boltab){
                        navysetab = tabcount
                        tabindex += 1 + navysetab
                        boltab = true
                    }
                    scan()
                    ifelse.bodyfalse = parse()
                    if(token == "}"){
                        scan()
                    }
                }
                if(boltab){
                    tabindex -= (1 + navysetab)
                }
                result.add(ifelse)
            }else if(token == "definuj" || token == "def" || token == "fun" || token == "metoda"|| token == "funkcia"){
                scan()
                check(WORD)
                var name = token
                if(name in globals){
                    throw IOException(token + "sa už používa")
                }
                scan()
                var pom = Subroutine(name, params(), null)
                globals[name] = pom
                //check(SYMBOL, "{")
                scan()
                locals = pom.vars
                localvardelta = -1
                pom.body = parse()
                //pom.vars = locals
                locals = mutableMapOf<String, Variable>()
                //check(SYMBOL, "}")
                scan()
                result.add(pom)
            }else if (token == "for") {
                scan()
                check(WORD)
                var name = token

                scan()
                scan()

                var pom :Assign? = null
                var rangeEnd = Syntax()
                var test = Syntax()
                var pom2: Assign? = null
                var pom3: Assign? = null
                var prve = expr()
                if(token == "."){
                    scan()
                    scan()
                    rangeEnd = expr()
                    var pomL = GlobalVariable(name, globalvaradr.toFloat())
                    pom = Assign(pomL, prve)
                    globals[name] = pomL
                    globalvaradr += 1
                    test = Less(pomL, rangeEnd)
                }else{
                    if(prve is MyList) {
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
                    }else {
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
                scan()
                var loopBody = parse()
                locals = mutableMapOf<String, Variable>()
                //check(SYMBOL, "]")
                scan()
                    result.add(ForLoop(pom!!, test, loopBody, pom2, pom3))
            }else if(token == "opakuj"){
                scan()
                var n = addsub()
                scan()
                result.add(Repeat(n, parse()))
                scan()
            }else if(token == "vrat"){
                scan()
                result.add(Return(expr()))
            }else if(token == "dopredu"){
                scan()
                //result.add(Fd(addsub()))
            }else if(token == "vlavo"){
                scan()
               // result.add(Lt(addsub()))
            }else if(token == "vpravo"){
                scan()
                //result.add(Rt(addsub()))
            }else if(token == "vypis"){
                scan()
                result.add(Print(addsub()))
            }else if(token == "kym"){
                scan()
                var test = expr()
                check(SYMBOL, "{")
                scan()
                result.add(While(test, parse()))
                check(SYMBOL, "}")
                scan()
            }else{
                var name = token
                scan()
                var pomIndex:Syntax? = null
                if(token == "["){
                    scan()
                    pomIndex = addsub()
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
                            if (token == "dopredu") {
                                scan()
                                if(token == "(") {
                                    scan()
                                    result.add(Fd(nieco as Variable, addsub()))
                                    scan()
                                }else{
                                    result.add(Fd(nieco as Variable, addsub()))
                                }
                            }
                            if (token == "vpravo") {
                                scan()
                                if(token == "(") {
                                    scan()
                                    result.add(Rt(nieco as Variable, addsub()))
                                    scan()
                                }else{
                                    result.add(Rt(nieco as Variable, addsub()))
                                }

                            }
                            if (token == "vlavo") {
                                scan()
                                if(token == "(") {
                                    scan()
                                    result.add(Lt(nieco as Variable, addsub()))
                                    scan()
                                }else{
                                    result.add(Lt(nieco as Variable, addsub()))
                                }
                            }
                            if (token == "farba") {
                                scan()
                                if(token == "(") {
                                    scan()
                                    result.add(Farba(nieco as Variable, addsub()))
                                    scan()
                                }else{
                                    result.add(Farba(nieco as Variable, addsub()))
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
                                check(SYMBOL, ")")
                                scan()
                            }
                            if (agrs.items.count() != subr.paramcount) {
                                throw Exception("Nesprávny počet parametrov")
                            }
                            result.add(Call(subr, agrs))
                        }
                    }else{
                        if (token == ".") {
                            var nieco = locals[name]
                            scan()
                            if (token == "dopredu") {
                                scan()
                                if(token == "(") {
                                    scan()
                                    result.add(Fd(nieco as Variable, addsub()))
                                    scan()
                                }else{
                                    result.add(Fd(nieco as Variable, addsub()))
                                }
                            }
                            if (token == "vpravo") {
                                scan()
                                if(token == "(") {
                                    scan()
                                    result.add(Rt(nieco as Variable, addsub()))
                                    scan()
                                }else{
                                    result.add(Rt(nieco as Variable, addsub()))
                                }

                            }
                            if (token == "vlavo") {
                                scan()
                                if(token == "(") {
                                    scan()
                                    result.add(Lt(nieco as Variable, addsub()))
                                    scan()
                                }else{
                                    result.add(Lt(nieco as Variable, addsub()))
                                }
                            }
                            if (token == "farba") {
                                scan()
                                if(token == "(") {
                                    scan()
                                    result.add(Farba(nieco as Variable, addsub()))
                                    scan()
                                }else{
                                    result.add(Farba(nieco as Variable, addsub()))
                                }
                            }
                        }
                    }
                }else{
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
                                throw Exception(name + "nie je premenna")
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
                    check(WORD)
                    if(token in result){
                        throw Exception("Duplicitný názov parametra")
                    }
                    result[token] = LocalVariable(token, (result.count()).toFloat())
                    scan()
                }
            }
            //check(SYMBOL, ")")
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
            check(SYMBOL, "]")
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
        check(SYMBOL, "]")
    }
    return MyList(agrs)
}

    fun operand():Syntax{
        var result = Syntax()
        if(kind == WORD){
            if(token == "false"){
                result = Const(0.toFloat())
            }else if (token == "true"){
                result = Const(1.toFloat())
            }else if (token == "turtle"){
                result = Trutl(Turtle(pg))
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
                            check(SYMBOL, ")")
                        }
                        if(agrs.items.count() != subr.paramcount){
                            throw Exception("Nesprávny počet parametrov")
                        }
                        result = Call(subr, agrs)
                    }else{
                        result = globals[token]!!
                    }
                }else{
                    throw Exception("„Neznáma premenna")
                }
            }
        }else if(kind == SYMBOL){
            //scan()
            result = elementsO() // AK je nove pole( napr. [1,4,6] vo for cykle
        }else{
            check(NUMBER)
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
        var result = expr()
        check(SYMBOL, ")")
        scan()
        return result
    }

    fun minus():Syntax{
        if(token != "-"){
            return braces()
        }
        scan()
        return Minus(braces())
    }


    fun multdiv():Syntax{
        var result = minus()
        while(true){
            if(token == "*"){
                scan()
                result = Mul(result, minus())
            }else if(token == "/"){
                scan()
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
                result = Add(result, multdiv())
            }else if(token == "-"){
                scan()
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
                result = Less(result, addsub())
            }else if(token == ">"){
                scan()
                result = Greater(result, addsub())
            }else if(token == "=="){
                scan()
                result = Equal(result, addsub())
            }else if(token == "<="){
                scan()
                result = LessEqual(result, addsub())
            }else if(token == ">="){
                scan()
                result = GreaterEqual(result, addsub())
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
        return Not(compare())
    }

    fun and():Syntax{
        var result = not()
        if(token !="and"){
            return result
        }
        scan()
        return And(result, not())
    }

    fun or():Syntax{
        var result = and()
        if(token !="or"){
            return result
        }
        scan()
        return Or(result, and())
    }

    fun expr():Syntax{
        var result = or()
        return result
    }

    fun check(expected_kind: Int, expected_token: String = ""){
        if(kind != expected_kind){
            throw IOException("zly kind ocakaval som: " + expected_kind + ", dostal som: " + kind + " " + token)
        }
        if(expected_token != "" && token != expected_token){
            throw IOException("zly token ocakaval som: " + expected_token + ", dostal som: " + token)
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
        while(look == ' ' || look == '\n' || look == '\t'){
            if(look == '\n'){
                tabcount= 0
            }
            next()
            while(look == ' ' || look == '\n'){
                if(look == '\n'){
                    tabcount= 0
                }
                next()
            }
            if(look == '\t'){
                tabcount += 1
            }
        }
        token = ""
        position = index - 1
        if(look.isDigit()){
            while(look.isDigit()){
                token += look
                next()
            }
            kind = NUMBER
        }else if(look.isLetter()){
            while(look.isLetter()){
                token += look
                next()
            }
            kind = WORD
        }else if(look == '\"'){
            token += look
            next()
            while(look != '\"'){
                token += look
                next()
            }
            token += look
            next()
            kind = WORD
        }else if(Array(2){"<";">"}.contains(look.toString())){
            token += look
            next()
            if(look == '='){
                token += look
                next()
            }
            kind = SYMBOL
        }else if(look != 0.toChar()){
            token += look
            next()
            kind = SYMBOL
        }else{
            kind = NOTHING
        }
    }
}