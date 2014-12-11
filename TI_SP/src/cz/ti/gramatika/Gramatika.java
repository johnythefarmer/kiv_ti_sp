package cz.ti.gramatika;

import java.util.Stack;

/**
 * Trida reprezentujici gramatiku
 *
 */
public class Gramatika {
	
	/**
	 * Typ dane gramatiky
	 */
    public TypGramatika typGramatiky;
    
    /**
     * Mnozina neterminalnich znaku
     */
	public char[] netermZnaky;
	
	/**
	 * Mnozina terminalnich znaku
	 */
	public char[] termZnaky;
	
	/**
	 * Mnozina prepisovacich pravidel
	 */
	public PrepisPravidlo[] prepPrav;
	
	/**
	 * Pocatecni znak
	 */
	public char pocZnak;
	
	/**
	 * Zasobnik pro funkci redo
	 */
    private final Stack<Retezec> retezce;
    
    /**
     * Prepisovany retezec
     */
    private Retezec r;
    
    /**
     * Graficke uzivatelske rozhrani
     */
    private final Gui gui;
    
    /**
     * Vytvori novou gramatiku se zadanymi udaji
     * @param typGramatiky Typ gramatiky
     * @param pocZnak Pocatecni znak
     * @param netermZnaky Mnozina neterm. znaku
     * @param termZnaky Mnozina term. znaku
     * @param prepPrav Mnozina prepisovacich pravidel
     * @param gui Graficke uzivatelske rozhrani
     */
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

    /**
     * Zkontroluje vsechna pravidla
     */
    private void zkontrolujGramatiku(){
    	for(PrepisPravidlo pravidlo : prepPrav){
    		zkontrolujPravidlo(pravidlo);
    	}
    }
    
    /**
     * Zkontroluje dane pravidlo
     * @param pp kontrolovane pravidlo
     */
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
    
    /**
     * Zjisti zda dany znak lezi v poli znaku
     * @param array pole znaku, kde hledame
     * @param znak hledany znak
     * @return prvek nalezen
     */
    private boolean poleObsahuje(char[] array, char znak){
    	for(int i = 0; i < array.length; i++){
    		if(array[i] == znak){
    			return true;
    		}
    	}
    	
    	return false;
    }
    
    /**
     * Nastavi gui novy seznam prepisovacich pravidel, ktere zobrazi
     * @param neter Neterminalni znak, ke hledana pravidla patri
     */
    private void setListGram(char neter){
        gui.model.removeAllElements();
        for(PrepisPravidlo e : prepPrav){
        	if(e.getNeterm() == neter){
        		gui.model.addElement(e);
        	}
        }
    }
	
    /**
     * Vrati pocatecni retezec
     * @return pocatecni retezec
     */
	private Retezec pocatecniRetezec(){
		return new Retezec(pocZnak + "",this);
	}
	
	/**
	 * Uzije prepisovaci pravidlo na danem indexu pro prepsane predaneho retezce
	 * @param index index prepisovaciho pravidla
	 * @param r Retezec, ktery prepisujeme
	 * @return prepsany retezec
	 */
	public Retezec uzijPravidloNaRet(int index, Retezec r){
		return prepPrav[index].prepisRetezec(r);
	}

	/**
	 * Vrati vsechny prepisovai pravil
	 * @return Prepisovaci pravidla
	 */
    public PrepisPravidlo[] getPrepPrav(){
        return prepPrav;
    }
    
    /**
     * Vrati se o krok zpet v odvozovani retezce
     * @return Predchozi retezec
     */
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

    /**
     * Pouzije dane prepisovaci pravidlo na vlastni retezec
     * @param pp Prepisovaci pravidlo
     * @return hodnota v retezci
     */
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

    /**
     * Vrati index pravidla, pokud se nachazi v mnozine prepisovacich pravidel
     * @param pp Prepisovaci pravidlo
     * @return je v mnozine
     */
    private int indexOf(PrepisPravidlo pp){
    	for(int i = 0 ; i < prepPrav.length; i++){
    		if(pp.equals(prepPrav[i])){
    			return i;
    		}
    	}
    	return -1;
    }
}
