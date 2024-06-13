
package expresiones.logicas;

import abstracto.Instruccion;
import excepciones.Errores;
import simbolo.Arbol;
import simbolo.Tipo;
import simbolo.tablaSimbolos;
import simbolo.tipoDato;

public class Logicas extends Instruccion{

    private Instruccion operando1;
    private Instruccion operando2;
    private Instruccion operadorUnico;
    private OperadoresLogicos operacion;

    public Logicas(Instruccion operando1, Instruccion operando2, OperadoresLogicos operacion, int linea, int columna) {
        super(new Tipo(tipoDato.BOOLEANO), linea, columna);
        this.operando1 = operando1;
        this.operando2 = operando2;
        this.operacion = operacion;
    }

    public Logicas(Instruccion operadorUnico, OperadoresLogicos operacion, int linea, int columna) {
        super(new Tipo(tipoDato.BOOLEANO), linea, columna);
        this.operadorUnico = operadorUnico;
        this.operacion = operacion;
    }
        
    
    @Override
    public Object interpretar(Arbol arbol, tablaSimbolos tabla) {
        
        Object opIzq = null, opDer = null, Unico = null;
        
        if (this.operadorUnico != null) {
            Unico = this.operadorUnico.interpretar(arbol, tabla);
            //Validacion de errores
            if (Unico instanceof Errores) {
                return Unico;
            }
            
        }else{        
            opIzq = this.operando1.interpretar(arbol, tabla);
            //Validacion de errores 
            if(opIzq instanceof Errores){
                return opIzq;
            }
            opDer = this.operando2.interpretar(arbol, tabla);
            if(opDer instanceof Errores){
                return opDer;
            }
        }
        
        return switch (operacion) {
            case OR ->
                this.or_logic(opIzq, opDer);
            case AND ->
                this.and_logic(opIzq, opDer);
            case XOR ->
                this.xor_logic(opIzq, opDer);
            case NOT ->
                this.not_logic(Unico);
            default ->
                new Errores("SEMANTICO", "Operador invalido", this.linea, this.columna);
        };
        
    }
    
    public Object or_logic(Object op1, Object op2) {
    
        var tipo1 = this.operando1.tipo.getTipo();
        var tipo2 = this.operando2.tipo.getTipo();
        
        switch (tipo1) {
            case tipoDato.BOOLEANO -> {
                
                switch (tipo2) {
                    case tipoDato.BOOLEANO -> {
                        return (boolean)op1 || (boolean)op2;
                    }
                    default -> {
                        String descripcion = "Operador relacional || erroneo entre tipo: " + tipo1 + " tipo: " + tipo2;
                        return new Errores("SEMANTICO", descripcion, this.linea, this.columna);
                    }
                }
                
            }
            default -> {
                String descripcion = "Operador relacional || erroneo entre tipo: " + tipo1 + " tipo: " + tipo2;
                return new Errores("SEMANTICO", descripcion, this.linea, this.columna);
            }
        }
    
    }
    
    public Object and_logic(Object op1, Object op2) {
    
        var tipo1 = this.operando1.tipo.getTipo();
        var tipo2 = this.operando2.tipo.getTipo();
        
        switch (tipo1) {
            case tipoDato.BOOLEANO -> {
                
                switch (tipo2) {
                    case tipoDato.BOOLEANO -> {
                        return (boolean)op1 && (boolean)op2;
                    }
                    default -> {
                        String descripcion = "Operador relacional && erroneo entre tipo: " + tipo1 + " tipo: " + tipo2;
                        return new Errores("SEMANTICO", descripcion, this.linea, this.columna);
                    }
                }
                
            }
            default -> {
                String descripcion = "Operador relacional && erroneo entre tipo: " + tipo1 + " tipo: " + tipo2;
                return new Errores("SEMANTICO", descripcion, this.linea, this.columna);
            }
        }
    
    }
    
    public Object xor_logic(Object op1, Object op2) {
    
        var tipo1 = this.operando1.tipo.getTipo();
        var tipo2 = this.operando2.tipo.getTipo();
        
        switch (tipo1) {
            case tipoDato.BOOLEANO -> {
                
                switch (tipo2) {
                    case tipoDato.BOOLEANO -> {
                        return (boolean)op1 ^ (boolean)op2;
                    }
                    default -> {
                        String descripcion = "Operador relacional ^ erroneo entre tipo: " + tipo1 + " tipo: " + tipo2;
                        return new Errores("SEMANTICO", descripcion, this.linea, this.columna);
                    }
                }
                
            }
            default -> {
                String descripcion = "Operador relacional ^ erroneo entre tipo: " + tipo1 + " tipo: " + tipo2;
                return new Errores("SEMANTICO", descripcion, this.linea, this.columna);
            }
        }
    
    }
    
    public Object not_logic(Object op1) {
    
        var tipo1 = this.operadorUnico.tipo.getTipo();
        
        switch (tipo1) {
            case tipoDato.BOOLEANO -> {
                return !(boolean)op1;                                
            }
            default -> {
                String descripcion = "Operador relacional ! erroneo entre tipo: " + tipo1;
                return new Errores("SEMANTICO", descripcion, this.linea, this.columna);
            }
        }
    
    }
    
}
