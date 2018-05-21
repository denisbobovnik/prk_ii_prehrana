package si.um.feri.prk.objekti;

import java.util.ArrayList;

public class Sestavine {
	private int id_sestavine;
	private String ime;
	private String enota;
	private ArrayList<Recept> list = new ArrayList();

	public Sestavine() {
		super();
	}
	
	public Sestavine(int id_sestavine, String ime, String enota) {
		super();
		this.id_sestavine = id_sestavine;
		this.ime = ime;
		this.enota = enota;
	}
	public Sestavine(int id_sestavine, String ime, String enota, ArrayList list) {
		super();
		this.id_sestavine = id_sestavine;
		this.ime = ime;
		this.enota = enota;
		this.list = list;
	}

	public int getId_sestavine() {
		return id_sestavine;
	}

	public void setId_sestavine(int id_sestavine) {
		this.id_sestavine = id_sestavine;
	}

	public String getIme() {
		return ime;
	}

	public void setIme(String ime) {
		this.ime = ime;
	}

	public String getEnota() {
		return enota;
	}

	public void setEnota(String enota) {
		this.enota = enota;
	}
	public ArrayList getList() {
		return list;
	}

	public void setRecept(ArrayList list) {
		this.list = list;
	}

	
	

	

	
}