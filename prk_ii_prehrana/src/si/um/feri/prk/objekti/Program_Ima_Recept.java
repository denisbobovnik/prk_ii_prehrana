package si.um.feri.prk.objekti;

public class Program_Ima_Recept {
	private int id_Program_Ima_Recept;
	private Recept recept = new Recept();
	private Program program = new Program();
	private String dan;
	
	public Program_Ima_Recept() {
		super();
	}
	
	public Program_Ima_Recept(int id_Program_Ima_Recept, String dan) {
		super();
		this.id_Program_Ima_Recept = id_Program_Ima_Recept;
		this.dan = dan;
	}
	
	public Program_Ima_Recept(int id_Program_Ima_Recept, Recept recept, Program program, String dan) {
		super();
		this.id_Program_Ima_Recept = id_Program_Ima_Recept;
		this.program = program;
		this.recept = recept;
		this.dan = dan;
	}

	public int getId_Program_Ima_Recept() {
		return id_Program_Ima_Recept;
	}

	public void setId_Program_Ima_Recept(int id_Program_Ima_Recept) {
		this.id_Program_Ima_Recept = id_Program_Ima_Recept;
	}

	public Program getProgram() {
		return program;
	}

	public void setProgram(Program program) {
		this.program = program;
	}

	public Recept getRecept() {
		return recept;
	}

	public void setRecept(Recept recept) {
		this.recept = recept;
	}

	public String getDan() {
		return dan;
	}

	public void setDan(String dan) {
		this.dan = dan;
	}
	
	
	
}