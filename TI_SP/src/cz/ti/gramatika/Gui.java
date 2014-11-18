package cz.ti.gramatika;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.*;
import java.util.ArrayList;

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
    private String nameAcceptFile = "GR soubory";
    private String acceptFile = "gr";
    private String startNameArea = "=>\n";

    DefaultListModel model = new DefaultListModel();
    JTextArea textArea = new JTextArea(10, 20);
    Gramatika gramatika;

	public void run(){
		JFrame.setDefaultLookAndFeelDecorated(true);
	    JDialog.setDefaultLookAndFeelDecorated(true);

	    JFrame frame = new JFrame(nameApp);

        frame.setUndecorated(true);
	    frame.setLayout(new FlowLayout());
        frame.setResizable(false);
	    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    frame.setSize(sizeX, sizeY);

        frame.add(new JScrollPane(textArea));
        frame.add(listComp());
        frame.add(fileComp());
        frame.add(backComp());
	    frame.setVisible(true);
	}

    private JButton backComp(){
        JButton button = new JButton(nameButtonBack);
        button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                textArea.append(gramatika.back());
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
                        textArea.append(gramatika.userRoll(o.toString()));
                    }
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
        BufferedReader br = null;

        String typGramatiky = "";
        char pocZnak = 0;
        ArrayList<PrepisPravidlo> prepisPrav = new ArrayList<PrepisPravidlo>();
//        ArrayList netZnaky = new ArrayList<Character>();
        String netZnaky = "";

        try {
            br = new BufferedReader( new FileReader(f));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        String line = "";
        try {
            typGramatiky = br.readLine().split("//")[0];


            int netZnakyC;
            String spl[];


            line = br.readLine();
            netZnakyC = Integer.valueOf(line.split("//")[0].trim());
            line = line.substring(line.length()-2*netZnakyC,line.length()).trim();
            spl = line.split(",");
            for(int i = 0; i < spl.length; i++){
                netZnaky += spl[i].charAt(0);
            }

            br.readLine();
            br.readLine();
            pocZnak = br.readLine().charAt(0);
            br.readLine();

            for(int i = 0; i < netZnakyC; i++){
                line = br.readLine();
                spl = line.split("->");
                char n = spl[0].trim().charAt(0);
                line = spl[1].trim();
                String tp = "";
//                System.out.println(line);
                for(int k = 0; k <= line.length(); k++){
                    if(k == line.length()){
                        prepisPrav.add(new PrepisPravidlo(prepisPrav.size()+1, n, tp));
                        break;
                    }
                    if(line.charAt(k) == ' ' || line.charAt(k) == '|'){
                        k += 3;
                        prepisPrav.add(new PrepisPravidlo(prepisPrav.size()+1, n, tp));
                        tp = "";
                    }
                    tp += line.charAt(k);
                }
            }

            br.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

//        checkInput(typGramatiky, pocZnak, netZnaky, prepisPrav);

        char nz[] = new char[netZnaky.length()];
        for(int i = 0; i < netZnaky.length(); i++) nz[i] = netZnaky.charAt(i);

        PrepisPravidlo ps[] = new PrepisPravidlo[prepisPrav.size()];
        for(int i = 0; i < prepisPrav.size(); i++) ps[i] = prepisPrav.get(i);


        gramatika = new Gramatika(typGramatiky, pocZnak, nz, ps, this);

    }

    private void checkInput(String typGrm, char pocZnak, ArrayList<Character> netZnaky, ArrayList<PrepisPravidlo> prepisPrav){
        System.out.println(typGrm);
        System.out.println(pocZnak);

        for(char c : netZnaky) System.out.print(c+" ,");
        System.out.print("\n");
        for(PrepisPravidlo p : prepisPrav) {
            System.out.println(p);
        }
    }


    public static void main(String [] arg){
    	Gui g = new Gui();
    	g.run();
    	
    }
}
