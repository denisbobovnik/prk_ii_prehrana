package si.um.feri.prk.objekti;

public class Recept_Ima_Sestavino {
	private int id_Recept_Ima_Sestavino, tk_id_recept, tk_id_sestavine;
	private double kolicina;
	
	public Recept_Ima_Sestavino() {
		super();
	}
	
	public Recept_Ima_Sestavino(int id_Recept_Ima_Sestavino, double kolicina) {
		super();
		this.id_Recept_Ima_Sestavino = id_Recept_Ima_Sestavino;
		this.kolicina = kolicina;
	}
	
	public Recept_Ima_Sestavino(int id_Recept_Ima_Sestavino, int tk_id_recept, int tk_id_sestavine, double kolicina) {
		super();
		this.id_Recept_Ima_Sestavino = id_Recept_Ima_Sestavino;
		this.tk_id_recept = tk_id_recept;
		this.tk_id_sestavine = tk_id_sestavine;
		this.kolicina = kolicina;
	}

	public int getId_Recept_Ima_Sestavino() {
		return id_Recept_Ima_Sestavino;
	}

	public void setId_Recept_Ima_Sestavino(int id_Recept_Ima_Sestavino) {
		this.id_Recept_Ima_Sestavino = id_Recept_Ima_Sestavino;
	}

	public int getTk_id_recept() {
		return tk_id_recept;
	}

	public void setTk_id_recept(int tk_id_recept) {
		this.tk_id_recept = tk_id_recept;
	}

	public int getTk_id_sestavine() {
		return tk_id_sestavine;
	}

	public void setTk_id_sestavine(int tk_id_sestavine) {
		this.tk_id_sestavine = tk_id_sestavine;
	}

	public double getKolicina() {
		return kolicina;
	}

	public void setKolicina(double kolicina) {
		this.kolicina = kolicina;
	}
	
	
	
	

}
