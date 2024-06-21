
package instrucciones.subrutina;

import abstracto.Instruccion;
import excepciones.Errores;
import instrucciones.Declaracion;
import java.util.LinkedList;
import simbolo.Arbol;
import simbolo.Tipo;
import simbolo.tablaSimbolos;
import simbolo.tipoDato;

public class Execute extends Instruccion{
    
    public String id;
    private LinkedList<Instruccion> parametros;
    private static int conteo = 0;

    public Execute(String id, LinkedList<Instruccion> parametros, int linea, int columna) {
        super(new Tipo(tipoDato.VOID), linea, columna);
        this.id = id;
        this.parametros = parametros;
    }    

    @Override
    public Object interpretar(Arbol arbol, tablaSimbolos tabla) {
        var busqueda = arbol.getFuncion(id);
        
        if( busqueda == null ) {
            String mensaje = "Funcion no existente";
            return new Errores("SEMANTICO", mensaje, linea, columna);
        }
        
        if( busqueda instanceof Metodo metodo) {
            conteo++;
            var newTabla = new tablaSimbolos(arbol.getTablaGlobal());
            newTabla.setNombre("METODO_" + conteo);
            
            if( ((Metodo) busqueda).parametros.size() != this.parametros.size() ) {
                String mensaje = "La cantidad de parametros no coincide con los que se esperan";
                return new Errores("SEMANTICO", mensaje, linea, columna);
            }
            
            for( int i =0; i < this.parametros.size(); i++ ){
                var identificador = (String) metodo.parametros.get(i).get("id");
                var valor = this.parametros.get(i);
                var tipo2 = (Tipo) metodo.parametros.get(i).get("tipo");
                
                var declaracionParametro = new Declaracion(
                        false, identificador, valor, tipo2, this.linea, this.columna
                );
                var resultadoDeclaracion = declaracionParametro.interpretar(arbol, newTabla);
                
                if( resultadoDeclaracion instanceof Errores) {
                    return resultadoDeclaracion;
                }                
            }
            
            var resultadosFuncion = metodo.interpretar(arbol, newTabla);
            if( resultadosFuncion instanceof Errores ){
                return resultadosFuncion;
            }
            
        }
        
        return null;
        
    }
    
}
