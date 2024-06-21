// Ejemplo
/*
// Esto es un comentario de una sola línea
println("Aqui empieza todo");
var a:int = 2+2*4;
var b:double = 10/5;
println("La suma de: " + a + " + "+ b);
println("es: ");
println((int)a + b);
*/

// ------------  Paquete e importaciones ------------
package analisis;

//importaciones
import java_cup.runtime.Symbol;
import java.util.LinkedList;
import excepciones.Errores;

%%

//definicion de variables
%{
    String cadena = "";
    public LinkedList<Errores> listaErrores = new LinkedList<>();
%}

// Definiciones iniciales
%init{
    yyline = 1;
    yycolumn = 1;
%init}

//declaraciones de caracteristicas de jflex
%cup
%class scanner //nombre de la clase
%public //acceso de la clase
%line //conteo de lineas
%column //conteo de columnas
%char //conteo de caracteres
%full //reconocimiento de caracteres
//%debug //depuracion
%ignorecase //quitar la distincion entre mayusculas y minusculas (case insensitive)

//estados
%state CADENA

// Definir los símbolos del sistema
//ESTRUCTURALES
PAR1 = "("
PAR2 = ")"
LLAV1 = "{"
LLAV2 = "}"

FINCADENA = ";"
DOSPUNTOS = ":"


//OPERADOS ARITMETICOS
MAS = "+"
MENOS = "-"
MULT = "*"
POTENCIA = "**"
MODULO = "%"
DIV = "/"
INCREMENTO = "++"
DECREMENTO = "--"

//OPERADORES RELACIONALES
IGUAL = "="
IGUAL_IGUAL = "=="
DIFERENTE_DE = "!="
MENOR_QUE = "<"
MENOR_IGUAL = "<="
MAYOR_QUE = ">"
MAYOR_IGUAL = ">="

//OPERACIONES LOGICOS
OR = "||"
AND = "&&"
XOR = "^"
NOT = "!"
FLECHA = "=>"
COMA = ","

// Reglas con regex
BLANCOS = [\ \r\t\n\f]+
ENTERO = [0-9]+
DECIMAL = [0-9]+"."[0-9]+
COMENTARIOS = (\/\/[^\n]*|\/\*[^*]*\*\/)
CHAR = \'[^\']\'
ID = [a-zA-Z][a-zA-Z0-9_]*
DEFAULT_MATCH = "_"

// Palabras reservadas
IMPRIMIR = "println"
TRUE = "true"
FALSE = "false"
INT = "int"
DOUBLE = "double"
TYPE_CARACTER = "char"
STRING = "string"
BOOLEAN = "bool"
VAR = "var"
CONST = "const"
IF = "if"
ELSE = "else"
MATCH = "match"
FOR = "for"
BREAK = "break"
WHILE = "while"
DO = "do"
CONTINUE = "continue"
VOID = "void"
START_WITH = "start_with"

%%
// Simbolos estructurales
<YYINITIAL> {PAR1}      {return new Symbol(sym.PAR1, yyline, yycolumn,yytext());}
<YYINITIAL> {PAR2}      {return new Symbol(sym.PAR2, yyline, yycolumn,yytext());}
<YYINITIAL> {LLAV1}      {return new Symbol(sym.LLAV1, yyline, yycolumn,yytext());}
<YYINITIAL> {LLAV2}      {return new Symbol(sym.LLAV2, yyline, yycolumn,yytext());}
<YYINITIAL> {FLECHA}    {return new Symbol(sym.FLECHA, yyline, yycolumn, yytext());}

// Palabras reservadas
<YYINITIAL> {IMPRIMIR}              {return new Symbol(sym.IMPRIMIR, yyline, yycolumn,yytext());}
<YYINITIAL> {TRUE}                  {return new Symbol(sym.TRUE, yyline, yycolumn,yytext());}
<YYINITIAL> {FALSE}                 {return new Symbol(sym.FALSE, yyline, yycolumn,yytext());}
<YYINITIAL> {INT}                   {return new Symbol(sym.TYPE_INT, yyline, yycolumn, yytext());}
<YYINITIAL> {DOUBLE}                {return new Symbol(sym.TYPE_DOUBLE, yyline, yycolumn, yytext());}
<YYINITIAL> {TYPE_CARACTER}         {return new Symbol(sym.TYPE_CHAR, yyline, yycolumn, yytext());}
<YYINITIAL> {STRING}                {return new Symbol(sym.TYPE_STRING, yyline, yycolumn, yytext());}
<YYINITIAL> {BOOLEAN}               {return new Symbol(sym.TYPE_BOOLEAN, yyline, yycolumn, yytext());}
<YYINITIAL> {VAR}                   {return new Symbol(sym.VAR, yyline, yycolumn, yytext());}
<YYINITIAL> {CONST}                 {return new Symbol(sym.CONST, yyline, yycolumn, yytext());}
<YYINITIAL> {IF}                    {return new Symbol(sym.IF, yyline, yycolumn, yytext());}
<YYINITIAL> {ELSE}                  {return new Symbol(sym.ELSE, yyline, yycolumn, yytext());}
<YYINITIAL> {MATCH}                 {return new Symbol(sym.MATCH, yyline, yycolumn, yytext());}
<YYINITIAL> {DEFAULT_MATCH}         {return new Symbol(sym.DEFAULT_MATCH, yyline, yycolumn, yytext());}
<YYINITIAL> {FOR}                   {return new Symbol(sym.FOR, yyline, yycolumn, yytext());}
<YYINITIAL> {BREAK}                 {return new Symbol(sym.BREAK, yyline, yycolumn, yytext());}
<YYINITIAL> {DO}                    {return new Symbol(sym.DO, yyline, yycolumn, yytext());}
<YYINITIAL> {WHILE}                 {return new Symbol(sym.WHILE, yyline, yycolumn, yytext());}
<YYINITIAL> {CONTINUE}              {return new Symbol(sym.CONTINUE, yyline, yycolumn, yytext());}
<YYINITIAL> {VOID}                  {return new Symbol(sym.VOID, yyline, yycolumn, yytext());}
<YYINITIAL> {START_WITH}            {return new Symbol(sym.START_WITH, yyline, yycolumn, yytext());}

