package si.um.feri.prk.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;

import javax.naming.InitialContext;
import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import si.um.feri.prk.objekti.Namig;
import si.um.feri.prk.objekti.ZauzitaHrana;

public class NamigDAO {

	DataSource baza;
	Logger log=LoggerFactory.getLogger(ZauzitaHranaDAO.class);
	
	private static NamigDAO instance=null;
	public static synchronized NamigDAO getInstance() {		
		if (instance==null) instance=new NamigDAO();
		return instance;
	}

	private NamigDAO() {
		log.info("NamigDAO: NamigDAO ");
		try {
			baza=(DataSource)new InitialContext().lookup("java:jboss/datasources/prk_ii_prehrana");	
			kreirajTabele();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void kreirajTabele() throws Exception {
		log.info("ZauzitaHranaDAO: kreirajTabele ");
		Connection conn=null;
		try {
			conn=baza.getConnection();
			conn.createStatement().execute("CREATE TABLE IF NOT EXISTS Namig(id_namig int not null auto_increment primary key,  naslov varchar(100) not null, datumNamiga timestamp not null, vsebina varchar(500) not null)");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			conn.close();
		}
	}
	
	public void pobrisiTabele() throws Exception {
		log.info("NamigDAO: pobrisiTabele ");
		Connection conn=null;
		try {
			conn=baza.getConnection();
			conn.createStatement().execute("DROP TABLE IF EXISTS Namig CASCADE");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			conn.close();
		}
	}
	

	public Namig najdi(int id_namig) throws Exception {
		log.info("NamigDAO: najdi " + id_namig);
		Namig ret = null;
		Connection conn=null;
		try {
			conn=baza.getConnection();
			PreparedStatement ps = conn.prepareStatement("SELECT * FROM Namig WHERE id_namig=?");
			ps.setInt(1, id_namig);
			ResultSet rs = ps.executeQuery();
			
			while(rs.next()) {
				ret = new Namig(rs.getInt("id_namig"),  rs.getString("naslov"), rs.getString("vsebina"));
				ret.getDatumNamiga().setTimeInMillis(rs.getTimestamp("datumNamiga").getTime());
				break;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			conn.close();
		}
		return ret;
	}
	

	public void shrani(Namig namig) throws Exception {
		log.info("Namig: shranjujem " + namig);
		Connection conn=null;
		try {
			conn=baza.getConnection();
			if(najdi(namig.getId_namig()) != null) {
				
			} else {

				PreparedStatement ps = conn.prepareStatement("INSERT INTO Namig(naslov, datumNamiga, vsebina) VALUES (?,?,?)");
				
				ps.setString(1, namig.getNaslov());
				ps.setTimestamp(2, new Timestamp(namig.getDatumNamiga().getTimeInMillis()));
				ps.setString(3, namig.getVsebina());

				ps.executeUpdate();
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			conn.close();
		}
	}
	

}
