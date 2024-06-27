
package instrucciones.struct;

import abstracto.Instruccion;
import excepciones.Errores;
import java.util.LinkedList;
import simbolo.Arbol;
import simbolo.Simbolo;
import simbolo.Tipo;
import simbolo.tablaSimbolos;
import simbolo.tipoDato;
import instrucciones.Declaracion;

public class Declaracion_Struct extends Instruccion{
    
    private String id;
    private LinkedList<Instruccion> declaraciones;
    private boolean mutabilidad;
    private String bloque;
    private int conteo = 0;

    public Declaracion_Struct(String id, LinkedList<Instruccion> declaraciones, int linea, int columna) {
        super(new Tipo(tipoDato.VOID), linea, columna);
        this.id = id;
        this.declaraciones = declaraciones;
        this.mutabilidad = true;
        this.bloque = "";
    }

    @Override
    public Object interpretar(Arbol arbol, tablaSimbolos tabla) {
        
        
        if( this.declaraciones.size() != 0 && this.declaraciones != null ){
            conteo ++;
            var newTabla = new tablaSimbolos();
            newTabla.setNombre("Struct_" + conteo);
            for( Instruccion declaracion: this.declaraciones ){
                
                ((Declaracion) declaracion).setBloque(newTabla.getNombre());
                var variable_Declarada = declaracion.interpretar(arbol, tabla);
                if( variable_Declarada instanceof Errores ) {
                    return variable_Declarada;
                } else if( variable_Declarada == null ){
                    return new Errores("SEMANTICO", "A ocurrido un error al declarar variables", this.linea, this.columna);
                }
                boolean creacion = newTabla.setVariable((Simbolo)variable_Declarada);
                if(!creacion) {
                    return new Errores("SEMANTICO", "Variable ya existente", this.linea, this.columna);
                }
            }
            
            Simbolo s = new Simbolo(this.tipo, this.id, newTabla, this.mutabilidad, this.linea, this.columna);
            
            if(bloque.equals("")){
                this.setBloque("Global");
            }
            s.setEntorno(bloque);
            boolean creacion = tabla.setVariable(s);
            if(!creacion) {
                return new Errores("SEMANTICO", "Variable ya existente", this.linea, this.columna);
            }
            view.Ventana_Base.tabla_Simbolos.add(s);
            
        }else{
            return new Errores("SEMANTICO", "El Struct: " + this.id + " se ha declarado sin variables", this.linea, this.columna);
        }
        return null;
    }

    public String getBloque() {
        return bloque;
    }

    public void setBloque(String bloque) {
        this.bloque = bloque;
    }
    
}
