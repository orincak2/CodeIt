package Compiler

class Scaner {

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
        var a = 0
        while(look == ' ' || look == '\n' || look == '\t' || look == ';' || look == 160.toChar()){
            a++
            if(a > 100000){
                throw Exception("Zacyklenie v programe?")
            }

            if(look == '\n'){
                tabcount= 0
                bolEnter = true
            }
            next()
            var b = 0
            while(look == ' ' || look == '\n' || look == ';'){
                b++
                if(b > 100000){
                    throw Exception("Zacyklenie v programe?")
                }
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
            var a = 0
            while(look.isDigit()){
                a++
                if(a > 100000){
                    throw Exception("Zacyklenie v programe?")
                }
                token += look
                next()
            }
            if(look == '.'){
                token += look
                next()
                var a = 0
                while(look.isDigit()){
                    a++
                    if(a > 100000){
                        throw Exception("Zacyklenie v programe?")
                    }
                    token += look
                    next()
                }
            }
            kind = NUMBER
        }else if(look.isLetter()){
            bolEnter = false
            var a = 0
            while(look.isLetter() || look.isDigit()){
                a++
                if(a > 100000){
                    throw Exception("Zacyklenie v programe?")
                }
                token += look
                next()
            }
            kind = WORD
        }else if(look == '\"'){
            bolEnter = false

            token += look
            next()
            var a = 0
            while(look != '\"' && look != 0.toChar()){
                a++
                if(a > 100000){
                    throw Exception("Zacyklenie v programe?")
                }
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