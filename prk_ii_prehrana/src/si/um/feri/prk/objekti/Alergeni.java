package si.um.feri.prk.objekti;

import java.util.ArrayList;

public class Alergeni {
	private int id;
	private String ime;
	private ArrayList<Recept> recepti = new ArrayList();

	public Alergeni() {
		super();
	}
	
	public Alergeni(int id, String ime) {
		super();
		this.id = id;
		this.ime = ime;
	}
	
	public Alergeni(int id, String ime, ArrayList recepti) {
		super();
		this.id = id;
		this.ime = ime;
		this.recepti = recepti;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getIme() {
		return ime;
	}

	public void setIme(String ime) {
		this.ime = ime;
	}

	public ArrayList<Recept> getRecepti() {
		return recepti;
	}

	public void setRecepti(ArrayList<Recept> recepti) {
		this.recepti = recepti;
	}
	
	
	
	
	

}