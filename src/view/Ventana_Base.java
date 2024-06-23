
package view;

import abstracto.Instruccion;
import analisis.parser;
import analisis.scanner;
import controller.Work_Table;
import excepciones.Errores;
import instrucciones.AsignacionVar;
import instrucciones.Declaracion;
import instrucciones.subrutina.Execute;
import instrucciones.subrutina.Metodo;
import instrucciones.vectores.Declaracion_Vectores;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.LinkedList;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;
import simbolo.Arbol;
import simbolo.tablaSimbolos;
import simbolo.Simbolo;

public class Ventana_Base extends javax.swing.JFrame {
    
    private ArrayList<Work_Table> list_WorkSpace = new ArrayList<Work_Table>();
    private Work_Table actual_WorkSpace;
    private int number_Work_Table;
    private LinkedList<Errores> lista = new LinkedList<>();
    public static LinkedList<Simbolo> tabla_Simbolos = new LinkedList<Simbolo>();

    public Ventana_Base() {
        initComponents();
        list_WorkSpace.add(new Work_Table("","",""));
        list_WorkSpace.add(new Work_Table("","",""));
        list_WorkSpace.add(new Work_Table("","",""));
        list_WorkSpace.add(new Work_Table("","",""));
    }
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jTextField1 = new javax.swing.JTextField();
        jScrollBar1 = new javax.swing.JScrollBar();
        panel_Base = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        btn_Tab1 = new javax.swing.JButton();
        btn_Tab2 = new javax.swing.JButton();
        btn_Tab3 = new javax.swing.JButton();
        btn_Tab4 = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        txtA_Entrada = new javax.swing.JTextArea();
        jScrollPane3 = new javax.swing.JScrollPane();
        txt_Con = new javax.swing.JTextArea();
        jLabel2 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        btn_Ejecutar = new javax.swing.JButton();
        btn_Reportes = new javax.swing.JButton();

        jTextField1.setText("jTextField1");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        panel_Base.setBackground(new java.awt.Color(51, 51, 51));

        jLabel1.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("Entrada");

