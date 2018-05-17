package si.um.feri.prk.objekti;

public class Alergeni {
	private int id, tk_id_recept;
	private String ime;
	
	public Alergeni() {
		super();
	}
	
	public Alergeni(int id, String ime) {
		super();
		this.id = id;
		this.ime = ime;
	}
	
	public Alergeni(int id, int tk_id_recept, String ime) {
		super();
		this.id = id;
		this.tk_id_recept = tk_id_recept;
		this.ime = ime;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getTk_id_recept() {
		return tk_id_recept;
	}

	public void setTk_id_recept(int tk_id_recept) {
		this.tk_id_recept = tk_id_recept;
	}

	public String getIme() {
		return ime;
	}

	public void setIme(String ime) {
		this.ime = ime;
	}
	
	
	
	

}
