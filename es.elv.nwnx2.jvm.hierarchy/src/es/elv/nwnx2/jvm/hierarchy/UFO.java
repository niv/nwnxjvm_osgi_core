package es.elv.nwnx2.jvm.hierarchy;


/**
 * A UFO is some object that fails classification, but is
 * there nevertheless. This is usually a stray object
 * created by nwserver due to a bug or undocumented feature,
 * and should not be acted/relied upon. This is simply here
 * to stop the classifier from barfing.
 */
public class UFO extends GameObject {

	public UFO(int oid) {
		super(oid);
	}

}
