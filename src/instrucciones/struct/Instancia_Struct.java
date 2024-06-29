
package instrucciones.struct;

import abstracto.Instruccion;
import excepciones.Errores;
import instrucciones.Declaracion;
import java.util.ArrayList;
import java.util.LinkedList;
import simbolo.Arbol;
import simbolo.Simbolo;
import simbolo.Tipo;
import simbolo.tablaSimbolos;
import simbolo.tipoDato;

public class Instancia_Struct extends Instruccion{
    
    public String id;
    public String id_Struct;
    private LinkedList<String> variables_Struct;
    private LinkedList<Instruccion> valores_Struct;
    private boolean mutabilidad;
    private String bloque;
    private int conteo = 0;
    private static boolean fue_Declarado = false;
    private static boolean retornar_Instancia = false;

    public Instancia_Struct(String id, String id_Struct, LinkedList<String> variables_Struct, LinkedList<Instruccion> valores_Struct, boolean mutabilidad, int linea, int columna) {
        super(new Tipo(tipoDato.VOID), linea, columna);
        this.id = id;
        this.id_Struct = id_Struct;
        this.variables_Struct = variables_Struct;
        this.valores_Struct = valores_Struct;
        this.mutabilidad = mutabilidad;
        this.bloque = "";
    }

    @Override
    public Object interpretar(Arbol arbol, tablaSimbolos tabla) {
        
        //VALIDAMOS QUE EXISTA LA VARIABLE Y SEA TIPO STRUCT
        if( this.id_Struct == null ){
            var struct_Id = this.find_Struct(arbol, tabla);
            if( struct_Id instanceof Errores){
                return struct_Id;
            }
            this.id_Struct = struct_Id.toString();
        }
        var busqueda = arbol.get_Struct(this.id_Struct);
        
        if( busqueda == null ){
            return new Errores("SEMANTICO", "El struct " + this.id_Struct + " no existe" , this.linea, this.columna);
        }
        
        if( busqueda instanceof Declaracion_Struct structOriginal){
            if (structOriginal.declaraciones.size() != this.variables_Struct.size()) {
                return new Errores("SEMANTICO", "La cantidad de parametros brindada no coincide", this.linea, this.columna);
            }

            try {
                if( !fue_Declarado ){
                    // CREACION DE UNA COPIA DEL STRUCT
                    Declaracion_Struct struct_Nuevo = (Declaracion_Struct) structOriginal.clone();

                    // CONFIGURACIÓN DEL STRUCT
                    struct_Nuevo.id = this.id;
                    var struct_Declarado = struct_Nuevo.interpretar(arbol, tabla);

                    if (struct_Declarado instanceof Errores) {
                        return struct_Declarado;
                    }
                }
                
                // ASIGNACION DE NUEVOS VALORES
                Simbolo struct_Variable = tabla.getVariable(id);
                tablaSimbolos simbolos_Struct = (tablaSimbolos) struct_Variable.getValor();
                int contador = 0;
                for (String variable : this.variables_Struct) {
                    var busqueda_Var = simbolos_Struct.getVariable(variable);
                    
                    if (busqueda_Var == null) {
                        return new Errores("SEMANTICO", "Variable de struct " + variable + " no encontrada", this.linea, this.columna);
                    }
                    if( this.valores_Struct.get(contador) instanceof Instancia_Struct ){
                        Instancia_Struct nueva_Instancia = (Instancia_Struct)this.valores_Struct.get(contador);
                        nueva_Instancia.id = variable;
                        fue_Declarado = true;
                        retornar_Instancia = true;
                        var resultado_Valor_Nuevo = this.valores_Struct.get(contador).interpretar(arbol, tabla);
                        if( resultado_Valor_Nuevo instanceof Errores ){
                            return resultado_Valor_Nuevo;
                        }
                        simbolos_Struct.getVariable(variable).setValor(resultado_Valor_Nuevo);
                        contador++;
                        fue_Declarado = false;
                        retornar_Instancia = false;
                        continue;
                    }
                    var resultado_Valor_Nuevo = this.valores_Struct.get(contador).interpretar(arbol, tabla);
                    
                    if( resultado_Valor_Nuevo instanceof Errores ){
                        return resultado_Valor_Nuevo;
                    }
                    if (this.valores_Struct.get(contador).tipo.getTipo() != simbolos_Struct.getVariable(variable).getTipo().getTipo()) {
                        String descripcion = "Tipos erroneos en instancia de struct entre " +
                                this.valores_Struct.get(contador).tipo.getTipo() + " y " +
                                simbolos_Struct.getVariable(variable).getTipo().getTipo();
                        return new Errores("SEMANTICO", descripcion, this.linea, this.columna);
                    }

                    // CAMBIAR EL VALOR
                    simbolos_Struct.getVariable(variable).setValor(resultado_Valor_Nuevo);
                    if( resultado_Valor_Nuevo instanceof tablaSimbolos ){
                        Simbolo buscar_Borrar = tabla.getVariable(variable);
                        tabla.removeVariable(buscar_Borrar);
                    }
                    contador++;
                }
                if( retornar_Instancia ){
                    return simbolos_Struct;
                }
            } catch (CloneNotSupportedException e) {
                return new Errores("SEMANTICO", "Error al clonar el struct", this.linea, this.columna);
            }
            
        }
        
        return null;
        
    }
    
    private Object find_Struct(Arbol arbol, tablaSimbolos tabla){
        int variables_Iguales = 0;
        LinkedList<Instruccion> instrucciones_Globales = arbol.getInstrucciones();
        for( Instruccion instruccion_Global: instrucciones_Globales ){
            
            if( instruccion_Global instanceof Declaracion_Struct posible_Struct){
                
                for(Instruccion declaracion: posible_Struct.declaraciones){
                    String id_Declaracion = ((Declaracion)declaracion).identificador;
                    
                    for( String variable_Struct: this.variables_Struct ){
                        
                        if( variable_Struct.equalsIgnoreCase(id_Declaracion) ){
                            variables_Iguales++;
                            break;
                        }
                    }
                }
                
                if( variables_Iguales == this.variables_Struct.size() ){
                    return posible_Struct.id;
                }else{
                    variables_Iguales = 0;
                }
            }
        }
        return new Errores("SEMANTICO", "Se intenta instanciar un struct no declarado", this.linea, this.columna);
        
    }
    
    public String getBloque() {
        return bloque;
    }

    public void setBloque(String bloque) {
        this.bloque = bloque;
    }
}
