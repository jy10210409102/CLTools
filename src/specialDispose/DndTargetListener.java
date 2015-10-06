/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package specialDispose;

import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DropTargetDragEvent;
import java.awt.dnd.DropTargetDropEvent;
import java.awt.dnd.DropTargetEvent;
import java.awt.dnd.DropTargetListener;
import java.io.File;
import java.util.List;
import test.TestFrame;

/**
 *
 * @author Administrator
 */
public class DndTargetListener implements DropTargetListener {

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
				List<File> list = (List<File>) tf
						.getTransferData(DataFlavor.javaFileListFlavor);
				for (File f : list) {
					if (f.exists() && f.isFile()) {
						TestFrame.jTextArea1.append(f.getAbsolutePath() + "\n");
						TestFrame.jTextArea1.append("======================\n");
//						br = new BufferedReader(new FileReader(f));
//						while ((line = br.readLine()) != null)
//							MainFrm.textArea.append(line + "n");
//						br.close();
					} else if (f.exists() && f.isDirectory()) {
						// 这里不对拖拽文件夹做操作
                                            TestFrame.jTextArea1.append(f.getAbsolutePath() + "文件夹！！！！\n");
                                            TestFrame.jTextArea1.append("======================\n");
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