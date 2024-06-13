
package expresiones.aritmeticas;
import abstracto.Instruccion;
import excepciones.Errores;
import simbolo.*;

public class Aritmeticas extends Instruccion{
    
    private Instruccion operando1;
    private Instruccion operando2;
    private OperadoresAritmeticos operacion;
    private Instruccion operadorUnico;

    //PROPIO PARA LA NEGACION
    public Aritmeticas(Instruccion operadorUnico, OperadoresAritmeticos operacion, int linea, int columna) {
        super(new Tipo(tipoDato.ENTERO), linea, columna);
        this.operacion = operacion;
        this.operadorUnico = operadorUnico;
    }
    
    //OTRAS OPERACIONES MENOS NEGACION

    public Aritmeticas(Instruccion operando1, Instruccion operando2, OperadoresAritmeticos operacion, int linea, int columna) {
        super(new Tipo(tipoDato.ENTERO), linea, columna);
        this.operando1 = operando1;
        this.operando2 = operando2;
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
            //Validacion de errores
            if(opDer instanceof Errores){
                return opDer;
            }
        }
        
        return switch (operacion) {
            case SUMA ->
                this.suma(opIzq, opDer);
            case NEGACION ->
                this.negacion(Unico);
            case RESTA ->
                this.resta(opIzq, opDer);
            case MULTIPLICACION -> 
                this.multiplicacion(opIzq, opDer);
            case DIVISION ->
                this.division(opIzq, opDer);
            case POTENCIA ->
                this.potencia(opIzq, opDer);
            case MODULO -> 
                this.modulo(opIzq, opDer);
            default ->
                new Errores("SEMANTICO", "Operador invalido", this.linea, this.columna);
        };
        
    }
    
    public Object suma(Object op1, Object op2) {
        
        var tipo1 = this.operando1.tipo.getTipo();
        var tipo2 = this.operando2.tipo.getTipo();
        
        switch (tipo1) {
            //SI VIENE TIPO ENTERO
            case tipoDato.ENTERO -> {
                
                switch (tipo2) {
                    case tipoDato.ENTERO -> {
                        this.tipo.setTipo(tipoDato.ENTERO);
                        return (int)op1 + (int)op2;
                    }
                    case tipoDato.DECIMAL -> {
                        this.tipo.setTipo(tipoDato.DECIMAL);
                        return (int)op1 + (double)op2;
                    }
                    case tipoDato.CARACTER -> {
                        this.tipo.setTipo(tipoDato.ENTERO);
                        int valor_Op2 = (char)op2;
                        return (int)op1 + valor_Op2;
                    }
                    case tipoDato.CADENA -> {
                        this.tipo.setTipo(tipoDato.CADENA);
                        return op1.toString() + op2.toString();
                    }
                    default -> {
                        String descripcion = "Suma erronea entre tipo: " + tipo1 + " tipo: " + tipo2;
                        return new Errores("SEMANTICO", descripcion, this.linea, this.columna);
                    }
                }
                
            }
            case tipoDato.DECIMAL -> {
                
                switch (tipo2){
                    case tipoDato.ENTERO -> {
                        this.tipo.setTipo(tipoDato.DECIMAL);
                        return (double)op1 + (int)op2;
                    }
                    case tipoDato.DECIMAL -> {
                        this.tipo.setTipo(tipoDato.DECIMAL);
                        return (double)op1 + (double)op2;
                    }
                    case tipoDato.CARACTER -> {
                        this.tipo.setTipo(tipoDato.DECIMAL);
                        int valor_Op2 = (char)op2;
                        return (double)op1 + valor_Op2;
                    }
                    case tipoDato.CADENA -> {
                        this.tipo.setTipo(tipoDato.CADENA);
                        return op1.toString() + op2.toString();
                    }
                    default -> {
                        String descripcion = "Suma erronea entre tipo: " + tipo1 + " tipo: " + tipo2;
                        return new Errores("SEMANTICO", descripcion, this.linea, this.columna);
                    }
                }
                
            }
            case tipoDato.BOOLEANO -> {
                
                switch (tipo2) {
                    case tipoDato.CADENA -> {
                        this.tipo.setTipo(tipoDato.CADENA);
                        return op1.toString() + op2.toString();
                    }
                    default -> {
                        String descripcion = "Suma erronea entre tipo: " + tipo1 + " tipo: " + tipo2;
                        return new Errores("SEMANTICO", descripcion, this.linea, this.columna);
                    }
                }
            }
            case tipoDato.CARACTER -> {
                switch (tipo2) {
                    case tipoDato.ENTERO -> {
                        this.tipo.setTipo(tipoDato.ENTERO);
                        int valor_1 = (char)op1;
                        return valor_1 + (int)op2;
                    }
                    case tipoDato.DECIMAL -> {
                        this.tipo.setTipo(tipoDato.DECIMAL);
                        int valor_1 = (char)op1;
                        return valor_1 + (double)op2;
                    }
                    case tipoDato.CARACTER -> {
                        this.tipo.setTipo(tipoDato.CADENA);
                        return op1.toString() + op2.toString();
                    }
                    case tipoDato.CADENA -> {
                        this.tipo.setTipo(tipoDato.CADENA);
                        return op1.toString() + op2.toString();
                    }
                    default -> {
                        String descripcion = "Suma erronea entre tipo: " + tipo1 + " tipo: " + tipo2;
                        return new Errores("SEMANTICO", descripcion, this.linea, this.columna);
                    }
                }
            }
            case tipoDato.CADENA -> {
                this.tipo.setTipo(tipoDato.CADENA);
                return op1.toString() + op2.toString();
            }
            default -> {
                String descripcion = "Suma erronea entre tipo: " + tipo1 + " tipo2: " + tipo2; 
                return new Errores("SEMANTICO", descripcion, this.linea, this.columna);
            }
        }
        
    }
    
    public Object negacion(Object op1){
        var opU = this.operadorUnico.tipo.getTipo();
        
        switch(opU){
            case tipoDato.ENTERO -> {
                this.tipo.setTipo(tipoDato.ENTERO);
                return (int)op1 * -1; 
            }
            case tipoDato.DECIMAL -> {
                this.tipo.setTipo(tipoDato.DECIMAL);
                return (double)op1 * -1;
            }
            default -> {
                String descripcion = "Negacion erronea para tipo: " + opU; 
                return new Errores("SEMANTICO", descripcion, this.linea, this.columna);
            }
        }
    }
    
    public Object resta(Object op1, Object op2){
        
        var tipo1 = this.operando1.tipo.getTipo();
        var tipo2 = this.operando2.tipo.getTipo();
        
        switch (tipo1) {
            case tipoDato.ENTERO -> {
                
                switch (tipo2) {
                    case tipoDato.ENTERO -> {
                        this.tipo.setTipo(tipoDato.ENTERO);
                        return (int)op1 - (int)op2;
                    }
                    case tipoDato.DECIMAL -> {
                        this.tipo.setTipo(tipoDato.DECIMAL);
                        return (int)op1 - (double)op2;
                    }
                    case tipoDato.CARACTER -> {
                        this.tipo.setTipo(tipoDato.ENTERO);
                        int valor_Op2 = (char)op2;
                        return (int)op1 - valor_Op2;
                    }
                     default -> {
                        String descripcion = "Resta erronea entre tipo: " + tipo1 + " tipo: " + tipo2;
                        return new Errores("SEMANTICO", descripcion, this.linea, this.columna);
                    }
                }
                
            }
            case tipoDato.DECIMAL -> {
                
                switch (tipo2) {
                    case tipoDato.ENTERO -> {
                        this.tipo.setTipo(tipoDato.DECIMAL);
                        return (double)op1 - (int)op2;
                    }
                    case tipoDato.DECIMAL -> {
                        this.tipo.setTipo(tipoDato.DECIMAL);
                        return (double)op1 - (double)op2;
                    }
                    case tipoDato.CARACTER -> {
                        this.tipo.setTipo(tipoDato.DECIMAL);
                        int valor_Op2 = (char)op2;
                        return (double)op1 - valor_Op2;
                    }
                    default -> {
                        String descripcion = "Resta erronea entre tipo: " + tipo1 + " tipo2: " + tipo2; 
                        return new Errores("SEMANTICO", descripcion, this.linea, this.columna);
                    }
                }
                
            }
            case tipoDato.CARACTER -> {
                
                switch (tipo2) {
                    case tipoDato.ENTERO -> {
                        this.tipo.setTipo(tipoDato.ENTERO);
                        int valor_1 = (char)op1;
                        return valor_1 - (int)op2;
                    }
                    case tipoDato.DECIMAL -> {
                        this.tipo.setTipo(tipoDato.DECIMAL);
                        int valor_1 = (char)op1;
                        return valor_1 - (double)op2;
                    }
                    default -> {
                        String descripcion = "Resta erronea entre tipo: " + tipo1 + " tipo: " + tipo2;
                        return new Errores("SEMANTICO", descripcion, this.linea, this.columna);
                    }
                }
                
            }
            default -> {
                String descripcion = "Resta erronea entre tipo: " + tipo1 + " tipo2: " + tipo2; 
                return new Errores("SEMANTICO", descripcion, this.linea, this.columna);
            }
        }
        
    }
    
    public Object multiplicacion(Object op1, Object op2){
        
        var tipo1 = this.operando1.tipo.getTipo();
        var tipo2 = this.operando2.tipo.getTipo();
        
        switch (tipo1) {
            case tipoDato.ENTERO -> {
                
                switch (tipo2) {
                    case tipoDato.ENTERO -> {
                        this.tipo.setTipo(tipoDato.ENTERO);
                        return (int)op1 * (int)op2;
                    }
                    case tipoDato.DECIMAL -> {
                        this.tipo.setTipo(tipoDato.DECIMAL);
                        return (int)op1 * (double)op2;
                    }
                    case tipoDato.CARACTER -> {
                        this.tipo.setTipo(tipoDato.ENTERO);
                        int valor_Op2 = (char)op2;
                        return (int)op1 * valor_Op2;
                    }
                     default -> {
                        String descripcion = "Multiplicacion erronea, entre tipo: " + tipo1 + " tipo: " + tipo2;
                        return new Errores("SEMANTICO", descripcion, this.linea, this.columna);
                    }
                }
                
            }
            case tipoDato.DECIMAL -> {
                switch (tipo2) {
                    
                    case tipoDato.ENTERO -> {
                        this.tipo.setTipo(tipoDato.DECIMAL);
                        return (double)op1 * (int)op2;
                    }
                    case tipoDato.DECIMAL -> {
                        this.tipo.setTipo(tipoDato.DECIMAL);
                        return (double)op1 * (double)op2;
                    }
                    case tipoDato.CARACTER -> {
                        this.tipo.setTipo(tipoDato.DECIMAL);
                        int valor_Op2 = (char)op2;
                        return (double)op1 * valor_Op2;
                    }
                    default -> {
                        String descripcion = "Multiplicacion erronea, entre tipo: " + tipo1 + " tipo2: " + tipo2; 
                        return new Errores("SEMANTICO", descripcion, this.linea, this.columna);
                    }
                    
                }
            }
            case tipoDato.CARACTER -> {
                
                switch (tipo2) {
                    case tipoDato.ENTERO -> {
                        this.tipo.setTipo(tipoDato.ENTERO);
                        int valor_1 = (char)op1;
                        return valor_1 * (int)op2;
                    }
                    case tipoDato.DECIMAL -> {
                        this.tipo.setTipo(tipoDato.DECIMAL);
                        int valor_1 = (char)op1;
                        return valor_1 * (double)op2;
                    }
                    default -> {
                        String descripcion = "Multiplicacion erronea, entre tipo: " + tipo1 + " tipo: " + tipo2;
                        return new Errores("SEMANTICO", descripcion, this.linea, this.columna);
                    }
                }
                
            }
            default -> {
                String descripcion = "Multiplicacion erronea, entre tipo: " + tipo1 + " tipo2: " + tipo2; 
                return new Errores("SEMANTICO", descripcion, this.linea, this.columna);
            }
        }
        
    }
    
    public Object division(Object op1, Object op2) {

        var tipo1 = this.operando1.tipo.getTipo();
        var tipo2 = this.operando2.tipo.getTipo();

        // Verificación de división por cero
        if ((op2 instanceof Integer && (Integer) op2 == 0) || (op2 instanceof Double && (Double) op2 == 0.0)) {
            String descripcion = "División errónea, entre " + op1 + " y " + op2 + ". Indeterminado";
            return new Errores("SEMANTICO", descripcion, this.linea, this.columna);
        }

        switch (tipo1) {
            case tipoDato.ENTERO -> {
                int valorOp1 = (Integer) op1;
                switch (tipo2) {
                    case tipoDato.ENTERO -> {
                        this.tipo.setTipo(tipoDato.DECIMAL);
                        double operacion = (double) valorOp1 / (Integer) op2;
                        return operacion;
                    }
                    case tipoDato.DECIMAL -> {
                        this.tipo.setTipo(tipoDato.DECIMAL);
                        double operacion = valorOp1 / (Double) op2;
                        return operacion;
                    }
                    case tipoDato.CARACTER -> {
                        this.tipo.setTipo(tipoDato.DECIMAL);
                        int valorOp2 = (Character) op2;
                        double operacion = (double) valorOp1 / valorOp2;
                        return operacion;
                    }
                    default -> {
                        String descripcion = "División errónea, entre tipo: " + tipo1 + " y tipo: " + tipo2;
                        return new Errores("SEMANTICO", descripcion, this.linea, this.columna);
                    }
                }
            }
            case tipoDato.DECIMAL -> {
                double valorOp1 = (Double) op1;
                switch (tipo2) {
                    case tipoDato.ENTERO -> {
                        this.tipo.setTipo(tipoDato.DECIMAL);
                        double operacion = valorOp1 / (Integer) op2;
                        return operacion;
                    }
                    case tipoDato.DECIMAL -> {
                        this.tipo.setTipo(tipoDato.DECIMAL);
                        double operacion = valorOp1 / (Double) op2;
                        return operacion;
                    }
                    case tipoDato.CARACTER -> {
                        this.tipo.setTipo(tipoDato.DECIMAL);
                        int valorOp2 = (Character) op2;
                        double operacion = valorOp1 / valorOp2;
                        return operacion;
                    }
                    default -> {
                        String descripcion = "División errónea, entre tipo: " + tipo1 + " y tipo: " + tipo2;
                        return new Errores("SEMANTICO", descripcion, this.linea, this.columna);
                    }
                }
            }
            case tipoDato.CARACTER -> {
                int valorOp1 = (Character) op1;
                switch (tipo2) {
                    case tipoDato.ENTERO -> {
                        this.tipo.setTipo(tipoDato.DECIMAL);
                        double operacion = (double) valorOp1 / (Integer) op2;
                        return operacion;
                    }
                    case tipoDato.DECIMAL -> {
                        this.tipo.setTipo(tipoDato.DECIMAL);
                        double operacion = valorOp1 / (Double) op2;
                        return operacion;
                    }
                    default -> {
                        String descripcion = "División errónea, entre tipo: " + tipo1 + " y tipo: " + tipo2;
                        return new Errores("SEMANTICO", descripcion, this.linea, this.columna);
                    }
                }
            }
            default -> {
                String descripcion = "División errónea, entre tipo: " + tipo1 + " y tipo: " + tipo2;
                return new Errores("SEMANTICO", descripcion, this.linea, this.columna);
            }
        }
    }
    
    public Object potencia(Object op1, Object op2){
        
        var tipo1 = this.operando1.tipo.getTipo();
        var tipo2 = this.operando2.tipo.getTipo();
        
        switch (tipo1) {
            case tipoDato.ENTERO -> {
                
                switch (tipo2) {
                    case tipoDato.ENTERO -> {
                        this.tipo.setTipo(tipoDato.ENTERO);
                        int resultado = (int)Math.pow((int)op1, (int)op2);
                        return resultado;
                    }
                    case tipoDato.DECIMAL -> {
                        this.tipo.setTipo(tipoDato.DECIMAL);
                        return Math.pow((int)op1, (double)op2);
                    }
                    default -> {
                        String descripcion = "Potencia erronea, entre tipo: " + tipo1 + " tipo: " + tipo2;
                        return new Errores("SEMANTICO", descripcion, this.linea, this.columna);
                    }
                }
            }
            case tipoDato.DECIMAL -> {
                
                switch (tipo2) {
                    case tipoDato.ENTERO -> {
                        this.tipo.setTipo(tipoDato.DECIMAL);
                        return Math.pow((double)op1, (int)op2);
                    }
                    case tipoDato.DECIMAL -> {
                        this.tipo.setTipo(tipoDato.DECIMAL);
                        return Math.pow((double)op1, (double)op2);
                    }
                    default -> {
                        String descripcion = "Potencia erronea, entre tipo: " + tipo1 + " tipo: " + tipo2;
                        return new Errores("SEMANTICO", descripcion, this.linea, this.columna);
                    }
                }
                
            }
            default -> {
                String descripcion = "Potencia erronea, entre tipo: " + tipo1 + " tipo: " + tipo2;
                return new Errores("SEMANTICO", descripcion, this.linea, this.columna);
            }
        }
        
    }
    
    public Object modulo(Object op1, Object op2){
        
        var tipo1 = this.operando1.tipo.getTipo();
        var tipo2 = this.operando2.tipo.getTipo();
        
        switch (tipo1) {
            case tipoDato.ENTERO -> {
                
                switch (tipo2) {
                    case tipoDato.ENTERO -> {
                        this.tipo.setTipo(tipoDato.DECIMAL);
                        double resultado = (int)op1 % (int)op2;
                        return resultado;
                    }
                    case tipoDato.DECIMAL -> {
                        this.tipo.setTipo(tipoDato.DECIMAL);
                        return (int)op1 % (double)op2;
                    }
                    default -> {
                        String descripcion = "Operacion Modulo erronea, entre tipo: " + tipo1 + " tipo: " + tipo2;
                        return new Errores("SEMANTICO", descripcion, this.linea, this.columna);
                    }
                }
            }
            case tipoDato.DECIMAL -> {
                
                switch (tipo2) {
                    case tipoDato.ENTERO -> {
                        this.tipo.setTipo(tipoDato.DECIMAL);
                        return (double)op1 % (int)op2;
                    }
                    case tipoDato.DECIMAL -> {
                        this.tipo.setTipo(tipoDato.DECIMAL);
                        return (double)op1 % (double)op2;
                    }
                    default -> {
                        String descripcion = "Operacion Modulo erronea, entre tipo: " + tipo1 + " tipo: " + tipo2;
                        return new Errores("SEMANTICO", descripcion, this.linea, this.columna);
                    }
                }
                
            }
            default -> {
                String descripcion = "Operacion Modulo erronea, entre tipo: " + tipo1 + " tipo: " + tipo2;
                return new Errores("SEMANTICO", descripcion, this.linea, this.columna);
            }
        }
        
    }
    
}
