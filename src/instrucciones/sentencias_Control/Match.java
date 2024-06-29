
package instrucciones.sentencias_Control;

import abstracto.Instruccion;
import excepciones.Errores;
import instrucciones.Declaracion;
import instrucciones.subrutina.Return;
import java.util.LinkedList;
import simbolo.Arbol;
import simbolo.Tipo;
import simbolo.tablaSimbolos;
import simbolo.tipoDato;

public class Match extends Instruccion{
    
    private Instruccion condicion;
    private LinkedList<Cases_Match> list_Cases;
    private static int conteo = 0;

    public Match(Instruccion condicion, LinkedList<Cases_Match> list_Cases, int linea, int columna) {
        super(new Tipo(tipoDato.VOID), linea, columna);
        this.condicion = condicion;
        this.list_Cases = list_Cases;
    }
    
    @Override
    public Object interpretar(Arbol arbol, tablaSimbolos tabla) {
        conteo++;
        var cond = this.condicion.interpretar(arbol, tabla);
        
        if(cond instanceof Errores) {
            return cond;
        }
        
        var newTabla = new tablaSimbolos(tabla);
        newTabla.setNombre("CASE_" + conteo);
        for(var caso : list_Cases){
            if(caso == null) {
                continue;
            }
            if( caso.isResultado() ){
                var resultado = caso.interpretar(arbol, tabla);
                if (this.condicion.tipo.getTipo() == caso.tipo.getTipo()){
                    if(resultado == cond) {
                        return this.validar(caso, arbol, newTabla);
                    }
                }
            } else {
                return this.validar(caso, arbol, newTabla);
            }
        }
        return null;
    }
    
    public Object validar(Cases_Match caso, Arbol arbol, tablaSimbolos tabla) {
        
        for(var instruccion : caso.getInstrucciones()){
            if(instruccion instanceof Declaracion){
                ((Declaracion) instruccion).setBloque(tabla.getNombre());
            }
            if(instruccion == null) {
                return null;
            }
            if(instruccion instanceof Return){
                var result = instruccion.interpretar(arbol, tabla);
                this.tipo.setTipo(instruccion.tipo.getTipo());
                return result;
            }            
            
            var result = instruccion.interpretar(arbol, tabla);
            if( result != null ){
                return result;
            }
            
            if(result instanceof Errores){
                return result;
            }
        }
        return null;
    }
}
