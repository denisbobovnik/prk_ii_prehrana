package si.um.feri.prk.objekti;

import java.sql.Blob;
import java.sql.SQLException;

import javax.sql.rowset.serial.SerialException;

public class Recept {
	private int id_recept;
	private String ime;
	private String dolzinaPriprave;
	private int steviloPorcij;
	private String opis;
	private Blob slika;
	private String linkVideo;
	private double kalorije;
	
	
	public Recept() {
		super();
	}
	
	public Recept(int id_recept, String ime, String dolzinaPriprave, int steviloPorcij, String opis, double kalorije, Blob slika, String linkVideo) {
		super();
		this.id_recept = id_recept;
		this.ime = ime;
		this.dolzinaPriprave = dolzinaPriprave;
		this.steviloPorcij = steviloPorcij;
		this.opis = opis;
		this.kalorije = kalorije;
		this.slika = slika;
		this.linkVideo = linkVideo;
	}

	public int getId_recept() {
		return id_recept;
	}

	public void setId_recept(int id_recept) {
		this.id_recept = id_recept;
	}

	public String getIme() {
		return ime;
	}

	public void setIme(String ime) {
		this.ime = ime;
	}

	public String getDolzinaPriprave() {
		return dolzinaPriprave;
	}

	public void setDolzinaPriprave(String dolzinaPriprave) {
		this.dolzinaPriprave = dolzinaPriprave;
	}

	public int getSteviloPorcij() {
		return steviloPorcij;
	}

	public void setSteviloPorcij(int steviloPorcij) {
		this.steviloPorcij = steviloPorcij;
	}

	public String getOpis() {
		return opis;
	}

	public void setOpis(String opis) {
		this.opis = opis;
	}

	public double getKalorije() {
		return kalorije;
	}

	public void setKalorije(double kalorije) {
		this.kalorije = kalorije;
	}

	public Blob getSlika() {
		return slika;
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

	public String getLinkVideo() {
		return linkVideo;
	}

	public void setLinkVideo(String linkVideo) {
		this.linkVideo = linkVideo;
	}
	
	
    
	

}