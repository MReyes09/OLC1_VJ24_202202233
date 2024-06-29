
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
import instrucciones.Declaracion;
import instrucciones.subrutina.Return;

public class If extends Instruccion{

    private Instruccion condicion;
    private LinkedList<Instruccion> instrucciones;
    private LinkedList<Instruccion> instrucciones_Else;
    private static int conteo = 0;

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
        conteo++;
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
        newTabla.setNombre("If_" + conteo);
        if ((boolean) cond) {
            for (var i : this.instrucciones) {
                if(i instanceof Declaracion){
                    ((Declaracion) i).setBloque(newTabla.getNombre());
                }
                if(i == null){
                    return null;
                }
                if (i instanceof Continue) {
                    return i;
                }
                if (i instanceof Break) {
                    return i;
                }
                if( i instanceof Return ){
                    var resultado = i.interpretar(arbol, newTabla);
                    this.tipo.setTipo(i.tipo.getTipo());
                    return resultado;
                }
                    
                var resultado = i.interpretar(arbol, newTabla);
                
                if( resultado != null) {
                    return resultado;
                }
                
                if (resultado instanceof Break) {
                    return resultado;
                }
                if (resultado instanceof Continue) {
                    return resultado;
                }
                
                if( resultado instanceof Return ){
                    var res = ((Return) resultado).interpretar(arbol, newTabla);
                    this.tipo.setTipo(i.tipo.getTipo());
                    return res;
                }
                
                if (resultado instanceof Errores) {
                    return resultado;
                }
                
            }
        } else {
            if(this.instrucciones_Else != null){
                for(var instruccion : this.instrucciones_Else) {
                    if(instruccion instanceof Declaracion){
                        ((Declaracion) instruccion).setBloque(newTabla.getNombre());
                    }
                    if (instruccion instanceof Break) {
                        return instruccion;
                    }
                    if (instruccion instanceof Continue) {
                        return instruccion;
                    }
                    if( instruccion instanceof Return ){
                        var res = instruccion.interpretar(arbol, newTabla);
                        return res;
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
                    if( resultado instanceof Return ){
                        var res = ((Return) resultado).interpretar(arbol, newTabla);
                        return res;
                    }
                }
            }
        }
        conteo = 0;
        return null;
    }
    
}
