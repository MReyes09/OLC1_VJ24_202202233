
package instrucciones;

import abstracto.Instruccion;
import excepciones.Errores;
import java.util.ArrayList;
import java.util.List;
import simbolo.Arbol;
import simbolo.Tipo;
import simbolo.tablaSimbolos;
import simbolo.tipoDato;

public class Length extends Instruccion{
    private Instruccion expresion;

    public Length(Instruccion expresion, int linea, int columna) {
        super(new Tipo(tipoDato.ENTERO), linea, columna);
        this.expresion = expresion;
    }

    @Override
    public Object interpretar(Arbol arbol, tablaSimbolos tabla) {
        
        if(this.expresion == null){
            return new Errores("SEMANTICO", "Se esperaba recibir expresion en funcion length", linea, columna);
        }
        
        var resultado = this.expresion.interpretar(arbol, tabla);
        
        if( resultado instanceof List){//Este es un vector
            return ((List<Object>)resultado).size();
        }else if( resultado instanceof ArrayList ) {
            return ((ArrayList<Object>) resultado).size();
        }else if( this.expresion.tipo.getTipo() == tipoDato.CADENA ){
            return resultado.toString().length();
        }else{
            return new Errores("SEMANTICO", "El tipo de dato brindado no es el esperado", linea, columna);
        }
    }
}
