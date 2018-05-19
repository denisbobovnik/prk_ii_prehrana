package si.um.feri.prk.jsfbeans;

import java.io.IOException;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import org.primefaces.model.UploadedFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import si.um.feri.prk.dao.ClanekDAO;
import si.um.feri.prk.objekti.Clanek;

@ManagedBean(name="LogoutJSFBean")
@SessionScoped
public class LogoutJSFBean {

	Logger log=LoggerFactory.getLogger(ClanekJSFBean.class);

	public void logout() {
		FacesContext context = FacesContext.getCurrentInstance();
     	context.getExternalContext().invalidateSession();
         try {
		 context.getExternalContext().redirect("login.xhtml");
		 } catch (IOException e) {
		 e.printStackTrace();
		 }
	}
}