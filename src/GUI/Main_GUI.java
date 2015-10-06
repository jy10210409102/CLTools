/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import GUI.CodeCreate.CodeCreate;
import com.chenli.createcode.CodeCreate_GUI;
import java.awt.AWTException;
import java.awt.Image;
import java.awt.MenuItem;
import java.awt.PopupMenu;
import java.awt.SystemTray;
import java.awt.Toolkit;
import java.awt.TrayIcon;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.net.DatagramSocket;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import tool.Tool;

/**
 *
 * @author Administrator
 */
public class Main_GUI extends javax.swing.JFrame {

    private Image icon;// 托盘图标
    private TrayIcon trayIcon;
    private SystemTray systemTray;// 系统托盘
    private PopupMenu pop = new PopupMenu(); // 构造一个右键弹出式菜单
    private MenuItem exit = new MenuItem("EXIT");
    private MenuItem show = new MenuItem("SHOW");

    /**
     * Creates new form Main_GUI
     */
    public Main_GUI() {
        initComponents();
        this.setResizable(false);
        this.setLocationRelativeTo(null);
        this.setTitle("众鸿工具系统");
        init(); 
        try {
            //启动时初始化
            //Tool.Tool().init();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void init() {
        //程序重复开启测试
        try {
            Tool.testDs = new DatagramSocket(Tool.testPort);
        } catch (Exception e) {
            this.setVisible(false);
            JOptionPane.showMessageDialog(null, "程序正在运行，最小化在托盘，请勿多开···");
            System.exit(0);
        }
        if (Tool.testDs == null) {
            System.exit(0);
        }

        //托盘图标部分结束
        // icon初始化
        icon = Toolkit.getDefaultToolkit().getImage(
                this.getClass().getResource("/img/备份.jpg"));//托盘图标显示的图片

        if (SystemTray.isSupported()) {
            systemTray = SystemTray.getSystemTray();// 获得系统托盘的实例
            trayIcon = new TrayIcon(icon, "众鸿工具集", pop);
            // wasw100
            pop.add(show);
            pop.add(exit);

            try {
                systemTray.add(trayIcon); // 设置托盘的图标
            } catch (AWTException e1) {
                e1.printStackTrace();
            }
            addWindowListener(new WindowAdapter() {
                public void windowIconified(WindowEvent e) {
                    dispose();// 窗口最小化时dispose该窗口
                }
            });

            trayIcon.addMouseListener(new MouseAdapter() {
                public void mouseClicked(MouseEvent e) {
                    if (e.getClickCount() == 1
                            && e.getButton() != MouseEvent.BUTTON3) {// 左击击托盘窗口再现，如果是双击就是e.getClickCount() == 2
                        setVisible(true);
                        setExtendedState(JFrame.NORMAL);//设置此 frame 的状态。
                    }
                }
            });

            show.addActionListener(new ActionListener() { // 点击"显示窗口"菜单后将窗口显示出来
                public void actionPerformed(ActionEvent e) {
                    // systemTray.remove(trayIcon); // 从系统的托盘实例中移除托盘图标
                    setVisible(true); // 显示窗口
                    setExtendedState(JFrame.NORMAL);
                }
            });
            exit.addActionListener(new ActionListener() { // 点击“退出演示”菜单后推出程序
                public void actionPerformed(ActionEvent e) {
                    //这里可以写执行退出时执行的操作
                    System.exit(0); // 退出程序
                }
            });
        }// 托盘图标部分结束

        //以下是swing程序
        setIconImage(icon);//更改程序图标

        //add(lbHello);
        //setTitle("hello");
        //setSize(200, 150);
        //自动确定窗口位置
        //setLocationByPlatform(true);
        //点击关闭按钮能够自动退出
        //setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //setVisible(true);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jRadioButtonMenuItem1 = new javax.swing.JRadioButtonMenuItem();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        jMenuItem1 = new javax.swing.JMenuItem();
        jMenuItem2 = new javax.swing.JMenuItem();
        jMenuItem4 = new javax.swing.JMenuItem();
        jMenuItem3 = new javax.swing.JMenuItem();
        jMenu3 = new javax.swing.JMenu();
        jMenuItem5 = new javax.swing.JMenuItem();
        jMenu4 = new javax.swing.JMenu();
        jMenuItem6 = new javax.swing.JMenuItem();
        jMenuItem7 = new javax.swing.JMenuItem();
        jMenuItem8 = new javax.swing.JMenuItem();
        jMenuItem9 = new javax.swing.JMenuItem();
        jMenu2 = new javax.swing.JMenu();
        jRadioButtonMenuItem2 = new javax.swing.JRadioButtonMenuItem();

        jRadioButtonMenuItem1.setSelected(true);
        jRadioButtonMenuItem1.setText("jRadioButtonMenuItem1");

        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosed(java.awt.event.WindowEvent evt) {
                formWindowClosed(evt);
            }
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });

        jMenu1.setText("工具集");

