
package instrucciones.subrutina;

import abstracto.Instruccion;
import excepciones.Errores;
import java.util.HashMap;
import java.util.LinkedList;
import simbolo.Arbol;
import simbolo.Tipo;
import simbolo.tablaSimbolos;

public class Metodo extends Instruccion{
    public String id;
    public LinkedList<HashMap> parametros;
    public LinkedList<Instruccion> instrucciones;

    public Metodo(String id, LinkedList<HashMap> parametros, LinkedList<Instruccion> instrucciones, Tipo tipo, int linea, int columna) {
        super(tipo, linea, columna);
        this.id = id;
        this.parametros = parametros;
        this.instrucciones = instrucciones;
    }

    @Override
    public Object interpretar(Arbol arbol, tablaSimbolos tabla) {
        for(var instruccion : this.instrucciones) {
            
            if( instruccion == null ) {
                return null;
            }
            
            var resultado = instruccion.interpretar(arbol, tabla);
            
            if ( resultado instanceof Errores ) {
                return resultado;
            }
            if( resultado instanceof Return ){
                return null;
            }
        }
        return null;
    }
    
}
