package si.um.feri.prk.jsfbeans;

import java.util.Properties;

import javax.faces.context.FacesContext;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import si.um.feri.prk.dao.NamigDAO;
import si.um.feri.prk.dao.PrehranaDAO;
import si.um.feri.prk.objekti.Namig;

public class NamigJSFBean {
	private Namig namig = new Namig();
	private Namig izbranNamig = new Namig();
	private NamigDAO nD = NamigDAO.getInstance();
	
	
	public Namig getNamig() {
		return namig;
	}
	public void setNamig(Namig namig) {
		this.namig = namig;
	}
	public Namig getIzbranNamig() {
		return izbranNamig;
	}
	public void setIzbranNamig(Namig izbranNamig) {
		this.izbranNamig = izbranNamig;
	}
	public String trimContent121(String content) {
		if(content.length()<121) {
			return content;
		} else {
			content = content.substring(0, 121);
			return content;
		}
	}
	
	
	
	
	
}
