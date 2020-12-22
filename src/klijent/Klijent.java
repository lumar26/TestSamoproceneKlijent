package klijent;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;

public class Klijent implements Runnable {

	static int port = 8000;
	private static Socket komunikacioniSoket;
	private static BufferedReader tokSaServera;
	private static PrintStream tokKaServeru;
	private static BufferedReader unosSaTastature;

//	u okviru main funkcije obradjujemo prijem poruka
	public static void main(String[] args) {

		try {
			komunikacioniSoket = new Socket("localhost", port);
//			da inicijalizujemo sve tokove
			tokSaServera = new BufferedReader(new InputStreamReader(komunikacioniSoket.getInputStream()));
			tokKaServeru = new PrintStream(komunikacioniSoket.getOutputStream());
			unosSaTastature = new BufferedReader(new InputStreamReader(System.in));
			System.out.println("Uspostavljena je veza sa serverom, nadamo se\n Za prekid veze unesite '*quit*'");

//			ovim pokrecemo ono sto se desava u run() metodi
			Thread slanjePoruka = new Thread(new Klijent());
			slanjePoruka.start();

//			idemo sa jednom petljom koja obradjuje primljene poruke
			while (true) {
				String input = tokSaServera.readLine();
//				ispis na konzoli
				System.out.println(input);
//				if (input.equals("Odjava...")) {
//					break;
//				}
			}
//			System.out.println("----------------\nPrekid rada...\n-----------------");
////			ocu ovde da utepam ovaj thread samo onda kad server to kaze, 
//			slanjePoruka.interrupt();
			
		} catch (IOException e) {
			e.printStackTrace();
		} catch(NullPointerException npe) {
			System.err.println("Greska, null pinter" + npe.getMessage());
		}
	}

//	u okviru run() obradjujemo slanje poruka
	@Override
	public void run() {
		String poruka;
		while (true) {
//			uhvatimo poruku sa tastature
			try {
				poruka = unosSaTastature.readLine();
//				sad to treba da posaljemo
				tokKaServeru.println(poruka);

//				ako korisnik bilo kad unese '*quit*' raskida vezu sa serverom
				if(poruka.equals("*quit*")) break;
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}


		}
	}

}
