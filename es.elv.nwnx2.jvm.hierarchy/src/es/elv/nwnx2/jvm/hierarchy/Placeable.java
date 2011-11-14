package es.elv.nwnx2.jvm.hierarchy;


public class Placeable extends GameObject {
	final private Lock lock = new Lock(this);

	public Placeable(int oid) {
		super(oid);
	}

	public Lock getLock() {
		return lock;
	}
}
