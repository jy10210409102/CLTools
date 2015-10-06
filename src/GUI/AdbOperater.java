/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import com.chenli.adb.Operater;
import java.awt.Color;
import java.awt.Component;
import java.awt.Image;
import java.awt.MenuItem;
import java.awt.PopupMenu;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DropTarget;
import java.awt.dnd.DropTargetDragEvent;
import java.awt.dnd.DropTargetDropEvent;
import java.awt.dnd.DropTargetEvent;
import java.awt.dnd.DropTargetListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.ItemEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.ListCellRenderer;

/**
 *
 * @author Administrator
 */
public class AdbOperater extends javax.swing.JFrame {

    /**
     * 当前选中过的设备
     */
    Device currentDev = null;
    /**
     * 安装状态
     */
    boolean isInstall = false;
    /**
     * 文件夹图标对象
     */
    ImageIcon dirIcon = new javax.swing.ImageIcon(getClass().getResource("/img/dir.jpg"));//new ImageIcon("/img/dir.jpg");
    /**
     * 文件图标对象
     */
    ImageIcon fileIcon = new javax.swing.ImageIcon(getClass().getResource("/img/file.jpg"));//new ImageIcon("/img/file.jpg");
    /**
     * 设备列表
     */
    ArrayList<Device> deviceList;
    /**
     * 设备根目录
     */
    final String ROOTPATH = "/mnt/";
    /**
     * 当前路径
     */
    String currentPath = ROOTPATH;
    /**
     * 当前文件夹列表
     */
    ArrayList<String> dirsList;
    /**
     * 当前文件列表
     */
    ArrayList<String> filesList;
    /**
     * 上一级目录
     */
    String str = "···";
    /**
     * apkPath 组件id
     */
    final int APKPATH_ID = 1;
    /**
     * 列表id
     */
    final int LIST_ID = 2;
    /**
     * 弹出菜单
     */
    PopupMenu popupMenu1;
    
