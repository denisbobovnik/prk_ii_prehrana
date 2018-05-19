package si.um.feri.prk.objekti;

public class Recept_Ima_Sestavino {
	private int id_Recept_Ima_Sestavino;
	private Recept recept = new Recept();
	private Sestavine sestavine = new Sestavine();
	private double kolicina;
	
	public Recept_Ima_Sestavino() {
		super();
	}
	
	public Recept_Ima_Sestavino(int id_Recept_Ima_Sestavino, double kolicina) {
		super();
		this.id_Recept_Ima_Sestavino = id_Recept_Ima_Sestavino;
		this.kolicina = kolicina;
	}
	
	public Recept_Ima_Sestavino(int id_Recept_Ima_Sestavino, Recept recept, Sestavine sestavine, double kolicina) {
		super();
		this.id_Recept_Ima_Sestavino = id_Recept_Ima_Sestavino;
		this.recept=recept;
		this.sestavine = sestavine;
		this.kolicina = kolicina;
	}

	public int getId_Recept_Ima_Sestavino() {
		return id_Recept_Ima_Sestavino;
	}

	public void setId_Recept_Ima_Sestavino(int id_Recept_Ima_Sestavino) {
		this.id_Recept_Ima_Sestavino = id_Recept_Ima_Sestavino;
	}

	public Recept getRecept() {
		return recept;
	}

	public void setRecept(Recept recept) {
		this.recept = recept;
	}

	public Sestavine getSestavine() {
		return sestavine;
	}

	public void setSestavine(Sestavine sestavine) {
		this.sestavine = sestavine;
	}

	public double getKolicina() {
		return kolicina;
	}

	public void setKolicina(double kolicina) {
		this.kolicina = kolicina;
	}
	
	
	
	

}