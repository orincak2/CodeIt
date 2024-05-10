package Compiler

import Compiler.Parser.Syntax
import Compiler.Parser.toBoolean
import Compiler.Parser.toFloat
import android.widget.EditText
import Drawing.Turtle

class VirtualMachine (nprint: EditText){
    var tttt = Turtle(
        pg!!,
        pg!!.pwidth.toFloat() * 0.5.toFloat(),
        pg!!.pheight.toFloat() * 0.5.toFloat(),
        "black"
    )
    var print = nprint

    fun run():Boolean{
        var controll = 0
        while(terminated != true){
            controll++
            if(controll > 1000000){
                throw Exception("Zacyklenie v programe?")
            }
            executeP()
        }
        if(bInput)
            return true
        return false
    }

    fun executeP(){
        if(fromAnyToFloat(mem[pc]) == INSTRUCTION_FD.toFloat()){
            pc += 1
            var turtlee = mem[top + 1] as Turtle
            turtlee.dopredu(pg!!.pwidth.toFloat() * fromAnyToFloat(mem[top])/1000)
            top += 2
        }else if (fromAnyToFloat(mem[pc]) == INSTRUCTION_MINUS.toFloat()){
            pc += 1
            mem[top] = -fromAnyToFloat(mem[top])
        }else if (fromAnyToFloat(mem[pc]) == INSTRUCTION_STVOREC.toFloat()){
            pc += 1
            var x = pg!!.pwidth.toFloat() * fromAnyToFloat(mem[top+3])/1000
            var y = pg!!.pwidth.toFloat() * fromAnyToFloat(mem[top+2])/1000
            var v = pg!!.pwidth.toFloat() * fromAnyToFloat(mem[top+1])/1000
            tttt!!.stvorec(x,y,v,fromAnyTo(mem[top]).toString())
            top += 4
        }else if (fromAnyToFloat(mem[pc]) == INSTRUCTION_RECTANGLE.toFloat()){
            pc += 1
            var x = pg!!.pwidth.toFloat() * fromAnyToFloat(mem[top+5])/1000
            var y = pg!!.pwidth.toFloat() * fromAnyToFloat(mem[top+4])/1000
            var xx = pg!!.pwidth.toFloat() * fromAnyToFloat(mem[top+3])/1000
            var yy = pg!!.pwidth.toFloat() * fromAnyToFloat(mem[top+2])/1000
            tttt!!.obdlznik(x,y,xx,yy,fromAnyTo(mem[top+1]).toString(),fromAnyToFloat(mem[top]))
            top += 6
        }
        else if (fromAnyToFloat(mem[pc]) == INSTRUCTION_OVAL.toFloat()){
            pc += 1
            var x = pg!!.pwidth.toFloat() * fromAnyToFloat(mem[top+5])/1000
            var y = pg!!.pwidth.toFloat() * fromAnyToFloat(mem[top+4])/1000
            var xx = pg!!.pwidth.toFloat() * fromAnyToFloat(mem[top+3])/1000
            var yy = pg!!.pwidth.toFloat() * fromAnyToFloat(mem[top+2])/1000
            tttt!!.oval(x,y,xx,yy,fromAnyTo(mem[top+1]).toString(),fromAnyToFloat(mem[top]))
            top += 6
        }else if (fromAnyToFloat(mem[pc]) == INSTRUCTION_KRUH.toFloat()){
            pc += 1
            var x = pg!!.pwidth.toFloat() * fromAnyToFloat(mem[top+3])/1000
            var y = pg!!.pwidth.toFloat() * fromAnyToFloat(mem[top+2])/1000
            var v = pg!!.pwidth.toFloat() * fromAnyToFloat(mem[top+1])/1000
            tttt!!.kruh(x,y,v,fromAnyTo(mem[top]).toString())
            top += 4
        }else if (fromAnyToFloat(mem[pc]) == INSTRUCTION_TROJUHOLNIK.toFloat()){
            pc += 1
            var x = pg!!.pwidth.toFloat() * fromAnyToFloat(mem[top+3])/1000
            var y = pg!!.pwidth.toFloat() * fromAnyToFloat(mem[top+2])/1000
            var v = pg!!.pwidth.toFloat() * fromAnyToFloat(mem[top+1])/1000
            tttt!!.trojuholnik(x,y,v,fromAnyTo(mem[top]).toString())
            top += 4
        }else if (fromAnyToFloat(mem[pc]) == INSTRUCTION_ADD.toFloat()){
            pc += 1
            mem[top + 1] = MojaTrieda(fromAnyTo(mem[top + 1])) + MojaTrieda(
                fromAnyTo(
                    mem[top]
                )
            )
            top = top + 1
        }else if (fromAnyToFloat(mem[pc]) == INSTRUCTION_SUB.toFloat()){
            pc += 1
            mem[top + 1] = fromAnyToFloat(mem[top + 1]) - fromAnyToFloat(mem[top])
            top = top + 1
        }else if (fromAnyToFloat(mem[pc]) == INSTRUCTION_MUL.toFloat()){
            pc += 1
            mem[top + 1] = MojaTrieda(fromAnyTo(mem[top + 1])) * MojaTrieda(
                fromAnyTo(
                    mem[top]
                )
            )
            top = top + 1
        }else if (fromAnyToFloat(mem[pc]) == INSTRUCTION_NOT.toFloat()){
            pc += 1
            if(fromAnyToFloat(mem[top]) == 1.toFloat()){
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
            mem[top + 1] = (fromAnyToFloat(mem[top + 1]).toBoolean() && fromAnyToFloat(mem[top]).toBoolean())
            top = top + 1
        } else if (fromAnyToFloat(mem[pc]) == INSTRUCTION_OR.toFloat()){
            pc += 1
            mem[top + 1] = (fromAnyToFloat(mem[top + 1]).toBoolean() || fromAnyToFloat(mem[top]).toBoolean())
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
            mem[top + 1] = (pom1 < pom2)
            top = top + 1
        } else if (fromAnyToFloat(mem[pc]) == INSTRUCTION_GREATER.toFloat()){
            pc += 1
            mem[top + 1] = (fromAnyToFloat(mem[top + 1]) > fromAnyToFloat(mem[top]))
            top = top + 1
        }else if (fromAnyToFloat(mem[pc]) == INSTRUCTION_NOTEQUAL.toFloat()){
            pc += 1
            mem[top + 1] = (MojaTrieda(mem[top + 1]).equals(MojaTrieda(mem[top])))
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
            mem[top + 1] = (fromAnyToFloat(mem[top + 1]) <= fromAnyToFloat(mem[top]))
            top = top + 1
        } else if (fromAnyToFloat(mem[pc]) == INSTRUCTION_GREATEREQUAL.toFloat()){
            pc += 1
            mem[top + 1] = (fromAnyToFloat(mem[top + 1]) >= fromAnyToFloat(mem[top]))
            top = top + 1
        } else if (fromAnyToFloat(mem[pc]) == INSTRUCTION_IN.toFloat()){
            pc += 1
            mem[top + 1] = (mem[top + 1].toString() in mem[top].toString())
            top = top + 1
        } else if (fromAnyToFloat(mem[pc]) == INSTRUCTION_EQUAL.toFloat()){
            pc += 1
            mem[top + 1] = (mem[top + 1].toString() == mem[top].toString())
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
        }else if (fromAnyToFloat(mem[pc]) == INSTRUCTION_RANDIT.toFloat()){
            pc += 1
            mem[top+1] = (fromAnyToFloat(mem[top+1]).toInt()..fromAnyToFloat(mem[top]).toInt()).random().toFloat()
            top += 1
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
            turtlee.x =pg!!.pwidth.toFloat() * fromAnyToFloat(mem[top+1])/100
            turtlee.y =pg!!.pwidth.toFloat() * fromAnyToFloat(mem[top])/100
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
        }else if (fromAnyToFloat(mem[pc]) == INSTRUCTION_INPUT.toFloat()){
            if(bInput) {
                top = top - 1
                val number: Float? = inputHodnota.toString().toFloatOrNull()
                if(number != null) {
                    mem[top] = number
                }else{
                    mem[top] = inputHodnota
                }
                pc += 1
                bInput = false
            }else{
                bInput = true
                terminated = true
            }
        }else if (fromAnyToFloat(mem[pc]) == INSTRUCTION_PRINT.toFloat()) {
            pc += 1
            var tt: String = ""
            if (mem[top] is Boolean) {
                if (mem[top] as Boolean)
                    tt = "True"
                else
                    tt = "False"
            } else{
                tt = vypisFloaty(fromAnyTo(mem[top])).toString()
            }
            var novyriadok =""
            if(tt.length>0){
                novyriadok = "\n"
            }
            print!!.setText(print.text.toString()+ ">" + tt + "\n")
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
            if(fromAnyToFloat(mem[top]) == 0.toFloat()){
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

    fun fromAnyToFloat(anyFloatValue: Any):Float{
        if(anyFloatValue is Float){
            return anyFloatValue.toFloat()
        }
        if(anyFloatValue is Boolean){
            return anyFloatValue.toFloat()
        }


        return 99999999.toFloat()
    }

    fun vypisFloaty(cisloo: Any):Any {
        var cislo = cisloo
        if(cislo is String) {
            if (cislo.contains(".")){
                val floatNumber: Float? = cislo.toFloatOrNull()
                if (floatNumber != null) {
                    cislo = floatNumber
                }
            }else{
                val floatNumber: Long? = cislo.toLongOrNull()
                if (floatNumber != null) {
                    cislo = floatNumber
                }
            }
        }
        if(cislo is String){
            var slova = cislo.split(' ')
            var strr = ""
            if(slova.count() > 1) {
                for (sl in slova) {
                    var pom = vypisFloaty(sl)
                    strr += pom.toString() + " "
                }
            }else{
                if(slova.count() > 0)
                    strr += slova[0]
            }
            return  strr
        }
        if(cislo is Float){
            if (cislo == (cislo as Float).toInt().toFloat()) {
                return (cislo as Float).toLong()
            } else {
                return cislo
            }}
        return cislo
    }

    fun len(obj: Any): Float {
        return when (obj) {
            is String -> obj.length.toFloat()
            is Array<*> -> obj.size.toFloat()
            is Collection<*> -> obj.size.toFloat()
            else -> throw IllegalArgumentException("Unsupported type")
        }
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
}