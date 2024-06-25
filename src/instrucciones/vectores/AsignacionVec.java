
package instrucciones.vectores;

import abstracto.Instruccion;
import simbolo.Arbol;
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
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
}
