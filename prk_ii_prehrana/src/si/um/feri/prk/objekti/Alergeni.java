package si.um.feri.prk.objekti;

public class Alergeni {
	
	private int id_alergen, tk_recept_id;
	private String ime_alergena;

	public Alergeni() {
		super();
	}
	public Alergeni(int id_alergen, int tk_recept_id, String ime_alergena) {
		super();
		this.id_alergen = id_alergen;
		this.tk_recept_id = tk_recept_id;
		this.ime_alergena = ime_alergena;
	}
	
	public int getId_alergen() {
		return id_alergen;
	}
	public void setId_alergen(int id_alergen) {
		this.id_alergen = id_alergen;
	}
	public int getTk_recept_id() {
		return tk_recept_id;
	}
	public void setTk_recept_id(int tk_recept_id) {
		this.tk_recept_id = tk_recept_id;
	}
	public String getIme_alergena() {
		return ime_alergena;
	}
	public void setIme_alergena(String ime_alergena) {
		this.ime_alergena = ime_alergena;
	}
	
	@Override
	public String toString() {
		return "Alergeni [id_alergen=" + id_alergen + ", tk_recept_id=" + tk_recept_id + ", ime_alergena="
				+ ime_alergena + "]";
	}
}