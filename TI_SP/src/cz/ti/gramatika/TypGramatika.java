package cz.ti.gramatika;

/**
 * Slouzi ke kontrole typu gramatiky
 */
public enum TypGramatika {
	G2(true, true, true), G3L(false,true,false), G3P(true,false,false);
	
	/**
	 * Znaci ze existuje jeden a vice znaku nalevo od nejlevejsiho neterminalniho znaku
	 */
	private final boolean znakyNalevo;
	
	/**
	 * Znaci ze existuje jeden a vice znaku napravo od nejlevejsiho neterm. znaku
	 */
	private final boolean znakyNapravo;
	
	/**
	 * Znaci zda pravidla obsahuji vice neterm znaku
	 */
	private final boolean viceNeterm;
	
	/**
	 * Konstruktor pro enum
	 * @param znakyNalevo ma znaky nalevo
	 * @param znakyNapravo ma znaky napravo
	 * @param viceNeterm ma vice neterminalnich znaku
	 */
	private TypGramatika(boolean znakyNalevo, boolean znakyNapravo, boolean viceNeterm){
		this.znakyNalevo = znakyNalevo;
		this.znakyNapravo = znakyNapravo;
		this.viceNeterm = viceNeterm;
	}

	/**
	 * @return the znakyNalevo
	 */
	public boolean isZnakyNalevo() {
		return znakyNalevo;
	}

	/**
	 * @return the znakyNapravo
	 */
	public boolean isZnakyNapravo() {
		return znakyNapravo;
	}

	/**
	 * @return the viceNeterm
	 */
	public boolean isViceNeterm() {
		return viceNeterm;
	}
	
}
