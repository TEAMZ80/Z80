/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package panta;

import java.io.File;
import java.util.ArrayList;
import javax.swing.JFileChooser;

/**
 *
 * @author alacr
 */
public class Pantalla extends javax.swing.JFrame {

    /**
     * Creates new form Pantalla
     */
    File archivo;
    Archivo files;
    int numAct;
    boolean mac, hex, lst;

    public Pantalla() {
        initComponents();
        this.setLocationRelativeTo(null);
        this.numAct = 0;
        mac = true;
        lst = true;
        hex = true;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jFileChooser1 = new javax.swing.JFileChooser();
        jCheckBox1 = new javax.swing.JCheckBox();
        jPanel1 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        ruta = new javax.swing.JTextField();
        macro = new javax.swing.JCheckBox();
        tabladesimbolos = new javax.swing.JCheckBox();
        codigoobjeto = new javax.swing.JCheckBox();
        Salida = new javax.swing.JPanel();
        Carga = new javax.swing.JLabel();
        btnaceptar = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();

        jLabel1.setText("MACROENSAMBLADOR");

        jCheckBox1.setText("jCheckBox1");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        jLabel2.setFont(new java.awt.Font("Sitka Small", 0, 24)); // NOI18N
        jLabel2.setText("MACROENSAMBLADOR");

        jLabel3.setFont(new java.awt.Font("Serif", 0, 14)); // NOI18N
        jLabel3.setText("ARCHIVO");

        ruta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rutaActionPerformed(evt);
            }
        });

        macro.setBackground(new java.awt.Color(255, 255, 255));
        macro.setSelected(true);
        macro.setText("MACRO");
        macro.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                macroItemStateChanged(evt);
            }
        });
        macro.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                macroStateChanged(evt);
            }
        });
        macro.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                macroActionPerformed(evt);
            }
        });

        tabladesimbolos.setBackground(new java.awt.Color(255, 255, 255));
        tabladesimbolos.setSelected(true);
        tabladesimbolos.setText("TABLA DE SIMBOLOS");
        tabladesimbolos.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                tabladesimbolosStateChanged(evt);
            }
        });

        codigoobjeto.setBackground(new java.awt.Color(255, 255, 255));
        codigoobjeto.setSelected(true);
        codigoobjeto.setText("CODIGO OBJETO");
        codigoobjeto.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                codigoobjetoStateChanged(evt);
            }
        });

        Salida.setForeground(new java.awt.Color(204, 204, 204));

        Carga.setText("PREPARANDO....");

        btnaceptar.setText("OK!");
        btnaceptar.setEnabled(false);
        btnaceptar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnaceptarMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout SalidaLayout = new javax.swing.GroupLayout(Salida);
        Salida.setLayout(SalidaLayout);
        SalidaLayout.setHorizontalGroup(
            SalidaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(SalidaLayout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addGroup(SalidaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(Carga)
                    .addComponent(btnaceptar, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(29, Short.MAX_VALUE))
        );
        SalidaLayout.setVerticalGroup(
            SalidaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(SalidaLayout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addComponent(Carga)
                .addGap(18, 18, 18)
                .addComponent(btnaceptar)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jButton2.setText("ABRIR");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(36, 36, 36)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel3)
                    .addComponent(jLabel2)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addGroup(jPanel1Layout.createSequentialGroup()
                            .addComponent(ruta, javax.swing.GroupLayout.PREFERRED_SIZE, 235, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jButton2))
                        .addGroup(jPanel1Layout.createSequentialGroup()
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(codigoobjeto)
                                .addComponent(tabladesimbolos)
                                .addComponent(macro))
                            .addGap(18, 18, 18)
                            .addComponent(Salida, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(50, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(28, 28, 28)
                .addComponent(jLabel2)
                .addGap(18, 18, 18)
                .addComponent(jLabel3)
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(ruta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton2))
                .addGap(26, 26, 26)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(macro)
                        .addGap(18, 18, 18)
                        .addComponent(tabladesimbolos)
                        .addGap(18, 18, 18)
                        .addComponent(codigoobjeto))
                    .addComponent(Salida, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(31, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void rutaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rutaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_rutaActionPerformed

    private void macroActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_macroActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_macroActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
        JFileChooser jf = new JFileChooser();
        jf.showOpenDialog(this);
        archivo = jf.getSelectedFile();
        if (archivo != null) {
            ruta.setText(archivo.getAbsolutePath());
        }
    }//GEN-LAST:event_jButton2ActionPerformed

    private void macroItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_macroItemStateChanged
        // TODO add your handling code here:
        /*if ( this.numAct >= 0 && !this.macro.isSelected()){
         this.numAct++;
         this.btnaceptar.enable(true);
         }
         if ( this.numAct == 1 && this.macro.isSelected() ){
         this.numAct--;
         this.btnaceptar.enable(false);
         }
         if ( this.numAct >= 1 && !this.macro.isSelected() ){
         this.numAct++;
         }*/
    }//GEN-LAST:event_macroItemStateChanged

    private void btnaceptarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnaceptarMouseClicked
        // TODO add your handling code here:
        this.Carga.setText("CARGANDO...");
        procesar();
    }//GEN-LAST:event_btnaceptarMouseClicked

    private void macroStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_macroStateChanged
        // TODO add your handling code here:
        this.mac = this.macro.isSelected();
        verificar();
    }//GEN-LAST:event_macroStateChanged

    private void tabladesimbolosStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_tabladesimbolosStateChanged
        // TODO add your handling code here:
        this.lst = this.tabladesimbolos.isSelected();
        verificar();
    }//GEN-LAST:event_tabladesimbolosStateChanged

    private void codigoobjetoStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_codigoobjetoStateChanged
        // TODO add your handling code here:
        this.hex = this.codigoobjeto.isSelected();
        verificar();
    }//GEN-LAST:event_codigoobjetoStateChanged
  
    void procesar() {
        
        files = new Archivo(archivo.getAbsolutePath());
        files.obtenerAtributos();
        if ( !files.leerArchivo() ) {
            this.Carga.setText("ERROR");
            return;
        }
        Macroensamblador macro = new Macroensamblador(files.lineas);
        String out = macro.macroensamblar();
        if (mac){
            if ( out == "LISTO!"){
                this.Carga.setText("GUARDANDO..!");
                if ( this.files.Archivo(macro.lineas, "M.ASM") ){
                    this.Carga.setText("MACRO GUARDADA!");  
                }else {
                    this.Carga.setText("ERROR AL GUARDAR");
                }
            } else {
                this.Carga.setText(out);
            }
        }
        files.preparar();
        Z80 en = new Z80();
        en.procesar(files);
        if ( hex ){
            files.Archivo(en.getHEX(), ".HEX");
        }
        if (lst) {
            files.Archivo(en.getLST(), ".LST");
        }
        if ( false ) {
            this.Carga.setText("ERROR AL ENSAMBLAR"); 
        }

    }
    
    public void verificar(){
        if ( !this.hex && !this.lst && !this.mac || this.ruta.getText().isEmpty() ){
            this.btnaceptar.setEnabled(false);
        } else {
            this.btnaceptar.setEnabled(true);
        }
    }
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Pantalla.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Pantalla.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Pantalla.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Pantalla.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Pantalla().setVisible(true);
            }
        });

    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel Carga;
    private javax.swing.JPanel Salida;
    private javax.swing.JButton btnaceptar;
    private javax.swing.JCheckBox codigoobjeto;
    private javax.swing.JButton jButton2;
    private javax.swing.JCheckBox jCheckBox1;
    private javax.swing.JFileChooser jFileChooser1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JCheckBox macro;
    private javax.swing.JTextField ruta;
    private javax.swing.JCheckBox tabladesimbolos;
    // End of variables declaration//GEN-END:variables
}
