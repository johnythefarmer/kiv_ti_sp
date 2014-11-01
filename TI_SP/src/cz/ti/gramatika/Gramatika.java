package cz.ti.gramatika;

import java.util.Scanner;
import java.util.Stack;

public class Gramatika {
	public char[] netermZnaky;
	public PrepisPravidlo[] prepPrav;
	public char pocZnak;
	
	public Gramatika(char[] netermZnaky, PrepisPravidlo[] prepPrav, char pocZnak){
		this.netermZnaky = netermZnaky;
		this.prepPrav = prepPrav;
		this.pocZnak = pocZnak;
	}
	
	public Retezec pocatecniRetezec(){
		return new Retezec(pocZnak + "",this);
	}
	
	public Retezec uzijPravidloNaRet(int index, Retezec r){
		return prepPrav[index - 1].prepisRetezec(r);
	}
	
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		char[] neterm = new char[]{'A', 'B', 'C', 'D'};
		PrepisPravidlo[] pp = new PrepisPravidlo[]{new PrepisPravidlo(1, 'A', "aB"), new PrepisPravidlo(2, 'B', "$"), new PrepisPravidlo(3, 'B', "bC"), new PrepisPravidlo(4,'C', "b")};
		Gramatika g = new Gramatika(neterm, pp, 'A');
		Stack<Retezec> retezce = new Stack<Retezec>();
		Retezec r = g.pocatecniRetezec();
		char vstup;
		while((vstup = sc.nextLine().charAt(0)) != '@'){
			if(vstup == 'u'){
				if(retezce.isEmpty()){
					System.out.println("Dale jiz nejde jit zpet.");
				}else{
					r = retezce.pop();
				}
			}else{
				int index = vstup - '0';
				Retezec pom = g.uzijPravidloNaRet(index, r);
				retezce.push(r);
				r = pom;
			}
			System.out.println(r);
		}
		System.out.println("Konec programu.");
		sc.close();
	}
}
