
package instrucciones;

import abstracto.Instruccion;
import excepciones.Errores;
import simbolo.Arbol;
import simbolo.Tipo;
import simbolo.tablaSimbolos;
import simbolo.tipoDato;

public class Round extends Instruccion{
    
    private Instruccion expresion;

    public Round(Instruccion expresion, int linea, int columna) {
        super(new Tipo(tipoDato.ENTERO), linea, columna);
        this.expresion = expresion;
    }

    @Override
    public Object interpretar(Arbol arbol, tablaSimbolos tabla) {
        
        var resultado = this.expresion.interpretar(arbol, tabla);
        if( this.expresion.tipo.getTipo() != tipoDato.DECIMAL ){
            String descripcion = "Se intenta redondear un valor que no es decimal";
            return new Errores("SEMANTICO", descripcion, linea, columna);
        }
        if( resultado instanceof Errores ){
            return resultado;
        }
        
        int valor_Redondeado = (int) Math.round((double) resultado);
        return valor_Redondeado;
    }
    
}
