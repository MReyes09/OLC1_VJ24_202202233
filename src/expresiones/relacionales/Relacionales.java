
package expresiones.relacionales;
import abstracto.Instruccion;
import excepciones.Errores;
import simbolo.Arbol;
import simbolo.Tipo;
import simbolo.tablaSimbolos;
import simbolo.tipoDato;

public class Relacionales extends Instruccion{

    private Instruccion operando1;
    private Instruccion operando2;
    private OperadoresRelacionales operacion;

    public Relacionales(Instruccion operador1, Instruccion operador2, OperadoresRelacionales operacion, int linea, int columna) {
        super(new Tipo(tipoDato.BOOLEANO), linea, columna);
        this.operando1 = operador1;
        this.operando2 = operador2;
        this.operacion = operacion;
    }    
    
    @Override
    public Object interpretar(Arbol arbol, tablaSimbolos tabla) {
        
        Object opIzq = null, opDer = null;
        
        opIzq = this.operando1.interpretar(arbol, tabla);
        //Validacion de errores 
        if(opIzq instanceof Errores){
            return opIzq;
        }
        opDer = this.operando2.interpretar(arbol, tabla);
        if(opDer instanceof Errores){
            return opDer;
        }
        
        return switch (operacion) {
            case IGUAL_QUE ->
                this.igual(opIzq, opDer);
            case DIFERENTE ->
                this.diferente_De(opIzq, opDer);
            case MENOR_QUE ->
                this.menor_Que(opIzq, opDer);
            case MENOR_IGUAL_QUE ->
                this.menor_Igual_Que(opIzq, opDer);
            case MAYOR_QUE -> 
                this.mayor_Que(opIzq, opDer);
            case MAYOR_IGUAL_QUE ->
                this.mayor_Igual_Que(opIzq, opDer);
            default -> 
                new Errores("SEMANTICO", "Operador invalido", this.linea, this.columna);
        };
    }
    
    public Object igual(Object op1, Object op2){
    
        var tipo1 = this.operando1.tipo.getTipo();
        var tipo2 = this.operando2.tipo.getTipo();
        
        switch (tipo1) {
            case tipoDato.ENTERO -> {
                
                switch (tipo2) {
                    case tipoDato.ENTERO -> {
                        return (int)op1 == (int)op2;
                    }
                    case tipoDato.DECIMAL -> {
                        return (int)op1 == (double)op2;
                    }
                    case tipoDato.CARACTER -> {
                        int valor_2 = (char)op2;
                        return (int)op1 == valor_2;
                    }
                    default -> {
                        String descripcion = "Operador relacional == erroneo entre tipo: " + tipo1 + " tipo: " + tipo2;
                        return new Errores("SEMANTICO", descripcion, this.linea, this.columna);
                    }
                }
                
            }
            case tipoDato.DECIMAL -> {
                
                switch (tipo2) {
                    case tipoDato.ENTERO -> {
                        return (double)op1 == (int)op2;
                    }
                    case tipoDato.DECIMAL -> {
                        return (double)op1 == (double)op2;
                    }
                    case tipoDato.CARACTER -> {
                        int valor_2 = (char)op2;
                        return (double)op1 == valor_2;
                    }
                    default -> {
                        String descripcion = "Operador relacional == erroneo entre tipo: " + tipo1 + " tipo: " + tipo2;
                        return new Errores("SEMANTICO", descripcion, this.linea, this.columna);
                    }
                }
                
            }
            case tipoDato.BOOLEANO -> {
                
                switch (tipo2) {
                    case tipoDato.BOOLEANO -> {
                        return (boolean)op1 == (boolean)op2;
                    }
                    default -> {
                        String descripcion = "Operador relacional == erroneo entre tipo: " + tipo1 + " tipo: " + tipo2;
                        return new Errores("SEMANTICO", descripcion, this.linea, this.columna);
                    }
                }
                
            }
            case tipoDato.CARACTER -> {
                int valor1 = (char)op1;
                switch (tipo2) {
                    case tipoDato.ENTERO -> {
                        return valor1 == (int)op2;
                    }
                    case tipoDato.DECIMAL -> {
                        return valor1 == (double)op2;
                    }
                    case tipoDato.CARACTER -> {
                        int valor_2 = (char)op2;
                        return valor1 == valor_2;
                    }
                    default -> {
                        String descripcion = "Operador relacional == erroneo entre tipo: " + tipo1 + " tipo: " + tipo2;
                        return new Errores("SEMANTICO", descripcion, this.linea, this.columna);
                    }
                }
                
            }
            case tipoDato.CADENA -> {
                
                switch (tipo2) {
                    case tipoDato.CADENA -> {
                        return op1.toString().equals(op2.toString());
                    }
                    default -> {
                        String descripcion = "Operador relacional == erroneo entre tipo: " + tipo1 + " tipo: " + tipo2;
                        return new Errores("SEMANTICO", descripcion, this.linea, this.columna);
                    }
                }
                
            }
            default -> {
                String descripcion = "Operador relacional erroneo == "+ "entre tipo: " + tipo1 + " tipo: " + tipo2;
                return new Errores("SEMANTICO", descripcion, this.linea, this.columna);
            }
        }
        
    }
    
