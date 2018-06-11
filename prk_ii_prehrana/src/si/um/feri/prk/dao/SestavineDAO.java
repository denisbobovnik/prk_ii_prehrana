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
		log.info("SestavineDAO: SestavineDAO");
		try {
			baza=(DataSource)new InitialContext().lookup("java:jboss/datasources/prk_ii_prehrana");	
			kreirajTabele();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void kreirajTabele() throws Exception {
		log.info("SestavineDAO: kreirajTabele");
		Connection conn=null;
		try {
			conn=baza.getConnection();
			conn.createStatement().execute("CREATE TABLE IF NOT EXISTS Sestavine(id_sestavine int not null auto_increment primary key, ime varchar(500) not null, enota_kolicine varchar(45) not null, tk_recept_id int not null, kolicina double not null, kalorije double not null, sladkorji double not null)");
			} catch (Exception e) {
			e.printStackTrace();
		} finally {
			conn.close();
		}
	}
	
	public void pobrisiTabele() throws Exception {
		log.info("SestavineDAO: pobrisiTabele");
		Connection conn=null;
		try {
			conn=baza.getConnection();
			conn.createStatement().execute("DROP TABLE IF EXISTS Sestavine CASCADE");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			conn.close();
		}
	}
	
	public Sestavine najdi(int id_sestavine) throws Exception {
		log.info("SestavineDAO: najdi " + id_sestavine);
		Sestavine ret = null;
		Connection conn=null;
		try {
			conn=baza.getConnection();
			PreparedStatement ps = conn.prepareStatement("SELECT * FROM Sestavine WHERE id_sestavine=?");
			ps.setInt(1, id_sestavine);
			ResultSet rs = ps.executeQuery();
			
			while(rs.next()) {
				ret = new Sestavine(rs.getInt("id_sestavine"), rs.getInt("tk_recept_id"), rs.getDouble("kolicina"), rs.getDouble("kalorije"), rs.getDouble("sladkorji"), rs.getString("ime"), rs.getString("enota_kolicine"));
				break;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			conn.close();
		}
		return ret;
	}
	
	public void shrani(Sestavine s) throws Exception {
		log.info("SestavineDAO: shranjujem " + s);
		Connection conn=null;
		try {
			conn=baza.getConnection();
			PreparedStatement ps = conn.prepareStatement("INSERT INTO Sestavine(id_sestavine, ime, enota_kolicine, tk_recept_id, kolicina, kalorije, sladkorji) VALUES (?,?,?,?,?,?,?)");
			ps.setInt(1, s.getId_sestavine());
			ps.setString(2, s.getIme());
			ps.setString(3, s.getEnota_kolicine());
			ps.setInt(4, s.getTk_recept_id());
			ps.setDouble(5, s.getKolicina());
			ps.setDouble(6, s.getKalorije());
			ps.setDouble(7, s.getSladkorji());
			
			ps.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			conn.close();
		}
	}
	
	public ArrayList<Sestavine> najdiVsePoReceptu(int tk_recept_id) throws Exception {
		log.info("SestavineDAO: najdiVsePoReceptu " + tk_recept_id);
		ArrayList<Sestavine> seznam = new ArrayList<Sestavine>();
		
		Connection conn=null;
		try {
			conn=baza.getConnection();
			PreparedStatement ps = conn.prepareStatement("SELECT * FROM Sestavine WHERE tk_recept_id=?");
			ps.setInt(1, tk_recept_id);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				Sestavine sestavina = new Sestavine(rs.getInt("id_sestavine"), rs.getInt("tk_recept_id"), rs.getDouble("kolicina"), rs.getDouble("kalorije"), rs.getDouble("sladkorji"), rs.getString("ime"), rs.getString("enota_kolicine"));
				seznam.add(sestavina);
			}
			rs.close();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			conn.close();
		}
		return seznam;
	}
	
	public ArrayList<Sestavine> vrniVse() throws Exception {
		log.info("SestavineDAO: vrniVse ");
		ArrayList<Sestavine> seznam = new ArrayList<Sestavine>();
	
		Connection conn=null;
		try {
			conn=baza.getConnection();

			PreparedStatement ps = conn.prepareStatement("SELECT * FROM Sestavine");
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				Sestavine sestavine = new Sestavine(rs.getInt("id_sestavine"), rs.getInt("tk_recept_id"), rs.getDouble("kolicina"), rs.getDouble("kalorije"), rs.getDouble("sladkorji"), rs.getString("ime"), rs.getString("enota_kolicine"));
				seznam.add(sestavine);
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