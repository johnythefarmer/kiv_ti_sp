package cz.ti.gramatika;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;


public class Gui extends JFrame {

	
	private static final long serialVersionUID = 1L;
	private final int sizeX = 300;
	private final int sizeY = 370;
    private final String nameApp = "Gramatika";
    private final String nameButtonFile = "Vyber soubor";
    private final String nameButtonBack = "Zpet";
    private final String nameAcceptFile = "GR soubory";
    private final String acceptFile = "gr";

    DefaultListModel<PrepisPravidlo> model = new DefaultListModel<PrepisPravidlo>();
    JTextArea textArea = new JTextArea();
    Gramatika gramatika;
    private JButton back;
    private JFileChooser fileChooser;

	public void run(){		
		try {
	        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
	    } 
	    catch (UnsupportedLookAndFeelException e) {
	    	e.printStackTrace();
	    }
	    catch (ClassNotFoundException e) {
	    	e.printStackTrace();
	    }
	    catch (InstantiationException e) {
	    	e.printStackTrace();
	    }
	    catch (IllegalAccessException e) {
	    	e.printStackTrace();
	    } 
//		textArea.setPreferredSize(new Dimension(280,150));;

		fileChooser = new JFileChooser();

        FileFilter filter = new FileNameExtensionFilter(nameAcceptFile, acceptFile);
        fileChooser.setFileFilter(filter);
		
	    JFrame frame = new JFrame(nameApp);
	    this.back = backComp();
//        frame.setUndecorated(true);
	    frame.setLayout(new FlowLayout());
        frame.setResizable(false);
	    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    frame.setSize(sizeX, sizeY);
	    textArea.setEditable(false);
	    JScrollPane textAreaScrollPane = new JScrollPane(textArea);
	    textAreaScrollPane.setPreferredSize(new Dimension(280,150));
        frame.add(textAreaScrollPane);
        frame.add(listComp());
        frame.add(fileComp());
        frame.add(back);
	    frame.setVisible(true);
	}

