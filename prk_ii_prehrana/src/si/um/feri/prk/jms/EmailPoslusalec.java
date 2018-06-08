package si.um.feri.prk.jms;

import java.text.SimpleDateFormat;
import java.util.GregorianCalendar;

import javax.ejb.ActivationConfigProperty;
import javax.ejb.EJB;
import javax.ejb.MessageDriven;
import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.Message;
import javax.jms.MessageListener;

@MessageDriven(activationConfig = {
		@ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Queue"),
		@ActivationConfigProperty(propertyName = "destination", propertyValue = "jms/queue/test"),
		@ActivationConfigProperty(propertyName = "acknowledgeMode", propertyValue = "Auto-acknowledge") })
public class EmailPoslusalec implements MessageListener {
    
	@EJB
	EmailSenderLocal esl;
	private static SimpleDateFormat sdf=new SimpleDateFormat("dd. MM. yyyy");
	
	public void onMessage(Message message) {
		if (message instanceof MapMessage) {
			System.out.println("[EmailPoslusalec] SPOROÈILO: email...");
			MapMessage mm = (MapMessage) message;
			try {
				String ime = mm.getString("ime");
				String priimek = mm.getString("priimek");
				String email = mm.getString("email");
				String password = mm.getString("password");
				
				//s pomoèjo esl ejb-ja se pošlje najprej mail uporabniko, da je uspešno se registriro, pol pa še admino (kr na naš email), da je zadolžen za registracijo...
				String vsebinaUporabniku, subjectUporabniku, vsebinaAdminu, subjectAdminu;
				String adminMail = "eprehrana@gmail.com";
				
				vsebinaUporabniku = "Lep pozdrav " + ime + " " + priimek + "! \n\nSporoèamo vam, da se se dne " + sdf.format(new GregorianCalendar().getTime()) + " uspešno registrirali na ePrehrana\nTo sporoèilo je avtomatizirano, zato nanj ne odgovarjajte. V kratkem boste prejeli prijavne podatke s strani administratorja, ko Vas bo vnesel v sistem. \n\nS spoštovanjem, \nePrehrana Team.";
				subjectUporabniku = "ePrehrana | Uspešna registracija!";
				vsebinaAdminu = "Lep pozdrav! \n\n" + ime + " " + priimek + " se je dne " + sdf.format(new GregorianCalendar().getTime()) + " registriral na ePrehrana. Uporabnik ima email: " + email + ", zadal pa si je geslo: " + password + "\nDodajte ga med uporabnike in mu osebno na njegov email posredujte prijavne podatke. \n\nS spoštovanjem, \nePrehrana Team.";
				subjectAdminu = "ePrehrana | Nova èakajoèa registracija";
				
				esl.posljiEmail(subjectUporabniku, vsebinaUporabniku, email);
				esl.posljiEmail(subjectAdminu, vsebinaAdminu, adminMail);
			} catch (JMSException e) {
				e.printStackTrace();
			}
		} else {
			System.out.println("Prispelo je neznano sporoèilo.");
		}
    }
}