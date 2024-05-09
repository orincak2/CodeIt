package Compiler.Parser


import Compiler.NUMBER
import Compiler.SYMBOL
import Compiler.Parser.Turtle.Farba
import Compiler.Parser.Turtle.Fd
import Compiler.Parser.Turtle.Kruh
import Compiler.Parser.Turtle.Lt
import Compiler.Parser.Turtle.Oval
import Compiler.Parser.Turtle.Position
import Compiler.Parser.Turtle.Rectangle
import Compiler.Parser.Turtle.Rt
import Compiler.Parser.Turtle.Stvorec
import Compiler.Parser.Turtle.Trojuholnik
import Drawing.Turtle
import Compiler.Scaner
import Compiler.WORD
import Compiler.bElif
import Compiler.bInput
import Compiler.bolEnter
import Compiler.frame
import Compiler.globals
import Compiler.globalvaradr
import Compiler.index
import Compiler.input
import Compiler.kind
import Compiler.locals
import Compiler.localvardelta
import Compiler.mem
import Compiler.pc
import Compiler.pg
import Compiler.pomindex
import Compiler.tabcount
import Compiler.tabindex
import Compiler.terminated
import Compiler.token
import Compiler.top
import Compiler.tttt


import java.io.IOException

fun Boolean.toFloat() = if (this) 1.toFloat() else 0.toFloat()
fun Float.toBoolean() = if (this == 1.toFloat()) true else false

class Parser():Syntax() {
    var myScanner = Scaner()
    fun run(txt:String):Syntax{
            input = txt.toString().trim()
            parserReset(myScanner)

            return parse(0)
    }

    fun parse(poradie: Int): Block {
        var result = Block()
        if(poradie== 0) {
            tttt = Turtle(pg!!, pg!!.pwidth.toFloat() * 0.5.toFloat(), pg!!.pheight.toFloat() * 0.5.toFloat(), "black")
            var basick = GlobalVariable("basicKoritnacka", globalvaradr.toFloat())
            result.add(Assign(basick, Trutl(tttt!!)))
            globals["basicKoritnacka"] = basick
            globalvaradr += 1
        }
        while (kind == WORD && (tabindex <= tabcount || bElif)){
            if(bElif){
                bElif = false
                //tabindex--
            }
            if(token == "ak" || token == "if" || token == "elif") {
                parseIf(result)
            }
            else if(token == "definuj" || token == "def" || token == "fun" || token == "metoda"|| token == "funkcia"){
                parseDef(result)
            }
            else if (token == "for" || token == "cyklus" || token == "forEach") {
                parseFor(result)
            }
            else if(token == "opakuj" || token == "repeat"){
                parseRepeat(result)
            }
            else if(token == "vrat" || token == "return"){
                parseReturn(result)
            }
            else if(token == "vypis" || token == "print"){
                parsePrint(result)
            }
            else if(token == "kym" || token == "while"){
                parseWhile(result)
            }
            else if (token == "dopredu" || token == "forward" || token == "vpred") {
                parseFd(result)
            }
            else if (token == "vpravo" || token == "right") {
                parseRt(result)
            }
            else if (token == "vlavo" || token == "left") {
                parseLt(result)
            }
            else if (token == "stvorec" || token == "square") {
                parseSquare(result)
            }
            else if (token == "rectangle" || token == "create_rectangle") {
                parseRectangle(result)
            }
            else if (token == "oval" || token == "create_oval") {
                parseOval(result)
            }
            else if (token == "kruh") {
                parseCircle(result)
            }
            else if (token == "trojuholnik") {
                parseTriangle(result)
            }
            else if (token == "farba" || token == "color" || token == "setColor" || token == "nastavFarbu") {
                parseColor(result)
            }
            else if (token == "position" || token == "pozicia" || token == "poloha" || token == "setPosition") {
                parsePosition(result)
            }
            else{
                parseOther(result)
            }
        }
        //tabindex = 0
        return result
    }