        btn_Tab1.setForeground(new java.awt.Color(0, 0, 0));
        btn_Tab1.setText("Tab1");
        btn_Tab1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_Tab1ActionPerformed(evt);
            }
        });

        btn_Tab2.setForeground(new java.awt.Color(0, 0, 0));
        btn_Tab2.setText("Tab2");
        btn_Tab2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_Tab2ActionPerformed(evt);
            }
        });

        btn_Tab3.setForeground(new java.awt.Color(0, 0, 0));
        btn_Tab3.setText("Tab3");
        btn_Tab3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_Tab3ActionPerformed(evt);
            }
        });

        btn_Tab4.setForeground(new java.awt.Color(0, 0, 0));
        btn_Tab4.setText("Tab4");
        btn_Tab4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_Tab4ActionPerformed(evt);
            }
        });

        txtA_Entrada.setEditable(false);
        txtA_Entrada.setBackground(new java.awt.Color(255, 255, 255));
        txtA_Entrada.setColumns(20);
        txtA_Entrada.setForeground(new java.awt.Color(0, 0, 0));
        txtA_Entrada.setRows(5);
        jScrollPane2.setViewportView(txtA_Entrada);

        txt_Con.setEditable(false);
        txt_Con.setBackground(new java.awt.Color(255, 255, 255));
        txt_Con.setColumns(20);
        txt_Con.setForeground(new java.awt.Color(0, 0, 0));
        txt_Con.setRows(5);
        jScrollPane3.setViewportView(txt_Con);

        jLabel2.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("Consola");

        jPanel1.setBackground(new java.awt.Color(51, 51, 51));
        jPanel1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255), 3));
        jPanel1.setForeground(new java.awt.Color(51, 51, 51));

        jButton1.setBackground(new java.awt.Color(0, 11, 0));
        jButton1.setForeground(new java.awt.Color(255, 255, 255));
        jButton1.setText("Abrir Archivo");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setBackground(new java.awt.Color(0, 11, 0));
        jButton2.setForeground(new java.awt.Color(255, 255, 255));
        jButton2.setText("Guardar Archivo");

        btn_Ejecutar.setBackground(new java.awt.Color(0, 11, 0));
        btn_Ejecutar.setForeground(new java.awt.Color(255, 255, 255));
        btn_Ejecutar.setText("Ejecutar");
        btn_Ejecutar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_EjecutarActionPerformed(evt);
            }
        });

        btn_Reportes.setBackground(new java.awt.Color(0, 11, 0));
        btn_Reportes.setForeground(new java.awt.Color(255, 255, 255));
        btn_Reportes.setText("Reportes");
        btn_Reportes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_ReportesActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 143, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btn_Ejecutar, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btn_Reportes, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(btn_Ejecutar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(btn_Reportes, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout panel_BaseLayout = new javax.swing.GroupLayout(panel_Base);
        panel_Base.setLayout(panel_BaseLayout);
        panel_BaseLayout.setHorizontalGroup(
            panel_BaseLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(panel_BaseLayout.createSequentialGroup()
                .addGap(87, 87, 87)
                .addGroup(panel_BaseLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(panel_BaseLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(jScrollPane3, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 608, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(panel_BaseLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 608, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(panel_BaseLayout.createSequentialGroup()
                                .addComponent(btn_Tab1, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btn_Tab2, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btn_Tab3, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btn_Tab4, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(90, Short.MAX_VALUE))
        );
        panel_BaseLayout.setVerticalGroup(
            panel_BaseLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_BaseLayout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(49, 49, 49)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panel_BaseLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btn_Tab1)
                    .addComponent(btn_Tab2)
                    .addComponent(btn_Tab3)
                    .addComponent(btn_Tab4))
                .addGap(27, 27, 27)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 257, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 172, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(25, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panel_Base, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panel_Base, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btn_Tab1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_Tab1ActionPerformed
        actual_WorkSpace = list_WorkSpace.get(0);
        set_Data_Table(actual_WorkSpace, number_Work_Table);
        number_Work_Table = 0;        
    }//GEN-LAST:event_btn_Tab1ActionPerformed

    private void btn_Tab2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_Tab2ActionPerformed
        actual_WorkSpace = list_WorkSpace.get(1);
        set_Data_Table(actual_WorkSpace, number_Work_Table);
        number_Work_Table = 1;
    }//GEN-LAST:event_btn_Tab2ActionPerformed

    private void btn_Tab3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_Tab3ActionPerformed
        actual_WorkSpace = list_WorkSpace.get(2);
        set_Data_Table(actual_WorkSpace, number_Work_Table);
        number_Work_Table = 2;
    }//GEN-LAST:event_btn_Tab3ActionPerformed

    private void btn_Tab4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_Tab4ActionPerformed
        actual_WorkSpace = list_WorkSpace.get(3);
        set_Data_Table(actual_WorkSpace, number_Work_Table);
        number_Work_Table = 3;
    }//GEN-LAST:event_btn_Tab4ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        //GENERAR LA INSTANCIA DE JFILECHOOSER
        javax.swing.JFileChooser explorador_Archivos = new javax.swing.JFileChooser();
        
        //PONER EL FILTRO DE LOS TIPOS DE DATOS QUE QUIERO BUSCAR
        explorador_Archivos.setFileFilter(new FileNameExtensionFilter("JC File", "jc"));
        
        // OBTENER EL NUMERO DE RESPUESTA
        // APROVADO = 0  CANCELADO = 1
        int dato_Valido = explorador_Archivos.showSaveDialog(null);
        
        // SI ES APROVADO = 0, entonces
        if(dato_Valido == JFileChooser.APPROVE_OPTION){
            //OBTENDRE LA RUTA DEL ARCHIVO QUE HE SELECCIONADO
            String ruta = explorador_Archivos.getSelectedFile().getAbsolutePath();
            read_File(ruta);
        }
    }//GEN-LAST:event_jButton1ActionPerformed

    private void btn_EjecutarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_EjecutarActionPerformed
        String entrada = txtA_Entrada.getText();
        try {
            txt_Con.setText("");
            scanner s = new scanner( new BufferedReader( new StringReader(entrada) ) );
            parser p = new parser(s);
            var resultado = p.parse();
            var ast = new Arbol((LinkedList<Instruccion>) resultado.value);
            var tabla = new tablaSimbolos();
            tabla.setNombre("Global");
            ast.setConsola("");
            ast.setTablaGlobal(tabla);
            tabla_Simbolos.removeAll(tabla_Simbolos);
            lista.removeAll(lista);
            lista.addAll(s.listaErrores);
            lista.addAll(p.listaErrores);
            
            for(var a: ast.getInstrucciones()){
                
                if(a == null) {
                    continue;
                }
                
                if ( a instanceof Metodo) {
                    ast.addFunciones(a);
                }
                // Metodos -> Funciones y Structs
            }
            
            for(var a: ast.getInstrucciones()){
                
                if(a == null) {
                    continue;
                }
                
                if ( a instanceof Declaracion || a instanceof AsignacionVar || a instanceof Declaracion_Vectores ) {
                    var res = a.interpretar(ast, tabla);
                    if (res instanceof Errores){
                        lista.add((Errores) res);
                    }
                }
                // DECLARACIONES O ASIGNACIONES
            }
            // TERCERA VUELTA AGREGAR EL START WITH
            Execute e = null;
            for(var a: ast.getInstrucciones()){
                
                if(a == null) {
                    continue;
                }
                
                if(a instanceof Execute) {
                    e = (Execute) a;
                    break;
                }
                // START_WITH
            }
            System.out.println("A ver");
            var resultadoExecute = e.interpretar(ast, tabla);
            if(resultadoExecute instanceof Errores) {
                System.out.println("Peto xD");
            }
            
            txt_Con.setText(ast.getConsola()+ "\n");

            for (var res : lista) {
                String error = "Error tipo: " + ((Errores) res).getTipo() + " " + ((Errores) res).getDescripcion()
                        + " fila: " +((Errores) res).getLinea()+ " columna: "+ ((Errores) res).getColumna();
                txt_Con.setText(txt_Con.getText() + "\n" + error);
            }            
            
        }catch(Exception ex){
            System.out.println("Algo salio mal");
            System.out.println("error: " + ex);
        }
    }//GEN-LAST:event_btn_EjecutarActionPerformed

    private void btn_ReportesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_ReportesActionPerformed
        this.reportes_Errores();
        this.reporte_Simbolos();
    }//GEN-LAST:event_btn_ReportesActionPerformed
    
    private void reporte_Simbolos() {
        StringBuilder html_Report = new StringBuilder();
        html_Report.append("<html>");
        html_Report.append("<head>");
        html_Report.append("<title>Tabla de Símbolos</title>");
        html_Report.append("<style>");
        html_Report.append("body { font-family: Arial, sans-serif; text-align: center; }");
        html_Report.append("table { margin: 0 auto; border-collapse: collapse; width: 80%; }");
        html_Report.append("th, td { border: 1px solid black; padding: 8px; }");
        html_Report.append("th { background-color: #f2f2f2; }");
        html_Report.append("tr:nth-child(even) { background-color: #f9f9f9; }");
        html_Report.append("</style>");
        html_Report.append("</head>");
        html_Report.append("<body>");
        html_Report.append("<h1>Tabla de Símbolos</h1>");
        html_Report.append("<table>");
        html_Report.append("<tr>");
        html_Report.append("<th>Número</th>");
        html_Report.append("<th>ID</th>");
        html_Report.append("<th>Tipo</th>");
        html_Report.append("<th>Entorno</th>");
        html_Report.append("<th>Tipo de Dato</th>");
        html_Report.append("<th>Valor</th>");
        html_Report.append("<th>Fila</th>");
        html_Report.append("<th>Columna</th>");
        html_Report.append("</tr>");

        int contador = 1;

        for (var simbol : tabla_Simbolos) {

            // Agregar una fila a la tabla con los valores del símbolo
            html_Report.append("<tr>");
            html_Report.append("<td>").append(contador).append("</td>");
            html_Report.append("<td>").append(simbol.getId()).append("</td>");
            html_Report.append("<td>").append(simbol.getTip()).append("</td>");
            html_Report.append("<td>").append(simbol.getEntorno()).append("</td>");
            html_Report.append("<td>").append(simbol.getTipo().getTipo().toString()).append("</td>");
            html_Report.append("<td>").append(simbol.getValor()).append("</td>");
            html_Report.append("<td>").append(simbol.getFila()).append("</td>");
            html_Report.append("<td>").append(simbol.getColumna()).append("</td>");
            html_Report.append("</tr>");

            contador++;
        }

        html_Report.append("</table>");
        html_Report.append("</body>");
        html_Report.append("</html>");

        String rutaArchivo = "C:\\Users\\matth\\Downloads\\Reporte_Simbolos.html";
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(rutaArchivo))) {
            writer.write(html_Report.toString());
            JOptionPane.showMessageDialog(null, "Reporte de símbolos generado con éxito en: " + rutaArchivo);
        } catch (IOException e) {
            System.out.println("Error al guardar la tabla de símbolos en el archivo: " + e.getMessage());
        }
    }

    
    private void reportes_Errores(){
        StringBuilder html_Report = new StringBuilder();
        html_Report.append("<html>");
        html_Report.append("<head>");
        html_Report.append("<title>Tabla de Errores</title>");
        html_Report.append("<style>");
        html_Report.append("body { font-family: Arial, sans-serif; text-align: center; }");
        html_Report.append("table { margin: 0 auto; border-collapse: collapse; width: 80%; }");
        html_Report.append("th, td { border: 1px solid black; padding: 8px; }");
        html_Report.append("th { background-color: #f2f2f2; }");
        html_Report.append("tr:nth-child(even) { background-color: #f9f9f9; }");
        html_Report.append("</style>");
        html_Report.append("</head>");
        html_Report.append("<body>");
        html_Report.append("<h1>Tabla de Errores</h1>");
        html_Report.append("<table>");
        html_Report.append("<tr>");
        html_Report.append("<th>Número</th>");
        html_Report.append("<th>Tipo</th>");
        html_Report.append("<th>Descripcion</th>");
        html_Report.append("<th>Línea</th>");
        html_Report.append("<th>Columna</th>");
        html_Report.append("</tr>");

        int contador = 1;

        for (Object obj : lista) {
            // Verificar si el objeto es una instancia de Token
            if (obj instanceof Errores) {
                Errores error = (Errores) obj;
                String tipo = error.getTipo();
                String descripcion = error.getDescripcion();
                int linea = error.getLinea();
                int columna = error.getColumna();

                // Agregar una fila a la tabla con los valores del token
                html_Report.append("<tr>");
                html_Report.append("<td>").append(contador).append("</td>");
                html_Report.append("<td>").append(tipo).append("</td>");
                html_Report.append("<td>").append(descripcion).append("</td>");
                html_Report.append("<td>").append(linea).append("</td>");
                html_Report.append("<td>").append(columna).append("</td>");
                html_Report.append("</tr>");

                contador++;
            }
        }

        html_Report.append("</table>");
        html_Report.append("</body>");
        html_Report.append("</html>");

        String rutaArchivo = "C:\\Users\\matth\\Downloads\\Reporte_Errores.html";
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(rutaArchivo))) {
            writer.write(html_Report.toString());
            JOptionPane.showMessageDialog(null, "Reporte de errores generado con éxito en: " + rutaArchivo);
        } catch (IOException e) {
            System.out.println("Error al guardar la tabla de Errores en el archivo: " + e.getMessage());
        }
    }
    
    private void set_Data_Table(Work_Table workSpace, int position) {
        txtA_Entrada.setEditable(true);
        Work_Table workSpace_Update = new Work_Table(workSpace.getPath(), txt_Con.getText(), txtA_Entrada.getText());
        list_WorkSpace.set(position, workSpace_Update);
        txtA_Entrada.setText(workSpace.getContent());
        txt_Con.setText(workSpace.getConsole());
    }

    private void read_File(String path){
        try{
            BufferedReader reader = new BufferedReader(new FileReader(path));            
            txtA_Entrada.setText("");
            String line;
            while ((line = reader.readLine()) != null){
                txtA_Entrada.append(line + '\n');
            }
        } catch (IOException e){
            JOptionPane.showMessageDialog(this, "Error reading file: " + e.getMessage(),
                "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_Ejecutar;
    private javax.swing.JButton btn_Reportes;
    private javax.swing.JButton btn_Tab1;
    private javax.swing.JButton btn_Tab2;
    private javax.swing.JButton btn_Tab3;
    private javax.swing.JButton btn_Tab4;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollBar jScrollBar1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JPanel panel_Base;
    private javax.swing.JTextArea txtA_Entrada;
    private javax.swing.JTextArea txt_Con;
    // End of variables declaration//GEN-END:variables
}
