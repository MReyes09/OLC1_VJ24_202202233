
package simbolo;

public class Simbolo {
    
    private Tipo tipo;
    private String id;
    private Object valor;
    private boolean mutabilidad;
    private String tip;
    private String entorno;
    private int fila;
    private int columna;

    public Simbolo(Tipo tipo, String id) {
        this.tipo = tipo;
        this.id = id;
    }

    public Simbolo(Tipo tipo, String id, Object valor, boolean mutabilidad, int fila, int columna) {
        this.tipo = tipo;
        this.id = id;
        this.valor = valor;
        this.mutabilidad = mutabilidad;
        this.tip = "Variable";
        this.entorno = "";
        this.fila = fila;
        this.columna = columna;
    }

    public Tipo getTipo() {
        return tipo;
    }

    public void setTipo(Tipo tipo) {
        this.tipo = tipo;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Object getValor() {
        return valor;
    }

    public void setValor(Object valor) {
        this.valor = valor;
    }

    public boolean getMutabilidad() {
        return mutabilidad;
    }

    public void setMutabilidad(boolean mutabilidad) {
        this.mutabilidad = mutabilidad;
    }

    public String getTip() {
        return tip;
    }

    public void setTip(String tip) {
        this.tip = tip;
    }

    public String getEntorno() {
        return entorno;
    }

    public void setEntorno(String entorno) {
        this.entorno = entorno;
    }

    public int getFila() {
        return fila;
    }

    public void setFila(int fila) {
        this.fila = fila;
    }

    public int getColumna() {
        return columna;
    }

    public void setColumna(int columna) {
        this.columna = columna;
    }
}
