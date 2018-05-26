package si.um.feri.prk.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import javax.naming.InitialContext;
import javax.sql.DataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import si.um.feri.prk.objekti.Cilj;

public class CiljDAO {
	
	DataSource baza;
	Logger log=LoggerFactory.getLogger(CiljDAO.class);
	
	private static CiljDAO instance=null;
	public static synchronized CiljDAO getInstance() {		
		if (instance==null) instance=new CiljDAO();
		return instance;
	}

	private CiljDAO() {
		log.info("CiljDAO: CiljDAO ");
		try {
			baza=(DataSource)new InitialContext().lookup("java:jboss/datasources/prk_ii_prehrana");	
			kreirajTabele();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void kreirajTabele() throws Exception {
		log.info("CiljDAO: kreirajTabele ");
		Connection conn=null;
		try {
			conn=baza.getConnection();
			conn.createStatement().execute("CREATE TABLE IF NOT EXISTS Cilj(id_cilj int not null auto_increment primary key, user_username varchar(100) not null, tip varchar(100) not null, datumZastavitve timestamp not null)");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			conn.close();
		}
	}
	
	public void pobrisiTabele() throws Exception {
		log.info("CiljDAO: pobrisiTabele ");
		Connection conn=null;
		try {
			conn=baza.getConnection();
			conn.createStatement().execute("DROP TABLE IF EXISTS Cilj CASCADE");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			conn.close();
		}
	}
	
	public Cilj najdi(int id_cilj) throws Exception {
		log.info("CiljDAO: najdi " + id_cilj);
		Cilj ret = null;
		Connection conn=null;
		try {
			conn=baza.getConnection();
			PreparedStatement ps = conn.prepareStatement("SELECT * FROM Cilj WHERE id_cilj=?");
			ps.setInt(1, id_cilj);
			ResultSet rs = ps.executeQuery();
			
			while(rs.next()) {
				ret = new Cilj(id_cilj, rs.getString("user_username"), rs.getString("tip"));
				ret.getDatumZastavitve().setTimeInMillis(rs.getTimestamp("datumZastavitve").getTime());
				break;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			conn.close();
		}
		return ret;
	}
	
	
	public void shrani(Cilj c) throws Exception {
		log.info("CiljDAO: shranjujem " + c);
		Connection conn=null;
		try {
			conn=baza.getConnection();
			if(najdi(c.getId_cilj()) != null) {
				//cilj z id-jem že obstaja...pohandle-at
			} else {

				PreparedStatement ps = conn.prepareStatement("INSERT INTO Cilj(user_username, tip, datumZastavitve) VALUES (?,?,?)");

				ps.setString(1, c.getUser_username());
				ps.setString(2, c.getTip());
				ps.setTimestamp(3, new Timestamp(c.getDatumZastavitve().getTimeInMillis()));
				ps.executeUpdate();
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			conn.close();
		}
	}
	
	public void izbrisi(int id_cilj) throws Exception {
		log.info("CiljDAO: izbrisi " + id_cilj);
		Connection conn=null;
		try {
			conn=baza.getConnection();
			PreparedStatement ps = conn.prepareStatement("DELETE FROM Cilj WHERE id_cilj=?");
			ps.setInt(1, id_cilj);
			ps.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			conn.close();
		}
	}
	
	public ArrayList<Cilj> vrniVse() throws Exception {
		log.info("CiljDAO: vrniVse ");
		ArrayList<Cilj> seznam = new ArrayList<Cilj>();
	
		Connection conn=null;
		try {
			conn=baza.getConnection();

			PreparedStatement ps = conn.prepareStatement("SELECT * FROM Cilj");
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				Cilj cilj = new Cilj(rs.getInt("id_cilj"), rs.getString("user_username"), rs.getString("tip"));
				cilj.getDatumZastavitve().setTimeInMillis(rs.getTimestamp("datumZastavitve").getTime());
						
				seznam.add(cilj);
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