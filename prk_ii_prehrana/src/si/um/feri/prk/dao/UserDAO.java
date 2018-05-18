package si.um.feri.prk.dao;

import java.sql.Connection;
import javax.naming.InitialContext;
import javax.sql.DataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UserDAO {
	
	DataSource baza;
	Logger log=LoggerFactory.getLogger(UserDAO.class);
	
	private static UserDAO instance=null;
	public static synchronized UserDAO getInstance() {		
		if (instance==null) instance=new UserDAO();
		return instance;
	}
	
	private UserDAO() {
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
			conn.createStatement().execute("CREATE TABLE IF NOT EXISTS `User` (`user_id` int(11) NOT NULL AUTO_INCREMENT, `username` varchar(20) NOT NULL, `password` tinytext NOT NULL, `role` varchar(15) NOT NULL, PRIMARY KEY (`user_id`), UNIQUE KEY `usrname` (`username`)) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1");
			//zgor še not null dodat
			//to spodaj priredit za user_id
			//conn.createStatement().execute("ALTER TABLE Meritev ADD CONSTRAINT IDpovezanegaClana FOREIGN KEY (idClanaTeMeritve) REFERENCES Clan(idClana) ON DELETE CASCADE");
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
			conn.createStatement().execute("DROP TABLE IF EXISTS User CASCADE");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			conn.close();
		}
	}
}