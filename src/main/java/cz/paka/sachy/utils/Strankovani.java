package cz.paka.sachy.utils;

/**
 * pomocna trida pro strankovani
 */
public class Strankovani {
	public static final int POCET_PRISPEVKU_NA_STRANKU = 10; // v diskuzi
	public static final int POCET_NOVINEK_NA_STRANKU = 3;
	public static final int POCET_PARTII_NA_STRANKU = 15;

	public static long spocitejPocetStran(long pocetCelkem, int pocetNaStranku) {
		long pocetStran = pocetCelkem / pocetNaStranku;
		if (pocetCelkem % pocetNaStranku > 0) {
			pocetStran += 1;
		}
		return pocetStran;
	}
}