    fun parseIf(result: Block){
        var navysetab = tabcount - tabindex
        myScanner.scan()
        var test = Syntax()
        if (token == "(") {
            myScanner.scan()
            test = expr()
            myScanner.scan()
        } else {
            test = expr()
        }
        var boltab = false
        if (token == ":") {
            tabindex += 1 + navysetab
            boltab = true
        }
        check(arrayOf(SYMBOL), arrayOf("{", ":"))
        myScanner.scan()
        var ifelse = IfElse(test, parse(1), null)

        if (!boltab)
            check(arrayOf(SYMBOL), arrayOf("}"))

        if (token == "}") {
            myScanner.scan()
        }

        if (token == "inak" || token == "else" || token == "elif") {
            if(token != "elif") {
                if (boltab) {
                    if (tabindex - 1 != tabcount) {
                        throw Exception("Zle odsadenie" + "?" + index)
                    }
                }
                myScanner.scan()
                check(arrayOf(SYMBOL), arrayOf("{", ":"))


                if (token == "{" || token == ":") {
                    if (token == ":" && !boltab) {
                        navysetab = tabcount - tabindex
                        tabindex += 1 + navysetab
                        boltab = true
                    }
                    myScanner.scan()
                }
            }else{
                bElif = true
            }
            ifelse.bodyfalse = parse(1)

            if (!boltab)
                check(arrayOf(SYMBOL), arrayOf("}"))

            if (token == "}") {
                myScanner.scan()
            }

        }
        if(boltab){
            tabindex -= (1 + navysetab)
        }
        result.add(ifelse)
    }
    fun parseDef(result: Block){
        if(tabindex != 0 || tabcount !=0){
            throw Exception("Metodu nieje mozne definovat vo vnutri ineho tela." + "?" + index)
        }
        myScanner.scan()
        check(arrayOf(WORD), emptyArray())
        var name = token
        if(name in globals){
            throw IOException(token + " sa už používa." + "?" + index)
        }
        myScanner.scan()
        var pom = Subroutine(name, params(), null)
        globals[name] = pom
        check(arrayOf(SYMBOL), arrayOf("{", ":"))
        //check(SYMBOL, "{")
        var boltab = false
        if(token == ":"){
            tabindex += 1
            boltab = true
        }
        myScanner.scan()
        locals = pom.vars
        localvardelta = -1
        pom.body = parse(1)
        //pom.vars = locals
        locals = mutableMapOf<String, Variable>()
        //check(SYMBOL, "}")
        if(!boltab)
            check(arrayOf(SYMBOL), arrayOf("}"))

        if(token == "}"){
            myScanner.scan()
        }
        if(boltab){
            tabindex -= (1)
        }
        result.add(pom)
    }
    fun parseFor(result: Block){
        var navysetab = tabcount - tabindex
        myScanner.scan()
        if(token == "("){
            myScanner.scan()
        }
        var name = token
        myScanner.scan()
        if(token == "in" || token == "="){
            myScanner.scan()
        }


        var pom : Assign? = null
        var rangeEnd = Syntax()
        var test = Syntax()
        var pom2: Assign? = null
        var pom3: Assign? = null

        if(token == "range"){
            myScanner.scan()
            if(token == "("){
                myScanner.scan()
            }
            var prve = expr()
            if(token == ","){
                myScanner.scan()
                rangeEnd = expr()
                var pomL = GlobalVariable(name, globalvaradr.toFloat())
                pom = Assign(pomL, prve)
                globals[name] = pomL
                globalvaradr += 1
                test = Less(pomL, rangeEnd)
            }else {
                rangeEnd = prve
                var pomL = GlobalVariable(name, globalvaradr.toFloat())
                pom = Assign(pomL, Const((0).toFloat()))
                globals[name] = pomL
                globalvaradr += 1
                test = Less(pomL, rangeEnd)
            }
        }else {
            var prve = expr()
            if (token == "." || token == "," || token == ";" || token == name) {
                myScanner.scan()
                if (token == name) {
                    myScanner.scan()
                }
                if (token == "." || token == "<" || token == ">") {
                    myScanner.scan()
                }
                rangeEnd = expr()
                var pomL = GlobalVariable(name, globalvaradr.toFloat())
                pom = Assign(pomL, prve)
                globals[name] = pomL
                globalvaradr += 1
                test = Less(pomL, rangeEnd)
            } else {
                if (prve is MyList || prve is Strings) {
                    var pomL3 = GlobalVariable(name + "FL", globalvaradr.toFloat())
                    pom3 = Assign(pomL3, prve)
                    globals[name + "FL"] = pomL3
                    globalvaradr += 1

                    var pomL = GlobalVariable(name + "FI", globalvaradr.toFloat())
                    pom = Assign(pomL, Const((0).toFloat()))
                    globals[name + "FI"] = pomL
                    globalvaradr += 1

                    var pomL2 = GlobalVariable(name, globalvaradr.toFloat())
                    pom2 = Assign(pomL2, ListElement(pomL3, pomL))
                    globals[name] = pomL2
                    globalvaradr += 1
                    test = Less(pomL, pomL3)
                } else {
                    var pomL = GlobalVariable(name + "FL", globalvaradr.toFloat())
                    pom = Assign(pomL, Const((0).toFloat()))
                    globals[name + "FL"] = pomL
                    globalvaradr += 1

                    var pomL2 = GlobalVariable(name, globalvaradr.toFloat())
                    pom2 = Assign(pomL2, ListElement(prve as Variable, pomL))
                    globals[name] = pomL2
                    globalvaradr += 1
                    test = Less(pomL, prve)
                }

            }
        }
        /* myScanner.scan()
        // check(SYMBOL, "in")
         myScanner.scan()
         var rangeStart = expr()*/
        //check(SYMBOL, "..")
        //check(SYMBOL, "[")
        if(token == ")"){
            myScanner.scan()
        }
        var boltab = false
        if(token == ":"){
            tabindex += 1 + navysetab
            boltab = true
        }
        myScanner.scan()
        var loopBody = parse(1)
        locals = mutableMapOf<String, Variable>()
        //check(SYMBOL, "]")
        if(boltab){
            tabindex -= (1 + navysetab)
        }
        if(token == "}"){
            myScanner.scan()
        }
        result.add(ForLoop(pom!!, test, loopBody, pom2, pom3))
    }
    fun parseRepeat(result: Block){
        var navysetab = tabcount - tabindex
        myScanner.scan()
        var n = addsub()
        var boltab = false
        if(token == ":"){
            tabindex += 1 + navysetab
            boltab = true
        }
        myScanner.scan()
        result.add(Repeat(n, parse(1)))
        if(boltab){
            tabindex -= (1 + navysetab)
        }
        if(token == "}"){
            myScanner.scan()
        }
    }
    fun parseReturn(result: Block){
        myScanner.scan()
        if(token == "("){
            myScanner.scan()
        }
        result.add(Return(expr()))
        if(token == ")"){
            myScanner.scan()
        }
    }
    fun parsePrint(result: Block){
        myScanner.scan()
        if(token == "("){
            myScanner.scan()
        }
        var x = expr()
        var a = 0
        while (token == ","){
            a++
            if(a > 100000){
                throw Exception("Zacyklenie v programe?")
            }
            myScanner.scan()
            var pom = expr()
            x = Add(x, Strings(" "))
            x = Add(x, pom)
        }
        result.add(Print(x))
        if(token == ")"){
            myScanner.scan()
        }
    }
    fun parseWhile(result: Block){
        var navysetab = tabcount - tabindex
        myScanner.scan()
        var test = Syntax()
        if(token == "("){
            myScanner.scan()
            test = expr()
            myScanner.scan()
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
        myScanner.scan()
        result.add(While(test, parse(1)))
        if(boltab){
            tabindex -= (1 + navysetab)
        }
        //check(SYMBOL, "}")
        if(!boltab)
            check(arrayOf(SYMBOL), arrayOf("}"))

        if(token == "}"){
            myScanner.scan()
        }
    }
    fun parseFd(result: Block){
        myScanner.scan()
        if(token == "(" || token == "=") {
            myScanner.scan()
            result.add(Fd(globals["basicKoritnacka"] as Variable, addsub()))
            if(token == ")")
                myScanner.scan()
        }else{
            result.add(Fd(globals["basicKoritnacka"] as Variable, addsub()))
        }
    }
    fun parseRt(result: Block){
        myScanner.scan()
        if(token == "(") {
            myScanner.scan()
            result.add(Rt(globals["basicKoritnacka"] as Variable, addsub()))
            myScanner.scan()
        }else{
            result.add(Rt(globals["basicKoritnacka"] as Variable, addsub()))
        }
    }
    fun parseLt(result: Block){
        myScanner.scan()
        if(token == "(") {
            myScanner.scan()
            result.add(Lt(globals["basicKoritnacka"] as Variable, addsub()))
            myScanner.scan()
        }else{
            result.add(Lt(globals["basicKoritnacka"] as Variable, addsub()))
        }
    }
    fun parseSquare(result: Block){
        myScanner.scan()
        var Color= Syntax()
        Color = Strings("black")
        var X= Syntax()
        var Y= Syntax()
        var velk = Syntax()
        if(token == "(") {
            myScanner.scan()
        }
        check(arrayOf(NUMBER, WORD), emptyArray())
        X = addsub()
        if(token == ",") {
            myScanner.scan() //,
        }
        check(arrayOf(NUMBER, WORD), emptyArray())
        Y = addsub()
        if(token == ",") {
            myScanner.scan() //,
        }
        check(arrayOf(NUMBER, WORD), emptyArray())
        velk = addsub()
        if(token == ","){
            myScanner.scan()
            Color = operand()
            myScanner.scan()
        }
        if(token == ")") {
            myScanner.scan()
        }

        result.add(Stvorec(X,Y,velk,Color))
    }
    fun parseRectangle(result: Block){
        myScanner.scan()
        var Color= Syntax()
        Color = Strings("black")
        var X= Syntax()
        var Y= Syntax()
        var XX= Syntax()
        var YY= Syntax()
        var velk = Syntax()
        if(token == "(") {
            myScanner.scan()
        }
        check(arrayOf(NUMBER, WORD), emptyArray())
        X = addsub()
        if(token == ",") {
            myScanner.scan() //,
        }
        check(arrayOf(NUMBER, WORD), emptyArray())
        Y = addsub()
        if(token == ",") {
            myScanner.scan() //,
        }
        check(arrayOf(NUMBER, WORD), emptyArray())
        XX = addsub()
        if(token == ",") {
            myScanner.scan() //,
        }
        check(arrayOf(NUMBER, WORD), emptyArray())
        YY = addsub()
        var bfar = 0
        if(token == ","){
            bfar = 1
            myScanner.scan()
            if(token == "fill") {
                myScanner.scan()
            }
            if(token == "=") {
                myScanner.scan()
            }
            Color = operand()
            myScanner.scan()
        }
        if(token == ")") {
            myScanner.scan()
        }
        result.add(Rectangle(X,Y,XX,YY,Color, Const(bfar.toFloat())))
    }
    fun parseOval(result: Block){
        myScanner.scan()
        var Color= Syntax()
        Color = Strings("black")
        var X= Syntax()
        var Y= Syntax()
        var XX= Syntax()
        var YY= Syntax()
        var velk = Syntax()
        if(token == "(") {
            myScanner.scan()
        }
        check(arrayOf(NUMBER, WORD), emptyArray())
        X = addsub()
        if(token == ",") {
            myScanner.scan() //,
        }
        check(arrayOf(NUMBER, WORD), emptyArray())
        Y = addsub()
        if(token == ",") {
            myScanner.scan() //,
        }
        check(arrayOf(NUMBER, WORD), emptyArray())
        XX = addsub()
        if(token == ",") {
            myScanner.scan() //,
        }
        check(arrayOf(NUMBER, WORD), emptyArray())
        YY = addsub()
        var bfar = 0
        if(token == ","){
            bfar = 1
            myScanner.scan()
            if(token == "fill") {
                myScanner.scan()
            }
            if(token == "=") {
                myScanner.scan()
            }
            Color = operand()
            myScanner.scan()
        }
        if(token == ")") {
            myScanner.scan()
        }
        result.add(Oval(X,Y,XX,YY,Color, Const(bfar.toFloat())))
    }
    fun parseCircle(result: Block){
        myScanner.scan()
        var Color= Syntax()
        Color = Strings("black")
        var X= Syntax()
        var Y= Syntax()
        var velk = Syntax()
        if(token == "(") {
            myScanner.scan()
        }
        check(arrayOf(NUMBER, WORD), emptyArray())
        X = addsub()
        if(token == ",") {
            myScanner.scan() //,
        }
        check(arrayOf(NUMBER, WORD), emptyArray())
        Y = addsub()
        if(token == ",") {
            myScanner.scan() //,
        }
        check(arrayOf(NUMBER, WORD), emptyArray())
        velk = addsub()
        if(token == ","){
            myScanner.scan()
            Color = operand()
            myScanner.scan()
        }
        if(token == ")") {
            myScanner.scan()
        }

        result.add(Kruh(X,Y,velk,Color))
    }
    fun parseTriangle(result: Block){
        myScanner.scan()
        var Color= Syntax()
        Color = Strings("black")
        var X= Syntax()
        var Y= Syntax()
        var velk = Syntax()
        if(token == "(") {
            myScanner.scan()
        }
        check(arrayOf(NUMBER, WORD), emptyArray())
        X = addsub()
        if(token == ",") {
            myScanner.scan() //,
        }
        check(arrayOf(NUMBER, WORD), emptyArray())
        Y = addsub()
        if(token == ",") {
            myScanner.scan() //,
        }
        check(arrayOf(NUMBER, WORD), emptyArray())
        velk = addsub()
        if(token == ","){
            myScanner.scan()
            Color = operand()
            myScanner.scan()
        }
        if(token == ")") {
            myScanner.scan()
        }

        result.add(Trojuholnik(X,Y,velk,Color))
    }
    fun parseColor(result: Block){
        myScanner.scan()
        if(token == "(" || token == "=") {
            myScanner.scan()
            result.add(Farba(globals["basicKoritnacka"] as Variable, addsub()))
            if(token == ")"){
                myScanner.scan()
            }
        }else{
            result.add(Farba(globals["basicKoritnacka"] as Variable, addsub()))
        }
    }
    fun parsePosition(result: Block){
        myScanner.scan()
        if(token == "(" || token == "=") {
            myScanner.scan()
            var xx = addsub()
            myScanner.scan()
            var yy = addsub()
            result.add(Position(globals["basicKoritnacka"] as Variable, xx, yy))
            if(token == ")"){
                myScanner.scan()
            }
        }else{
            var xx = addsub()
            myScanner.scan()
            var yy = addsub()
            result.add(Position(globals["basicKoritnacka"] as Variable, xx, yy))
        }
    }
    fun parseOther(result: Block){
        var name = token
        myScanner.scan()
        var pomIndex:Syntax? = null
        if(token == "["){
            myScanner.scan()
            pomIndex = addsub()
            check(arrayOf(SYMBOL), arrayOf("]"))
            myScanner.scan()
        }
        if(token != "="){
            parseNoEqual(result,name)
        }
        else{
            parseEqual(result,name,pomIndex)
        }
    }
    fun parseNoEqual(result: Block, name:String){
        if((name in globals)) {
            //throw Exception("Neznámy príkaz" + name)

            /* if(!(globals[name] is Subroutine) && !(globals[name] is Trutl) ){
            throw Exception(name + " nie je podprogram")
        }*/
            if (token == ".") {
                var nieco = globals[name]
                myScanner.scan()
                if (token == "dopredu" || token == "forward" || token == "vpred") {
                    myScanner.scan()
                    if(token == "(" || token == "=") {
                        myScanner.scan()
                        result.add(Fd(nieco as Variable, addsub()))
                        if(token == ")")
                            myScanner.scan()
                    }else{
                        result.add(Fd(nieco as Variable, addsub()))
                    }
                }
                if (token == "vpravo" || token == "right") {
                    myScanner.scan()
                    if(token == "(") {
                        myScanner.scan()
                        result.add(Rt(nieco as Variable, addsub()))
                        myScanner.scan()
                    }else{
                        result.add(Rt(nieco as Variable, addsub()))
                    }

                }
                if (token == "vlavo" || token == "left") {
                    myScanner.scan()
                    if(token == "(") {
                        myScanner.scan()
                        result.add(Lt(nieco as Variable, addsub()))
                        myScanner.scan()
                    }else{
                        result.add(Lt(nieco as Variable, addsub()))
                    }
                }
                if (token == "farba" || token == "color" || token == "setColor" || token == "nastavFarbu") {
                    myScanner.scan()
                    if(token == "(" || token == "=") {
                        myScanner.scan()
                        result.add(Farba(nieco as Variable, addsub()))
                        if(token == ")"){
                            myScanner.scan()
                        }
                    }else{
                        result.add(Farba(nieco as Variable, addsub()))
                    }
                }
                if (token == "position" || token == "pozicia" || token == "poloha" || token == "setPosition") {
                    myScanner.scan()
                    if(token == "(" || token == "=") {
                        myScanner.scan()
                        var xx = addsub()
                        myScanner.scan()
                        var yy = addsub()
                        result.add(Position(nieco as Variable, xx, yy))
                        if(token == ")"){
                            myScanner.scan()
                        }
                    }else{
                        var xx = addsub()
                        myScanner.scan()
                        var yy = addsub()
                        result.add(Position(nieco as Variable, xx, yy))
                    }
                }
                if (token == "add" || token == "pridaj" || token == "vloz"){
                    myScanner.scan()
                    if(token == "(" || token == "=") {
                        myScanner.scan()
                        var xx = addsub()
                        result.add(AddElement(nieco as Variable, xx))
                        if(token == ")"){
                            myScanner.scan()
                        }
                    }
                }
            }else if (token == "+"){
                myScanner.scan()
                if(token == "="){
                    myScanner.scan()
                }
                var nieco = globals[name]
                val r = expr()
                result.add(Assign(nieco, Add(nieco as Variable, r)))

            }else if (token == "-"){
                myScanner.scan()
                if(token == "="){
                    myScanner.scan()
                }
                var nieco = globals[name]
                val r = expr()
                result.add(Assign(nieco, Sub(nieco as Variable, r)))

            } else {
                var subr = globals[name] as Subroutine
                var agrs = Block()
                if (token == "(") {
                    myScanner.scan()
                    if (token != ")") {
                        agrs.add(expr())
                        var a = 0
                        while (token == ",") {
                            a++
                            if(a > 100000){
                                throw Exception("Zacyklenie v programe?")
                            }
                            myScanner.scan()
                            agrs.add(expr())
                        }
                    }
                    check(arrayOf(SYMBOL), arrayOf(")"))
                    myScanner.scan()
                }
                if (agrs.items.count() != subr.paramcount) {
                    throw Exception("Nesprávny počet parametrov")
                }
                result.add(Call(subr, agrs))
            }
        }else if(name == "len" || name == "str" || name == "int"){
            if (token == "(") {
                myScanner.scan()
                var agrs = expr()
                check(arrayOf(SYMBOL), arrayOf(")"))
                myScanner.scan()
                result.add(Len(agrs))
            }else{
                result.add(Len(expr()))
            }

        }
        else if (name in locals){
            if (token == ".") {
                var nieco = locals[name]
                myScanner.scan()
                if (token == "dopredu" || token == "forward" || token == "vpred") {
                    myScanner.scan()
                    if(token == "(") {
                        myScanner.scan()
                        result.add(Fd(nieco as Variable, addsub()))
                        myScanner.scan()
                    }else{
                        result.add(Fd(nieco as Variable, addsub()))
                    }
                }
                if (token == "vpravo" || token == "right") {
                    myScanner.scan()
                    if(token == "(") {
                        myScanner.scan()
                        result.add(Rt(nieco as Variable, addsub()))
                        myScanner.scan()
                    }else{
                        result.add(Rt(nieco as Variable, addsub()))
                    }

                }
                if (token == "vlavo" || token == "left") {
                    myScanner.scan()
                    if(token == "(") {
                        myScanner.scan()
                        result.add(Lt(nieco as Variable, addsub()))
                        myScanner.scan()
                    }else{
                        result.add(Lt(nieco as Variable, addsub()))
                    }
                }
                if (token == "farba" || token == "color" || token == "setColor" || token == "nastavFarbu") {
                    myScanner.scan()
                    if(token == "(" || token == "=") {
                        myScanner.scan()
                        result.add(Farba(nieco as Variable, addsub()))
                        if(token == ")"){
                            myScanner.scan()
                        }
                    }else{
                        result.add(Farba(nieco as Variable, addsub()))
                    }
                }
                if (token == "position" || token == "pozicia" || token == "poloha" || token == "setPosition") {
                    myScanner.scan()
                    if(token == "(" || token == "=") {
                        myScanner.scan()
                        var xx = addsub()
                        myScanner.scan()
                        var yy = addsub()
                        result.add(Position(nieco as Variable, xx, yy))
                        if(token == ")"){
                            myScanner.scan()
                        }
                    }else{
                        var xx = addsub()
                        myScanner.scan()
                        var yy = addsub()
                        result.add(Position(nieco as Variable, xx, yy))
                    }
                }
                if (token == "add" || token == "pridaj" || token == "vloz"){
                    myScanner.scan()
                    if(token == "(" || token == "=") {
                        myScanner.scan()
                        var xx = addsub()
                        result.add(AddElement(nieco as Variable, xx))
                        if(token == ")"){
                            myScanner.scan()
                        }
                    }
                }
            }
        }else{
            throw Exception("Neznama premenna?" + pomindex)
        }
    }
    fun parseEqual(result: Block, name:String, pomIndex:Syntax?){
        myScanner.scan()
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



    fun params(): MutableMap<String, Variable>{
        var result = mutableMapOf<String, Variable>()
        if(token == "(" ){
            myScanner.scan()
            if(kind == WORD){
                result[token] = LocalVariable(token,(0).toFloat())
                myScanner.scan()
                var a = 0
                while(token == ","){
                    a++
                    if(a > 100000){
                        throw Exception("Zacyklenie v programe?")
                    }
                    myScanner.scan()
                    check(arrayOf(WORD), emptyArray())
                    if(token in result){
                        throw Exception("Duplicitný názov parametra" + "?" + index)
                    }
                    result[token] = LocalVariable(token, (result.count()).toFloat())
                    myScanner.scan()
                }
            }
            check(arrayOf(SYMBOL), arrayOf(")"))
            myScanner.scan()
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
            myScanner.scan()
            if(token != "]"){
                agrs.add(expr())
                var a = 0
                while (token == ","){
                    a++
                    if(a > 100000){
                        throw Exception("Zacyklenie v programe?")
                    }
                    myScanner.scan()
                    agrs.add(expr())
                }
            }
            check(arrayOf(SYMBOL), arrayOf("]"))
            myScanner.scan()
        }
        return MyList(agrs)
    }
    fun elementsO():MyList{
        var agrs = mutableListOf<Syntax>()
        if(token == "["){
            myScanner.scan()
            if(token != "]"){
                agrs.add(expr())
                var a = 0
                while (token == ","){
                    a++
                    if(a > 100000){
                        throw Exception("Zacyklenie v programe?")
                    }
                    myScanner.scan()
                    agrs.add(expr())
                }
            }
            check(arrayOf(SYMBOL), arrayOf("]"))
        }
        return MyList(agrs)
    }
    fun operand():Syntax{
        var result = Syntax()
        if(token == "str" || token == "int") {
            myScanner.scan()
            if (token == "(") {
                myScanner.scan()
            }
            result = expr()
            if (token == ")") {
                myScanner.scan()
            }
            return result
        }

        if(kind == WORD){
            if(token == "false" || token == "False"){
                result = Const(0.toFloat())
            }else if (token == "cervena" || token == "modra" || token == "red" || token == "blue"){
                result = Strings(token.replace("\"",""))
            }else if (token == "true" || token == "True"){
                result = Const(1.toFloat())
            }else if (token == "turtle" || token == "korytnacka" || token == "pero" || token == "pen"){
                myScanner.scan()
                var turtleX = 0F
                var turtleY = 0F
                var turtleColor = "red"
                if(token == "("){
                    myScanner.scan()
                    turtleX = token.toFloat()
                    myScanner.scan() //,
                    myScanner.scan()
                    turtleY = token.toFloat()
                    myScanner.scan()
                    if(token == ","){
                        myScanner.scan()
                        turtleColor = token.replace("\"","")
                        myScanner.scan()
                    }
                    myScanner.scan()
                }
                return Trutl(Turtle(pg!!,turtleX, turtleY,turtleColor))
            }else if (token.count() > 0 && token[0] == '\"'){
                result = Strings(token.replace("\"",""))
            }else {
                if ((token in locals)) {
                    result = locals[token]!!

                }else if (token in globals){
                    if (!(globals[token] is Variable)){
                        var subr = globals[token] as Subroutine
                        var agrs = Block()
                        myScanner.scan()
                        if(token == "("){
                            myScanner.scan()
                            if(token != ")"){
                                agrs.add(expr())
                                var a = 0
                                while (token == ","){
                                    a++
                                    if(a > 100000){
                                        throw Exception("Zacyklenie v programe?")
                                    }
                                    myScanner.scan()
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
                    myScanner.scan()
                    if (token == "(") {
                        myScanner.scan()
                        var agrs = expr()
                        check(arrayOf(SYMBOL), arrayOf(")"))
                        result = Len(agrs)
                    }else{
                        result = Len(expr())
                    }

                }else if(token == "random" || token == "randint"){
                    myScanner.scan()
                    if (token == ".") {
                        myScanner.scan()
                    }
                    if (token == "randint") {
                        myScanner.scan()
                    }
                    if (token == "(") {
                        myScanner.scan()
                        var a = expr()
                        check(arrayOf(SYMBOL), arrayOf(","))
                        myScanner.scan()
                        var b = expr()
                        result = Randit(a, b)
                    }else{
                        var a = expr()
                        myScanner.scan()
                        var b = expr()
                        result = Randit(a, b)
                    }

                }else if(token == "input"){
                    myScanner.scan()
                    check(arrayOf(SYMBOL), arrayOf("("))
                    if (token == "(") {
                        myScanner.scan()
                        if(token != ")") {
                            var agrs = expr()
                            check(arrayOf(SYMBOL), arrayOf(")"))
                            result = Input(agrs)
                        }else{
                            result = Input(Strings(""))
                        }
                    }else{
                        result = Input(Strings(""))
                    }

                }else{
                    throw Exception("Neznáma premenna" + "?" + index)
                }
            }
        }else if(kind == SYMBOL){
            //myScanner.scan()
            result = elementsO() // AK je nove pole( napr. [1,4,6] vo for cykle
        }else{
            //check(NUMBER)
            result = Const(token.toFloat())
        }
        myScanner.scan()
        if(token == "[" && result is Variable){
            myScanner.scan()
            var indexx = addsub()
            var variab = result
            result = ListElement(variab,indexx)
            myScanner.scan()
        }
       // myScanner.scan()
        return result
    }
    fun braces():Syntax{
        if(token !="("){
            return operand()
        }
        myScanner.scan()
        //check(arrayOf(WORD, SYMBOL), emptyArray(), pomindex)
        var result = expr()
       // check(SYMBOL, ")")
        myScanner.scan()
        return result
    }
    fun minus():Syntax{
        if(token != "-"){
            return braces()
        }
        myScanner.scan()
        //check(arrayOf(WORD, NUMBER), emptyArray(), pomindex)
        return Minus(braces())
    }
    fun multdiv():Syntax{
        var result = minus()
        var a = 0
        while(true){
            a++
            if(a > 100000){
                throw Exception("Zacyklenie v programe?")
            }
            if(token == "*"){
                myScanner.scan()
               // check(arrayOf(WORD, NUMBER, SYMBOL), emptyArray(), pomindex)
                result = Mul(result, minus())
            }else if(token == "/"){
                myScanner.scan()
                //check(arrayOf(WORD, NUMBER, SYMBOL), emptyArray(), pomindex)
                result = Div(result, minus())
            }else{
                return result
            }
        }
    }
    fun addsub():Syntax{
        var result = multdiv()
        var a = 0
        while(true){
            a++
            if(a > 100000){
                throw Exception("Zacyklenie v programe?")
            }
            if(token == "+"){
                myScanner.scan()
               // check(arrayOf(WORD, NUMBER, SYMBOL), emptyArray(), pomindex)
                result = Add(result, multdiv())
            }else if(token == "-"){
                myScanner.scan()
               // check(arrayOf(WORD, NUMBER, SYMBOL), emptyArray(), pomindex)
                result = Sub(result, multdiv())
            }else{
                return result
            }
        }
    }
    fun compare():Syntax{
        var result = addsub()
        var a = 0
        while(true){
            a++
            if(a > 100000){
                throw Exception("Zacyklenie v programe?")
            }
            if(token == "<"){
                myScanner.scan()
                //check(arrayOf(WORD, NUMBER), emptyArray(), pomindex)
                result = Less(result, addsub())
            }else if(token == ">"){
                myScanner.scan()
               // check(arrayOf(WORD, NUMBER), emptyArray(), pomindex)
                result = Greater(result, addsub())
            }else if(token == "==" || token == "="){
                myScanner.scan()
               // check(arrayOf(WORD, NUMBER), emptyArray(), pomindex)
                result = Equal(result, addsub())
            }else if(token == "<="){
                myScanner.scan()
                //check(arrayOf(WORD, NUMBER), emptyArray(), pomindex)
                result = LessEqual(result, addsub())
            }else if(token == "!="){
                myScanner.scan()
                result = NotEqual(result, addsub())
            }else if(token == ">="){
                myScanner.scan()
               // check(arrayOf(WORD, NUMBER), emptyArray(), pomindex)
                result = GreaterEqual(result, addsub())
            }else if(token == "in"){
                myScanner.scan()
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
        myScanner.scan()
        //check(arrayOf(WORD, NUMBER), emptyArray(), pomindex)
        return Not(compare())
    }
    fun and():Syntax{
        var result = not()
        if(token !="and"){
            return result
        }
        myScanner.scan()
        //check(arrayOf(WORD, NUMBER), emptyArray(), pomindex)
        return And(result, not())
    }
    fun or():Syntax{
        var result = and()
        if(token !="or"){
            return result
        }
        myScanner.scan()
        //check(arrayOf(WORD, NUMBER), emptyArray(), pomindex)
        return Or(result, and())
    }
    fun expr():Syntax{
        var result = or()
        return result
    }


    
    fun  check(expected_kind: Array<Int>, expected_token: Array<String>, ind: Int = index){
        if(expected_kind.size > 0 && !expected_kind.contains(kind)){
            throw IOException("zly typ ?" + ind)
        }
        if(expected_token.size > 0 && !expected_token.contains(token)){
            throw IOException("nesprávna syntax, očakával som: " + expected_token.joinToString(" alebo ") + ", dostal som: " + token + "?" + ind)
        }
    }
    fun parserReset(myScanner: Scaner){
        pg!!.reset()
        pc = 0
        top = mem.size-1
        frame = top
        terminated = false

        tabindex = 0
        tabcount = 0
        index = 0
        pomindex = 0
        bInput = false
        bolEnter = false
        myScanner.next()
        myScanner.scan()
        globals = mutableMapOf<String, Identifier>()
        locals = mutableMapOf<String, Variable>()
        globalvaradr = 2
        localvardelta = 0
        //variables = mutableMapOf<String, Int>()
        //subroutines = mutableMapOf<String, Subroutine>()
        tttt = Turtle(
            pg!!,
            pg!!.pwidth.toFloat() * 0.5.toFloat(),
            pg!!.pheight.toFloat() * 0.5.toFloat(),
            "black"
        )
    }
}