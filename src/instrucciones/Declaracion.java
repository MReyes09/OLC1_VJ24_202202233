
package instrucciones;

import abstracto.Instruccion;
import excepciones.Errores;
import simbolo.Arbol;
import simbolo.Simbolo;
import simbolo.Tipo;
import simbolo.tablaSimbolos;

public class Declaracion extends Instruccion{

    public String identificador;
    public Instruccion valor;
    public boolean mutabilidad;

    public Declaracion(boolean mutabilidad, String identificador, Instruccion valor, Tipo tipo, int linea, int columna) {
        super(tipo, linea, columna);
        this.identificador = identificador;
        this.valor = valor;
        this.mutabilidad = mutabilidad;
    }
    
    public Declaracion(boolean mutabilidad, String identificador, Tipo tipo, int linea, int columna) {
        super(tipo, linea, columna);
        this.identificador = identificador;
        this.mutabilidad = mutabilidad;
    }   
    
    
    @Override
    public Object interpretar(Arbol arbol, tablaSimbolos tabla) {
        if (this.valor != null) {
         
            var valorInterpretado = this.valor.interpretar(arbol, tabla);
            if(valorInterpretado instanceof Errores) {
                return valorInterpretado;
            }

            if(this.valor.tipo.getTipo() != this.tipo.getTipo()) {
                String descripcion = "Se intenta asignar el tipo: " + this.valor.tipo.getTipo() 
                        + "A una variable de tipo: " + this.tipo.getTipo(); 
                return new Errores("SEMANTICO", "Tipos Erroneos", this.linea, this.columna);
            }

            Simbolo s = new Simbolo(this.tipo, this.identificador, valorInterpretado, this.mutabilidad);

            boolean creacion = tabla.setVariable(s);
            if(!creacion) {
                return new Errores("SEMANTICO", "Variable ya existente", this.linea, this.columna);
            }
            
        } else {
            Simbolo s;
            switch (this.tipo.getTipo()) {
                case ENTERO -> {
                    s = new Simbolo(this.tipo, this.identificador, 0, this.mutabilidad);
                }
                case DECIMAL -> {
                    s = new Simbolo(this.tipo, identificador, 0.0, this.mutabilidad);
                }
                case BOOLEANO -> {
                    s = new Simbolo(this.tipo, identificador, true, this.mutabilidad);
                }
                case CARACTER -> {
                    s = new Simbolo(this.tipo, identificador, '\u0000', this.mutabilidad);
                }
                case CADENA -> {
                    s = new Simbolo(this.tipo, identificador, "", this.mutabilidad);
                }
                default -> {
                    String texto = "Asignacion de tipo: " + this.tipo.getTipo() + " invalido";
                    return new Errores("SEMANTICO", texto, this.linea, this.columna);
                }
            };
            
            boolean creacion = tabla.setVariable(s);
            if(!creacion) {
                return new Errores("SEMANTICO", "Variable ya existente", this.linea, this.columna);
            }
            
        }
        
        return null;
    }
    
}
