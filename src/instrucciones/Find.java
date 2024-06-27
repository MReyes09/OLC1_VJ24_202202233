
package instrucciones;

import abstracto.Instruccion;
import excepciones.Errores;
import java.util.ArrayList;
import java.util.List;
import simbolo.Arbol;
import simbolo.Simbolo;
import simbolo.Tipo;
import simbolo.tablaSimbolos;
import simbolo.tipoDato;

public class Find extends Instruccion{
    
    private Instruccion expresion;
    private String id;

    public Find(Instruccion expresion, String id, int linea, int columna) {
        super(new Tipo(tipoDato.BOOLEANO), linea, columna);
        this.expresion = expresion;
        this.id = id;
    }    

    @Override
    public Object interpretar(Arbol arbol, tablaSimbolos tabla) {
        
        Simbolo variable = tabla.getVariable(id);
        
        if( variable == null ){
            return new Errores("SEMANTICO", "La variable " + variable + " No existe", linea, columna);
        }
        
        var expInterpretada = this.expresion.interpretar(arbol, tabla);
        if( expInterpretada instanceof Errores ){
            return expInterpretada;
        }
        
        if( variable.getValor() instanceof List ){
            
            List<Object> lista_Vector = (List<Object>) variable.getValor();
            
            if( lista_Vector.get(0) instanceof ArrayList ){ //ESte es un vector de 2D
            
                for( int i = 0; i < lista_Vector.size(); i++ ){
                    ArrayList<Object> subLista = (ArrayList<Object>)lista_Vector.get(i);
                    for( Object valor: subLista ) {
                        if( valor == expInterpretada ){
                            return true;
                        }
                    }
                }
                
                return false;
                
            }else{
                
                for(Object valor: lista_Vector){
                    if( valor == expInterpretada ){
                        return true;
                    }
                }
                
                return false;
                
            }
        }else if( variable.getValor() instanceof ArrayList){
            ArrayList<Object> lista = (ArrayList<Object>) variable.getValor();
            
            for( Object valor: lista ){
                if( valor == expInterpretada ){
                    return true;
                }
            }
            
            return false;
            
        }
        
        return new Errores("SEMANTICO", "Esta funcion es unica para listas y vectores", linea, columna);
        
    }
    
}
