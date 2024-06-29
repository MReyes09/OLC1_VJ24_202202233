
package instrucciones.subrutina;

import abstracto.Instruccion;
import excepciones.Errores;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import simbolo.Arbol;
import simbolo.Tipo;
import simbolo.tablaSimbolos;

public class Funcion extends Instruccion{
    
    public String id;
    public LinkedList<HashMap> parametros;
    public LinkedList<Instruccion> instrucciones;

    public Funcion(String id, LinkedList<HashMap> parametros, LinkedList<Instruccion> instrucciones, Tipo tipo, int linea, int columna) {
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
            
            if( resultado != null ){
                return resultado;
            }
            
            if ( resultado instanceof Errores ) {
                return resultado;
            }
            if( resultado instanceof Return ){
                
                var res = ((Return) resultado).interpretar(arbol, tabla);
                
                if( this.tipo.getTipo() != ((Return) resultado).tipo.getTipo() ){
                    return new Errores("SEMANTICO", "El resultado del return no corresponde al tipo esperado", this.linea, this.columna);
                }
                
                if( res instanceof Errores){
                    return res;
                }
                return res;
            }
        }
        return null;
        
    }
    
}
