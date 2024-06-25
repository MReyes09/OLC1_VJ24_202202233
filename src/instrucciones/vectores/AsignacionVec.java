
package instrucciones.vectores;

import abstracto.Instruccion;
import excepciones.Errores;
import java.util.ArrayList;
import java.util.List;
import simbolo.Arbol;
import simbolo.Simbolo;
import simbolo.Tipo;
import simbolo.tablaSimbolos;
import simbolo.tipoDato;

public class AsignacionVec extends Instruccion{
    
    private String id;
    private Instruccion posicion_F;
    private Instruccion posicion_C;
    private Instruccion operacion;

    public AsignacionVec(String id, Instruccion posicion_F, Instruccion posicion_C, Instruccion operacion, int linea, int columna) {
        super(new Tipo(tipoDato.VOID), linea, columna);
        this.id = id;
        this.posicion_F = posicion_F;
        this.posicion_C = posicion_C;
        this.operacion = operacion;
    }

    @Override
    public Object interpretar(Arbol arbol, tablaSimbolos tabla) {
        
        var variable = tabla.getVariable(id);        
        if ( variable == null ) {
            return new Errores("SEMANTICO", "Variable" + id + " no exitente", this.linea, this.columna);
        }        
        if( variable.getMutabilidad() ){            
            if( this.posicion_F != null && this.posicion_C != null ){ // DOS DIMENSIONES
                var newValor = this.operacion.interpretar(arbol, tabla);
                var resu_F = this.posicion_F.interpretar(arbol, tabla);
                var resu_C = this.posicion_C.interpretar(arbol, tabla);
            
                if (newValor instanceof Errores) {
                    return newValor;
                }else if(resu_F instanceof Errores) {
                    return resu_F;
                }else if(resu_C instanceof Errores) {
                    return resu_C;
                }
                //validar tipos
                if (variable.getTipo().getTipo() != this.operacion.tipo.getTipo()) {
                    return new Errores("SEMANTICO", "Tipos erroneos en asignacion", this.linea, this.columna);
                }else if ( this.posicion_F.tipo.getTipo() != tipoDato.ENTERO || this.posicion_C.tipo.getTipo() != tipoDato.ENTERO){
                    String descripcion = "Se esperaban datos de tipo Entero";
                    return new Errores("SEMANTICA", descripcion,
                        this.linea, this.columna);
                }
                
                return this.asignacion_Vec_2D(resu_F, resu_C, variable, newValor);
                
            }else{ //UNA DIMESION
                
                var newValor = this.operacion.interpretar(arbol, tabla);
                var resu_F = this.posicion_F.interpretar(arbol, tabla);
                if (newValor instanceof Errores) {
                    return newValor;
                }else if(resu_F instanceof Errores) {
                    return resu_F;
                }
                //validar tipos
                if (variable.getTipo().getTipo() != this.operacion.tipo.getTipo()) {
                    return new Errores("SEMANTICO", "Tipos erroneos en asignacion", this.linea, this.columna);
                }else if ( this.posicion_F.tipo.getTipo() != tipoDato.ENTERO ){
                    String descripcion = "Se esperaban datos de tipo Entero";
                    return new Errores("SEMANTICA", descripcion,
                        this.linea, this.columna);
                }
                
                return this.asignacion_Vec_1D(resu_F, variable, newValor);
            }            
        }else{
            String descripcion = "La variable: \'" + id + "\' es de tipo CONST";
            return new Errores("SEMANTICO", descripcion, this.linea, this.columna);
        }
    }
    
    public Object asignacion_Vec_1D(Object resu_F, Simbolo variable, Object newValor){
        
        int posicion_Y = (int)resu_F;
        
        List<Object> lista_Vectores = (List<Object>)variable.getValor();
        
        if( lista_Vectores.size() <= posicion_Y ){
            String descripcion = "La posicion es mayor al tamaño de la fila";
            return new Errores("SEMANTICA", descripcion,
                this.linea, this.columna);
        }
        
        lista_Vectores.set(posicion_Y, newValor);        
        return null;
    }
    
    public Object asignacion_Vec_2D(Object resu_F, Object resu_C, Simbolo variable, Object newValor){
        
        int posicion_Y = (int)resu_F;
        int posicion_X = (int)resu_C;

        List<List<Object>> lista_Vectores = (List<List<Object>>)variable.getValor();

        if(lista_Vectores.size() <= posicion_Y){
            String descripcion = "La posicion es mayor al tamaño de la fila";
            return new Errores("SEMANTICA", descripcion,
                this.linea, this.columna);
        }

        ArrayList<Object> lista_Final = (ArrayList<Object>)lista_Vectores.get(posicion_Y);

        if ( lista_Final.size() <= posicion_X ) {

            String descripcion = "La posicion es mayor al tamaño de la columna";
            return new Errores("SEMANTICA", descripcion,
                this.linea, this.columna);

        }

        lista_Final.set(posicion_X, newValor);
        lista_Vectores.set(posicion_Y, lista_Final);
        return null;
    }
    
}
