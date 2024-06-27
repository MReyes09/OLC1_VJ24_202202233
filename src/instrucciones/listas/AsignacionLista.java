
package instrucciones.listas;

import abstracto.Instruccion;
import excepciones.Errores;
import java.util.ArrayList;
import simbolo.Arbol;
import simbolo.Simbolo;
import simbolo.Tipo;
import simbolo.tablaSimbolos;
import simbolo.tipoDato;

public class AsignacionLista extends Instruccion{
    
    private String id;
    private Instruccion exp;
    private Instruccion posicion;
    private OperadoresLista operacion;

    public AsignacionLista(String id, Instruccion exp, Instruccion posicion, OperadoresLista operacion, int linea, int columna) {
        super(new Tipo(tipoDato.VOID), linea, columna);
        this.id = id;
        this.exp = exp;
        this.operacion = operacion;
        this.posicion = posicion;
    }

    @Override
    public Object interpretar(Arbol arbol, tablaSimbolos tabla) {
        
        var variable = tabla.getVariable(id);
        
        if( variable == null ){
            return new Errores("SEMANTICO", "Variable" + id + " no exitente", this.linea, this.columna);
        }
        
         switch(this.operacion){
            case ADD -> {
                //INTERPRETAR VALOR NUEVO
        
                var newValor = this.exp.interpretar(arbol, tabla);
                if( newValor instanceof Errores ){
                    return newValor;
                }

                //VALIDAR TIPOS
                if( variable.getTipo().getTipo() != this.exp.tipo.getTipo() ){
                    return new Errores("SEMANTICO", "Tipos erroneos en asignacion", this.linea, this.columna);
                }
                variable.setValor(this.add_Data(newValor, variable));
            }
            case REMOVE -> {
                var resultado = this.remove_Data(variable, this.posicion, arbol, tabla);
                if( resultado instanceof Errores){
                    return resultado;
                }
                this.tipo.setTipo(variable.getTipo().getTipo());
                return resultado;
            }
            default -> {
                new Errores("SEMANTICO", "Operacion invalida para lista", this.linea, this.columna);
            }
        };
        
        return null;
    }
    
    private Object add_Data(Object newValor, Simbolo variable){
        
        ArrayList<Object> resultado = (ArrayList<Object>)variable.getValor();
        resultado.add(newValor);
        return resultado;
        
    }
    
    private Object remove_Data(Simbolo variable, Instruccion posicion, Arbol arbol, tablaSimbolos tabla){
        
        ArrayList<Object> resultado = (ArrayList<Object>)variable.getValor();
        
        if( posicion.tipo.getTipo() != tipoDato.ENTERO ){
            return new Errores("SEMANTICO", "Se esperaba posicion de tipo Entero", this.linea, this.columna);
        }
        
        var res_Posicion = posicion.interpretar(arbol, tabla);
        if( res_Posicion instanceof Errores){
            return res_Posicion;
        }
        
        Object valor = resultado.get((int) res_Posicion);
        resultado.remove((int) res_Posicion);
        return valor;
    }
    
}