    public Object diferente_De(Object op1, Object op2){
    
        var tipo1 = this.operando1.tipo.getTipo();
        var tipo2 = this.operando2.tipo.getTipo();
        
        switch (tipo1) {
            case tipoDato.ENTERO -> {
                
                switch (tipo2) {
                    case tipoDato.ENTERO -> {
                        return (int)op1 != (int)op2;
                    }
                    case tipoDato.DECIMAL -> {
                        return (int)op1 != (double)op2;
                    }
                    case tipoDato.CARACTER -> {
                        int valor_2 = (char)op2;
                        return (int)op1 != valor_2;
                    }
                    default -> {
                        String descripcion = "Operador relacional != erroneo entre tipo: " + tipo1 + " tipo: " + tipo2;
                        return new Errores("SEMANTICO", descripcion, this.linea, this.columna);
                    }
                }
                
            }
            case tipoDato.DECIMAL -> {
                
                switch (tipo2) {
                    case tipoDato.ENTERO -> {
                        return (double)op1 != (int)op2;
                    }
                    case tipoDato.DECIMAL -> {
                        return (double)op1 != (double)op2;
                    }
                    case tipoDato.CARACTER -> {
                        int valor_2 = (char)op2;
                        return (double)op1 != valor_2;
                    }
                    default -> {
                        String descripcion = "Operador relacional != erroneo entre tipo: " + tipo1 + " tipo: " + tipo2;
                        return new Errores("SEMANTICO", descripcion, this.linea, this.columna);
                    }
                }
                
            }
            case tipoDato.BOOLEANO -> {
                
                switch (tipo2) {
                    case tipoDato.BOOLEANO -> {
                        return (boolean)op1 != (boolean)op2;
                    }
                    default -> {
                        String descripcion = "Operador relacional != erroneo entre tipo: " + tipo1 + " tipo: " + tipo2;
                        return new Errores("SEMANTICO", descripcion, this.linea, this.columna);
                    }
                }
                
            }
            case tipoDato.CARACTER -> {
                int valor1 = (char)op1;
                switch (tipo2) {
                    case tipoDato.ENTERO -> {
                        return valor1 != (int)op2;
                    }
                    case tipoDato.DECIMAL -> {
                        return valor1 != (double)op2;
                    }
                    case tipoDato.CARACTER -> {
                        int valor_2 = (char)op2;
                        return valor1 != valor_2;
                    }
                    default -> {
                        String descripcion = "Operador relacional != erroneo entre tipo: " + tipo1 + " tipo: " + tipo2;
                        return new Errores("SEMANTICO", descripcion, this.linea, this.columna);
                    }
                }
                
            }
            case tipoDato.CADENA -> {
                
                switch (tipo2) {
                    case tipoDato.CADENA -> {
                        return op1.toString().equals(op2.toString());
                    }
                    default -> {
                        String descripcion = "Operador relacional != erroneo entre tipo: " + tipo1 + " tipo: " + tipo2;
                        return new Errores("SEMANTICO", descripcion, this.linea, this.columna);
                    }
                }
                
            }
            default -> {
                String descripcion = "Operador relacional != erroneo entre tipo: " + tipo1 + " tipo: " + tipo2;
                return new Errores("SEMANTICO", descripcion, this.linea, this.columna);
            }
        }
        
    }
    
