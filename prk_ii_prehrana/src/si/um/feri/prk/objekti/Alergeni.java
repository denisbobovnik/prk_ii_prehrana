package si.um.feri.prk.objekti;

public class Alergeni {
	private int id;
	private String ime;
	private Recept recept = new Recept();

	public Alergeni() {
		super();
	}
	
	public Alergeni(int id, String ime) {
		super();
		this.id = id;
		this.ime = ime;
	}
	
	public Alergeni(int id, String ime, Recept recept) {
		super();
		this.id = id;
		this.ime = ime;
		this.recept = recept;
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
	public Recept getRecept() {
		return recept;
	}

	public void setRecept(Recept recept) {
		this.recept = recept;
	}
	
	
	
	

}