// Expresiones regulares
<YYINITIAL> {DECIMAL}   {return new Symbol(sym.DECIMAL, yyline, yycolumn,yytext());}
<YYINITIAL> {ENTERO}    {return new Symbol(sym.ENTERO, yyline, yycolumn,yytext());}

// Símbolos básicos
<YYINITIAL> {FINCADENA} {return new Symbol(sym.FINCADENA, yyline, yycolumn,yytext());}
<YYINITIAL> {DOSPUNTOS} {return new Symbol(sym.DOSPUNTOS, yyline, yycolumn,yytext());}
<YYINITIAL> {COMA}      {return new Symbol(sym.COMA, yyline, yycolumn,yytext());}

// Símbolos Aritméticos
<YYINITIAL> {MAS}       {return new Symbol(sym.MAS, yyline, yycolumn,yytext());}
<YYINITIAL> {MENOS}     {return new Symbol(sym.MENOS, yyline, yycolumn,yytext());}
<YYINITIAL> {MULT}      {return new Symbol(sym.MULT, yyline, yycolumn,yytext());}
<YYINITIAL> {DIV}       {return new Symbol(sym.DIV, yyline, yycolumn,yytext());}
<YYINITIAL> {POTENCIA}     {return new Symbol(sym.POTENCIA, yyline, yycolumn, yytext());}
<YYINITIAL> {MODULO}       {return new Symbol(sym.MODULO, yyline, yycolumn, yytext());}
<YYINITIAL> {INCREMENTO}   {return new Symbol(sym.INCREMENTO, yyline, yycolumn, yytext());}
<YYINITIAL> {DECREMENTO}   {return new Symbol(sym.DECREMENTO, yyline, yycolumn, yytext());}

// Símbolos Relacionales
<YYINITIAL> {IGUAL}        {return new Symbol(sym.IGUAL, yyline, yycolumn, yytext());}
<YYINITIAL> {IGUAL_IGUAL}  {return new Symbol(sym.IGUAL_IGUAL, yyline, yycolumn, yytext());}
<YYINITIAL> {DIFERENTE_DE} {return new Symbol(sym.DIFERENTE_DE, yyline, yycolumn, yytext());}
<YYINITIAL> {MENOR_QUE}    {return new Symbol(sym.MENOR_QUE, yyline, yycolumn, yytext());}
<YYINITIAL> {MENOR_IGUAL}  {return new Symbol(sym.MENOR_IGUAL, yyline, yycolumn, yytext());}
<YYINITIAL> {MAYOR_QUE}    {return new Symbol(sym.MAYOR_QUE, yyline, yycolumn, yytext());}
<YYINITIAL> {MAYOR_IGUAL}  {return new Symbol(sym.MAYOR_IGUAL, yyline, yycolumn, yytext());}

// Símbolos Lógicos
<YYINITIAL> {OR}           {return new Symbol(sym.OR, yyline, yycolumn, yytext());}
<YYINITIAL> {AND}          {return new Symbol(sym.AND, yyline, yycolumn, yytext());}
<YYINITIAL> {XOR}          {return new Symbol(sym.XOR, yyline, yycolumn, yytext());}
<YYINITIAL> {NOT}          {return new Symbol(sym.NOT, yyline, yycolumn, yytext());}

// SIMBOLOS QUE SE OMITEN
{COMENTARIOS}           {}
<YYINITIAL> {BLANCOS}   {}
<YYINITIAL> {CHAR}          {return new Symbol(sym.CHAR, yyline, yycolumn, yytext());}

<YYINITIAL> {ID}            {return new Symbol(sym.ID_VARIABLE, yyline, yycolumn, yytext());}


//SIMBOLOS CON STATE

// estado cadena

<YYINITIAL> [\"]        {cadena = ""; yybegin(CADENA);}

<CADENA> {
    [\"]        {String tmp= cadena;
                cadena="";
                yybegin(YYINITIAL);
                return new Symbol(sym.CADENA, yyline, yycolumn,tmp);}
    
    [^\"]       {cadena += yytext();}
    "\\\\"      {cadena += "\\";}               // Barra invertida
    "\\\""      {cadena += "\"";}               // Comillas dobles
    "\\n"       {cadena += "\n";}               // Nueva línea
    "\\t"       {cadena += "\t";}               // Tabulación
    "\\r"       {cadena += "\r";}               // Retorno de carro
    "\\f"       {cadena += "\f";}               // Avance de página
    "\\'"       {cadena += "'";}                // Comilla simple
}

// Errores Léxicos
<YYINITIAL> . {
                listaErrores.add(new Errores("LEXICO","El caracter "+
                yytext()+" NO pertenece al lenguaje", yyline, yycolumn));
}
