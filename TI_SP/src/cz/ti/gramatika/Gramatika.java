package cz.ti.gramatika;

import java.util.Scanner;
import java.util.Stack;

public class Gramatika {
    public String typGramatiky;
	public char[] netermZnaky;
	public PrepisPravidlo[] prepPrav;
	public char pocZnak;
    private Stack<Retezec> retezce;
    private Retezec r;
    private Gui gui;

    public Gramatika(String typGramatiky, char pocZnak, char[] netermZnaky, PrepisPravidlo[] prepPrav, Gui gui){
        this.typGramatiky = typGramatiky;
		this.netermZnaky = netermZnaky;
		this.prepPrav = prepPrav;
		this.pocZnak = pocZnak;
        this.gui = gui;

        this.retezce = new Stack<Retezec>();
        this.r = pocatecniRetezec();
        setListGram(pocZnak);
	}

    private void setListGram(char neter){
        gui.model.removeAllElements();
        for(PrepisPravidlo e : prepPrav)if(e.getNeterm() == neter) gui.model.addElement(e);
    }
	
	public Retezec pocatecniRetezec(){
		return new Retezec(pocZnak + "",this);
	}
	
	public Retezec uzijPravidloNaRet(int index, Retezec r){
		return prepPrav[index].prepisRetezec(r);
	}

    public PrepisPravidlo[] getPrepPrav(){
        return prepPrav;
    }

    public String back(){
        if(retezce.isEmpty()){
//            gui.textArea.append("Dale jiz nejde jit zpet.");
            // disable button back
        }else{
            r = retezce.pop();
        }
        return r.toString();
    }

    public String userRoll(String select) {
        int index = getSelected(select);
        if(index < 0) {}// error

        Retezec pom = uzijPravidloNaRet(index, r);
        retezce.push(r);
        r = pom;
        return "null";
    }

    public int getSelected(String select){
//        char neter = select.split(":")[1].split("->")[0].trim().charAt(0);
//        System.out.println(neter);
        Retezec pom = new Retezec(select.split("->")[1], this);
        int id = pom.najdiPrvniNeterminal(pom.getVlevo(), netermZnaky);
        setListGram(netermZnaky[id]);
        int index = 0;
        for(int i = 0; i < prepPrav.length; i++) if (prepPrav[i].toString().equals(select)) index = i;

        return index;
    }

	public static void main(String[] args) {
//		Scanner sc = new Scanner(System.in);
//		char[] neterm = new char[]{'A', 'B', 'C', 'D'};
//		PrepisPravidlo[] pp = new PrepisPravidlo[]{new PrepisPravidlo(1, 'A', "aB"), new PrepisPravidlo(2, 'B', "$"), new PrepisPravidlo(3, 'B', "bC"), new PrepisPravidlo(4,'C', "b")};
//		Gramatika g = new Gramatika("G2", 'A', neterm, pp);
//        Gui gui = new Gui();
//        gui.run();
//        Gramatika g = gui.getGramatika();
//		Stack<Retezec> retezce = new Stack<Retezec>();
//		Retezec r = g.pocatecniRetezec();

//        gui.model.addElement(g.getPrepPrav());

//		System.out.println("Konec programu.");
//		sc.close();
	}
}
