package si.um.feri.prk.jsfbeans;

import java.security.Principal;
import java.text.SimpleDateFormat;
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
import si.um.feri.prk.objekti.Program;

@ManagedBean(name="ProgramJSFBean")
@SessionScoped
public class ProgramJSFBean {
	
	
	Logger log=LoggerFactory.getLogger(ProgramJSFBean.class);
	private ProgramDAO pD = ProgramDAO.getInstance();
	private Program izbranProgram = new Program();
	

	
	public void izberiProgram(int id_program) {
		log.info("ProgramJSFBean: izberiProgram");
		try {
			izbranProgram = pD.najdi(id_program);
		} catch (Exception e) {
			e.printStackTrace();
		};
	}
	

}