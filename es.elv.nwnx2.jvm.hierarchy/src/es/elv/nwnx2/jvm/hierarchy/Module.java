package es.elv.nwnx2.jvm.hierarchy;


public class Module extends GameObject {
	private static Module instance = new Module(0);

	public static Module getInstance() {
		return instance;
	}

	private Module(int oid) {
		super(oid);
	}
}
