
package instrucciones;

import abstracto.Instruccion;
import excepciones.Errores;
import expresiones.variable.AccesoVar;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import simbolo.Arbol;
import simbolo.Simbolo;
import simbolo.Tipo;
import simbolo.tablaSimbolos;
import simbolo.tipoDato;

public class ToString extends Instruccion{
    
    private Instruccion expresion;

    public ToString(Instruccion expresion, int linea, int columna) {
        super(new Tipo(tipoDato.CADENA), linea, columna);
        this.expresion = expresion;
    }

    @Override
    public Object interpretar(Arbol arbol, tablaSimbolos tabla) {
        System.out.println("");
        if( this.expresion instanceof AccesoVar ){
            ((AccesoVar)this.expresion).posible_toString = true;
        }
        var resultado_Interpretado = this.expresion.interpretar(arbol, tabla);
        
        Object valor_Interpretado = null;
        String id = "";
        
        if( resultado_Interpretado instanceof ArrayList ){
            valor_Interpretado = ((ArrayList) resultado_Interpretado).get(0);
            id = ((ArrayList) resultado_Interpretado).get(1).toString();
        }else{
            valor_Interpretado = resultado_Interpretado;
        }
        
        if( valor_Interpretado == null ){
            return new Errores("SEMANTICO", "La expresion ha tenido un error" , this.linea, this.columna);
        }
        tipoDato tipo_Variable = this.expresion.tipo.getTipo();
        if( tipo_Variable == tipoDato.VOID || tipo_Variable == tipoDato.CADENA ){
            return new Errores("SEMANTICO", "La expresion no tiene el tipo apropiado para la funcion toString", this.linea, this.columna);
        }
        String resultado = "";
        if( tipo_Variable != tipoDato.STRUCT ){
            
            resultado = valor_Interpretado.toString();
            
        }else{
            String toString_Struct = id + " { ";

            HashMap<String, Object> mi_Tablita = ((tablaSimbolos)valor_Interpretado).getTablaActual();
            StringBuilder sb = new StringBuilder(toString_Struct);

            // Usar un iterator para recorrer el HashMap
            Iterator<Map.Entry<String, Object>> iterator = mi_Tablita.entrySet().iterator();
            while (iterator.hasNext()) {
                Map.Entry<String, Object> entry = iterator.next();
                sb.append(entry.getKey()).append(": ");
                Simbolo valor_Struct = (Simbolo) entry.getValue();
                if (valor_Struct.getValor() instanceof tablaSimbolos) {
                    sb.append("{ ");
                    tablaSimbolos tabla_Valor = (tablaSimbolos) valor_Struct.getValor();

                    HashMap<String, Object> mi_Tablita2 = tabla_Valor.getTablaActual();

                    // Usar otro iterator para recorrer el segundo HashMap
                    Iterator<Map.Entry<String, Object>> iterator2 = mi_Tablita2.entrySet().iterator();
                    while (iterator2.hasNext()) {
                        Map.Entry<String, Object> entry2 = iterator2.next();
                        Simbolo valor_Struct2 = (Simbolo) entry2.getValue();
                        sb.append(entry2.getKey()).append(": ").append(valor_Struct2.getValor().toString());
                        if (iterator2.hasNext()) {
                            sb.append(", ");
                        }
                    }
                    sb.append("}");
                } else {
                    sb.append(valor_Struct.getValor().toString());
                }
                if (iterator.hasNext()) {
                    sb.append(", ");
                }
            }
            sb.append(" }");

            toString_Struct = sb.toString();

            return toString_Struct;

        }
        
        return resultado;
    }
}
