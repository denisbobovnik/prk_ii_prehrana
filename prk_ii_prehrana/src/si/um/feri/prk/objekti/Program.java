package si.um.feri.prk.objekti;

import java.sql.Blob;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.sql.rowset.serial.SerialException;

public class Program {
   private int id_program, tk_id_prehrana;
   private String naslov, tipPrograma, tipSlike;
   private Blob slika;
   private String user_username;
   private ArrayList<Recept> recepti = new ArrayList<Recept>();
   
   public Program() {
		super();
	}  
    public Program(int id_program, String user_username, String naslov, Blob slika, String tipPrograma, String tipSlike) {
	    super();
	    this.id_program = id_program;
	    this.user_username = user_username;
	    this.naslov = naslov;
	    this.slika = slika;
	    this.tipPrograma = tipPrograma;
	    this.tipSlike = tipSlike;
   }
    public Program(int id_program, String user_username, String naslov, Blob slika, ArrayList<Recept> recepti, String tipPrograma, String tipSlike) {
    	super();
    	this.id_program = id_program;
    	this.user_username = user_username;
    	this.naslov = naslov;
    	this.slika = slika;
    	this.recepti = recepti;
    	this.tipPrograma = tipPrograma;
    	this.tipSlike = tipSlike;
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
	public String getNaslov() {
		return naslov;
	}
	public void setNaslov(String naslov) {
		this.naslov = naslov;
	}
	public Blob getSlika() {
		return slika;
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
	public String getUser_username() {
		return user_username;
	}
	public void setUser_username(String user_username) {
		this.user_username = user_username;
	}
	public ArrayList<Recept> getRecepti() {
		return recepti;
	}
	public void setRecepti(ArrayList<Recept> recepti) {
		this.recepti = recepti;
	}
	public String getTipPrograma() {
		return tipPrograma;
	}
	public void setTipPrograma(String tipPrograma) {
		this.tipPrograma = tipPrograma;
	}
	public void setSlika(Blob slika) {
		this.slika = slika;
	}
	public String getTipSlike() {
		return tipSlike;
	}
	public void setTipSlike(String tipSlike) {
		this.tipSlike = tipSlike;
	}
	public int getTk_id_prehrana() {
		return tk_id_prehrana;
	}
	public void setTk_id_prehrana(int tk_id_prehrana) {
		this.tk_id_prehrana = tk_id_prehrana;
	}
	
	@Override
	public String toString() {
		return "Program [id_program=" + id_program + ", tk_id_prehrana=" + tk_id_prehrana + ", naslov=" + naslov
				+ ", tipPrograma=" + tipPrograma + ", tipSlike=" + tipSlike + ", slika=" + slika + ", user_username="
				+ user_username + "]";
	}
}