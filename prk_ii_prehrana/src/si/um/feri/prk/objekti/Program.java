package si.um.feri.prk.objekti;

import java.sql.Blob;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.sql.rowset.serial.SerialException;

public class Program {
	private int id_program, tk_id_prehrana;
	private String naslov, tipPrograma, tipSlike, user_username;
	private Blob slika;
	private ArrayList<Enota> enote = new ArrayList<Enota>();
	
	public Program() {
		super();
	}
	public Program(int id_program, int tk_id_prehrana, String naslov, String tipPrograma, String tipSlike, Blob slika,
			String user_username) {
		super();
		this.id_program = id_program;
		this.tk_id_prehrana = tk_id_prehrana;
		this.naslov = naslov;
		this.tipPrograma = tipPrograma;
		this.tipSlike = tipSlike;
		this.slika = slika;
		this.user_username = user_username;
	}

	public int getId_program() {
		return id_program;
	}
	public void setId_program(int id_program) {
		this.id_program = id_program;
	}
	public int getTk_id_prehrana() {
		return tk_id_prehrana;
	}
	public void setTk_id_prehrana(int tk_id_prehrana) {
		this.tk_id_prehrana = tk_id_prehrana;
	}
	public String getNaslov() {
		return naslov;
	}
	public void setNaslov(String naslov) {
		this.naslov = naslov;
	}
	public String getTipPrograma() {
		return tipPrograma;
	}
	public void setTipPrograma(String tipPrograma) {
		this.tipPrograma = tipPrograma;
	}
	public String getTipSlike() {
		return tipSlike;
	}
	public void setTipSlike(String tipSlike) {
		this.tipSlike = tipSlike;
	}
	public Blob getSlika() {
		return slika;
	}
	public void setSlika(Blob slika) {
		this.slika = slika;
	}
	public String getUser_username() {
		return user_username;
	}
	public void setUser_username(String user_username) {
		this.user_username = user_username;
	}
	public ArrayList<Enota> getEnote() {
		return enote;
	}
	public void setEnote(ArrayList<Enota> enote) {
		this.enote = enote;
	}
	public void setSlika(byte[] iS) {
		try {
			this.slika = new javax.sql.rowset.serial.SerialBlob(iS);
		} catch (SerialException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public String toString() {
		return "Program [id_program=" + id_program + ", tk_id_prehrana=" + tk_id_prehrana + ", naslov=" + naslov
				+ ", tipPrograma=" + tipPrograma + ", tipSlike=" + tipSlike + ", slika=" + slika + ", user_username="
				+ user_username + "]";
	}
}