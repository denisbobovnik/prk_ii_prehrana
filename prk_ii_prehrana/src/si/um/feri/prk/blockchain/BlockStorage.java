package si.um.feri.prk.blockchain;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.context.FacesContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import si.um.feri.prk.objekti.ZauzitaHrana;

public class BlockStorage implements Serializable, ServletContextListener {

	private static final long serialVersionUID = 1L;
	private final String STORELOCATION = "blockchain.data";
	private List<Block> blockchain = new ArrayList<Block>();
	private File blockchainFile = null;
	private static BlockStorage instance=null;
	public static synchronized BlockStorage getInstance() {
		if (instance==null) instance=new BlockStorage();
		return instance;
	}

	private BlockStorage() {
		blockchain.add(new Block(new ZauzitaHrana(), "0"));
	}

	public void addBlock(Block block) {
		blockchain.add(block);
	}

	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
		System.out.println("Writing file here: " + blockchainFile.getAbsolutePath());
		try {
			@SuppressWarnings("resource")
			ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(blockchainFile));
			oos.writeObject(BlockStorage.getInstance().blockchain);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public void contextInitialized(ServletContextEvent arg0) {
		System.out.println("Loading blockchain on startup...");
		blockchainFile = new File(STORELOCATION);
		if (blockchainFile != null && blockchainFile.exists() && !blockchainFile.isDirectory()) {
			try {
				ObjectInputStream ois = new ObjectInputStream(new FileInputStream(blockchainFile));
				BlockStorage.getInstance().blockchain = (List<Block>) ois.readObject();
				ois.close();
				if (BlockStorage.getInstance().blockchain.size() == 0)
					BlockStorage.getInstance().blockchain.add(new Block(new ZauzitaHrana(), "0"));
				System.out.println("Blockchain loaded.");
				System.out.println("blockchain size: " + BlockStorage.instance.blockchain.size());
			} catch (IOException | ClassNotFoundException e) {
				e.printStackTrace();
			}
		} else if (!blockchainFile.exists()) {
			BlockStorage.getInstance().blockchain.add(new Block(new ZauzitaHrana(), "0"));
		}
	}

	public List<Block> getBlockchain() {
		return blockchain;
	}
	public void setBlockchain(List<Block> blockchain) {
		this.blockchain = blockchain;
	}
	
	public ArrayList<ZauzitaHrana> vrniVseZaPrijavljenega() {
		FacesContext context = FacesContext.getCurrentInstance();
		String username = context.getExternalContext().getRemoteUser();
		
		ArrayList<ZauzitaHrana> vsaZaPrijavljenega = new ArrayList<ZauzitaHrana>();
		for(Block block : instance.getBlockchain()) {
			try {
				if(block.getZauzitaHrana().getUser_username().equals(username)) {
					vsaZaPrijavljenega.add(block.getZauzitaHrana());
				}
			} catch (NullPointerException e) {
				continue;
			}
		}
		return vsaZaPrijavljenega;
	}
}