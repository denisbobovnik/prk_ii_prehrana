package si.um.feri.prk.dao;

import java.sql.Blob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.naming.InitialContext;
import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import si.um.feri.prk.objekti.Prehrana;
import si.um.feri.prk.objekti.Program;

public class ProgramDAO {
	
	DataSource baza;
	Logger log=LoggerFactory.getLogger(ProgramDAO.class);
	
	private static ProgramDAO instance=null;
	public static synchronized ProgramDAO getInstance() {		
		if (instance==null) instance=new ProgramDAO();
		return instance;
	}
	
	private ProgramDAO() {
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
			conn.createStatement().execute("CREATE TABLE IF NOT EXISTS PROGRAM(id_program int not null auto_increment primary key, naslov varchar(100) not null, autor varchar(100) not null, slika longblob not null, tk_id_prehrana int not null, tipPrograma varchar(45) not null, tipSlike varchar(20) not null)");
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
			conn.createStatement().execute("DROP TABLE IF EXISTS PROGRAM CASCADE");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			conn.close();
		}
	}
	
	public Program najdi(int id_program) throws Exception{
		log.info("ProgramDAO: išèem " + id_program);
		Program ret = null;
		Connection conn=null;
		try {
			conn=baza.getConnection();
			PreparedStatement ps = conn.prepareStatement("SELECT * FROM Program WHERE id_program=?");
			ps.setInt(1, id_program);
			ResultSet rs = ps.executeQuery();
			
			while(rs.next()) {
				ret = new Program(rs.getInt("id_program"), rs.getString("autor"), rs.getString("naslov"), rs.getBlob("slika"), rs.getString("tipPrograma"), rs.getString("tipSlike"));				
				//ko bodo strukture narete dalje, še njegove enote in recepte setat tu in posobno metodo kot za prehrano tukaj napiši še tam v receptih
				//program.setRecepti("tukaj jo klices");
				break;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			conn.close();
		}
		return ret;
	}
		
	
	
	public void shrani(Program p, Prehrana pr) throws Exception {
		log.info("ProgramDAO: shranjujem " + p);
		Connection conn=null;
		try {
			conn=baza.getConnection();
			if(najdi(p.getId_program()) != null) {
				
			} else {
				PreparedStatement ps = conn.prepareStatement("INSERT INTO Program(naslov, autor,slika,tk_id_prehrana) VALUES (?,?,?,?)");
				ps.setString(1, p.getNaslov());
				ps.setString(2, p.getUser_username());
				ps.setBinaryStream(3, p.getSlika().getBinaryStream());
				ps.setInt(4, pr.getId_prehrana());
				
				
				ps.executeUpdate();
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			conn.close();
		}
	}

	public ArrayList<Program> najdiVsePoPrehrani(int id_prehrana) throws Exception {
		ArrayList<Program> seznam = new ArrayList<Program>();
		
		Connection conn=null;
		try {
			conn=baza.getConnection();
			PreparedStatement ps = conn.prepareStatement("SELECT * FROM PROGRAM WHERE tk_id_prehrana=?");
			ps.setInt(1, id_prehrana);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				Program program = new Program(rs.getInt("id_program"), rs.getString("autor"), rs.getString("naslov"), rs.getBlob("slika"), rs.getString("tipPrograma"), rs.getString("tipSlike"));
				//ko bodo strukture narete dalje, še njegove enote in recepte setat tu in posobno metodo kot za prehrano tukaj napiši še tam v receptih
				//program.setRecepti("tukaj jo klices");
				seznam.add(program);
			}
			rs.close();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			conn.close();
		}
		return seznam;
	}
	
	public ArrayList<Program> vrniSplosneProgrameZaToPrehrano(int id_prehrana) throws Exception {
		ArrayList<Program> vsiZaToPrehrano = najdiVsePoPrehrani(id_prehrana);
		ArrayList<Program> ret = new ArrayList<Program>();
		for(Program pr : vsiZaToPrehrano)
			if(pr.getTipPrograma().equals("splosni"))
				ret.add(pr);
		return ret;
	}
}