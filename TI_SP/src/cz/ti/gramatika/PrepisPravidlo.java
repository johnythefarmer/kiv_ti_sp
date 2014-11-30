package cz.ti.gramatika;

public class PrepisPravidlo {
	private final int index;
	private final char neterm;
	private final String prepsane;
	
	/**
	 * @param index
	 * @param neterm
	 * @param prepsane
	 */
	public PrepisPravidlo(int index, char neterm, String prepsane) {
		this.index = index;
		this.neterm = neterm;
		this.prepsane = prepsane;
	}
	
	public String toString(){
		return index + ": " + neterm + " -> " + prepsane;
	}
	
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

	public int getIndex(){
		return index;
	}
	
	public String getPrepsane(){
		return prepsane;
	}
	
    public char getNeterm(){
        return neterm;
    }

}
