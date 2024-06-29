
package instrucciones.sentencias_Ciclicas;

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

public class While extends Instruccion{
    
    private Instruccion condicion;
    private LinkedList<Instruccion> instrucciones;
    private static int conteo =0;

    public While(Instruccion condicion, LinkedList<Instruccion> instrucciones, int linea, int columna) {
        super(new Tipo(tipoDato.VOID), linea, columna);
        this.condicion = condicion;
        this.instrucciones = instrucciones;
    }

    @Override
    public Object interpretar(Arbol arbol, tablaSimbolos tabla) {
        conteo++;
        var newTabla = new tablaSimbolos(tabla);
        newTabla.setNombre("While_" + conteo);
        var cond = this.condicion.interpretar(arbol, tabla);
        if( cond instanceof Errores ) {
            return cond;
        }
        
        if (this.condicion.tipo.getTipo() != tipoDato.BOOLEANO) {
            return new Errores("SEMANTICO", "La condicion debe ser bool",
                    this.linea, this.columna);
        }
        
        while ((boolean) this.condicion.interpretar(arbol, newTabla)) {
            //nuevo entorno
            var newTabla2 = new tablaSimbolos(newTabla);

            //ejecutar instrucciones
            for (var i : this.instrucciones) {
                if(i instanceof Declaracion){
                    ((Declaracion) i).setBloque(newTabla.getNombre());
                }
                if (i == null) {
                    return null;
                }
                if (i instanceof Break) {
                    return null;
                }
                
                if (i instanceof Continue){
                    break;
                }
                
                if(i instanceof Return){
                    var resInst = i.interpretar(arbol, newTabla2);
                    this.tipo.setTipo(i.tipo.getTipo());
                    return resInst;
                }
                
                var resIns = i.interpretar(arbol, newTabla2);
                if (resIns instanceof Break) {
                    return null;
                }
                if (resIns instanceof Errores) {
                    return resIns;
                }
                if (resIns instanceof Continue){
                    break;
                }
                
                if(resIns != null) {
                    this.tipo.setTipo(i.tipo.getTipo());
                    return resIns;
                }
            }
        }
        
        return null;
    }
    
}
