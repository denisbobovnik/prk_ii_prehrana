package si.um.feri.prk.rest;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.sql.rowset.serial.SerialException;
import si.um.feri.prk.objekti.Alergeni;
import si.um.feri.prk.objekti.Sestavine;

public class ReceptJSON implements Serializable {
	private int id_recept;
	private String ime, opis, kategorija;
	private double kalorije, sladkorji;
	private ArrayList<Sestavine> sestavine = new ArrayList<Sestavine>();
	private ArrayList<Alergeni> alergeni = new ArrayList<Alergeni>();

	public ReceptJSON() {
		super();
	}
	public ReceptJSON(int id_recept, String ime, String opis, String kategorija, double kalorije, double sladkorji) {
		super();
		this.id_recept = id_recept;
		this.ime = ime;
		this.opis = opis;
		this.kategorija = kategorija;
		this.kalorije = kalorije;
		this.sladkorji = sladkorji;
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
	public String getOpis() {
		return opis;
	}
	public void setOpis(String opis) {
		this.opis = opis;
	}
	public String getKategorija() {
		return kategorija;
	}
	public void setKategorija(String kategorija) {
		this.kategorija = kategorija;
	}
	public double getKalorije() {
		return kalorije;
	}
	public void setKalorije(double kalorije) {
		this.kalorije = kalorije;
	}
	public double getSladkorji() {
		return sladkorji;
	}
	public void setSladkorji(double sladkorji) {
		this.sladkorji = sladkorji;
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
	
	@Override
	public String toString() {
		return "ReceptJSON [id_recept=" + id_recept + ", ime=" + ime + ", opis=" + opis + ", kategorija=" + kategorija
				+ ", kalorije=" + kalorije + ", sladkorji=" + sladkorji + "]";
	}
}