        jMenuItem1.setText("文件分割还原");
        jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem1ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem1);

        jMenuItem2.setText("同类型文件抽取");
        jMenuItem2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem2ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem2);

        jMenuItem4.setText("加密解密工具（非公司）");
        jMenuItem4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem4ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem4);

        jMenuItem3.setText("多国语言翻译工具");
        jMenuItem3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem3ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem3);

        jMenuBar1.add(jMenu1);

        jMenu3.setText("Can工具");

        jMenuItem5.setText("CAN计算器");
        jMenuItem5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem5ActionPerformed(evt);
            }
        });
        jMenu3.add(jMenuItem5);

        jMenuBar1.add(jMenu3);

        jMenu4.setText("Android");

        jMenuItem6.setText("按键状态xml生成");
        jMenuItem6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem6ActionPerformed(evt);
            }
        });
        jMenu4.add(jMenuItem6);

        jMenuItem7.setText("代码生成");
        jMenuItem7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem7ActionPerformed(evt);
            }
        });
        jMenu4.add(jMenuItem7);

        jMenuItem8.setText("adb工具");
        jMenuItem8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem8ActionPerformed(evt);
            }
        });
        jMenu4.add(jMenuItem8);

        jMenuItem9.setText("android贴图工具");
        jMenuItem9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem9ActionPerformed(evt);
            }
        });
        jMenu4.add(jMenuItem9);

        jMenuBar1.add(jMenu4);

        jMenu2.setText("快捷键");

        jRadioButtonMenuItem2.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_Q, java.awt.event.InputEvent.ALT_MASK | java.awt.event.InputEvent.CTRL_MASK));
        jRadioButtonMenuItem2.setSelected(true);
        jRadioButtonMenuItem2.setText("显示工具");
        jRadioButtonMenuItem2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioButtonMenuItem2ActionPerformed(evt);
            }
        });
        jMenu2.add(jRadioButtonMenuItem2);

        jMenuBar1.add(jMenu2);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents
//文件分割还原工具
    private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem1ActionPerformed
        // TODO add your handling code here:
        PartFile_GUI partFile = new PartFile_GUI();
        partFile.startProject();
    }//GEN-LAST:event_jMenuItem1ActionPerformed

    private void jMenuItem2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem2ActionPerformed
        // TODO add your handling code here:
        DatDispose_GUI dd = new DatDispose_GUI();
        dd.startProject();
    }//GEN-LAST:event_jMenuItem2ActionPerformed

    private void jMenuItem3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem3ActionPerformed
        // TODO add your handling code here:
        Translate_GUI tran = new Translate_GUI();
        tran.startProject();
    }//GEN-LAST:event_jMenuItem3ActionPerformed

    private void jMenuItem4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem4ActionPerformed
        // TODO add your handling code here:
        JiaMi jm = new JiaMi();
        jm.startProject();
    }//GEN-LAST:event_jMenuItem4ActionPerformed

    private void jMenuItem5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem5ActionPerformed
        // TODO add your handling code here:
        Calculating_GUI c = new Calculating_GUI();
        c.startProject();
    }//GEN-LAST:event_jMenuItem5ActionPerformed

    private void formWindowClosed(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosed
        // TODO add your handling code here:
        //   System.out.println("退出");
    }//GEN-LAST:event_formWindowClosed

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        // TODO add your handling code here:
        // System.out.println("退出");
    }//GEN-LAST:event_formWindowClosing

    private void jRadioButtonMenuItem2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRadioButtonMenuItem2ActionPerformed
        // TODO add your handling code here:

        if (this.isVisible()) {
            this.setVisible(false);
        }else{
              this.setVisible(true);
        }
    }//GEN-LAST:event_jRadioButtonMenuItem2ActionPerformed

    private void jMenuItem6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem6ActionPerformed
        // TODO add your handling code here:
           java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new CodeCreate().setVisible(true);
            }
        });
    }//GEN-LAST:event_jMenuItem6ActionPerformed

    private void jMenuItem7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem7ActionPerformed
        // TODO add your handling code here:
           java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new CodeCreate_GUI().setVisible(true);
            }
        });
    }//GEN-LAST:event_jMenuItem7ActionPerformed

    private void jMenuItem8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem8ActionPerformed
        // TODO add your handling code here:
         java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new AdbOperater().setVisible(true);
            }
        });
    }//GEN-LAST:event_jMenuItem8ActionPerformed

    private void jMenuItem9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem9ActionPerformed
        // TODO add your handling code here:
        
        
    }//GEN-LAST:event_jMenuItem9ActionPerformed

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
            java.util.logging.Logger.getLogger(Main_GUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Main_GUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Main_GUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Main_GUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Main_GUI().setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenu jMenu3;
    private javax.swing.JMenu jMenu4;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem jMenuItem2;
    private javax.swing.JMenuItem jMenuItem3;
    private javax.swing.JMenuItem jMenuItem4;
    private javax.swing.JMenuItem jMenuItem5;
    private javax.swing.JMenuItem jMenuItem6;
    private javax.swing.JMenuItem jMenuItem7;
    private javax.swing.JMenuItem jMenuItem8;
    private javax.swing.JMenuItem jMenuItem9;
    private javax.swing.JRadioButtonMenuItem jRadioButtonMenuItem1;
    private javax.swing.JRadioButtonMenuItem jRadioButtonMenuItem2;
    // End of variables declaration//GEN-END:variables
}