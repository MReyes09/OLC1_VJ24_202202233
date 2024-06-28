
package expresiones.variable;

import abstracto.Instruccion;
import excepciones.Errores;
import java.util.ArrayList;
import simbolo.Arbol;
import simbolo.Tipo;
import simbolo.tablaSimbolos;
import simbolo.tipoDato;

public class AccesoVar extends Instruccion{
    
    private String id;
    public boolean posible_toString;

    public AccesoVar(String id, int linea, int col) {
        super(new Tipo(tipoDato.VOID), linea, col);
        this.id = id;
        this.posible_toString = false;
    }

    @Override
    public Object interpretar(Arbol arbol, tablaSimbolos tabla) {
        var valor = tabla.getVariable(this.id);
        if (valor == null) {
            return new Errores("SEMANTICA", "Variable " + this.id + " no existente",
                    this.linea, this.columna);
        }
        this.tipo.setTipo(valor.getTipo().getTipo());
        if( this.posible_toString ){
            ArrayList<Object> resultados = new ArrayList<>();
            resultados.add(valor.getValor());
            resultados.add(id);
            return resultados;
        }
        
        return valor.getValor();
    }
}
