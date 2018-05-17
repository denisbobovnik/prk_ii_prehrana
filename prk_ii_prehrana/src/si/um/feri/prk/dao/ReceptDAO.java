package si.um.feri.prk.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;

import javax.naming.InitialContext;
import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import si.um.feri.prk.objekti.Recept;

public class ReceptDAO {


		DataSource baza;
		Logger log=LoggerFactory.getLogger(ReceptDAO.class);
		
		private static ReceptDAO instance=null;
		public static synchronized ReceptDAO getInstance() {		
			if (instance==null) instance=new ReceptDAO();
			return instance;
		}
		
		private ReceptDAO() {
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
				conn.createStatement().execute("CREATE TABLE IF NOT EXISTS RECEPT(id_recept int not null auto_increment primary key, ime varchar(100) not null, dolzinaPriprave varchar(20) not null,steviloPorcij int not null, opis varchar(500) not null, slika longblob not null, linkVideo varchar(700) not null, kalorije double not null");
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
				conn.createStatement().execute("DROP TABLE IF EXISTS RECEPT CASCADE");
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				conn.close();
			}
		}
		
		public void shrani(Recept r) throws Exception {
			log.info("ReceptDAO: shranjujem " + r);
			Connection conn=null;
			try {
				conn=baza.getConnection();

					PreparedStatement ps = conn.prepareStatement("INSERT INTO RECEPT(ime , dolzinaPriprave,steviloPorcij, opis, slika , linkVideo, kalorije) VALUES (?,?,?,?,?,?,?)");
					ps.setString(1, r.getIme());
					ps.setString(2, r.getDolzinaPriprave());
					ps.setInt(3, r.getSteviloPorcij());
					ps.setString(4, r.getOpis());
					ps.setBinaryStream(5, r.getSlika().getBinaryStream());
					ps.setString(6, r.getLinkVideo());
					ps.setDouble(7, r.getKalorije());
					//ps.setInt(5, p.getUser_id);
					
					ps.executeUpdate();
				
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				conn.close();
			}
		}	

}
