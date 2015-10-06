/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package img;

import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Administrator
 */
public class MyTableModel {

    private static final int MAX = 100;
    private static final int MIN = 0;

    public MyTableModel() {
        final DefaultTableModel dm = new DefaultTableModel() {
            public Class getColumnClass(int col) {
                switch (col) {
                    case 0:
                        return String.class;
                    case 1:
                        return Integer.class;
                    case 2:
                        return Integer.class;
                    default:
                        return Object.class;
                }
            }

            public boolean isCellEditable(int row, int col) {
                switch (col) {
                    case 2:
                        return false;
                    default:
                        return true;
                }
            }

            public void setValueAt(Object obj, int row, int col) {
                if (col != 1) {
                    super.setValueAt(obj, row, col);
                    return;
                }
                try {
                    Integer integer = new Integer(obj.toString());
                } catch (NumberFormatException ex) {
                    ex.printStackTrace();
                }
            }
        };

//        dm.setDataVector(new Object[][]{
//            {"not human", new Integer(100), new Integer(100)},
//            {"hard worker", new Integer(76), new Integer(76)},
//            {"ordinary guy", new Integer(51), new Integer(51)},
//            {"lazy fellow", new Integer(12), new Integer(12)},
//            {"陈立", new Integer(20), new Integer(20)}},
//                new Object[]{"Name", "Result", "Indicator"});

        final JTable table = new JTable(dm);
    }
}
