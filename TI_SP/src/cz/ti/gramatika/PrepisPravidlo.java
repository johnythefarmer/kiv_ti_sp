package cz.ti.gramatika;

/**
 * Trida reprezentujici prepisovaci pravidlo
 */
public class PrepisPravidlo {
	
	/**
	 * Index v dane mnozine u gramatiky
	 */
	private final int index;
	
	/**
	 * Neterminalni znak na leve strane pravidla
	 */
	private final char neterm;
	
	/**
	 * Retezec, na ktery se dany znak prepise
	 */
	private final String prepsane;
	
	/**
	 * Vytvori prepisovaci pravidlo
	 * @param index index v mnozine pravidel
	 * @param neterm neterminalni znak na leve strane pravidla
	 * @param prepsane Retezec na ktery se dany znak prepise
	 */
	public PrepisPravidlo(int index, char neterm, String prepsane) {
		this.index = index;
		this.neterm = neterm;
		this.prepsane = prepsane;
	}
	
	public String toString(){
		return index + ": " + neterm + " -> " + prepsane;
	}
	
	/**
	 * Veme retezec a jeho prvni nejlevejsi
	 * neterm znak (pokud exituje a je shodny
	 * s nasim neterm znakem) prepise
	 * @param ret Jakykoliv retezec
	 * @return Retezec vznikly prepsanim predaneho retezce
	 */
	public Retezec prepisRetezec(Retezec ret){
		if(this.neterm != ret.getNeterminZnak()){
			throw new IllegalArgumentException("Dane pravidlo neni na tento znak uplatnitelne.");
		}
		
		String pomS = prepsane;
		if(prepsane.equals("$")){
			pomS = "";
		}
		
		return new Retezec(ret.getVlevo() + pomS + ret.getVpravo(), ret.getGramatika());
	}

	/**
	 * Vrati index v mnozine pravidel
	 * @return index
	 */
	public int getIndex(){
		return index;
	}
	
	/**
	 * Vrati retezec na prave strane
	 * @return retezec na prave strane
	 */
	public String getPrepsane(){
		return prepsane;
	}
	
	/**
	 * Vrati neterm na leve strane
	 * @return neter na leve strane
	 */
    public char getNeterm(){
        return neterm;
    }

}