    /**
     * 拷出路径
     */
    final String OUT_PATH = "E:\\ADB";
    /**
     * Creates new form AdbOperater
     */
    public AdbOperater() {
        initComponents();
        this.setResizable(false);
        this.setLocationRelativeTo(null);
        this.setTitle("ADB工具");
        deviceName.setText("");
        this.deviceList = new ArrayList<>();
        dirsList = new ArrayList<>();
        filesList = new ArrayList<>();
        dirIcon.setImage(dirIcon.getImage().getScaledInstance(20, 20, Image.SCALE_DEFAULT));
        fileIcon.setImage(fileIcon.getImage().getScaledInstance(20, 20, Image.SCALE_DEFAULT));
        setPopupMenu();
        try {
            init();
            // setListData(); //设置列表数据
        } catch (Exception ex) {
            Logger.getLogger(AdbOperater.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * 初始化
     */
    private void init() throws Exception {
        File file = new File(OUT_PATH);  //缓存目录
        if(!file.exists()){
            file.mkdirs();
        }
        //获得所有设备id以及设备名称
        // getDevices();
        loadDev();
        //添加拖拽效果
        DndTargetListener dnd = new DndTargetListener(APKPATH_ID);
        apkPath.setDropTarget(new DropTarget(this, DnDConstants.ACTION_REFERENCE, dnd, true));
        jPanel1.setDropTarget(new DropTarget(this, DnDConstants.ACTION_REFERENCE, dnd, true));
        DndTargetListener listDnd = new DndTargetListener(LIST_ID);
        fileList.setDropTarget(new DropTarget(this, DnDConstants.ACTION_REFERENCE, listDnd, true));
    }

    /**
     * 给列表添加弹出菜单
     */
    private void setPopupMenu() {
        popupMenu1 = new PopupMenu();
        MenuItem refresh = new MenuItem("refresh");		//刷新
        MenuItem del = new MenuItem("delete");			//删除
        //子菜单添加项
        popupMenu1.add(refresh);
        popupMenu1.add(del);
        //添加弹出菜单
        fileList.add(popupMenu1);
        //子项添加监听
        refresh.addActionListener(refreshActionListener);
        del.addActionListener(delActionListener);

        //列表添加监听
        fileList.addMouseListener(listMouseListener);

    }
    MouseListener listMouseListener = new MouseListener() {
        @Override
        public void mouseReleased(MouseEvent event) { // 释放点击时调用
        }

        @Override
        public void mousePressed(MouseEvent event) { // 鼠标按下时触发    这里做无图片文本的创建
        }

        @Override
        public void mouseExited(MouseEvent event) { // 鼠标移出时触发
        }

        @Override
        public void mouseEntered(MouseEvent event) { // 鼠标移入时触发
            // System.out.println("mouseEntered");
        }

        @Override
        public void mouseClicked(MouseEvent event) { // 点击事件 用做判断右点击 开启菜单
            if (isMouseRightKey(event)) {
                popupMenu1.show(fileList, event.getX(), event.getY());//弹出菜单
            }
        }
    };

    /**
     * 判断是否是鼠标右键点击
     *
     * @param event
     * @return
     */
    public boolean isMouseRightKey(MouseEvent event) {
        int mods = event.getModifiers();
        // 鼠标右键
        if ((mods & InputEvent.BUTTON3_MASK) != 0) {
            return true;
        }
        return false;
    }
    /**
     * 刷新监听
     */
    ActionListener refreshActionListener = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent event) {
            setListData(); //设置列表数据
        }
    };
    /**
     * 删除监听
     */
    ActionListener delActionListener = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent event) {
            try {
                String path = currentPath + fileList.getSelectedValue();
                printLog("正在删除：" + path);
                Operater.getInstance().delFileOrDir(currentDev.deviceID, path);
                printLog("删除成功！");
                setListData(); //设置列表数据
            } catch (Exception ex) {
                Logger.getLogger(AdbOperater.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    };

    /**
     * 获得设备信息
     */
    private void getDevices() throws Exception {
        String[] devices = Operater.getInstance().getDevicesID();  //获得设备id
        deviceList.clear();
        String deviceID;
        String deveicName;
        for (int i = 0; i < devices.length; i++) {
            deviceID = devices[i];
            deveicName = Operater.getInstance().getDeviceName(devices[i]);
            if ("/system/bin/sh: grep: not found".equals(deveicName)) {
                deveicName = deviceID;
            }
            deviceList.add(new Device(deviceID, deveicName));
            System.out.println("设备ID：" + devices[i]);
            System.out.println("设备名称" + Operater.getInstance().getDeviceName(devices[i]));////获得设备名称
        }
        if (deviceList.size() == 0) {
            deviceName.setText("无设备连接");
        }
        updataDeviceBox();
    }

    /**
     * 加载设备
     */
    private void loadDev() {
        new Thread() {
            @Override
            public void run() {
                // while(true){
                try {
                    deviceName.setText("正在加载设备");
                    getDevices();
                    //deviceName.setText("正在加载设备");
                    //   sleep(5000);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
                // }
            }
        }.start();
    }

    /**
     * 更新所有已连接的设备
     */
    private void updataDeviceBox() {
        deviceBox.removeAllItems();
        for (int i = 0; i < deviceList.size(); i++) {
            deviceBox.addItem(deviceList.get(i).deviceName);
        }
    }

    public class DndTargetListener implements DropTargetListener {

        int id;

        public DndTargetListener(int id) {
            this.id = id;
        }

        @Override
        public void dragEnter(DropTargetDragEvent arg0) {
            System.out.println("dragEnter");
        }

        @Override
        public void dragExit(DropTargetEvent arg0) {
            System.out.println("dragExit");
        }

        @Override
        public void dragOver(DropTargetDragEvent arg0) {
            //    System.out.println("dragOver");
        }

        @Override
        public void drop(DropTargetDropEvent arg0) {
            System.out.println("drop");
            arg0.acceptDrop(DnDConstants.ACTION_REFERENCE);
            if (arg0.isDataFlavorSupported(DataFlavor.javaFileListFlavor)) {
                try {
                    Transferable tf = arg0.getTransferable();
                    List<File> list = (List<File>) tf.getTransferData(DataFlavor.javaFileListFlavor);
                    for (File f : list) {
                        if (this.id == APKPATH_ID) {
                            if (f.exists() && f.isFile()) {//如果是文件
                                apkPath.setText(f.getAbsolutePath());
                                return;
                            } else if (f.exists() && f.isDirectory()) {//如果是文件夹
                                // 这里不对拖拽文件夹做操作
                                apkPath.setText("");
                                return;
                            }
                        } else if (this.id == LIST_ID) {
                            System.out.println("@!@@@@@");
                            if (f.exists() && f.isFile()) {//如果是文件
                                final String filePath = f.getAbsolutePath();
                                System.out.println("!!!!!!!!!!!!!" + filePath.substring(filePath.lastIndexOf("\\") + 1, filePath.length()));
                                if (Operater.getInstance().isHaveChinese(filePath.substring(filePath.lastIndexOf("\\") + 1, filePath.length())) || Operater.getInstance().isHaveChinese(currentPath)) {
                                    JOptionPane.showMessageDialog(AdbOperater.this, "路径、文件夹名、文件名以及目标路径不可包含中文", "错误", JOptionPane.INFORMATION_MESSAGE);
                                    return;
                                }
                                new Thread() {
                                    public void run() {
                                        printLog("开始移动文件:" + filePath);
                                        try {
                                            Operater.getInstance().moveFile(currentDev.deviceID, filePath, currentPath);
                                        } catch (Exception ex) {
                                            Logger.getLogger(AdbOperater.class.getName()).log(Level.SEVERE, null, ex);
                                        }
                                        setListData(); //更新列表
                                        printLog("移动文件完成！");
                                    }
                                }.start();
                                return;
                            } else if (f.exists() && f.isDirectory()) {//如果是文件夹
                                // 这里不对拖拽文件夹做操作
                                final String filePath = f.getAbsolutePath();
                                if (Operater.getInstance().isHaveChinese(filePath.substring(filePath.lastIndexOf("\\") + 1, filePath.length())) || Operater.getInstance().isHaveChinese(currentPath)) {
                                    JOptionPane.showMessageDialog(AdbOperater.this, "路径、文件夹名、文件名以及目标路径不可包含中文", "错误", JOptionPane.INFORMATION_MESSAGE);
                                    return;
                                }
                                new Thread() {
                                    public void run() {
                                        printLog("开始移动文件夹: " + filePath);
                                        try {
                                            Operater.getInstance().moveFile(currentDev.deviceID, filePath, currentPath);
                                        } catch (Exception ex) {
                                            Logger.getLogger(AdbOperater.class.getName()).log(Level.SEVERE, null, ex);
                                        }
                                        setListData(); //更新列表
                                        printLog("移动文件夹完成！");
                                    }
                                }.start();

                                return;
                            }
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        @Override
        public void dropActionChanged(DropTargetDragEvent arg0) {
            System.out.println("dropActionChanged");
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        deviceName = new javax.swing.JLabel();
        installApk = new javax.swing.JButton();
        apkPath = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        deviceBox = new javax.swing.JComboBox();
        jButton2 = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        text = new javax.swing.JTextArea();
        jScrollPane2 = new javax.swing.JScrollPane();
        fileList = new javax.swing.JList();
        selectPath = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jPanel1.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel1.setText("当前设备：");

        deviceName.setFont(new java.awt.Font("幼圆", 1, 18)); // NOI18N
        deviceName.setForeground(new java.awt.Color(102, 0, 0));
        deviceName.setText("设备未连接");

        installApk.setText("安装APK");
        installApk.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                installApkActionPerformed(evt);
            }
        });

        jLabel3.setText("可选设备：");

        deviceBox.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                deviceBoxItemStateChanged(evt);
            }
        });

        jButton2.setText("签名安装");

        jLabel4.setText("APK 路径:");

        jButton1.setText("刷新设备");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(deviceName, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel4)
                                    .addComponent(jLabel3))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(apkPath)
                                    .addComponent(deviceBox, 0, 195, Short.MAX_VALUE)))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(installApk)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jButton2)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jButton1)))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(deviceName))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(deviceBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(16, 16, 16)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(apkPath, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4))
                .addGap(27, 27, 27)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(installApk)
                    .addComponent(jButton2)
                    .addComponent(jButton1))
                .addContainerGap(30, Short.MAX_VALUE))
        );

        text.setColumns(20);
        text.setRows(5);
        jScrollPane1.setViewportView(text);

        fileList.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                fileListMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(fileList);

        selectPath.setFont(new java.awt.Font("幼圆", 1, 14)); // NOI18N
        selectPath.setText("······");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane1))
                .addGap(28, 28, 28)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(selectPath, javax.swing.GroupLayout.PREFERRED_SIZE, 302, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 338, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(selectPath, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 340, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jScrollPane1)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void installApkActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_installApkActionPerformed
        // TODO add your handling code here:
        if (isInstall) {
            printLog("安装中，请勿连续点击····");
            JOptionPane.showMessageDialog(AdbOperater.this, "安装中，请勿连续点击····", "提示", JOptionPane.INFORMATION_MESSAGE);
        }
        String apkPathStr = apkPath.getText().trim();
        File file = new File(apkPathStr);
        if (!apkPathStr.endsWith(".apk") || !file.exists()) {
            printLog("安装失败,路径错误:" + apkPathStr);
            JOptionPane.showMessageDialog(AdbOperater.this, "APK路径错误", "错误", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        final String apkName = file.getName();
        new Thread() {
            public void run() {
                printLog(apkName + " 安装中····");
                isInstall = true;
                try {
                    Operater.getInstance().installApp(currentDev.deviceID, apkPath.getText());
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
                printLog(apkName + " 安装完成！");
                isInstall = false;
            }
        }.start();
    }//GEN-LAST:event_installApkActionPerformed

    /**
     * 日志输出
     */
    private void printLog(String txt) {
        System.out.println(txt);
        text.append(txt + "\r\n");
    }

    private void deviceBoxItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_deviceBoxItemStateChanged
        if (evt.getStateChange() == ItemEvent.SELECTED) {
            System.out.println("当前选中过的是 item = " + deviceBox.getSelectedItem());
            //设置并更新当前选中的设备
            setUpdataCurDev();
        }
    }//GEN-LAST:event_deviceBoxItemStateChanged

    /**
     * 列表点击事件处理
     */
    private void fileListMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_fileListMouseClicked
        // TODO add your handling code here:
        int index = fileList.getSelectedIndex();
        if (index != -1) {
            if (evt.getClickCount() == 2) {
                if (index >= dirsList.size() + 1) {
                    try {
                        //printLog("双击的为文件" );
                        //拷贝出文件并打开
                        String fileName = (String) fileList.getSelectedValue();
                        if (Operater.getInstance().isHaveChinese(fileName)) {
                            JOptionPane.showMessageDialog(AdbOperater.this, "路径、文件夹名、文件名以及目标路径不可包含中文", "错误", JOptionPane.INFORMATION_MESSAGE);
                            return;
                        }
                        printLog("拷出文件中···       " + fileName);
                        Operater.getInstance().outOpenFile(currentDev.deviceID, OUT_PATH, currentPath + "/" + fileName);
                         printLog("打开文件···       " + fileName);
                        Operater.getInstance().openFile(OUT_PATH + "\\" + fileName);
                    } catch (Exception ex) {
                        Logger.getLogger(AdbOperater.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    return;
                }
                twoClick(fileList.getSelectedValue()+"");
            }
        }
    }//GEN-LAST:event_fileListMouseClicked

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        loadDev();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void twoClick(String value) {
        if (Operater.getInstance().isHaveChinese(value)) {
            JOptionPane.showMessageDialog(AdbOperater.this, "不让打开中文目录", "错误", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        if (str.equals(value)) {//如果跳往上一级目录
            if (currentPath.equals(ROOTPATH)) {
                return;
            }
            currentPath = currentPath.substring(0, currentPath.length() - 2);
            currentPath = currentPath.substring(0, currentPath.lastIndexOf("/") + 1);
        } else {                //拼接路径
            currentPath += value + "/";
        }
        //printLog("双击处理  路径：" + currentPath);
        setListData();
    }

    /**
     * 设置并更新当前选中的设备
     */
    private void setUpdataCurDev() {
        //当前选中过的是
        int selectIndex = deviceBox.getSelectedIndex();
        currentDev = deviceList.get(selectIndex);
        deviceName.setText(currentDev.deviceName);
        //printLog("---------\r\n当前选中设备：" + currentDev.deviceName + "\r\n---------");
        printLog("当前选中设备：" + currentDev.deviceName);
        currentPath = ROOTPATH;
        setListData(); //设置列表数据
    }

    public class MyCellRenderer extends JLabel implements ListCellRenderer {

        public MyCellRenderer() {
        }

        @Override
        public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
            String s = value.toString();
            setText(s);
            setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
            if (isSelected) {
                setBackground(list.getSelectionBackground());
                setForeground(list.getSelectionForeground());
            } else {
                setBackground(list.getBackground());
                setForeground(list.getForeground());
            }
            if (index < dirsList.size() + 1) {
                setIcon(dirIcon);
            } else {
                setIcon(fileIcon);
            }

            setEnabled(list.isEnabled());
            setFont(list.getFont());
            setOpaque(true);
            if (index % 2 == 0) {
                this.setBackground(Color.LIGHT_GRAY);
            } else {
                this.setBackground(Color.GRAY);
            }
            return this;
        }
    }

    /**
     * 设置列表数据
     */
    private void setListData() {
        DefaultListModel m = new DefaultListModel();
        selectPath.setText(currentPath);
        try {
            Operater.getInstance().getDirFile(currentDev.deviceID, currentPath, this.dirsList, this.filesList);
        } catch (Exception ex) {
            Logger.getLogger(AdbOperater.class.getName()).log(Level.SEVERE, null, ex);
        }

        m.addElement(str);
        for (String str : dirsList) {
            m.addElement(str);
        }
        for (String str : filesList) {
            m.addElement(str);
        }
        fileList.setModel(m);
        fileList.setCellRenderer(new MyCellRenderer());
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
            java.util.logging.Logger.getLogger(AdbOperater.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(AdbOperater.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(AdbOperater.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(AdbOperater.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new AdbOperater().setVisible(true);
            }
        });
    }

    /**
     * 设备描述类
     */
    class Device {

        public String deviceID;
        public String deviceName;

        public Device(String deviceID, String deviceName) {
            this.deviceID = deviceID;
            this.deviceName = deviceName;
        }
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField apkPath;
    private javax.swing.JComboBox deviceBox;
    private javax.swing.JLabel deviceName;
    private javax.swing.JList fileList;
    private javax.swing.JButton installApk;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel selectPath;
    private javax.swing.JTextArea text;
    // End of variables declaration//GEN-END:variables
}
