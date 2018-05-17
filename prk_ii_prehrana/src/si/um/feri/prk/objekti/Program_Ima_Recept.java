package si.um.feri.prk.objekti;

public class Program_Ima_Recept {
	private int id_Program_Ima_Recept, tk_id_program, tk_id_recept;
	private int dan;
	
	public Program_Ima_Recept() {
		super();
	}
	
	public Program_Ima_Recept(int id_Program_Ima_Recept, int dan) {
		super();
		this.id_Program_Ima_Recept = id_Program_Ima_Recept;
		this.dan = dan;
	}
	
	public Program_Ima_Recept(int id_Program_Ima_Recept, int tk_id_program, int tk_id_recept, int dan) {
		super();
		this.id_Program_Ima_Recept = id_Program_Ima_Recept;
		this.tk_id_program = tk_id_program;
		this.tk_id_recept = tk_id_recept;
		this.dan = dan;
	}

	public int getId_Program_Ima_Recept() {
		return id_Program_Ima_Recept;
	}

	public void setId_Program_Ima_Recept(int id_Program_Ima_Recept) {
		this.id_Program_Ima_Recept = id_Program_Ima_Recept;
	}

	public int getTk_id_program() {
		return tk_id_program;
	}

	public void setTk_id_program(int tk_id_program) {
		this.tk_id_program = tk_id_program;
	}

	public int getTk_id_recept() {
		return tk_id_recept;
	}

	public void setTk_id_recept(int tk_id_recept) {
		this.tk_id_recept = tk_id_recept;
	}

	public int getDan() {
		return dan;
	}

	public void setDan(int dan) {
		this.dan = dan;
	}
	
	
	
}
