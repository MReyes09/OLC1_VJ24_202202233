
package instrucciones.sentencias_Control;

import abstracto.Instruccion;
import excepciones.Errores;
import java.util.LinkedList;
import simbolo.Arbol;
import simbolo.Tipo;
import simbolo.tablaSimbolos;
import simbolo.tipoDato;

public class Cases_Match extends Instruccion{
    
    private Instruccion condicion;
    private LinkedList<Instruccion> instrucciones;
    private boolean resultado;

    public Cases_Match(Instruccion condicion, LinkedList<Instruccion> instrucciones, boolean resultado, int linea, int columna) {
        super(new Tipo(tipoDato.VOID), linea, columna);
        this.condicion = condicion;
        this.instrucciones = instrucciones;
        this.resultado = resultado;
    }

    @Override
    public Object interpretar(Arbol arbol, tablaSimbolos tabla) {
        
        var cond = this.condicion.interpretar(arbol, tabla);
        
        if(cond instanceof Errores) {
            return cond;
        }
        
        this.tipo.setTipo(this.condicion.tipo.getTipo());
        return cond;
    }

    public Instruccion getCondicion() {
        return condicion;
    }

    public void setCondicion(Instruccion condicion) {
        this.condicion = condicion;
    }

    public LinkedList<Instruccion> getInstrucciones() {
        return instrucciones;
    }

    public void setInstrucciones(LinkedList<Instruccion> instrucciones) {
        this.instrucciones = instrucciones;
    }

    public boolean isResultado() {
        return resultado;
    }

    public void setResultado(boolean resultado) {
        this.resultado = resultado;
    }

    public Tipo getTipo() {
        return tipo;
    }

    public void setTipo(Tipo tipo) {
        this.tipo = tipo;
    }

    public int getLinea() {
        return linea;
    }

    public void setLinea(int linea) {
        this.linea = linea;
    }

    public int getColumna() {
        return columna;
    }

    public void setColumna(int columna) {
        this.columna = columna;
    }
    
}
