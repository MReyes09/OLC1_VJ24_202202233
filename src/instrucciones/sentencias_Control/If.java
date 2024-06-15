
package instrucciones.sentencias_Control;

import abstracto.Instruccion;
import excepciones.Errores;
import instrucciones.sentencias_Transferencia.Break;
import instrucciones.sentencias_Transferencia.Continue;
import java.util.LinkedList;
import simbolo.Arbol;
import simbolo.Tipo;
import simbolo.tablaSimbolos;
import simbolo.tipoDato;

public class If extends Instruccion{

    private Instruccion condicion;
    private LinkedList<Instruccion> instrucciones;
    private LinkedList<Instruccion> instrucciones_Else;

    public If(Instruccion condicion, LinkedList<Instruccion> instrucciones, int linea, int col) {
        super(new Tipo(tipoDato.VOID), linea, col);
        this.condicion = condicion;
        this.instrucciones = instrucciones;
    }

    public If(Instruccion condicion, LinkedList<Instruccion> instrucciones, LinkedList<Instruccion> instrucciones_Else, int linea, int columna) {
        super(new Tipo(tipoDato.VOID), linea, columna);
        this.condicion = condicion;
        this.instrucciones = instrucciones;
        this.instrucciones_Else = instrucciones_Else;
    }

    @Override
    public Object interpretar(Arbol arbol, tablaSimbolos tabla) {
        var cond = this.condicion.interpretar(arbol, tabla);
        if (cond instanceof Errores) {
            return cond;
        }

        // ver que cond sea booleano
        if (this.condicion.tipo.getTipo() != tipoDato.BOOLEANO) {
            return new Errores("SEMANTICO", "Expresion invalida",
                    this.linea, this.columna);
        }

        var newTabla = new tablaSimbolos(tabla);
        if ((boolean) cond) {
            for (var i : this.instrucciones) {
                if(i == null){
                    return null;
                }
                if (i instanceof Continue) {
                    return i;
                }
                if (i instanceof Break) {
                    return i;
                }
                var resultado = i.interpretar(arbol, newTabla);
                if(resultado == null){
                    return null;
                }
                if (resultado instanceof Break) {
                    return resultado;
                }
                if (resultado instanceof Continue) {
                    return resultado;
                }
                if (resultado instanceof Errores) {
                    return resultado;
                }
                
            }
        } else {
            if(this.instrucciones_Else != null){
                for(var instruccion : this.instrucciones_Else) {
                    if (instruccion instanceof Break) {
                        return instruccion;
                    }
                    if (instruccion instanceof Continue) {
                        return instruccion;
                    }
                    var resultado = instruccion.interpretar(arbol, newTabla);
                    if (resultado instanceof Break) {
                        return resultado;
                    }
                    if (resultado instanceof Errores) {
                        return resultado;
                    }
                    if (resultado instanceof Continue) {
                        return resultado;
                    }
                }
            }
        }
        return null;
    }
    
}
