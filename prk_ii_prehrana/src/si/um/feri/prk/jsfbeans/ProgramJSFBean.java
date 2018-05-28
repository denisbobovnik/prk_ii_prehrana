package si.um.feri.prk.jsfbeans;

import java.security.Principal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import javax.ejb.SessionContext;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.naming.InitialContext;

import org.primefaces.model.UploadedFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import si.um.feri.prk.dao.ClanekDAO;
import si.um.feri.prk.dao.ProgramDAO;
import si.um.feri.prk.objekti.Clanek;
import si.um.feri.prk.objekti.Enota;
import si.um.feri.prk.objekti.Program;
import si.um.feri.prk.objekti.Recept;

@ManagedBean(name="ProgramJSFBean")
@SessionScoped
public class ProgramJSFBean {
	
	
	Logger log=LoggerFactory.getLogger(ProgramJSFBean.class);
	private ProgramDAO pD = ProgramDAO.getInstance();
	private Program p = new Program();
	private Program izbranProgram = new Program();
	private UploadedFile thumbnail;
	private Enota enota = new Enota();
	private Recept izbranRecept = new Recept();
	
	public void dodajProgram() {
		try {
			String str = thumbnail.getFileName();
			if(str.contains(".")) {
				String ext = str.substring(str.lastIndexOf('.'), str.length());
				if(ext.equalsIgnoreCase(".jpg")||(ext.equalsIgnoreCase(".png"))||(ext.equalsIgnoreCase(".jpeg"))||(ext.equalsIgnoreCase(".gif"))) {
					p.setSlika(thumbnail.getContents());
					pD.shrani(p);
					p = new Program();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			FacesMessage errorMsg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Napaka nalaganja!", e.getMessage());
			FacesContext.getCurrentInstance().addMessage(null, errorMsg);
		}
	}

	public void dodajEnoto() {
		p.getEnote().add(enota);
		enota = new Enota();
	}
	public void dodajRecept() {
	//	enota.getRecepti().add(izbranRecept);
		izbranRecept = new Recept();
	}
	
	public Recept getIzbranRecept() {
		return izbranRecept;
	}

	public void setIzbranRecept(Recept izbranRecept) {
		this.izbranRecept = izbranRecept;
	}
	public Enota getEnota() {
		return enota;
	}

	public void setEnota(Enota enota) {
		this.enota = enota;
	}

	public UploadedFile getThumbnail() {
		return thumbnail;
	}

	public void setThumbnail(UploadedFile thumbnail) {
		this.thumbnail = thumbnail;
	}

	public ProgramDAO getpD() {
		return pD;
	}

	public void setpD(ProgramDAO pD) {
		this.pD = pD;
	}

	public Program getP() {
		return p;
	}

	public void setP(Program p) {
		this.p = p;
	}

	public Program getIzbranProgram() {
		return izbranProgram;
	}

	public void setIzbranProgram(Program izbranProgram) {
		this.izbranProgram = izbranProgram;
	}

	public void izberiProgram(int id_program) {
		log.info("ProgramJSFBean: izberiProgram");
		try {
			izbranProgram = pD.najdi(id_program);
		} catch (Exception e) {
			e.printStackTrace();
		};
	}
	

}