package si.um.feri.prk.blockchain;

import java.io.Serializable;
import java.util.Date;

import si.um.feri.prk.objekti.ZauzitaHrana;

public class Block implements Serializable {
	public String hash;
	public String previousHash;
	private ZauzitaHrana zauzitaHrana;
	private long timeStamp; //as number of milliseconds since 1/1/1970.

	//Block Constructor.
	public Block(ZauzitaHrana zauzitaHrana,String previousHash ) {
		this.zauzitaHrana = zauzitaHrana;
		this.previousHash = previousHash;
		this.timeStamp = new Date().getTime();
		this.hash = calculateHash(); //Making sure we do this after we set the other values.
	}
	
	public String calculateHash() {
		String calculatedhash = StringUtil.applySha256( 
				previousHash +
				Long.toString(timeStamp) +
				zauzitaHrana 
				);
		return calculatedhash;
	}

	public ZauzitaHrana getZauzitaHrana() {
		return zauzitaHrana;
	}
	public void setZauzitaHrana(ZauzitaHrana zauzitaHrana) {
		this.zauzitaHrana = zauzitaHrana;
	}
}