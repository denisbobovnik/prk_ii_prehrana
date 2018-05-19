package si.um.feri.prk.objekti;

import java.sql.Blob;
import java.sql.SQLException;

import javax.sql.rowset.serial.SerialException;

public class Program {
   private int id_program, tk_id_prehrana;
   private String naslov;
   private String autor;
   private Blob slika;
   
   public Program() {
		super();
	}  
    public Program(int id_program, String naslov, String autor, Blob slika) {
	    super();
	    this.id_program = id_program;
	    this.naslov = naslov;
	    this.autor = autor;
	    this.slika = slika;
   }
    public Program(int id_program, int tk_id_prehrana, String naslov, String autor, Blob slika) {
    	super();
    	this.id_program = id_program;
    	this.tk_id_prehrana = tk_id_prehrana;
    	this.naslov = naslov;
    	this.autor = autor;
    	this.slika = slika;
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
	public String getAutor() {
		return autor;
	}
	public void setAutor(String autor) {
		this.autor = autor;
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
	public int getTk_id_prehrana() {
		return tk_id_prehrana;
	}
	public void setTk_id_prehrana(int tk_id_prehrana) {
		this.tk_id_prehrana = tk_id_prehrana;
	}
	
    
}