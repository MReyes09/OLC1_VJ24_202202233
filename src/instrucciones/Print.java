
package instrucciones;

import abstracto.Instruccion;
import excepciones.Errores;
import simbolo.Arbol;
import simbolo.Tipo;
import simbolo.tablaSimbolos;
import simbolo.tipoDato;

public class Print extends Instruccion{
    
    private Instruccion expresion;

    public Print(Instruccion expresion, int linea, int columna) {
        super(new Tipo(tipoDato.VOID), linea, columna);
        this.expresion = expresion;
    }    
    
    @Override
    public Object interpretar(Arbol arbol, tablaSimbolos tabla) {
        
        var resultado = this.expresion.interpretar(arbol, tabla);
        
        if(resultado instanceof Errores){
            return resultado;
        }
        
        arbol.Print(resultado.toString());
        return null;
    }
}
