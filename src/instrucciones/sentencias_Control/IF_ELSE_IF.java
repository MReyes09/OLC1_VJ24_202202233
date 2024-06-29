
package instrucciones.sentencias_Control;

import abstracto.Instruccion;
import excepciones.Errores;
import instrucciones.Declaracion;
import instrucciones.sentencias_Transferencia.Break;
import instrucciones.sentencias_Transferencia.Continue;
import instrucciones.subrutina.Return;
import java.util.LinkedList;
import simbolo.Arbol;
import simbolo.Tipo;
import simbolo.tablaSimbolos;
import simbolo.tipoDato;

public class IF_ELSE_IF extends Instruccion{
    
    private Instruccion condicion;
    private LinkedList<Instruccion> instrucciones;
    private Instruccion instruccion_IF;
    private static int conteo = 0;

    public IF_ELSE_IF(Instruccion condicion, LinkedList<Instruccion> instrucciones, Instruccion instrucciones_IF, int linea, int columna) {
        super(new Tipo(tipoDato.VOID), linea, columna);
        this.condicion = condicion;
        this.instrucciones = instrucciones;
        this.instruccion_IF = instrucciones_IF;
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
        newTabla.setNombre("ELSE_If_" + conteo);
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
                    this.tipo.setTipo(((Return)resultado).tipo.getTipo());
                    return res;
                }
            }
        }else{
            if(this.instruccion_IF != null){
                
                var resultado = this.instruccion_IF.interpretar(arbol, newTabla);
                
                if( resultado != null ){
                    return resultado;
                }
                
                if (resultado instanceof Errores) {
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
                    this.tipo.setTipo(((Return)resultado).tipo.getTipo());
                    return res;
                }
            }
        }
        
        return null;
    }
}
