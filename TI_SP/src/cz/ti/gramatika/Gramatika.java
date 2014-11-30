package cz.ti.gramatika;

import java.util.Stack;

public class Gramatika {
    public TypGramatika typGramatiky;
	public char[] netermZnaky;
	public char[] termZnaky;
	public PrepisPravidlo[] prepPrav;
	public char pocZnak;
    private final Stack<Retezec> retezce;
    private Retezec r;
    private final Gui gui;

    public Gramatika(TypGramatika typGramatiky, char pocZnak, char[] netermZnaky, char[] termZnaky, PrepisPravidlo[] prepPrav, Gui gui){
        this.typGramatiky = typGramatiky;
		this.netermZnaky = netermZnaky;
		this.termZnaky = termZnaky;
		this.prepPrav = prepPrav;
		this.pocZnak = pocZnak;
        this.gui = gui;
        this.retezce = new Stack<Retezec>();
        this.r = pocatecniRetezec();
        zkontrolujGramatiku();
        setListGram(pocZnak);
	}

    private void zkontrolujGramatiku(){
    	for(PrepisPravidlo pravidlo : prepPrav){
    		zkontrolujPravidlo(pravidlo);
    	}
    }
    
    private void zkontrolujPravidlo(PrepisPravidlo pp){
    	if(!poleObsahuje(netermZnaky, pp.getNeterm())){
			throw new IllegalArgumentException("Pravidlo c. "
					+ pp.getIndex() + " obsahuje neplatny znak " + pp.getNeterm() + "!");
    	}
    	
    	int vlevo = 0, vpravo = 0, neterm = 0;
    	boolean netermNalezen = false;
    	
    	String prepsane = pp.getPrepsane();
    	
    	for(int i = 0; i < prepsane.length(); i++){
    		char tmp = prepsane.charAt(i);
    		if(poleObsahuje(netermZnaky, tmp)){
    			neterm++;
    			netermNalezen = true;
    		} else if(poleObsahuje(termZnaky, tmp)){
    			if(netermNalezen){
    				vpravo++;
    			}else{
    				vlevo++;
    			}
    		}else {
    			throw new IllegalArgumentException("Pravidlo c. "
    					+ pp.getIndex() + " obsahuje neplatny znak " + tmp + "!");
    		}
    	}
    	
    	if(vlevo != 0 && !typGramatiky.isZnakyNalevo() && netermNalezen){
			throw new IllegalArgumentException("Pravidlo c. "
					+ pp.getIndex() +
					" obsahuje terminalni znaky pred neterminalnim, coz nelze v gramatice "
					+ typGramatiky + "!");
    	}
    	
    	if(vpravo != 0 && !typGramatiky.isZnakyNapravo()){
    		throw new IllegalArgumentException("Pravidlo c. "
					+ pp.getIndex() +
					" obsahuje terminalni znaky za neterminalnim, coz nelze v gramatice "
					+ typGramatiky + "!");
    	}
    	
    	if(neterm > 1 && !typGramatiky.isViceNeterm()){
    		throw new IllegalArgumentException("Pravidlo c." + pp.getIndex() +
    				"obsahuje vice neterminalnich symbolu"
    				+ "coz nelze v gramatice typu " + typGramatiky + "!");
    	}
    }
    
    private boolean poleObsahuje(char[] array, char znak){
    	for(int i = 0; i < array.length; i++){
    		if(array[i] == znak){
    			return true;
    		}
    	}
    	
    	return false;
    }
    
    private void setListGram(char neter){
        gui.model.removeAllElements();
        for(PrepisPravidlo e : prepPrav){
        	if(e.getNeterm() == neter){
        		gui.model.addElement(e);
        	}
        }
    }
	
	private Retezec pocatecniRetezec(){
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
            gui.disable();
            return null;
        }else{
            r = retezce.pop();
            setListGram(r.getNeterminZnak());
            return r+"";
        }
        
        
    }

    public String useRule(PrepisPravidlo pp) {
        /*int index = getSelected(select);
        if(index < 0) {}// error
*/
    	
    	int index = indexOf(pp);
    	retezce.push(r);
        r = uzijPravidloNaRet(index, r);
        
        setListGram(r.getNeterminZnak());
        gui.enable();
        return r+"";
    }

    private int indexOf(PrepisPravidlo pp){
    	for(int i = 0 ; i < prepPrav.length; i++){
    		if(pp.equals(prepPrav[i])){
    			return i;
    		}
    	}
    	return -1;
    	/*//        char neter = select.split(":")[1].split("->")[0].trim().charAt(0);
//        System.out.println(neter);
    	System.out.println(select);
        Retezec pom = new Retezec(select.split("->")[1], this);
        int id = pom.najdiPrvniNeterminal(pom.getVlevo(), netermZnaky);
        setListGram(netermZnaky[id]);
        int index = 0;
        for(int i = 0; i < prepPrav.length; i++) if (prepPrav[i].toString().equals(select)) index = i;

        return index;*/
    }

/*	public static void main(String[] args) {
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
	}*/
}
