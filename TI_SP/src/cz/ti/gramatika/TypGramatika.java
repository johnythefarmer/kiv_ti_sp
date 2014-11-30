package cz.ti.gramatika;

public enum TypGramatika {
	G2(true, true, true), G3L(false,true,false), G3P(true,false,false);
	
	private final boolean znakyNalevo;
	private final boolean znakyNapravo;
	private final boolean viceNeterm;
	
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