    public Object menor_Que(Object op1, Object op2){
    
        var tipo1 = this.operando1.tipo.getTipo();
        var tipo2 = this.operando2.tipo.getTipo();
        
        switch (tipo1) {
            case tipoDato.ENTERO -> {
                
                switch (tipo2) {
                    case tipoDato.ENTERO -> {
                        return (int)op1 < (int)op2;
                    }
                    case tipoDato.DECIMAL -> {
                        return (int)op1 < (double)op2;
                    }
                    case tipoDato.CARACTER -> {
                        int valor_2 = (char)op2;
                        return (int)op1 < valor_2;
                    }
                    default -> {
                        String descripcion = "Operador relacional < erroneo entre tipo: " + tipo1 + " tipo: " + tipo2;
                        return new Errores("SEMANTICO", descripcion, this.linea, this.columna);
                    }
                }
                
            }
            case tipoDato.DECIMAL -> {
                
                switch (tipo2) {
                    case tipoDato.ENTERO -> {
                        return (double)op1 < (int)op2;
                    }
                    case tipoDato.DECIMAL -> {
                        return (double)op1 < (double)op2;
                    }
                    case tipoDato.CARACTER -> {
                        int valor_2 = (char)op2;
                        return (double)op1 < valor_2;
                    }
                    default -> {
                        String descripcion = "Operador relacional < erroneo entre tipo: " + tipo1 + " tipo: " + tipo2;
                        return new Errores("SEMANTICO", descripcion, this.linea, this.columna);
                    }
                }
                
            }
            case tipoDato.BOOLEANO -> {
                
                switch (tipo2) {
                    case tipoDato.BOOLEANO -> {
                        int valor1 = (boolean)op1 ? 0 : 1;
                        int valor2 = (boolean)op2 ? 0 : 1;
                        return valor1 < valor2;
                    }
                    default -> {
                        String descripcion = "Operador relacional < erroneo entre tipo: " + tipo1 + " tipo: " + tipo2;
                        return new Errores("SEMANTICO", descripcion, this.linea, this.columna);
                    }
                }
                
            }
            case tipoDato.CARACTER -> {
                int valor1 = (char)op1;
                switch (tipo2) {
                    case tipoDato.ENTERO -> {
                        return valor1 < (int)op2;
                    }
                    case tipoDato.DECIMAL -> {
                        return valor1 < (double)op2;
                    }
                    case tipoDato.CARACTER -> {
                        int valor_2 = (char)op2;
                        return valor1 < valor_2;
                    }
                    default -> {
                        String descripcion = "Operador relacional < erroneo entre tipo: " + tipo1 + " tipo: " + tipo2;
                        return new Errores("SEMANTICO", descripcion, this.linea, this.columna);
                    }
                }
                
            }
            default -> {
                String descripcion = "Operador relacional < erroneo entre tipo: " + tipo1 + " tipo: " + tipo2;
                return new Errores("SEMANTICO", descripcion, this.linea, this.columna);
            }
        }
        
    }
    
