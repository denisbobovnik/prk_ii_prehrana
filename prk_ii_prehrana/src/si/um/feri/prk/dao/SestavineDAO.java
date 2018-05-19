package si.um.feri.prk.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;

import javax.naming.InitialContext;
import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import si.um.feri.prk.objekti.Sestavine;

public class SestavineDAO {
	
	DataSource baza;
	Logger log=LoggerFactory.getLogger(SestavineDAO.class);
	
	private static SestavineDAO instance=null;
	public static synchronized SestavineDAO getInstance() {		
		if (instance==null) instance=new SestavineDAO();
		return instance;
	}
	
	private SestavineDAO() {
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
			conn.createStatement().execute("CREATE TABLE IF NOT EXISTS SESTAVINE(id_sestavine int not null auto_increment primary key, ime varchar(100) not null, enota varchar(10) not null)");
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
			conn.createStatement().execute("DROP TABLE IF EXISTS SESTAVINE CASCADE");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			conn.close();
		}
	}
	
	public void shrani(Sestavine s) throws Exception {
		log.info("SestavineDAO: shranjujem " + s);
		Connection conn=null;
		try {
			    conn=baza.getConnection();
				PreparedStatement ps = conn.prepareStatement("INSERT INTO SESTAVINE(ime, enota) VALUES (?,?)");
				ps.setString(1, s.getIme());
				ps.setString(2, s.getEnota());
				ps.executeUpdate();
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			conn.close();
		}
	}	
}