package si.um.feri.prk.jsfbeans;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.Queue;
import javax.jms.QueueConnection;
import javax.jms.QueueConnectionFactory;
import javax.jms.QueueSender;
import javax.jms.QueueSession;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@ManagedBean(name="RegistrationJSFBean")
@SessionScoped
public class RegistrationJSFBean {
	
	Logger log=LoggerFactory.getLogger(RegistrationJSFBean.class);
	private String ime, priimek, email, password;
	
	public void registrirajUporabnika() {		
		InitialContext ctx;
		try {
			ctx = new InitialContext();
			Queue queue = (Queue) ctx.lookup("jms/queue/test");
			QueueConnectionFactory factory = (QueueConnectionFactory) ctx.lookup("ConnectionFactory");
			QueueConnection cnn = factory.createQueueConnection("guest", "guest");
			QueueSession session = cnn.createQueueSession(false, QueueSession.AUTO_ACKNOWLEDGE);
			QueueSender sender = session.createSender(queue);
			
			MapMessage mm = session.createMapMessage();
			mm.setString("ime", ime);
			mm.setString("priimek", priimek);
			mm.setString("email", email);
			mm.setString("password", password);
			sender.send(mm);

			session.close();
		} catch (NamingException e) {
			e.printStackTrace();
		} catch (JMSException e) {
			e.printStackTrace();
		}
		ime = null;
		priimek = null;
		email = null;
		password = null;
	}
	
	public String getIme() {
		return ime;
	}
	public void setIme(String ime) {
		this.ime = ime;
	}
	public String getPriimek() {
		return priimek;
	}
	public void setPriimek(String priimek) {
		this.priimek = priimek;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
}