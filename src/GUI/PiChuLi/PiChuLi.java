/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI.PiChuLi;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Administrator
 */
public class PiChuLi extends javax.swing.JFrame {

    /**
     * Creates new form PiChuLi
     */
    public PiChuLi() {
        initComponents();
    }

    /*
     copy .\Rel\appDisc.exe .\Rel\appAux.exe
     copy .\Rel\appDisc.exe .\Rel\appIpod.exe
     copy .\Rel\appDisc.exe .\Rel\appPark.exe
     copy .\Rel\appDisc.exe .\Rel\appSd.exe
     copy .\Rel\appDisc.exe .\Rel\appUsb.exe
     copy .\Rel\appDisc.exe .\Rel\appCmmb.exe
     copy .\Rel\appDisc.exe .\Rel\appTv.exe
     copy .\Rel\appDisc.exe .\Rel\appdvr.exe
     copy .\Rel\appDisc.exe .\Rel\appradar.exe
     * 
     * 
     * 
     * 
     * 
     * copy .\Rel\appCarSource.exe .\Rel\appCarDoor.exe
     copy .\Rel\appCarSource.exe .\Rel\appCarUsb.exe
     copy .\Rel\appCarSource.exe .\Rel\appCarIpod.exe
     copy .\Rel\appCarSource.exe .\Rel\appCarInfo.exe
     copy .\Rel\appCarSource.exe .\Rel\appCarBt.exe

     copy .\Rel\appSetup.exe .\Rel\appSCREEN.exe

     copy .\Rel\FreeType.dll   .\FlashUI\FreeType.dll
     copy .\Rel\TextEngine.dll .\FlashUI\TextEngine.dll
     copy .\Rel\mcuComm.dll    .\FlashUI\mcuComm.dll
     copy .\Rel\cancomm.dll    .\FlashUI\cancomm.dll

     copy .\Rel\appBt.exe .\FlashUI\appBt.exe

     copy .\Rel\appCalculator.exe .\FlashUI\appCalculator.exe

     copy .\Rel\appCanbus.exe .\FlashUI\appCanbus.exe

     copy .\Rel\appCarDoor.exe   .\FlashUI\appCarDoor.exe
     copy .\Rel\appCarUsb.exe    .\FlashUI\appCarUsb.exe
     copy .\Rel\appCarIpod.exe   .\FlashUI\appCarIpod.exe
     copy .\Rel\appCarInfo.exe   .\FlashUI\appCarInfo.exe
     copy .\Rel\appCarBt.exe   .\FlashUI\appCarBt.exe

     copy .\Rel\appDisc.exe .\FlashUI\appDisc.exe
     copy .\Rel\appAux.exe  .\FlashUI\appAux.exe
     copy .\Rel\appIpod.exe .\FlashUI\appIpod.exe
     copy .\Rel\appPark.exe .\FlashUI\appPark.exe
     copy .\Rel\appSd.exe   .\FlashUI\appSd.exe
     copy .\Rel\appUsb.exe  .\FlashUI\appUsb.exe
     copy .\Rel\appCmmb.exe .\FlashUI\appCmmb.exe
     copy .\Rel\appTv.exe   .\FlashUI\appTv.exe

     copy .\Rel\appDisp.exe .\FlashUI\appDisp.exe

     copy .\Rel\appMain.exe .\FlashUI\appMain.exe

     copy .\Rel\AppPhoto.exe .\FlashUI\AppPhoto.exe

     copy .\Rel\appRadio.exe .\FlashUI\appRadio.exe
     copy .\Rel\appRadio.exe .\FlashUI\appLamp.exe
     copy .\Rel\appRadio.exe .\FlashUI\appOff.exe

     copy .\Rel\appSetup.exe .\FlashUI\appSetup.exe
     copy .\Rel\appSCREEN.exe .\FlashUI\appSCREEN.exe

     copy .\Rel\appTPMS.exe .\FlashUI\appTPMS.exe

     copy .\Rel\appVirtualDisc.exe .\FlashUI\appVirtualDisc.exe

     copy .\Rel\appForceExitMap.exe .\FlashUI\appForceExitMap.exe

     copy .\Rel\zhmain.exe .\FlashUI\zhmain.exe
     copy .\Rel\FactorySet.exe .\FlashUI\FactorySet.exe

     copy .\FlashUI\appDisc.txt .\FlashUI\appAux.txt
     copy .\FlashUI\appDisc.txt .\FlashUI\appIpod.txt
     copy .\FlashUI\appDisc.txt .\FlashUI\appPark.txt
     copy .\FlashUI\appDisc.txt .\FlashUI\appSd.txt
     copy .\FlashUI\appDisc.txt .\FlashUI\appUsb.txt
     copy .\FlashUI\appDisc.txt .\FlashUI\appCmmb.txt
     copy .\FlashUI\appDisc.txt .\FlashUI\appTv.txt
     */
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();
        jButton1 = new javax.swing.JButton();
        jTextField1 = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jTextArea1.setColumns(20);
        jTextArea1.setRows(5);
        jScrollPane1.setViewportView(jTextArea1);

