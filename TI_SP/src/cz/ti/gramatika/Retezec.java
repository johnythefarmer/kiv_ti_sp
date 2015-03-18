package cz.ti.gramatika;

public class Retezec {
	private final String vlevo;
	private final char neterminZnak;
	private final String vpravo;
	private final Gramatika g;

	
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
	 * @return the vlevo
	 */
	public String getVlevo() {
		return vlevo;
	}

	/**
	 * @return the neterminZnak
	 */
	public char getNeterminZnak() {
		return neterminZnak;
	}

	/**
	 * @return the vpravo
	 */
	public String getVpravo() {
		return vpravo;
	}
	
	public Gramatika getGramatika(){
		return g;
	}

	public boolean isFinal(){
		return neterminZnak == ' ';
	}
	
	public String toString(){
		String finalString = "";
		if(isFinal()){
			finalString = "(final) ";
		}
		return finalString + vlevo + neterminZnak + vpravo; 
	}
}
