package si.um.feri.prk.objekti;

import java.util.ArrayList;

public class Enota {

	private int id_enota, tk_program_id, tk_recept_id;
	private String dan_v_tednu;
	
	public Enota() {
		super();
	}
	public Enota(int id_enota, int tk_program_id, int tk_recept_id, String dan_v_tednu) {
		super();
		this.id_enota = id_enota;
		this.tk_program_id = tk_program_id;
		this.tk_recept_id = tk_recept_id;
		this.dan_v_tednu = dan_v_tednu;
	}

	public int getId_enota() {
		return id_enota;
	}
	public void setId_enota(int id_enota) {
		this.id_enota = id_enota;
	}
	public int getTk_program_id() {
		return tk_program_id;
	}
	public void setTk_program_id(int tk_program_id) {
		this.tk_program_id = tk_program_id;
	}
	public int getTk_recept_id() {
		return tk_recept_id;
	}
	public void setTk_recept_id(int tk_recept_id) {
		this.tk_recept_id = tk_recept_id;
	}
	public String getDan_v_tednu() {
		return dan_v_tednu;
	}
	public void setDan_v_tednu(String dan_v_tednu) {
		this.dan_v_tednu = dan_v_tednu;
	}

	@Override
	public String toString() {
		return "Enota [id_enota=" + id_enota + ", tk_program_id=" + tk_program_id + ", tk_recept_id=" + tk_recept_id
				+ ", dan_v_tednu=" + dan_v_tednu + "]";
	}
}