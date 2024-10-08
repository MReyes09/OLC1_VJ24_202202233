
package expresiones.struct;

import abstracto.Instruccion;
import excepciones.Errores;
import java.util.ArrayList;
import simbolo.Arbol;
import simbolo.Simbolo;
import simbolo.Tipo;
import simbolo.tablaSimbolos;
import simbolo.tipoDato;

public class AccesoStruct extends Instruccion{
    
    private ArrayList<String> lista_ids;

    public AccesoStruct(ArrayList<String> id, int linea, int columna) {
        super(new Tipo(tipoDato.VOID), linea, columna);
        this.lista_ids = id;
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
                this.tipo.setTipo(valor.getTipo().getTipo());
                return valor.getValor();
                
            }
            
            var valor = tabla.getVariable(variable);
            
            if (valor == null) {
                return new Errores("SEMANTICA", "Variable " + variable + " no existente",
                    this.linea, this.columna);
            }
            
            if( valor.getTipo().getTipo() == tipoDato.STRUCT ){
                
                secuencia_Struct = (tablaSimbolos)valor.getValor();
                continue;
                
            }
            this.tipo.setTipo(valor.getTipo().getTipo());
            return valor.getValor();
            
        }
        
        return null;
        
    }
    
}
