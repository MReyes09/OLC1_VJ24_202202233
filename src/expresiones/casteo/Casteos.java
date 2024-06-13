
package expresiones.casteo;

import abstracto.Instruccion;
import excepciones.Errores;
import simbolo.Arbol;
import simbolo.Tipo;
import simbolo.tablaSimbolos;
import simbolo.tipoDato;

public class Casteos extends Instruccion{

    private Instruccion operando1;
    private OperadoresCasteo operacion;

    public Casteos(Instruccion operando1, OperadoresCasteo operacion, int linea, int columna) {
        super(new Tipo(tipoDato.ENTERO), linea, columna);
        this.operando1 = operando1;
        this.operacion = operacion;
    }
    
    @Override
    public Object interpretar(Arbol arbol, tablaSimbolos tabla) {
        
        Object opDer = null;
        
        opDer = this.operando1.interpretar(arbol, tabla);
        if(opDer instanceof Errores){
            return opDer;
        }
        
        return switch (operacion) {
            case INT ->
                this.int_Casteo(opDer);
            case DOUBLE ->
                this.double_Casteo(opDer);
            case CHAR ->
                this.char_Casteo(opDer);
            default ->
                new Errores("SEMANTICO", "Operador invalido", this.linea, this.columna);
        };
    }
    
    public Object int_Casteo(Object op1) {
    
        var tipo1 = this.operando1.tipo.getTipo();
        
        switch (tipo1) {
            case tipoDato.DECIMAL -> {
                double valor1 = (double)op1;
                return (int)valor1;
            }
            case tipoDato.CARACTER -> {
                int valor1 = (char)op1;
                return valor1;
            }
            default -> {
                String descripcion = "Error al castear: " + op1 + " a " + this.operacion;
                return new Errores("SEMANTICO", descripcion, this.linea, this.columna);
            }
        }
    
    }
    
    public Object double_Casteo(Object op1) {
        
        var tipo1 = this.operando1.tipo.getTipo();
        
        switch (tipo1) {
            case tipoDato.ENTERO -> {
                this.tipo.setTipo(tipoDato.DECIMAL);
                int valor1 = (int)op1;
                return (double)valor1;
            }
            default -> {
                String descripcion = "Error al castear: " + op1 + " a " + this.operacion;
                return new Errores("SEMANTICO", descripcion, this.linea, this.columna);
            }
        }
        
    }
 
    public Object char_Casteo(Object op1) {
        
        var tipo1 = this.operando1.tipo.getTipo();
        
        switch (tipo1) {
            case tipoDato.ENTERO -> {
                this.tipo.setTipo(tipoDato.CARACTER);
                int valor1 = (int)op1;
                return (char)valor1;
            }
            case tipoDato.DECIMAL -> {
                this.tipo.setTipo(tipoDato.CARACTER);
                double valor1 = (double)op1;
                return (char)valor1;                
            }
            default -> {
                String descripcion = "Error al castear: " + op1 + " a " + this.operacion;
                return new Errores("SEMANTICO", descripcion, this.linea, this.columna);
            }
        }
        
    }
    
}
