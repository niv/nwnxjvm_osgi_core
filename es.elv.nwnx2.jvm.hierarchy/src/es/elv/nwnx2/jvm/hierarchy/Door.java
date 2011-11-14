package es.elv.nwnx2.jvm.hierarchy;


public class Door extends Transition {
	final private Lock lock = new Lock(this);

	public Door(int oid) {
		super(oid);
	}

	public Lock getLock() {
		return lock;
	}

}
