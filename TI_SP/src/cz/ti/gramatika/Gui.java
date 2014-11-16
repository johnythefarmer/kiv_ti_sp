package cz.ti.gramatika;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import javax.swing.*;

/**
 * Created by lukas on 11/16/14.
 */
public class Gui extends JFrame {

	
	private static final long serialVersionUID = 1L;
	int sizeX = 300;
	int sizeY = 300;
	String[] labels = { "1:  A -> aBb ", "2:  A -> bCa ", "3:  B -> aBb ", "4:  B -> $ " };

	
	private void fileOpen(){
		JFrame.setDefaultLookAndFeelDecorated(true);
	    JDialog.setDefaultLookAndFeelDecorated(true);
	    JFrame frame = new JFrame("Gramatika");
	    frame.setLayout(new FlowLayout());
	    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    frame.setSize(sizeX, sizeY);
	    
	    JButton button = new JButton("Vyber soubor");
	    button.addActionListener(new ActionListener() {
	      public void actionPerformed(ActionEvent ae) {
	        JFileChooser fileChooser = new JFileChooser();
	        int returnValue = fileChooser.showOpenDialog(null);
	        if (returnValue == JFileChooser.APPROVE_OPTION) {
	          File f = fileChooser.getSelectedFile();
	          parseFile(f);
	        }
	      }
	    });
	    
	    JList<?> jlist = new JList(labels);
	    JScrollPane scrollPane1 = new JScrollPane(jlist);
	    frame.add(scrollPane1, BorderLayout.SOUTH);

	    MouseListener mouseListener = new MouseAdapter() {
	      public void mouseClicked(MouseEvent mouseEvent) {
	        JList<?> theList = (JList<?>) mouseEvent.getSource();
	        if (mouseEvent.getClickCount() == 2) {
	          int index = theList.locationToIndex(mouseEvent.getPoint());
	          if (index >= 0) {
	            Object o = theList.getModel().getElementAt(index);
	            System.out.println("Vybrano: " + o.toString());
	          }
	        }
	      }
	    };
	    jlist.addMouseListener(mouseListener);
	    
	    frame.add(button);
	    frame.pack();
	    frame.setVisible(true);
	}
	
    
    private void parseFile(File f){
    	FileReader fr = null;
    	try {
			fr = new FileReader(f);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
    	
    	char [] a = new char[300];
        try {
			fr.read(a);
		} catch (IOException e) {
			e.printStackTrace();
		}
        
        String line = null;
        int i = 0;
        for(char c : a){
        	if(c=='\n') labels[i++] = line;
        	line += c;
//            System.out.print(c);
        }
        
        
        try {
			fr.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
    	
    }


    public static void main(String [] arg){
    	Gui g = new Gui();
    	g.fileOpen();
    	
    }
}
