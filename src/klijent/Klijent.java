package klijent;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.ConnectException;
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
				if (input.equals("KRAJ_RADA")) {
					System.out.println("Napustili ste aplikaciju");
					slanjePoruka.interrupt();
					komunikacioniSoket.close();
					tokKaServeru.close();
					tokSaServera.close();
					unosSaTastature.close();
					return;
				}

			}
		} catch (IOException e) {
			System.err.println("Nije uspostavljena konekcija sa serverom. Prekid aplikacije...");
		} catch(NullPointerException npe) {
			System.err.println("Greska, null pointer" + npe.getMessage());
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
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}


		}
	}

}
