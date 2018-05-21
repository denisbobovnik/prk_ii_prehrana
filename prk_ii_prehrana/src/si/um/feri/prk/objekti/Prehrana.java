package si.um.feri.prk.objekti;

import java.sql.Blob;
import java.util.ArrayList;

public class Prehrana {
	private int id_prehrana;
	private String naslovPrehrane;
	private Blob thumbnail;
	private String content;
	private ArrayList<Program> prehranskiProgrami;
	
	
	public Prehrana() {
		super();
	}
	
	public Prehrana(int id_prehrana, String naslovPrehrane) {
		super();
		this.id_prehrana = id_prehrana;
		this.naslovPrehrane = naslovPrehrane;
	}

	public int getId_prehrana() {
		return id_prehrana;
	}

	public void setId_prehrana(int id_prehrana) {
		this.id_prehrana = id_prehrana;
	}

	public String getNaslovPrehrane() {
		return naslovPrehrane;
	}

	public void setNaslovPrehrane(String naslovPrehrane) {
		this.naslovPrehrane = naslovPrehrane;
	}

	
}