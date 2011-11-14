package es.elv.nwnx2.jvm.hierarchy;

public class CreatureClass {
	private final int classConst;

	public CreatureClass(int classConst) {
		this.classConst = classConst;
	}

	public static CreatureClass getByConst(int classConst) {
		return new CreatureClass(classConst);
	}

	public int getClassConst() {
		return classConst;
	}

}
