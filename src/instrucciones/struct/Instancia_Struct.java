
package instrucciones.struct;

import abstracto.Instruccion;
import excepciones.Errores;
import java.util.ArrayList;
import java.util.LinkedList;
import simbolo.Arbol;
import simbolo.Simbolo;
import simbolo.Tipo;
import simbolo.tablaSimbolos;
import simbolo.tipoDato;

public class Instancia_Struct extends Instruccion{
    
    private String id;
    private String id_Struct;
    private LinkedList<String> variables_Struct;
    private LinkedList<Instruccion> valores_Struct;
    private boolean mutabilidad;
    private String bloque;
    private int conteo = 0;

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
        var busqueda = arbol.get_Struct(this.id_Struct);
        
        if( busqueda == null ){
            return new Errores("SEMANTICO", "El struct " + this.id_Struct + " no existe" , this.linea, this.columna);
        }
        
        if( busqueda instanceof Declaracion_Struct structOriginal){
            if (structOriginal.declaraciones.size() != this.variables_Struct.size()) {
                return new Errores("SEMANTICO", "La cantidad de parametros brindada no coincide", this.linea, this.columna);
            }

            try {
                // CREACION DE UNA COPIA DEL STRUCT
                Declaracion_Struct struct_Nuevo = (Declaracion_Struct) structOriginal.clone();

                // CONFIGURACIÓN DEL STRUCT
                struct_Nuevo.id = this.id;
                var struct_Declarado = struct_Nuevo.interpretar(arbol, tabla);

                if (struct_Declarado instanceof Errores) {
                    return struct_Declarado;
                }
                
                // ASIGNACION DE NUEVOS VALORES
                Simbolo struct_Variable = tabla.getVariable(id);
                tablaSimbolos simbolos_Struct = (tablaSimbolos) struct_Variable.getValor();
                int contador = 0;
                for (String variable : this.variables_Struct) {
                    if(variable.equalsIgnoreCase("cui")){
                        System.out.println("");
                    }
                    var busqueda_Var = simbolos_Struct.getVariable(variable);
                    
                    if (busqueda_Var == null) {
                        return new Errores("SEMANTICO", "Variable de struct " + variable + " no encontrada", this.linea, this.columna);
                    }

                    var resultado_Valor_Nuevo = this.valores_Struct.get(contador).interpretar(arbol, tabla);
                    
                    if( resultado_Valor_Nuevo instanceof ArrayList){
                        
                    }

                    if (this.valores_Struct.get(contador).tipo.getTipo() != simbolos_Struct.getVariable(variable).getTipo().getTipo()) {
                        String descripcion = "Tipos erroneos en instancia de struct entre " +
                                this.valores_Struct.get(contador).tipo.getTipo() + " y " +
                                simbolos_Struct.getVariable(variable).getTipo().getTipo();
                        return new Errores("SEMANTICO", descripcion, this.linea, this.columna);
                    }

                    // CAMBIAR EL VALOR
                    simbolos_Struct.getVariable(variable).setValor(resultado_Valor_Nuevo);
                    contador++;
                }
            } catch (CloneNotSupportedException e) {
                return new Errores("SEMANTICO", "Error al clonar el struct", this.linea, this.columna);
            }
            
        }
        
        return null;
        
    }
    
    private Object valor_Instancia_Struct(Arbol arbol, tablaSimbolos tabla, ArrayList<Object> valores_Struct2, String id){
        ArrayList<Object> lista_Separar = (ArrayList<Object>)valores_Struct2;
        LinkedList<String> variables_Struct = new LinkedList<>();
        LinkedList<Instruccion> valores_Struct = new LinkedList<>();
        for( int i = 0; i < lista_Separar.size(); i++ ){
            if( i % 2 == 0 ){
                variables_Struct.add( lista_Separar.get(i).toString() );
            }else{
                valores_Struct.add( (Instruccion) lista_Separar.get(i) );
            }
        }
        
        return new Instancia_Struct(id, id_Struct, variables_Struct, valores_Struct, mutabilidad, linea, columna);
    }
    
    public String getBloque() {
        return bloque;
    }

    public void setBloque(String bloque) {
        this.bloque = bloque;
    }
}
