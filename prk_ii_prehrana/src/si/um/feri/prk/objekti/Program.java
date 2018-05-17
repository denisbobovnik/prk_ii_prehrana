package si.um.feri.prk.objekti;

import java.sql.Blob;


public class Program {
   private int id_program;
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
	public void setSlika(Blob slika) {
		this.slika = slika;
	}
    
}
