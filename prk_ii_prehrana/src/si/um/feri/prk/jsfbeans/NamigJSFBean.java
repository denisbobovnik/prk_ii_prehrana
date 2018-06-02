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

public class NamigJSFBean {
	private String namig;

	
	
	public String getNamig() {
		return namig;
	}

	public void setNamig(String namig) {
		this.namig = namig;
	}
	
	private String getUserRole(){
		FacesContext context = FacesContext.getCurrentInstance();
		String[] vloge = {"ADMINISTRATOR", "STROKOVNJAK", "POSAMEZNIK"};
		String ret = "";
		for(String s : vloge)
			if(context.getExternalContext().isUserInRole(s))
				ret = s;
		return ret;
	}
	
	public void poslji() {
     		String obvestilo = namig;
     	    String m = "praktikumekd@gmail.com";
    		String g="praktikumEKD1997";
    		String mail = null;
	
			
			Properties props = new Properties();
			props.put("mail.smtp.auth", "true");
			props.put("mail.smtp.starttls.enable", "true");
			props.put("mail.smtp.host", "smtp.gmail.com");
			props.put("mail.smtp.port", "587");

			Session session = Session.getDefaultInstance(props,
				new javax.mail.Authenticator() {
					protected PasswordAuthentication getPasswordAuthentication() {
						return new PasswordAuthentication(m,g);
					}
				});

			try {

				Message message = new MimeMessage(session);
				message.setFrom(new InternetAddress(m));
				message.setRecipients(Message.RecipientType.TO,
					InternetAddress.parse(mail));
				message.setSubject("Registracija");
				message.setText(obvestilo);

				Transport.send(message);

				

			} catch (MessagingException e) {
				throw new RuntimeException(e);
			}
		
	}
	
}
