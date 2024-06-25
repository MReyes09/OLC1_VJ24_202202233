
package instrucciones.vectores;

import abstracto.Instruccion;
import excepciones.Errores;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import simbolo.Arbol;
import simbolo.Simbolo;
import simbolo.Tipo;
import simbolo.tablaSimbolos;

public class Declaracion_Vectores extends Instruccion{
    
    public String identificador;
    public LinkedList<Instruccion> valores1;
    public LinkedList<LinkedList<Instruccion>> valores2;
    public boolean mutabilidad;
    public String bloque;

    public Declaracion_Vectores(String identificador, LinkedList<Instruccion> valores1, LinkedList<LinkedList<Instruccion>> valores2, boolean mutabilidad, Tipo tipo, int linea, int columna) {
        super(tipo, linea, columna);
        this.identificador = identificador;
        this.valores1 = valores1;
        this.valores2 = valores2;
        this.mutabilidad = mutabilidad;
        this.bloque = "";
    }

    @Override
    public Object interpretar(Arbol arbol, tablaSimbolos tabla) {
        
        if( this.valores2 != null ) {
            
            // SOLO esta lista contiene ya definida las dos dimensiones
            List<List<Object>> staticList = new ArrayList<>();
            // Ambos valores1 y valores2 tienen datos
            LinkedList<Instruccion> lista_Final = valores2.get(0);
            for (int i = 0; i < valores2.size(); i++) {
                List<Object> fila = new ArrayList<>();
                for (int j = 0; j < lista_Final.size(); j++) {
                    fila.add(null); // Inicializar con un valor por defecto
                }
                staticList.add(fila);
            }
            
            var resultado = this.lista_2D(arbol, tabla, staticList);
            
            if( resultado instanceof Errores){
                return resultado;
            }
            
            Simbolo s = new Simbolo(this.tipo, this.identificador, resultado, this.mutabilidad, linea, columna);
            
            if(bloque.equals("")){
                this.setBloque("Global");
            }
            
            s.setEntorno(bloque);
            s.setTip("vector");
            boolean creacion = tabla.setVariable(s);
            
            if(!creacion) {
                return new Errores("SEMANTICO", "Variable ya existente " + this.identificador, this.linea, this.columna);
            }
            view.Ventana_Base.tabla_Simbolos.add(s);
        }else{
             // Solo valores1 tiene datos
            List<Object> staticList = new ArrayList<>();        
            for (int i = 0; i < valores1.size(); i++) {
                staticList.add(new ArrayList<>());
            }
            var resultado = this.lista_1D(arbol, tabla, staticList);
            if( resultado instanceof Errores){
                return resultado;
            }
            
            Simbolo s = new Simbolo(this.tipo, this.identificador, resultado, this.mutabilidad, linea, columna);
            
            if(bloque.equals("")){
                this.setBloque("Global");
            }
            
            s.setEntorno(bloque);
            s.setTip("vector");
            boolean creacion = tabla.setVariable(s);
            
            if(!creacion) {
                return new Errores("SEMANTICO", "Variable ya existente " + this.identificador, this.linea, this.columna);
            }
            view.Ventana_Base.tabla_Simbolos.add(s);
        }
        
        return null;
    }
    
    public Object lista_1D(Arbol arbol, tablaSimbolos tabla, List<Object> staticList){
        int contador = 0;
        for( var instruccion: this.valores1 ){

            if( instruccion == null ){
                return null;
            }

            var resIns = instruccion.interpretar(arbol, tabla);
            if ( resIns instanceof Errores ) {
                return resIns;
            }

            if( instruccion.tipo.getTipo() != this.tipo.getTipo() ) {
                String descripcion = "Se intenta asignar el tipo: " + instruccion.tipo.getTipo() 
                        + " A una variable de tipo: " + this.tipo.getTipo(); 
                return new Errores("SEMANTICO", descripcion, this.linea, this.columna);
            }

            staticList.set(contador, resIns);
            contador++;
        }
        
        return staticList;
    }
    
    public Object lista_2D(Arbol arbol, tablaSimbolos tabla, List<List<Object>> staticList) {
        
        int posiciones_F = 0;
        int posiciones_C = 0;
        
        for( LinkedList<Instruccion> lista_C: this.valores2 ){
            
            if( lista_C == null ){
                return null;
            }
            
            for( var instruccion: lista_C ){
                
                if( instruccion == null ){
                    return null;
                }
                
                if( instruccion.tipo.getTipo() != this.tipo.getTipo() ){
                    
                    String descripcion = "Se intenta asignar el tipo: " + instruccion.tipo.getTipo() 
                        + " A una variable de tipo: " + this.tipo.getTipo(); 
                    return new Errores("SEMANTICO", descripcion, this.linea, this.columna);
                    
                }
                
                var resIns = instruccion.interpretar(arbol, tabla);
                
                if (resIns instanceof Errores) {
                    return resIns;
                }
                
                staticList.get(posiciones_F).set(posiciones_C, resIns);
                posiciones_C++;
            }
            posiciones_C = 0;
            posiciones_F++;
        }
        
        return staticList;
    }

    public String getBloque() {
        return bloque;
    }

    public void setBloque(String bloque) {
        this.bloque = bloque;
    }
}
