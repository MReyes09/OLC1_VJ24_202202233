
package instrucciones.struct;

import abstracto.Instruccion;
import excepciones.Errores;
import java.util.ArrayList;
import simbolo.Arbol;
import simbolo.Tipo;
import simbolo.tablaSimbolos;
import simbolo.tipoDato;

public class Asignacion_Struct extends Instruccion{
    
    private ArrayList<String> lista_ids;
    private Instruccion expresion;

    public Asignacion_Struct(ArrayList<String> lista_ids, Instruccion expresion, int linea, int columna) {
        super(new Tipo(tipoDato.ENTERO), linea, columna);
        this.lista_ids = lista_ids;
        this.expresion = expresion;
    }

    @Override
    public Object interpretar(Arbol arbol, tablaSimbolos tabla) {
        
        tablaSimbolos secuencia_Struct = null;
        
        for( String variable: this.lista_ids ){
            
            if( secuencia_Struct != null ){
                
                var valor = secuencia_Struct.getVariable(variable);
                
                if (valor == null) {
                    return new Errores("SEMANTICA", "Variable " + variable + " no existente",
                        this.linea, this.columna);
                }
                
                if( valor.getTipo().getTipo() == tipoDato.STRUCT ){
                
                    secuencia_Struct = (tablaSimbolos)valor.getValor();
                    continue;

                }
                var valor_Nuevo = this.expresion.interpretar(arbol, tabla);
                if( valor_Nuevo == null ){
                    return new Errores("SEMANTICA", "No se ha podido interpretar el valor nuevo",
                        this.linea, this.columna);
                }else if( valor_Nuevo instanceof Errores ){
                    return valor_Nuevo;
                }
                valor.setValor(valor_Nuevo);
                return null;
            }
            
            var valor = tabla.getVariable(variable);
            
            if (valor == null) {
                return new Errores("SEMANTICA", "Variable " + variable + " no existente",
                    this.linea, this.columna);
            }
            
            if( valor.getTipo().getTipo() == tipoDato.STRUCT ){
                
                secuencia_Struct = (tablaSimbolos)valor.getValor();
                this.tipo.setTipo(tipoDato.STRUCT);
                continue;
                
            }
            
        }
        
        return null;
        
    }
    
}
