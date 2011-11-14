package es.elv.nwnx2.jvm.hierarchy;

public class CreatureClassLevels {
	private final CreatureClass classConst;
	private final int classLevels;

	public CreatureClassLevels(int classConst, int classLevels) {
		this(CreatureClass.getByConst(classConst), classLevels);
	}

	public CreatureClassLevels(CreatureClass creatureClass, int classLevels) {
		this.classConst = creatureClass;
		this.classLevels = classLevels;
	}

	public CreatureClass getCreatureClass() {
		return classConst;
	}

	public int getLevel() {
		return classLevels;
	}
}
