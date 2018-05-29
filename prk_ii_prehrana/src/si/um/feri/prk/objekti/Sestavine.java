package si.um.feri.prk.objekti;

public class Sestavine {
	private int id_sestavine, tk_recept_id;
	private double kolicina, kalorije, sladkorji;
	private String ime, enota_kolicine;
	
	public Sestavine() {
		super();
	}	
	public Sestavine(int id_sestavine, int tk_recept_id, double kolicina, double kalorije, double sladkorji, String ime,
			String enota_kolicine) {
		super();
		this.id_sestavine = id_sestavine;
		this.tk_recept_id = tk_recept_id;
		this.kolicina = kolicina;
		this.kalorije = kalorije;
		this.sladkorji = sladkorji;
		this.ime = ime;
		this.enota_kolicine = enota_kolicine;
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
	public int getId_sestavine() {
		return id_sestavine;
	}
	public void setId_sestavine(int id_sestavine) {
		this.id_sestavine = id_sestavine;
	}
	public double getKolicina() {
		return kolicina;
	}
	public void setKolicina(double kolicina) {
		this.kolicina = kolicina;
	}
	public int getTk_recept_id() {
		return tk_recept_id;
	}
	public void setTk_recept_id(int tk_recept_id) {
		this.tk_recept_id = tk_recept_id;
	}
	public String getIme() {
		return ime;
	}
	public void setIme(String ime) {
		this.ime = ime;
	}
	public String getEnota_kolicine() {
		return enota_kolicine;
	}
	public void setEnota_kolicine(String enota_kolicine) {
		this.enota_kolicine = enota_kolicine;
	}
	
	@Override
	public String toString() {
		return "Sestavine [id_sestavine=" + id_sestavine + ", tk_recept_id=" + tk_recept_id + ", kolicina=" + kolicina
				+ ", kalorije=" + kalorije + ", sladkorji=" + sladkorji + ", ime=" + ime + ", enota_kolicine="
				+ enota_kolicine + "]";
	}
}