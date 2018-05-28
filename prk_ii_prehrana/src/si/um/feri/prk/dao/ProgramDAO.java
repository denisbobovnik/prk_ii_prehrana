package si.um.feri.prk.dao;

import java.sql.Blob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import javax.naming.InitialContext;
import javax.sql.DataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import si.um.feri.prk.objekti.Enota;
import si.um.feri.prk.objekti.Program;

public class ProgramDAO {
	
	DataSource baza;
	Logger log=LoggerFactory.getLogger(ProgramDAO.class);
	private EnotaDAO eD = EnotaDAO.getInstance();
	
	private static ProgramDAO instance=null;
	public static synchronized ProgramDAO getInstance() {		
		if (instance==null) instance=new ProgramDAO();
		return instance;
	}
	
	private ProgramDAO() {
		log.info("ProgramDAO: ProgramDAO ");
		try {
			baza=(DataSource)new InitialContext().lookup("java:jboss/datasources/prk_ii_prehrana");	
			kreirajTabele();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void kreirajTabele() throws Exception {
		log.info("ProgramDAO: kreirajTabele ");
		Connection conn=null;
		try {
			conn=baza.getConnection();
			conn.createStatement().execute("CREATE TABLE IF NOT EXISTS Program(id_program int not null auto_increment primary key, tk_id_prehrana int not null, naslov varchar(100) not null, tipPrograma varchar(30) not null, tipSlike varchar(20) not null, slika longblob not null, user_username varchar(100) not null)");
			} catch (Exception e) {
			e.printStackTrace();
		} finally {
			conn.close();
		}
	}
	
	public void pobrisiTabele() throws Exception {
		log.info("ProgramDAO: pobrisiTabele ");
		Connection conn=null;
		try {
			conn=baza.getConnection();
			conn.createStatement().execute("DROP TABLE IF EXISTS Program CASCADE");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			conn.close();
		}
	}
	
	public Program najdi(int id_program) throws Exception{
		log.info("ProgramDAO: najdi " + id_program);
		Program ret = null;
		Connection conn=null;
		try {
			conn=baza.getConnection();
			PreparedStatement ps = conn.prepareStatement("SELECT * FROM Program WHERE id_program=?");
			ps.setInt(1, id_program);
			ResultSet rs = ps.executeQuery();
			
			while(rs.next()) {
				ret = new Program(rs.getInt("id_program"), rs.getInt("tk_id_prehrana"), rs.getString("naslov"), rs.getString("tipPrograma"), rs.getString("tipSlike"), rs.getBlob("slika"), rs.getString("user_username"));
				ret.setEnote(eD.najdiVsePoProgramu(ret.getId_program()));
				break;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			conn.close();
		}
		return ret;
	}
	
	public void shrani(Program p) throws Exception {
		log.info("ProgramDAO: shranjujem " + p);
		Connection conn=null;
		try {
			conn=baza.getConnection();
			if(najdi(p.getId_program()) != null) {
				//že obstaja, update
			} else { //ne obstaja, insert
				PreparedStatement ps = conn.prepareStatement("INSERT INTO Program(id_program, tk_id_prehrana, naslov, tipPrograma, tipSlike, slika, user_username) VALUES (?,?,?,?,?,?,?)");
				ps.setInt(1, p.getId_program());
				ps.setInt(2, p.getTk_id_prehrana());
				ps.setString(3, p.getNaslov());
				ps.setString(4, p.getTipPrograma());
				ps.setString(5, p.getTipSlike());
				ps.setBinaryStream(6, p.getSlika().getBinaryStream());
				ps.setString(7, p.getUser_username());
				
				ps.executeUpdate();
				
				for(Enota e : p.getEnote())
					eD.shrani(e);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			conn.close();
		}
	}
	public void shraniInVrniId(Program p) throws Exception {
		log.info("ProgramDAO: shranjujem " + p);
		Connection conn=null;
		try {
			conn=baza.getConnection();
			if(najdi(p.getId_program()) != null) {
				//že obstaja, update
			} else { //ne obstaja, insert
				PreparedStatement ps = conn.prepareStatement("INSERT INTO Program(id_program, tk_id_prehrana, naslov, tipPrograma, tipSlike, slika, user_username) VALUES (?,?,?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS);
				ps.setInt(1, p.getId_program());
				ps.setInt(2, p.getTk_id_prehrana());
				ps.setString(3, p.getNaslov());
				ps.setString(4, p.getTipPrograma());
				ps.setString(5, p.getTipSlike());
				ps.setBinaryStream(6, p.getSlika().getBinaryStream());
				ps.setString(7, p.getUser_username());
				
				ps.executeUpdate();
				
				for(Enota e : p.getEnote())
					eD.shrani(e);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			conn.close();
		}
	}

	public ArrayList<Program> najdiVsePoPrehrani(int id_prehrana) throws Exception {
		log.info("ProgramDAO: najdiVsePoPrehrani " + id_prehrana);
		ArrayList<Program> seznam = new ArrayList<Program>();
		
		Connection conn=null;
		try {
			conn=baza.getConnection();
			PreparedStatement ps = conn.prepareStatement("SELECT * FROM Program WHERE tk_id_prehrana=?");
			ps.setInt(1, id_prehrana);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				Program program = new Program(rs.getInt("id_program"), rs.getInt("tk_id_prehrana"), rs.getString("naslov"), rs.getString("tipPrograma"), rs.getString("tipSlike"), null, rs.getString("user_username"));

				Blob blob = rs.getBlob("slika");
				int blobLength = (int) blob.length();  
				byte[] blobAsBytes = blob.getBytes(1, blobLength);
				
				program.setSlika(blobAsBytes);
				
				program.setEnote(eD.najdiVsePoProgramu(program.getId_program()));
				
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
		log.info("ProgramDAO: vrniSplosneProgrameZaToPrehrano " + id_prehrana);
		ArrayList<Program> vsiZaToPrehrano = najdiVsePoPrehrani(id_prehrana);
		ArrayList<Program> ret = new ArrayList<Program>();
		for(Program pr : vsiZaToPrehrano)
			if(pr.getTipPrograma().equals("splosni"))
				ret.add(pr);
		return ret;
	}
}