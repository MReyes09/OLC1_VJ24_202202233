
package instrucciones;

import abstracto.Instruccion;
import excepciones.Errores;
import instrucciones.struct.Declaracion_Struct;
import simbolo.Arbol;
import simbolo.Simbolo;
import simbolo.Tipo;
import simbolo.tablaSimbolos;
import simbolo.tipoDato;

public class Declaracion extends Instruccion{

    public String identificador;
    public String identificador2;
    public Instruccion valor;
    public boolean mutabilidad;
    public String bloque;
    public boolean es_Struct;

    public Declaracion(boolean mutabilidad, String identificador, Instruccion valor, Tipo tipo, int linea, int columna) {
        super(tipo, linea, columna);
        this.identificador = identificador;
        this.valor = valor;
        this.mutabilidad = mutabilidad;
        this.bloque = "";
        this.es_Struct = false;
    }
    
    public Declaracion(boolean mutabilidad, String identificador, Tipo tipo, int linea, int columna) {
        super(tipo, linea, columna);
        this.identificador = identificador;
        this.mutabilidad = mutabilidad;
        this.bloque = "";
        this.es_Struct = false;
    }
    
    public Declaracion(String identificador, Tipo tipo, int linea, int columna) {
        super(tipo, linea, columna);
        this.identificador = identificador;
        this.mutabilidad = true;
        this.bloque = "";
        this.es_Struct = true;
    }
    
    public Declaracion(String identificador, String identificador2, int linea, int columna) {
        super(new Tipo(tipoDato.STRUCT), linea, columna);
        this.identificador = identificador;
        this.identificador2 = identificador2;
        this.mutabilidad = true;
        this.bloque = "";
        this.es_Struct = true;
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
                        + " a una variable de tipo: " + this.tipo.getTipo(); 
                return new Errores("SEMANTICO", descripcion, this.linea, this.columna);
            }

            Simbolo s = new Simbolo(this.tipo, this.identificador, valorInterpretado, this.mutabilidad, this.linea, this.columna);
            if(bloque.equals("")){
                this.setBloque("Global");
            }
            s.setEntorno(bloque);
            boolean creacion = tabla.setVariable(s);
            if(!creacion) {
                return new Errores("SEMANTICO", "Variable ya existente", this.linea, this.columna);
            }
            view.Ventana_Base.tabla_Simbolos.add(s);
        } else {
            Simbolo s;
            switch (this.tipo.getTipo()) {
                case ENTERO -> {
                    s = new Simbolo(this.tipo, this.identificador, 0, this.mutabilidad, this.linea, this.columna);
                }
                case DECIMAL -> {
                    s = new Simbolo(this.tipo, identificador, 0.0, this.mutabilidad, this.linea, this.columna);
                }
                case BOOLEANO -> {
                    s = new Simbolo(this.tipo, identificador, true, this.mutabilidad, this.linea, this.columna);
                }
                case CARACTER -> {
                    s = new Simbolo(this.tipo, identificador, '\u0000', this.mutabilidad, this.linea, this.columna);
                }
                case CADENA -> {
                    s = new Simbolo(this.tipo, identificador, "", this.mutabilidad, this.linea, this.columna);
                }
                case STRUCT -> {
                   var resultado = this.caso_Struct(arbol, tabla);
                   if( resultado instanceof Errores){
                       return resultado;
                   }else if( resultado == null ){
                       return new Errores("SEMANTICO", "A ocurrido un error con el Struct", this.linea, this.columna);
                   }
                   s = (Simbolo)resultado;
                }
                default -> {
                    String texto = "Asignacion de tipo: " + this.tipo.getTipo() + " invalido";
                    return new Errores("SEMANTICO", texto, this.linea, this.columna);
                }
            };
            if(bloque.equals("")){
                this.setBloque("Global");
            }
            s.setEntorno(bloque);
            if( this.es_Struct ){
                return s;
            }
            boolean creacion = tabla.setVariable(s);
            if(!creacion) {
                return new Errores("SEMANTICO", "Variable ya existente", this.linea, this.columna);
            }
            view.Ventana_Base.tabla_Simbolos.add(s);
        }
        
        return null;
    }
    private Object caso_Struct(Arbol arbol, tablaSimbolos tabla) {

        // Validamos que exista la variable y sea tipo Struct
        var busqueda = arbol.get_Struct(this.identificador2);
        if (busqueda == null) {
            return new Errores("SEMANTICO", "El struct " + this.identificador2 + " no existe", this.linea, this.columna);
        }

        if (busqueda instanceof Declaracion_Struct structOriginal) {
            try {
                // Creación de una copia del struct
                Declaracion_Struct struct_Nuevo = (Declaracion_Struct) structOriginal.clone();
                struct_Nuevo.id = this.identificador;
                var struct_Declarado = struct_Nuevo.interpretar(arbol, tabla);

                if (struct_Declarado instanceof Errores) {
                    return struct_Declarado;
                }

                Simbolo s = tabla.getVariable(this.identificador);
                return s;
            } catch (CloneNotSupportedException e) {
                return new Errores("SEMANTICO", "Error al clonar el struct", this.linea, this.columna);
            }
        }

        return null;
    }

    public String getBloque() {
        return bloque;
    }

    public void setBloque(String bloque) {
        this.bloque = bloque;
    }
}
