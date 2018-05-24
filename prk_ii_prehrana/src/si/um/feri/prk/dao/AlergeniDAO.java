package si.um.feri.prk.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
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
		log.info("AlergeniDAO: AlergeniDAO ");
		try {
			baza=(DataSource)new InitialContext().lookup("java:jboss/datasources/prk_ii_prehrana");	
			kreirajTabele();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void kreirajTabele() throws Exception {
		log.info("AlergeniDAO: kreirajTabele ");
		Connection conn=null;
		try {
			conn=baza.getConnection();
			conn.createStatement().execute("CREATE TABLE IF NOT EXISTS Alergeni(id_alergen int not null auto_increment primary key, ime_alergena varchar(60) not null, tk_recept_id int not null)");
			} catch (Exception e) {
			e.printStackTrace();
		} finally {
			conn.close();
		}
	}
	
	public void pobrisiTabele() throws Exception {
		log.info("AlergeniDAO: pobrisiTabele ");
		Connection conn=null;
		try {
			conn=baza.getConnection();
			conn.createStatement().execute("DROP TABLE IF EXISTS Alergeni CASCADE");
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
			PreparedStatement ps = conn.prepareStatement("INSERT INTO Alergeni(id_alergen, ime_alergena, tk_recept_id) VALUES (?,?,?)");
			ps.setInt(1, a.getId_alergen());
			ps.setString(2, a.getIme_alergena());
			ps.setInt(3, a.getTk_recept_id());
			ps.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			conn.close();
		}
	}	
	
	public ArrayList<Alergeni> najdiVsePoReceptu(int tk_recept_id) throws Exception {
		log.info("AlergeniDAO: najdiVsePoReceptu " + tk_recept_id);
		ArrayList<Alergeni> seznam = new ArrayList<Alergeni>();
		
		Connection conn=null;
		try {
			conn=baza.getConnection();
			PreparedStatement ps = conn.prepareStatement("SELECT * FROM Alergeni WHERE tk_recept_id=?");
			ps.setInt(1, tk_recept_id);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				Alergeni alergen = new Alergeni(rs.getInt("id_alergen"), rs.getInt("tk_recept_id"), rs.getString("ime_alergena"));
				seznam.add(alergen);
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