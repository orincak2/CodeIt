package Parser

var INSTRUCTION_FD = 1
var INSTRUCTION_LT = 2
var INSTRUCTION_RT = 3
var INSTRUCTION_SET = 4
var INSTRUCTION_LOOP = 5
var INSTRUCTION_PUSH = 6
var INSTRUCTION_MINUS = 7
var INSTRUCTION_ADD = 8
var INSTRUCTION_SUB = 9
var INSTRUCTION_MUL = 10
var INSTRUCTION_DIV = 11
var INSTRUCTION_GET = 12
var INSTRUCTION_PRINT = 13
var INSTRUCTION_JUMP = 14
var INSTRUCTION_GREATER = 15
var INSTRUCTION_EQUAL = 16
var INSTRUCTION_LESSEQUAL = 17
var INSTRUCTION_GREATEREQUAL = 18
var INSTRUCTION_AND = 19
var INSTRUCTION_OR = 20
var INSTRUCTION_SQUER = 21
var INSTRUCTION_LESS = 22
var INSTRUCTION_NOT = 23
var INSTRUCTION_JUMPIFFALSE = 24
var INSTRUCTION_CALL = 25
var INSTRUCTION_RETURN = 26
var INSTRUCTION_BOOL = 27
var INSTRUCTION_GETLOCAL = 28
var INSTRUCTION_SETLOCAL = 29
var INSTRUCTION_RETURN_VALUE = 30
var INSTRUCTION_GET_ELEMENT = 31
var INSTRUCTION_SET_ELEMENT = 32
var INSTRUCTION_SET_COLOR = 33
var INSTRUCTION_SET_POSITION = 34
var INSTRUCTION_CHOSE = 35
var INSTRUCTION_ADD_ELEMENT = 36
var INSTRUCTION_STVOREC = 37
var INSTRUCTION_TROJUHOLNIK = 38
var INSTRUCTION_KRUH = 39

var NOTHING = 0
var NUMBER = 1
var WORD = 2
var SYMBOL = 3

var frame = 0
var mem = mutableListOf<Any>(1000)
var pc = 0
var top = 0
var terminated = false
var counter_adr = 99
var adr = 0
/*var variables = mutableMapOf<String, Int>()
var subroutines = mutableMapOf<String, Subroutine>()*/
var globals = mutableMapOf<String, Identifier>()
var locals = mutableMapOf<String, Variable>()
var globalvaradr = 0
var localvardelta = 0

var input = "opakuj 10 [dopredu 70 vpravo 45]"
var index = 0
var look = '0'

var token = ""
var kind = 0
var position = 0
