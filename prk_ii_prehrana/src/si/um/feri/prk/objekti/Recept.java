package si.um.feri.prk.objekti;

import java.sql.Blob;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.sql.rowset.serial.SerialException;

public class Recept {
	private int id_recept, dolzinaPriprave, steviloPorcij, tk_id_enota;
	private String ime, opis, linkVideo, tipSlike;
	private Blob slika;
	private double kalorije;
	private ArrayList<Sestavine> sestavine = new ArrayList<Sestavine>();
	private ArrayList<Alergeni> alergeni = new ArrayList<Alergeni>();

	public Recept() {
		super();
	}
	public Recept(int id_recept, int dolzinaPriprave, int steviloPorcij, int tk_id_enota, String ime, String opis,
			String linkVideo, String tipSlike, Blob slika, double kalorije) {
		super();
		this.id_recept = id_recept;
		this.dolzinaPriprave = dolzinaPriprave;
		this.steviloPorcij = steviloPorcij;
		this.tk_id_enota = tk_id_enota;
		this.ime = ime;
		this.opis = opis;
		this.linkVideo = linkVideo;
		this.tipSlike = tipSlike;
		this.slika = slika;
		this.kalorije = kalorije;
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
	public int getTk_id_enota() {
		return tk_id_enota;
	}
	public void setTk_id_enota(int tk_id_enota) {
		this.tk_id_enota = tk_id_enota;
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
	
	@Override
	public String toString() {
		return "Recept [id_recept=" + id_recept + ", dolzinaPriprave=" + dolzinaPriprave + ", steviloPorcij="
				+ steviloPorcij + ", tk_id_enota=" + tk_id_enota + ", ime=" + ime + ", opis=" + opis + ", linkVideo="
				+ linkVideo + ", tipSlike=" + tipSlike + ", slika=" + slika + ", kalorije=" + kalorije + "]";
	}
}