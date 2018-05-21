package si.um.feri.prk.objekti;

import java.sql.Blob;
import java.sql.SQLException;

import javax.sql.rowset.serial.SerialException;

public class Program {
   private int id_program;
   private String naslov;
   private String autor;
   private Blob slika;
   private String user_username;
   private Recept recept = new Recept();
   private Prehrana prehrana = new Prehrana();
   
   public Program() {
		super();
	}  
    public Program(int id_program, String user_username, String naslov, String autor, Blob slika) {
	    super();
	    this.id_program = id_program;
	    this.user_username = user_username;
	    this.naslov = naslov;
	    this.autor = autor;
	    this.slika = slika;
   }
    public Program(int id_program, String user_username, String naslov, String autor, Blob slika, Recept recept, Prehrana prehrana) {
    	super();
    	this.id_program = id_program;
    	this.user_username = user_username;
    	this.naslov = naslov;
    	this.autor = autor;
    	this.slika = slika;
    	this.recept = recept;
    	this.prehrana = prehrana;
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
	public Recept getRecept() {
		return recept;
	}
	public void setRecept(Recept recept) {
		this.recept = recept;
	}
	public Prehrana getPrehrana() {
	    return prehrana;
	}
	public void setPrehrana(Prehrana prehrana) {
		this.prehrana = prehrana;
	}
	public String getUser_username() {
		return user_username;
	}
	public void setUser_username(String user_username) {
		this.user_username = user_username;
	}
    
}