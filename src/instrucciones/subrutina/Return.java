
package instrucciones.subrutina;

import abstracto.Instruccion;
import excepciones.Errores;
import simbolo.Arbol;
import simbolo.Tipo;
import simbolo.tablaSimbolos;
import simbolo.tipoDato;

public class Return extends Instruccion{
    
    public Instruccion expresion;

    public Return(Instruccion expresion, int linea, int columna) {
        super(new Tipo(tipoDato.ENTERO), linea, columna);
        this.expresion = expresion;
    }

    @Override
    public Object interpretar(Arbol arbol, tablaSimbolos tabla) {
        
        //EN CASO FUERA UN RETURN SIN EXPRESION
        if( expresion == null ){
            return null;
        }
        
        //RETURN CON EXPRESION
        var resultado_Return = this.expresion.interpretar(arbol, tabla);
        
        if( resultado_Return instanceof Errores ){
            return resultado_Return;
        }
        
        this.tipo.setTipo(this.expresion.tipo.getTipo());
        return resultado_Return;
    }
    
}
