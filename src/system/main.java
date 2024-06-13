
package system;

import analisis.Generador;
import view.Ventana_Base;

public class main {
    
    static Ventana_Base ventana_Base = new Ventana_Base();
    static Generador generador = new Generador();
    
    public static void main(String[] args) {
        
        ventana_Base.setVisible(true);
        ventana_Base.setLocationRelativeTo(null);
        generador.generarCompilador();
//        System.out.println("\\");
    }
    
}
