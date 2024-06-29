
package simbolo;

import abstracto.Instruccion;
import excepciones.Errores;
import instrucciones.struct.Declaracion_Struct;
import instrucciones.subrutina.Funcion;
import instrucciones.subrutina.Metodo;
import java.util.LinkedList;

public class Arbol {
    
    private LinkedList<Instruccion> instrucciones;
    private String consola;
    private tablaSimbolos tablaGlobal;
    public LinkedList<Errores> errores;
    private LinkedList<Instruccion> funciones;

    public Arbol(LinkedList<Instruccion> instrucciones) {
        this.instrucciones = instrucciones;
        this.consola = "";
        this.tablaGlobal = new tablaSimbolos();
        this.errores = new LinkedList<>();
        this.funciones = new LinkedList<>();
    }

    public LinkedList<Instruccion> getInstrucciones() {
        return instrucciones;
    }

    public void setInstrucciones(LinkedList<Instruccion> instrucciones) {
        this.instrucciones = instrucciones;
    }

    public String getConsola() {
        return consola;
    }

    public void setConsola(String consola) {
        this.consola = consola;
    }

    public tablaSimbolos getTablaGlobal() {
        return tablaGlobal;
    }

    public void setTablaGlobal(tablaSimbolos tablaGlobal) {
        this.tablaGlobal = tablaGlobal;
    }

    public LinkedList<Errores> getErrores() {
        return errores;
    }

    public void setErrores(LinkedList<Errores> errores) {
        this.errores = errores;
    }
    
    public void Print(String valor){
        this.consola += valor + "\n";
    }
    
    public LinkedList<Instruccion> getFunciones() {
        return funciones;
    }

    public void setFunciones(LinkedList<Instruccion> funciones) {
        this.funciones = funciones;
    }
    
    public void addFunciones(Instruccion funcion) {
        
        this.funciones.add(funcion);
    }
    
    public Instruccion getFuncion(String id) {
        for( var instruccion: this.instrucciones ) {
            
            if ( instruccion instanceof Metodo ) {
                if( ((Metodo)instruccion).id.equalsIgnoreCase(id) ) {
                    return instruccion;
                }
            } else if ( instruccion instanceof Funcion ){
                if( ((Funcion)instruccion).id.equalsIgnoreCase(id) ){
                    return instruccion;
                }
            }
            
        }
        return null;
    }
    
    public Instruccion get_Struct(String id) {
        for( var instruccion: this.instrucciones ){
            
            if( instruccion instanceof Declaracion_Struct ){
                if( ((Declaracion_Struct)instruccion).id.equalsIgnoreCase(id)) {
                    return instruccion;
                }
            }
                
        }
        return null;
    }
}
