
package instrucciones.listas;

import abstracto.Instruccion;
import excepciones.Errores;
import java.util.ArrayList;
import simbolo.Arbol;
import simbolo.Simbolo;
import simbolo.Tipo;
import simbolo.tablaSimbolos;

public class Declaracion_LDinamicas extends Instruccion{
    
    private String identificador;
    public boolean mutabilida;
    public String bloque;

    public Declaracion_LDinamicas(String identificador, Tipo tipo, int linea, int columna) {
        super(tipo, linea, columna);
        this.identificador = identificador;
        this.mutabilida = true;
        this.bloque = "";
    }

    @Override
    public Object interpretar(Arbol arbol, tablaSimbolos tabla) {
        
        ArrayList<Object> dinamicList = new ArrayList<Object>();
        Simbolo s = new Simbolo(this.tipo, this.identificador, dinamicList, mutabilida, linea, columna);
        
        if( bloque.equals("") ){
            this.setBloque("Global");
        }
        
        s.setEntorno(bloque);
        s.setTip("Lista");
        
        boolean creacion = tabla.setVariable(s);
        
        if(!creacion) {
            return new Errores("SEMANTICO", "Variable ya existente " + this.identificador, this.linea, this.columna);
        }
        view.Ventana_Base.tabla_Simbolos.add(s);
        
        return null;
    }
    
    public String getBloque() {
        return bloque;
    }

    public void setBloque(String bloque) {
        this.bloque = bloque;
    }
}
