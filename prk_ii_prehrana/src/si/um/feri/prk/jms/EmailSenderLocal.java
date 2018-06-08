package si.um.feri.prk.jms;

import javax.ejb.Local;

@Local
public interface EmailSenderLocal {
	void posljiEmail(String subject, String vsebina, String komu);
}