        jButton1.setText("jButton1");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButton1)
                .addGap(28, 28, 28))
            .addGroup(layout.createSequentialGroup()
                .addGap(34, 34, 34)
                .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 272, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(178, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 240, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 44, Short.MAX_VALUE)
                .addComponent(jButton1)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        String txt = jTextField1.getText();
        try {
             File f=new File("\\Rel\\appDisc.exe");
            System.out.println( f.getAbsoluteFile());
            for(int i=0;i<files.length;i++){
            PiChuLi.writeFileTo("\\Rel\\appDisc.exe ",files[i]);            
            }
        } catch (Exception ex) {
            Logger.getLogger(PiChuLi.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jButton1ActionPerformed
    String[] files = new String[]{
        ".\\Rel\\appAux.exe",
        ".\\Rel\\appIpod.exe",
        ".\\Rel\\appPark.exe",
        ".\\Rel\\appSd.exe",
        ".\\Rel\\appUsb.exe",
        ".\\Rel\\appCmmb.exe",
        ".\\Rel\\appTv.exe",
        ".\\Rel\\appdvr.exe",
        ".\\Rel\\appradar.exe",};
    /*
     copy .\Rel\appDisc.exe .\Rel\appAux.exe
     copy .\Rel\appDisc.exe .\Rel\appIpod.exe
     copy .\Rel\appDisc.exe .\Rel\appPark.exe
     copy .\Rel\appDisc.exe .\Rel\appSd.exe
     copy .\Rel\appDisc.exe .\Rel\appUsb.exe
     copy .\Rel\appDisc.exe .\Rel\appCmmb.exe
     copy .\Rel\appDisc.exe .\Rel\appTv.exe
     copy .\Rel\appDisc.exe .\Rel\appdvr.exe
     copy .\Rel\appDisc.exe .\Rel\appradar.exe
     * 
     */
    //写文件到指定目录 返回改后的文件名

    public static String writeFileTo(String path, String savePath) throws Exception {
        File file = new File(path);
        InputStream is = new FileInputStream(path);
        File saveDir = new File(savePath);
        if (!saveDir.exists()) {
            saveDir.mkdirs();
        }
        File saveFile = new File(savePath + "\\" + file.getName());
//        if (saveFile.exists()) {//如果文件存在则重命名
//            saveFile = new File(savePath + "\\" + file.getName() + "-重名" + new Date().getTime());
//        }
        OutputStream os = new FileOutputStream(saveFile);
        byte[] data = new byte[1024 * 1024];
        if (file.length() < 1024 * 1024) {//如果文件小于一兆
            data = new byte[Integer.parseInt(file.length() + "")];
            is.read(data, 0, data.length);
            os.write(data, 0, data.length);
        } else {
            long yuShu = file.length() % (1024 * 1024);//余数
            long duoS = file.length() / 1024 / 1024;
            if (yuShu != 0) {
                duoS++;
            }
            int count = 0;
            while (is.read(data, 0, data.length) != -1) {
                count++;
                os.write(data, 0, data.length);
                if (yuShu != 0 && duoS == count - 1) {
                    data = new byte[Integer.parseInt(yuShu + "")];
                }
            }
        }
        is.close();
        os.close();
        return saveFile.getName();
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
            java.util.logging.Logger.getLogger(PiChuLi.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(PiChuLi.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(PiChuLi.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(PiChuLi.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new PiChuLi().setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextArea jTextArea1;
    private javax.swing.JTextField jTextField1;
    // End of variables declaration//GEN-END:variables
}
