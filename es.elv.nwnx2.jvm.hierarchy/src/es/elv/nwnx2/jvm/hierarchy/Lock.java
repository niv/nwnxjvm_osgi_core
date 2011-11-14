package es.elv.nwnx2.jvm.hierarchy;

import org.nwnx.nwnx2.jvm.NWScript;

public class Lock {
	private final GameObject parent;

	public Lock(GameObject on) {
		this.parent = on;
	}

	public boolean isLocked() {
		return NWScript.getLocked(parent);
	}

	public void setLocked(boolean state) {
		NWScript.setLocked(parent, state);
	}

	public boolean isLockable() {
		return NWScript.getLockLockable(parent);
	}

	public void setLockable(boolean state) {
		NWScript.setLockLockable(parent, state);
	}

	public void lock() {
		setLocked(true);
	}

	public void unlock() {
		setLocked(false);
	}

	public int getLockDC() {
		return NWScript.getLockLockDC(parent);
	}

	public void setLockDC(int dc) {
		NWScript.setLockLockDC(parent, dc);
	}

	public int getUnlockDC() {
		return NWScript.getLockUnlockDC(parent);
	}

	public boolean isKeyRequired() {
		return NWScript.getLockKeyRequired(parent);
	}

	public void setKeyRequired(boolean state) {
		NWScript.setLockKeyRequired(parent, state);
	}

	public String getKeyTag() {
		return NWScript.getLockKeyTag(parent);
	}

	public void setKeyTag(String tag) {
		NWScript.setLockKeyTag(parent, tag);
	}

}
