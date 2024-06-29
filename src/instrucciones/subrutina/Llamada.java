
package instrucciones.subrutina;

import abstracto.Instruccion;
import excepciones.Errores;
import instrucciones.Declaracion;
import java.util.LinkedList;
import simbolo.Arbol;
import simbolo.Tipo;
import simbolo.tablaSimbolos;
import simbolo.tipoDato;

public class Llamada extends Instruccion{
    
    private String id;
    private LinkedList<Instruccion> parametros;

    public Llamada(String id, LinkedList<Instruccion> parametros, int linea, int columna) {
        super(new Tipo(tipoDato.VOID), linea, columna);
        this.id = id;
        this.parametros = parametros;
    }

    @Override
    public Object interpretar(Arbol arbol, tablaSimbolos tabla) {
        var busqueda = arbol.getFuncion(this.id);
        
        if( busqueda == null ){
            return new Errores("SEMANTICO", "La funcion "+ id +" no existe", this.linea, this.columna);
        }
        
        if ( busqueda instanceof Metodo metodo){
            
            var newTabla = new tablaSimbolos(arbol.getTablaGlobal());
            newTabla.setNombre("Llamada Metodo: " + this.id);
            if( metodo.parametros.size() != this.parametros.size() ){
                return new Errores("SEMANTICO", "La cantidad de parametros brindada no conincide ", this.linea, this.columna);
            }
            
            for( int i = 0; i < this.parametros.size(); i++ ){
                //POR CADA PARAMETRO DENTRO DE LA FUNCION O METODO
                var identificador = (String)metodo.parametros.get(i).get("id");
                var valor = this.parametros.get(i);
                var tipo2 = (Tipo)metodo.parametros.get(i).get("tipo");
                var declaracionParametros = new Declaracion(true, identificador, tipo2, this.linea, this.columna);
                
                var resultado = declaracionParametros.interpretar(arbol, newTabla);
                
                if( resultado instanceof Errores ){
                    return resultado;
                }
                
                var valorInterpretadp = valor.interpretar(arbol, tabla);
                if( valorInterpretadp instanceof Errores ){
                    return valorInterpretadp;
                }
                
                var variable = newTabla.getVariable(identificador);
                if( variable == null){
                    return new Errores("SEMANTICO", "Error declaracion de parametros ", this.linea, this.columna);
                }
                
                if( variable.getTipo().getTipo() != valor.tipo.getTipo() ){
                    return new Errores("SEMANTICO", "Error en tipo de parametros", this.linea, this.columna);
                }
                
                variable.setValor(valorInterpretadp);
            }
            
            //INTERPRETAMOS LA FUNCION
            var resultadoFuncion = metodo.interpretar(arbol, newTabla);
            if( resultadoFuncion instanceof Errores){
                return resultadoFuncion;
            }
        }else if( busqueda instanceof Funcion funcion){
            
            var newTabla = new tablaSimbolos(arbol.getTablaGlobal());
            newTabla.setNombre("Llamada Funcion: " + this.id);
            if( funcion.parametros.size() != this.parametros.size() ){
                return new Errores("SEMANTICO", "La cantidad de parametros brindada no conincide ", this.linea, this.columna);
            }
            
            for( int i = 0; i < this.parametros.size(); i++ ){
                //POR CADA PARAMETRO DENTRO DE LA FUNCION O METODO
                var identificador = (String)funcion.parametros.get(i).get("id");
                var valor = this.parametros.get(i);
                var tipo2 = (Tipo)funcion.parametros.get(i).get("tipo");
                var declaracionParametros = new Declaracion(true, identificador, tipo2, this.linea, this.columna);
                
                var resultado = declaracionParametros.interpretar(arbol, newTabla);
                
                if( resultado instanceof Errores ){
                    return resultado;
                }
                
                var valorInterpretadp = valor.interpretar(arbol, tabla);
                if( valorInterpretadp instanceof Errores ){
                    return valorInterpretadp;
                }
                
                var variable = newTabla.getVariable(identificador);
                if( variable == null){
                    return new Errores("SEMANTICO", "Error declaracion de parametros ", this.linea, this.columna);
                }
                
                if( variable.getTipo().getTipo() != valor.tipo.getTipo() ){
                    return new Errores("SEMANTICO", "Error en tipo de parametros", this.linea, this.columna);
                }
                
                variable.setValor(valorInterpretadp);
            }
            //INTERPRETAMOS LA FUNCION
            var resultadoFuncion = funcion.interpretar(arbol, newTabla);
            if( resultadoFuncion instanceof Errores){
                return resultadoFuncion;
            }else if ( resultadoFuncion != null ){
                this.tipo.setTipo(funcion.tipo.getTipo());
                return resultadoFuncion;
            }
            
        }
        return null;
    }
    
}
