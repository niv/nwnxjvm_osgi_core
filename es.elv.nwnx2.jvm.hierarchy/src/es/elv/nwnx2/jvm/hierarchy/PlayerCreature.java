package es.elv.nwnx2.jvm.hierarchy;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.nwnx.nwnx2.jvm.NWObject;
import org.nwnx.nwnx2.jvm.NWScript;
import org.nwnx.nwnx2.jvm.Scheduler;

public class PlayerCreature extends Creature {

	volatile private String account;
	private String ipAddress;
	private String cdKey;
	private Date lastLoginAt;

	public PlayerCreature(int oid) {
		super(oid);
		Scheduler.schedule(new Runnable() {
			@Override
			public void run() {
				getAccount();
			}
		});
	}

	public Date getLastLoginAt() {
		return lastLoginAt;
	}

	public void updateLastLoginDate() {
		lastLoginAt = new Date();
	}

	public String getAccount() {
		if (null == account || account.equals(""))
			account = NWScript.getPCPlayerName(this);
		return account;
	}

	public String getIPAddress() {
		if (null == ipAddress || ipAddress.equals(""))
			ipAddress = NWScript.getPCIPAddress(this);
		return ipAddress;
	}

	public String getPublicCDKey() {
		if (null == cdKey || cdKey.equals(""))
			cdKey = NWScript.getPCPublicCDKey(this, false);
		return cdKey;
	}

	public int getAge() {
		return NWScript.getAge(this);
	}

	public void kick() {
		NWScript.bootPC(this);
	}

	/**
	 * Returns an array of all PlayerCreatures online (Players and DMs).
	 */
	public static PlayerCreature[] all() {
		List<PlayerCreature> ret = new LinkedList<PlayerCreature>();
		for (NWObject p : NWScript.getPCs())
			//XXX: possessed familiars end up here to and break everything
			if (NWScript.getIsPossessedFamiliar(p))
				ret.add((PlayerCreature) NWScript.getMaster(p));
			else
				ret.add((PlayerCreature) p);
		return ret.toArray(new PlayerCreature[ret.size()]);
	}


	public static PlayerCreature byAccount(String accountNoCase) {
		for (PlayerCreature p : all())
			if (p.getAccount().equalsIgnoreCase(accountNoCase))
				return p;
		return null;
	}

	@Override
	protected Map<String, String> toStringContextParams() {
		Map<String, String> ret = super.toStringContextParams();
		ret.put("account", getAccount());
		ret.put("ip", getIPAddress());
		ret.put("cdkey", getPublicCDKey());
		return ret;
	}
}