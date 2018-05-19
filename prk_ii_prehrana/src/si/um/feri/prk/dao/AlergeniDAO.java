package si.um.feri.prk.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;

import javax.naming.InitialContext;
import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import si.um.feri.prk.objekti.Alergeni;

public class AlergeniDAO {

	DataSource baza;
	Logger log=LoggerFactory.getLogger(AlergeniDAO.class);
	
	private static AlergeniDAO instance=null;
	public static synchronized AlergeniDAO getInstance() {		
		if (instance==null) instance=new AlergeniDAO();
		return instance;
	}
	
	private AlergeniDAO() {
		try {
			baza=(DataSource)new InitialContext().lookup("java:jboss/datasources/prk_ii_prehrana");	
			kreirajTabele();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void kreirajTabele() throws Exception {
		Connection conn=null;
		try {
			conn=baza.getConnection();
			conn.createStatement().execute("CREATE TABLE IF NOT EXISTS ALERGENI(id_alergeni int not null auto_increment primary key, ime varchar(100) not null, tk_id_recept int not null)");
			} catch (Exception e) {
			e.printStackTrace();
		} finally {
			conn.close();
		}
	}
	
	public void pobrisiTabele() throws Exception {
		Connection conn=null;
		try {
			conn=baza.getConnection();
			conn.createStatement().execute("DROP TABLE IF EXISTS ALERGENI CASCADE");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			conn.close();
		}
	}
	
	public void shrani(Alergeni a) throws Exception {
		log.info("AlergeniDAO: shranjujem " + a);
		Connection conn=null;
		try {
			    conn=baza.getConnection();
				PreparedStatement ps = conn.prepareStatement("INSERT INTO SESTAVINE(ime, tk_id_recept) VALUES (?,?)");
				ps.setString(1, a.getIme());
				ps.setInt(2, a.getTk_id_recept());
				ps.executeUpdate();
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			conn.close();
		}
	}	
	

}