    public Object menor_Igual_Que(Object op1, Object op2){
    
        var tipo1 = this.operando1.tipo.getTipo();
        var tipo2 = this.operando2.tipo.getTipo();
        
        switch (tipo1) {
            case tipoDato.ENTERO -> {
                
                switch (tipo2) {
                    case tipoDato.ENTERO -> {
                        return (int)op1 <= (int)op2;
                    }
                    case tipoDato.DECIMAL -> {
                        return (int)op1 <= (double)op2;
                    }
                    case tipoDato.CARACTER -> {
                        int valor_2 = (char)op2;
                        return (int)op1 <= valor_2;
                    }
                    default -> {
                        String descripcion = "Operador relacional <= erroneo entre tipo: " + tipo1 + " tipo: " + tipo2;
                        return new Errores("SEMANTICO", descripcion, this.linea, this.columna);
                    }
                }
                
            }
            case tipoDato.DECIMAL -> {
                
                switch (tipo2) {
                    case tipoDato.ENTERO -> {
                        return (double)op1 <= (int)op2;
                    }
                    case tipoDato.DECIMAL -> {
                        return (double)op1 <= (double)op2;
                    }
                    case tipoDato.CARACTER -> {
                        int valor_2 = (char)op2;
                        return (double)op1 <= valor_2;
                    }
                    default -> {
                        String descripcion = "Operador relacional <= erroneo entre tipo: " + tipo1 + " tipo: " + tipo2;
                        return new Errores("SEMANTICO", descripcion, this.linea, this.columna);
                    }
                }
                
            }
            case tipoDato.BOOLEANO -> {
                
                switch (tipo2) {
                    case tipoDato.BOOLEANO -> {
                        int valor1 = (boolean)op1 ? 0 : 1;
                        int valor2 = (boolean)op2 ? 0 : 1;
                        return valor1 <= valor2;
                    }
                    default -> {
                        String descripcion = "Operador relacional <= erroneo entre tipo: " + tipo1 + " tipo: " + tipo2;
                        return new Errores("SEMANTICO", descripcion, this.linea, this.columna);
                    }
                }
                
            }
            case tipoDato.CARACTER -> {
                int valor1 = (char)op1;
                switch (tipo2) {
                    case tipoDato.ENTERO -> {
                        return valor1 <= (int)op2;
                    }
                    case tipoDato.DECIMAL -> {
                        return valor1 <= (double)op2;
                    }
                    case tipoDato.CARACTER -> {
                        int valor_2 = (char)op2;
                        return valor1 <= valor_2;
                    }
                    default -> {
                        String descripcion = "Operador relacional <= erroneo entre tipo: " + tipo1 + " tipo: " + tipo2;
                        return new Errores("SEMANTICO", descripcion, this.linea, this.columna);
                    }
                }
                
            }
            default -> {
                String descripcion = "Operador relacional <= erroneo entre tipo: " + tipo1 + " tipo: " + tipo2;
                return new Errores("SEMANTICO", descripcion, this.linea, this.columna);
            }
        }
        
    }
    
    public Object mayor_Que(Object op1, Object op2){
    
        var tipo1 = this.operando1.tipo.getTipo();
        var tipo2 = this.operando2.tipo.getTipo();
        
        switch (tipo1) {
            case tipoDato.ENTERO -> {
                
                switch (tipo2) {
                    case tipoDato.ENTERO -> {
                        return (int)op1 > (int)op2;
                    }
                    case tipoDato.DECIMAL -> {
                        return (int)op1 > (double)op2;
                    }
                    case tipoDato.CARACTER -> {
                        int valor_2 = (char)op2;
                        return (int)op1 > valor_2;
                    }
                    default -> {
                        String descripcion = "Operador relacional > erroneo entre tipo: " + tipo1 + " tipo: " + tipo2;
                        return new Errores("SEMANTICO", descripcion, this.linea, this.columna);
                    }
                }
                
            }
            case tipoDato.DECIMAL -> {
                
                switch (tipo2) {
                    case tipoDato.ENTERO -> {
                        return (double)op1 > (int)op2;
                    }
                    case tipoDato.DECIMAL -> {
                        return (double)op1 > (double)op2;
                    }
                    case tipoDato.CARACTER -> {
                        int valor_2 = (char)op2;
                        return (double)op1 > valor_2;
                    }
                    default -> {
                        String descripcion = "Operador relacional > erroneo entre tipo: " + tipo1 + " tipo: " + tipo2;
                        return new Errores("SEMANTICO", descripcion, this.linea, this.columna);
                    }
                }
                
            }
            case tipoDato.BOOLEANO -> {
                
                switch (tipo2) {
                    case tipoDato.BOOLEANO -> {
                        int valor1 = (boolean)op1 ? 1 : 0;
                        int valor2 = (boolean)op2 ? 1 : 0;
                        return valor1 > valor2;
                    }
                    default -> {
                        String descripcion = "Operador relacional > erroneo entre tipo: " + tipo1 + " tipo: " + tipo2;
                        return new Errores("SEMANTICO", descripcion, this.linea, this.columna);
                    }
                }
                
            }
            case tipoDato.CARACTER -> {
                int valor1 = (char)op1;
                switch (tipo2) {
                    case tipoDato.ENTERO -> {
                        return valor1 > (int)op2;
                    }
                    case tipoDato.DECIMAL -> {
                        return valor1 > (double)op2;
                    }
                    case tipoDato.CARACTER -> {
                        int valor_2 = (char)op2;
                        return valor1 > valor_2;
                    }
                    default -> {
                        String descripcion = "Operador relacional > erroneo entre tipo: " + tipo1 + " tipo: " + tipo2;
                        return new Errores("SEMANTICO", descripcion, this.linea, this.columna);
                    }
                }
                
            }
            default -> {
                String descripcion = "Operador relacional > erroneo entre tipo: " + tipo1 + " tipo: " + tipo2;
                return new Errores("SEMANTICO", descripcion, this.linea, this.columna);
            }
        }
        
    }
    
