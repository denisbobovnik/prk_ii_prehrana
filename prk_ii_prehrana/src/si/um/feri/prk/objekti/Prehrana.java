package si.um.feri.prk.objekti;


public class Prehrana {
	private int id_prehrana;
	private String naslovPrehrane;
	
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
