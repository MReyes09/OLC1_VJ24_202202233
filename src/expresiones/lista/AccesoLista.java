
package expresiones.lista;

import abstracto.Instruccion;
import simbolo.Arbol;
import simbolo.Tipo;
import simbolo.tablaSimbolos;

public class AccesoLista extends Instruccion{

    public AccesoLista(Tipo tipo, int linea, int columna) {
        super(tipo, linea, columna);
    }   

    @Override
    public Object interpretar(Arbol arbol, tablaSimbolos tabla) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}
