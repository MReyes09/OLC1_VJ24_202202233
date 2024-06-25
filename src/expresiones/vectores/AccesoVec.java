
package expresiones.vectores;

import abstracto.Instruccion;
import excepciones.Errores;
import java.util.ArrayList;
import java.util.List;
import simbolo.Arbol;
import simbolo.Tipo;
import simbolo.tablaSimbolos;
import simbolo.tipoDato;

public class AccesoVec extends Instruccion{
    
    private String id;
    private Instruccion posicion_F;
    private Instruccion posicion_C;

    public AccesoVec(String id, Instruccion posicion_F, Instruccion posicion_C, int linea, int columna) {
        super(new Tipo(tipoDato.VOID), linea, columna);
        this.id = id;
        this.posicion_F = posicion_F;
        this.posicion_C = posicion_C;
    }

    @Override
    public Object interpretar(Arbol arbol, tablaSimbolos tabla) {
        
        var variable = tabla.getVariable(this.id);
        
        if( variable == null ){
            return new Errores("SEMANTICA", "Variable " + this.id + " no existente",
                    this.linea, this.columna);
        }
        
        this.tipo.setTipo(variable.getTipo().getTipo());
        // VALIDAR QUE LAS POSICIONES BRINDANDAS COINCIDAN CON LOS VALORES
        List<Object> lista_Vectores = new ArrayList<>();
        lista_Vectores = (List<Object>)variable.getValor();
        
        if( lista_Vectores.get(0) instanceof ArrayList){
            // SI entra aqui es porque se tiene un vector de dos dimensiones    
            if( this.posicion_C != null ){
                
                return this.acceso_Vector_2D(arbol, tabla, lista_Vectores);
                
            }else{
                
                String descripcion = "Se esperaban dos posiciones para vector de dos dimensiones";
                return new Errores("SEMANTICA", descripcion,
                    this.linea, this.columna);
                
            }
            
        }else{
            
            // VALIDACION PARA VECTOR 1D
            return this.acceso_Vector_1D(arbol, tabla, lista_Vectores);
            
        }
        
    }
    
    public Object acceso_Vector_1D(Arbol arbol, tablaSimbolos tabla, List<Object> lista_Vectores){
        
        var resultado_Posicion1 = posicion_F.interpretar(arbol, tabla);
            
            //Errores Posibles
            if( resultado_Posicion1 instanceof Errores ){
                return resultado_Posicion1;
            }else if( posicion_F.tipo.getTipo() != tipoDato.ENTERO ){
                
                String descripcion = "Se esperaban datos de tipo Entero";
                return new Errores("SEMANTICA", descripcion,
                    this.linea, this.columna);
                
            }else if( lista_Vectores.size() <= (int)resultado_Posicion1 ){
                
                String descripcion = "La posicion es mayor al tamaño de la lista";
                return new Errores("SEMANTICA", descripcion,
                    this.linea, this.columna);
                
            }
            
            return lista_Vectores.get((int) resultado_Posicion1);
        
    }
    
    public Object acceso_Vector_2D(Arbol arbol, tablaSimbolos tabla, List<Object> lista_Vectores){
        
        var resultado_Posicion1 = posicion_F.interpretar(arbol, tabla);
        var resultado_Posicion2 = posicion_C.interpretar(arbol, tabla);
        
        //Errores Posibles
        if( resultado_Posicion1 instanceof Errores ){
            return resultado_Posicion1;
        }else if( resultado_Posicion2 instanceof Errores) {
            return resultado_Posicion2;
        }else if( posicion_F.tipo.getTipo() != tipoDato.ENTERO || posicion_C.tipo.getTipo() != tipoDato.ENTERO){

            String descripcion = "Se esperaban datos de tipo Entero";
            return new Errores("SEMANTICA", descripcion,
                this.linea, this.columna);

        }else if( lista_Vectores.size() <= (int)resultado_Posicion1 || (int)resultado_Posicion2 >= 2){

            String descripcion = "La posicion es mayor al tamaño de la lista";
            return new Errores("SEMANTICA", descripcion,
                this.linea, this.columna);

        }
        int posicion_Y = (int)resultado_Posicion1;
        int posicion_X = (int)resultado_Posicion2;
        
        ArrayList<Object> lista_Final = (ArrayList<Object>)lista_Vectores.get(posicion_Y);
            
        return lista_Final.get(posicion_X);
        
    }
}
