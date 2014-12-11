package cz.ti.gramatika;

public class Retezec {
	/**
	 * Znaky nalevo od nejlevejsiho neterm znaku
	 */
	private final String vlevo;
	
	/**
	 * Nejlevejsi neterm znak
	 */
	private final char neterminZnak;
	
	/**
	 * Znaky napravo od nejlevejsiho neterm znaku
	 */
	private final String vpravo;
	
	/**
	 * Nadrizena gramatika
	 */
	private final Gramatika g;

	/**
	 * Z daneho stringu vytvori retezec
	 * @param ret nejaky string
	 * @param g nadrizena gramatika
	 */
	public Retezec(String ret, Gramatika g){
		int netermPoz = najdiPrvniNeterminal(ret,g.netermZnaky);
		if(netermPoz == -1){
			this.vlevo = ret;
			this.neterminZnak = ' ';
			this.vpravo = "";
		}else{
			this.vlevo = ret.substring(0,netermPoz);
			this.neterminZnak = ret.charAt(netermPoz);
			this.vpravo = ret.substring(netermPoz + 1, ret.length());
		}
		this.g = g;
	}
	
	/**
	 * Nalezne nejlevejsi neterminalni symbol v retezci
	 * @param ret retezec
	 * @param netermZnaky mnozina neterm znaku
	 * @return poradi nejlevejsiho neterm prvku v retezci
	 */
	public int najdiPrvniNeterminal(String ret, char[] netermZnaky){
		int delkaRet = ret.length();
		int velikostNetermZn = netermZnaky.length;
		for(int i = 0; i < delkaRet;i++){
			char nynejsi = ret.charAt(i);
			for(int j = 0; j < velikostNetermZn; j++){
				if(netermZnaky[j] == nynejsi){
					return i;
				}
			}
		}
		
		return -1;
	}
	
	
	
	/**
	 * Vrati znaky nalevo od nejlevejsiho neterm znaku
	 * @return Znaky nalevo od nejlevejsiho neterm znaku
	 */
	public String getVlevo() {
		return vlevo;
	}

	/**
	 * Vrati nejlevejsi neterm znak
	 * @return neterminalni znak
	 */
	public char getNeterminZnak() {
		return neterminZnak;
	}

	/**
	 * Vrati znaky napravo od nejlevejsiho neterm znaku
	 * @return Znaky napravo od nejlevejsiho neterm znaku
	 */
	public String getVpravo() {
		return vpravo;
	}
	
	/**
	 * Vrati nadrazenou gramatiku
	 * @return nadrazena gramatika
	 */
	public Gramatika getGramatika(){
		return g;
	}

	/**
	 * Zjisti zda je retezec konecny
	 * @return je konecny
	 */
	public boolean isFinal(){
		return neterminZnak == ' ';
	}
	
	public String toString(){
		String finalString = "";
		if(isFinal()){
			finalString = " (final)";
		}
		return vlevo + neterminZnak + vpravo + finalString; 
	}
}
