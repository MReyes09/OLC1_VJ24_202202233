
package instrucciones.sentencias_Control;

import abstracto.Instruccion;
import excepciones.Errores;
import java.util.LinkedList;
import simbolo.Arbol;
import simbolo.Tipo;
import simbolo.tablaSimbolos;
import simbolo.tipoDato;

public class If extends Instruccion{

    private Instruccion condicion;
    private LinkedList<Instruccion> instrucciones;

    public If(Instruccion condicion, LinkedList<Instruccion> instrucciones, int linea, int col) {
        super(new Tipo(tipoDato.VOID), linea, col);
        this.condicion = condicion;
        this.instrucciones = instrucciones;
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
                var resultado = i.interpretar(arbol, newTabla);
                /*
                    Manejo de errores
                */
            }
        }
        return null;
    }
    
}
