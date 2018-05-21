package si.um.feri.prk.dao;

import java.sql.Blob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import javax.naming.InitialContext;
import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import si.um.feri.prk.objekti.Clanek;
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
					
					
					ps.executeUpdate();
				
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				conn.close();
			}
		}
		public Recept najdi(int recept_id) throws Exception {
		
			Recept ret = null;
			Connection conn=null;
			try {
				conn=baza.getConnection();
				PreparedStatement ps = conn.prepareStatement("SELECT * FROM RECEPT WHERE id_recept=?");
				ps.setInt(1, recept_id);
				ResultSet rs = ps.executeQuery();
				
				while(rs.next()) {
					ret = new Recept(rs.getString("ime"));
					ret.setId_recept(recept_id);
					
					Blob blob = rs.getBlob("slika");
					int blobLength = (int) blob.length();  
					byte[] blobAsBytes = blob.getBytes(1, blobLength);
					
					ret.setSlika(blobAsBytes);
					
					
					break;
				}
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				conn.close();
			}
			return ret;
		}
		
		
		public ArrayList<Recept> vrniVse() throws Exception {
			ArrayList<Recept> seznam = new ArrayList<Recept>();
		
			Connection conn=null;
			try {
				conn=baza.getConnection();

				PreparedStatement ps = conn.prepareStatement("SELECT * FROM RECEPT");
				ResultSet rs = ps.executeQuery();
				while (rs.next()) {

					Recept recept = new Recept(rs.getInt("recept_id"),rs.getString("ime"), rs.getBlob("slika"));
					//recept.setTipSlika(rs.getString("tipSlike"));

					seznam.add(recept);
				}
				rs.close();
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				conn.close();
			}
			return seznam;
		}
		

}