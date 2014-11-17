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


public class Gui extends JFrame {

	
	private static final long serialVersionUID = 1L;
	int sizeX = 300;
	int sizeY = 300;
//	String[] labels = { "1:  A -> aBb ", "2:  A -> bCa ", "3:  B -> aBb ", "4:  B -> $ " };
    String[] labels = new String[100];

    DefaultListModel model = new DefaultListModel();

//    JList list = new JList(model);

	
	private void fileOpen(){
		JFrame.setDefaultLookAndFeelDecorated(true);
	    JDialog.setDefaultLookAndFeelDecorated(true);

	    final JFrame frame = new JFrame("Gramatika");

	    frame.setLayout(new FlowLayout());
	    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    frame.setSize(sizeX, sizeY);
	    
	    JButton button = new JButton("Vyber soubor");
	    button.addActionListener(new ActionListener() {
	      public void actionPerformed(ActionEvent ae) {
	        JFileChooser fileChooser = new JFileChooser();
	        
	        
//	        fileChooser.setFileFilter(new javax.swing.filechooser.FileFilter() {
//	               public boolean accept(File file){
//	                  return file.getName().toUpperCase().equals(".TXT");
//	               }
//	               public String getDescription(){
//	                  return ".txt files";
//	               }
//	            });        
	        
	        int returnValue = fileChooser.showOpenDialog(null);
	        if (returnValue == JFileChooser.APPROVE_OPTION) {
	          File f = fileChooser.getSelectedFile();
	          parseFile(f);
	        }
	      }
	    });
	    
	    JList<?> jlist = new JList(model);
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

        
        JTextArea textArea = new JTextArea();
        JScrollPane scrollPane = new JScrollPane(textArea);
        frame.add(scrollPane);

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
    	
    	char [] a = new char[1000];
        try {
			fr.read(a);
		} catch (IOException e) {
			e.printStackTrace();
		}
        
        int i = 0;
        String line = "";
        for(char c : a){
            if(c == '\n'){
                model.addElement(line);
                line = "";
            }
            line += c;
            System.out.print(c);
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
