
package analisis;

public class Generador {
    
     public static void generarCompilador() {
        try{
            String ruta = "src/analisis/";

           String Flex[] = {ruta + "lexico.jflex", "-d", ruta};
           jflex.Main.generate(Flex);

            String Cup[] = { "-destdir", ruta, "-parser", "parser", ruta + "sintactico.cup" };
            java_cup.Main.main(Cup);
            
        }catch(Exception e){
            System.out.println("No se ha podido generar los analizadores");
            System.out.println(e);
        }
    }
     
}
