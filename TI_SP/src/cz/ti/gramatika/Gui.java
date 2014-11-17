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
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;


public class Gui extends JFrame {

	
	private static final long serialVersionUID = 1L;
	private final int sizeX = 300;
	private final int sizeY = 400;
    private String nameApp = "Gramatika";
    private String nameButtonFile = "Vyber soubor";
    private String nameButtonBack = "Zpet";
    private String nameAcceptFile = "TXT soubory";
    private String acceptFile = "txt";
    private String startNameArea = "=>";

    DefaultListModel model = new DefaultListModel();
    JTextArea textArea = new JTextArea(10, 20);

	private void run(){
		JFrame.setDefaultLookAndFeelDecorated(true);
	    JDialog.setDefaultLookAndFeelDecorated(true);

	    JFrame frame = new JFrame(nameApp);

	    frame.setLayout(new FlowLayout());
        frame.setResizable(false);
	    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    frame.setSize(sizeX, sizeY);

        frame.add(new JScrollPane(textArea));
        frame.add(listComp());
        frame.add(fileComp());
        frame.add(backComp());
//	    frame.pack();
	    frame.setVisible(true);
	}

    private JButton backComp(){
        JButton button = new JButton(nameButtonBack);
        button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                textArea.append("\nzpet");
            }
        });
        return button;
    }

    private JScrollPane listComp(){
        JList<?> jlist = new JList(model);
        JScrollPane scrollPaneList = new JScrollPane(jlist);

        MouseListener mouseListener = new MouseAdapter() {
            public void mouseClicked(MouseEvent mouseEvent) {
                JList<?> theList = (JList<?>) mouseEvent.getSource();
                if (mouseEvent.getClickCount() == 2) {
                    int index = theList.locationToIndex(mouseEvent.getPoint());
                    if (index >= 0) {
                        Object o = theList.getModel().getElementAt(index);
                        textArea.append(o.toString());
                    }
                }
            }
        };
        jlist.addMouseListener(mouseListener);

        return scrollPaneList;
    }

    private JButton fileComp(){
        JButton button = new JButton(nameButtonFile);
        button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                JFileChooser fileChooser = new JFileChooser();

                FileFilter filter = new FileNameExtensionFilter(nameAcceptFile, acceptFile);
                fileChooser.setFileFilter(filter);

                int returnValue = fileChooser.showOpenDialog(null);
                if (returnValue == JFileChooser.APPROVE_OPTION) {
                    File f = fileChooser.getSelectedFile();
                    model.removeAllElements();
                    textArea.setText(startNameArea);
                    parseFile(f);
                }
            }
        });
        return button;
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
        
        String line = "";
        for(char c : a){
            if(c == '\n'){
                model.addElement(line);
                line = "";
            }
            line += c;
        }
        
        
        try {
			fr.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
    	
    }


    public static void main(String [] arg){
    	Gui g = new Gui();
    	g.run();
    	
    }
}
