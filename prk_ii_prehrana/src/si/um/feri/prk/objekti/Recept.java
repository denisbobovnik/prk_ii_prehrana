package si.um.feri.prk.objekti;

import java.sql.Blob;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

import javax.sql.rowset.serial.SerialException;

public class Recept {
	private int id_recept, dolzinaPriprave, steviloPorcij;
	private String ime, opis, linkVideo, tipSlike;
	private Blob slika;
	private double kalorije, sladkorji;
	private Calendar datumDodajanja;
	private ArrayList<Sestavine> sestavine = new ArrayList<Sestavine>();
	private ArrayList<Alergeni> alergeni = new ArrayList<Alergeni>();
	private ArrayList<Enota> enote = new ArrayList<Enota>();

	public Recept() {
		super();
		this.datumDodajanja = new GregorianCalendar();
	}
	public Recept(int id_recept, int dolzinaPriprave, int steviloPorcij, double sladkorji, String ime, String opis,
			String linkVideo, String tipSlike, Blob slika, double kalorije) {
		super();
		this.id_recept = id_recept;
		this.sladkorji = sladkorji;
		this.dolzinaPriprave = dolzinaPriprave;
		this.steviloPorcij = steviloPorcij;
		this.ime = ime;
		this.opis = opis;
		this.linkVideo = linkVideo;
		this.tipSlike = tipSlike;
		this.slika = slika;
		this.kalorije = kalorije;
		this.datumDodajanja = new GregorianCalendar();
	}

	public int getId_recept() {
		return id_recept;
	}
	public void setId_recept(int id_recept) {
		this.id_recept = id_recept;
	}
	public int getDolzinaPriprave() {
		return dolzinaPriprave;
	}
	public void setDolzinaPriprave(int dolzinaPriprave) {
		this.dolzinaPriprave = dolzinaPriprave;
	}
	public int getSteviloPorcij() {
		return steviloPorcij;
	}
	public void setSteviloPorcij(int steviloPorcij) {
		this.steviloPorcij = steviloPorcij;
	}
	public ArrayList<Enota> getEnote() {
		return enote;
	}
	public void setEnote(ArrayList<Enota> enote) {
		this.enote = enote;
	}
	public String getIme() {
		return ime;
	}
	public void setIme(String ime) {
		this.ime = ime;
	}
	public String getOpis() {
		return opis;
	}
	public void setOpis(String opis) {
		this.opis = opis;
	}
	public String getLinkVideo() {
		return linkVideo;
	}
	public void setLinkVideo(String linkVideo) {
		this.linkVideo = linkVideo;
	}
	public String getTipSlike() {
		return tipSlike;
	}
	public void setTipSlike(String tipSlike) {
		this.tipSlike = tipSlike;
	}
	public Blob getSlika() {
		return slika;
	}
	public void setSlika(Blob slika) {
		this.slika = slika;
	}
	public double getKalorije() {
		return kalorije;
	}
	public void setKalorije(double kalorije) {
		this.kalorije = kalorije;
	}
	public ArrayList<Sestavine> getSestavine() {
		return sestavine;
	}
	public void setSestavine(ArrayList<Sestavine> sestavine) {
		this.sestavine = sestavine;
	}
	public ArrayList<Alergeni> getAlergeni() {
		return alergeni;
	}
	public void setAlergeni(ArrayList<Alergeni> alergeni) {
		this.alergeni = alergeni;
	}
	public void setSlika(byte[] iS) {
		try {
			this.slika = new javax.sql.rowset.serial.SerialBlob(iS);
		} catch (SerialException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	public Calendar getDatumDodajanja() {
		return datumDodajanja;
	}
	public void setDatumDodajanja(Calendar datumDodajanja) {
		this.datumDodajanja = datumDodajanja;
	}
	public double getSladkorji() {
		return sladkorji;
	}
	public void setSladkorji(double sladkorji) {
		this.sladkorji = sladkorji;
	}
	
	@Override
	public String toString() {
		return "Recept [id_recept=" + id_recept + ", dolzinaPriprave=" + dolzinaPriprave + ", steviloPorcij="
				+ steviloPorcij + ", ime=" + ime + ", opis=" + opis + ", linkVideo=" + linkVideo + ", tipSlike="
				+ tipSlike + ", slika=" + slika + ", kalorije=" + kalorije + ", sladkorji=" + sladkorji
				+ ", datumDodajanja=" + datumDodajanja + "]";
	}
}