package si.um.feri.prk.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import javax.naming.InitialContext;
import javax.sql.DataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import si.um.feri.prk.objekti.Enota;
import si.um.feri.prk.objekti.Recept;

public class EnotaDAO {
	
	DataSource baza;
	Logger log=LoggerFactory.getLogger(EnotaDAO.class);
	private ReceptDAO rD = ReceptDAO.getInstance();
	
	private static EnotaDAO instance=null;
	public static synchronized EnotaDAO getInstance() {		
		if (instance==null) instance=new EnotaDAO();
			return instance;
	}
	
	private EnotaDAO() {
		log.info("EnotaDAO: EnotaDAO ");
		try {
			baza=(DataSource)new InitialContext().lookup("java:jboss/datasources/prk_ii_prehrana");	
			kreirajTabele();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void kreirajTabele() throws Exception {
		log.info("EnotaDAO: kreirajTabele ");
		Connection conn=null;
		try {
			conn=baza.getConnection();
			conn.createStatement().execute("CREATE TABLE IF NOT EXISTS Enota(id_enota int not null auto_increment primary key, tk_program_id int not null, dan_v_tednu varchar(100) not null)");
			} catch (Exception e) {
			e.printStackTrace();
		} finally {
			conn.close();
		}
	}
	
	public void pobrisiTabele() throws Exception {
		log.info("EnotaDAO: pobrisiTabele ");
		Connection conn=null;
		try {
			conn=baza.getConnection();
			conn.createStatement().execute("DROP TABLE IF EXISTS Enota CASCADE");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			conn.close();
		}
	}
	
	public Enota najdi(int id_enota) throws Exception{
		log.info("EnotaDAO: najdi " + id_enota);
		Enota ret = null;
		Connection conn=null;
		try {
			conn=baza.getConnection();
			PreparedStatement ps = conn.prepareStatement("SELECT * FROM Enota WHERE id_enota=?");
			ps.setInt(1, id_enota);
			ResultSet rs = ps.executeQuery();
			
			while(rs.next()) {
				ret = new Enota(id_enota, rs.getInt("tk_program_id"), rs.getString("dan_v_tednu"));
				ret.setRecepti(rD.najdiVsePoEnoti(ret.getId_enota()));
				break;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			conn.close();
		}
		return ret;
	}
	
	public void shrani(Enota e) throws Exception {
		log.info("EnotaDAO: shranjujem " + e);
		Connection conn=null;
		try {
			conn=baza.getConnection();
			if(najdi(e.getId_enota()) != null) {
				//že obstaja, update
			} else { //ne obstaja, insert
				PreparedStatement ps = conn.prepareStatement("INSERT INTO Enota(id_enota, tk_program_id, dan_v_tednu) VALUES (?,?,?)");
				ps.setInt(1, e.getId_enota());
				ps.setInt(2, e.getTk_program_id());
				ps.setString(3, e.getDan_v_tednu());
				
				ps.executeUpdate();
				
				for(Recept r : e.getRecepti())
					rD.shrani(r);
			}
		} catch (Exception e1) {
			e1.printStackTrace();
		} finally {
			conn.close();
		}
	}
	
	public ArrayList<Enota> najdiVsePoProgramu(int tk_program_id) throws Exception {
		log.info("EnotaDAO: najdiVsePoProgramu " + tk_program_id);
		ArrayList<Enota> seznam = new ArrayList<Enota>();
		
		Connection conn=null;
		try {
			conn=baza.getConnection();
			PreparedStatement ps = conn.prepareStatement("SELECT * FROM Enota WHERE tk_program_id=?");
			ps.setInt(1, tk_program_id);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				Enota enota = new Enota(rs.getInt("id_enota"), rs.getInt("tk_program_id"), rs.getString("dan_v_tednu"));
				enota.setRecepti(rD.najdiVsePoEnoti(enota.getId_enota()));
				seznam.add(enota);
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