    private JButton backComp(){
       	JButton button = new JButton(nameButtonBack);
        button.setEnabled(false);
        button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                
                String text = gramatika.back();
                if(text != null){
                	textArea.append(text+"\n");
                }
            }
        });
        return button;
    }

    public void disable(){
//    	textArea.append("Dale jiz nejde jit zpet.\n");
    	JOptionPane.showMessageDialog(null, "Nelze jiz jit o dalsi krok zpet.", "Nelze zpet", JOptionPane.INFORMATION_MESSAGE);
    	back.setEnabled(false);
    }
    
    public void enable(){
    	back.setEnabled(true);
    }
    
    private JScrollPane listComp(){
        JList<PrepisPravidlo> jlist = new JList<PrepisPravidlo>(model);
        JScrollPane scrollPaneList = new JScrollPane(jlist);
        scrollPaneList.setPreferredSize(new Dimension(280,150));

        MouseListener mouseListener = new MouseAdapter() {
            public void mouseClicked(MouseEvent mouseEvent) {
                @SuppressWarnings("unchecked")
				JList<PrepisPravidlo> theList = (JList<PrepisPravidlo>)mouseEvent.getSource();
                if (mouseEvent.getClickCount() == 2) {
                        PrepisPravidlo p = theList.getSelectedValue();
                        textArea.append(gramatika.useRule(p)+"\n");
                }
            }
        };
        jlist.addMouseListener(mouseListener);

        return scrollPaneList;
    }


    private JButton fileComp(){
        final JButton button = new JButton(nameButtonFile);
        button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                /*JFileChooser fileChooser = new JFileChooser();

                FileFilter filter = new FileNameExtensionFilter(nameAcceptFile, acceptFile);
                fileChooser.setFileFilter(filter);

                int returnValue = fileChooser.showOpenDialog(null);
                if (returnValue == JFileChooser.APPROVE_OPTION) {
                    File f = fileChooser.getSelectedFile();
                    model.removeAllElements();
                    textArea.setText(startNameArea);
                    parseFile(f);
                }*/
            	if(!textArea.getText().isEmpty()){
            		int n = JOptionPane.showConfirmDialog(
            			    null,
            			    "Dosavadni postup bude ztracen.\nPokracovat?",
            			    "Pokracovat?",
            			    JOptionPane.YES_NO_OPTION);
            		if(n != JOptionPane.YES_OPTION){
            			return;
            		}
            	}
/*            	
            	File f = new File("gramatika.gr");
            	model.removeAllElements();
                try{
                	parseFile(f);
                    back.setEnabled(true);
                    textArea.setText(gramatika.pocZnak+"\n");
                }catch(IllegalArgumentException e){
                	JOptionPane.showMessageDialog(null, e.getMessage(), "Chyba pri kontrole gramatiky", JOptionPane.INFORMATION_MESSAGE);
                }*/
            	
            	
            
     
                int returnValue = fileChooser.showOpenDialog(null);
                if (returnValue == JFileChooser.APPROVE_OPTION) {
                	File f = fileChooser.getSelectedFile();
                	model.removeAllElements();
                	try{
	                	parseFile(f);
	                    back.setEnabled(true);
	                    textArea.setText(gramatika.pocZnak+"\n");
	                }catch(IllegalArgumentException e){
	                	JOptionPane.showMessageDialog(null, e.getMessage(), "Chyba pri kontrole gramatiky", JOptionPane.INFORMATION_MESSAGE);
	                }
                }
            	
            }
        });
        return button;
    }
	
    
    private void parseFile(File f){
        BufferedReader br = null;

        TypGramatika typGramatiky;
        char pocZnak = 0;
        ArrayList<PrepisPravidlo> prepisPrav = new ArrayList<PrepisPravidlo>();
//        ArrayList netZnaky = new ArrayList<Character>();

        try {
            br = new BufferedReader( new FileReader(f));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        String line = "";
        try {
            typGramatiky = TypGramatika.valueOf(br.readLine().split("//")[0].trim());

            line = br.readLine();
            int netZnakyC = Integer.valueOf(line.split("//")[0].trim());
            char nz[] = new char[netZnakyC];
            
            char pom = 'A';
            for(int i = 0; i < netZnakyC; i++){
            	nz[i] = pom++;
            }

            line = br.readLine();
            int terZnakyC = Integer.valueOf(line.split("//")[0].trim());
            char tz[] = new char[terZnakyC + 1];
            pom = 'a';

            for(int i = 0; i < terZnakyC; i++){
            	tz[i] = pom++;
            }
            tz[tz.length - 1] = '$';
            
            br.readLine();
            pocZnak = br.readLine().charAt(0);
            br.readLine();

            String spl[];
            
            while(br.ready()){
                line = br.readLine();
                spl = line.split("->");
                char n = spl[0].trim().charAt(0);
                line = spl[1].trim();
                String[] pravidla = line.split("\\|");
                
                for(int i = 0; i < pravidla.length; i++){
                	String pravidlo = pravidla[i].trim();
                	prepisPrav.add(new PrepisPravidlo(prepisPrav.size()+1, n, pravidlo));
                }
            }

            br.close();
            PrepisPravidlo ps[] = new PrepisPravidlo[prepisPrav.size()];
            for(int i = 0; i < prepisPrav.size(); i++){
            	ps[i] = prepisPrav.get(i);
            }

            gramatika = new Gramatika(typGramatiky, pocZnak, nz, tz, ps, this);

        } catch (IOException e) {
            e.printStackTrace();
        }

//        checkInput(typGramatiky, pocZnak, netZnaky, prepisPrav);

//        char nz[] = new char[netZnaky.length()];
//        for(int i = 0; i < netZnaky.length(); i++) nz[i] = netZnaky.charAt(i);

    }

/*    private void checkInput(String typGrm, char pocZnak, ArrayList<Character> netZnaky, ArrayList<PrepisPravidlo> prepisPrav){
        System.out.println(typGrm);
        System.out.println(pocZnak);

        for(char c : netZnaky) System.out.print(c+" ,");
        System.out.print("\n");
        for(PrepisPravidlo p : prepisPrav) {
            System.out.println(p);
        }
    }*/


    public static void main(String [] arg){
    	Gui g = new Gui();
    	g.run();
    }
}
