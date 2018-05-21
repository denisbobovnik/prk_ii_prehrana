package si.um.feri.prk.dao;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.naming.InitialContext;
import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import si.um.feri.prk.objekti.Prehrana;


public class PrehranaDAO {
	

	DataSource baza;
	Logger log=LoggerFactory.getLogger(PrehranaDAO.class);
	
	private static PrehranaDAO instance=null;
	public static synchronized PrehranaDAO getInstance() {		
		if (instance==null) instance=new PrehranaDAO();
		return instance;
	}
	
	private PrehranaDAO() {
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
			conn.createStatement().execute("CREATE TABLE IF NOT EXISTS PREHRANA(id_prehrana int not null auto_increment primary key, naslovPrehrane varchar(100) not null)");
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
			conn.createStatement().execute("DROP TABLE IF EXISTS PREHRANA CASCADE");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			conn.close();
		}
	}
	
	public  Prehrana najdi(int id_prehrana) throws SQLException {
		Prehrana ret = null;
		Connection conn=null;
		try {
			conn=baza.getConnection();
			PreparedStatement ps = conn.prepareStatement("SELECT * FROM Prehrana ");
			ps.setInt(1, 0);
			ResultSet rs = ps.executeQuery();
			
			while(rs.next()) {
				ret = new Prehrana(rs.getInt("id_prehrana"), rs.getString("naslovPrehrane"));
				ret.setId_prehrana(id_prehrana);
				break;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			conn.close();
		}
		return ret;
	
		
	}
	
	public void shrani(Prehrana pr)throws Exception {
	log.info("PrehranaDAO: shranjujem " + pr);
	Connection conn=null;
	try {
		conn=baza.getConnection();
		if(najdi(pr.getId_prehrana()) != null) {
			
		} else {
			PreparedStatement ps = conn.prepareStatement("INSERT INTO Prehrana(naslovPrehrane) VALUES (?)");
			ps.setString(1, pr.getNaslovPrehrane());
	        
			ps.executeUpdate();
		}
	} catch (Exception e) {
		e.printStackTrace();
	} finally {
		conn.close();
	}
}	
}