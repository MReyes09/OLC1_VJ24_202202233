
package instrucciones;

import abstracto.Instruccion;
import excepciones.Errores;
import expresiones.variable.OperadoresVariable;
import simbolo.Arbol;
import simbolo.Tipo;
import simbolo.tablaSimbolos;
import simbolo.tipoDato;

public class AsignacionVar extends Instruccion{
    private String id;
    private Instruccion exp;
    private OperadoresVariable operacion;

    public AsignacionVar(String id, Instruccion exp, int linea, int col) {
        super(new Tipo(tipoDato.VOID), linea, col);
        this.id = id;
        this.exp = exp;
    }
    
    public AsignacionVar(String id, OperadoresVariable operacion, int linea, int col) {
        super(new Tipo(tipoDato.VOID), linea, col);
        this.id = id;
        this.operacion = operacion;
    }

    @Override
    public Object interpretar(Arbol arbol, tablaSimbolos tabla) {
        //variable exista
        var variable = tabla.getVariable(id);
        
        if (variable == null) {
            return new Errores("SEMANTICO", "Variable" + id + " no exitente", this.linea, this.columna);
        }
        
        if(variable.getMutabilidad()) {

            // interpretar el nuevo valor a asignar
            if(this.exp != null){
                var newValor = this.exp.interpretar(arbol, tabla);
                if (newValor instanceof Errores) {
                    return newValor;
                }

                //validar tipos
                if (variable.getTipo().getTipo() != this.exp.tipo.getTipo()) {
                    return new Errores("SEMANTICO", "Tipos erroneos en asignacion", this.linea, this.columna);
                }
                variable.setValor(newValor);
            }else{
                switch(operacion){
                    case INCREMENTO -> {
                        
                        switch (variable.getTipo().getTipo()) {
                            case ENTERO -> {
                                int valor = (int)variable.getValor();
                                valor++;
                                variable.setValor(valor);
                            }
                            case DECIMAL -> {
                                double valor = (double)variable.getValor();
                                valor++;
                                variable.setValor(valor);
                            }
                            default -> {
                                String descripcion = "Incremento incorrecto para: \'"+ id + "\' tiene tipo: " + variable.getTipo().getTipo();
                                return new Errores("SEMANTICO", descripcion, this.linea, this.columna);
                            }
                        }
                        
                    }
                    case DECREMENTO -> {
                        
                        switch (variable.getTipo().getTipo()) {
                            case ENTERO -> {
                                int valor = (int)variable.getValor();
                                valor --;
                                variable.setValor(valor);
                            }
                            case DECIMAL -> {
                                double valor = (double)variable.getValor();
                                valor--;
                                variable.setValor(valor);
                            }
                            default -> {
                                String descripcion = "Incremento incorrecto para: \'"+ id + "\' tiene tipo: " + variable.getTipo().getTipo();
                                return new Errores("SEMANTICO", descripcion, this.linea, this.columna);
                            }
                        }
                        
                    }
                    default -> {
                        String descripcion = "operador incorrecto para: \'"+ id + "\' Operador: " + operacion;
                        return new Errores("SEMANTICO", descripcion, this.linea, this.columna);
                    }
                }
            }            
            
        }else{
            String descripcion = "La variable: \'" + id + "\' es de tipo CONST";
            return new Errores("SEMANTICO", descripcion, this.linea, this.columna);
        }
        
        return null;
    }
}