    public Object mayor_Igual_Que(Object op1, Object op2){
    
        var tipo1 = this.operando1.tipo.getTipo();
        var tipo2 = this.operando2.tipo.getTipo();
        
        switch (tipo1) {
            case tipoDato.ENTERO -> {
                
                switch (tipo2) {
                    case tipoDato.ENTERO -> {
                        return (int)op1 >= (int)op2;
                    }
                    case tipoDato.DECIMAL -> {
                        return (int)op1 >= (double)op2;
                    }
                    case tipoDato.CARACTER -> {
                        int valor_2 = (char)op2;
                        return (int)op1 >= valor_2;
                    }
                    default -> {
                        String descripcion = "Operador relacional >= erroneo entre tipo: " + tipo1 + " tipo: " + tipo2;
                        return new Errores("SEMANTICO", descripcion, this.linea, this.columna);
                    }
                }
                
            }
            case tipoDato.DECIMAL -> {
                
                switch (tipo2) {
                    case tipoDato.ENTERO -> {
                        return (double)op1 >= (int)op2;
                    }
                    case tipoDato.DECIMAL -> {
                        return (double)op1 >= (double)op2;
                    }
                    case tipoDato.CARACTER -> {
                        int valor_2 = (char)op2;
                        return (double)op1 >= valor_2;
                    }
                    default -> {
                        String descripcion = "Operador relacional >= erroneo entre tipo: " + tipo1 + " tipo: " + tipo2;
                        return new Errores("SEMANTICO", descripcion, this.linea, this.columna);
                    }
                }
                
            }
            case tipoDato.BOOLEANO -> {
                
                switch (tipo2) {
                    case tipoDato.BOOLEANO -> {
                        int valor1 = (boolean)op1 ? 1 : 0;
                        int valor2 = (boolean)op2 ? 1 : 0;
                        return valor1 >= valor2;
                    }
                    default -> {
                        String descripcion = "Operador relacional >= erroneo entre tipo: " + tipo1 + " tipo: " + tipo2;
                        return new Errores("SEMANTICO", descripcion, this.linea, this.columna);
                    }
                }
                
            }
            case tipoDato.CARACTER -> {
                int valor1 = (char)op1;
                switch (tipo2) {
                    case tipoDato.ENTERO -> {
                        return valor1 >= (int)op2;
                    }
                    case tipoDato.DECIMAL -> {
                        return valor1 >= (double)op2;
                    }
                    case tipoDato.CARACTER -> {
                        int valor_2 = (char)op2;
                        return valor1 >= valor_2;
                    }
                    default -> {
                        String descripcion = "Operador relacional >= erroneo entre tipo: " + tipo1 + " tipo: " + tipo2;
                        return new Errores("SEMANTICO", descripcion, this.linea, this.columna);
                    }
                }
                
            }
            default -> {
                String descripcion = "Operador relacional >= erroneo entre tipo: " + tipo1 + " tipo: " + tipo2;
                return new Errores("SEMANTICO", descripcion, this.linea, this.columna);
            }
        }
        
    }
    
}
