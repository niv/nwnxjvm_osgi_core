package es.elv.nwnx2.jvm.events.game;

import org.nwnx.nwnx2.jvm.NWScript;

import es.elv.nwnx2.jvm.hierarchy.GameObject;
import es.elv.nwnx2.jvm.hierarchy.events.GameEvent;

public abstract class ObjectSpellCastAt<T extends GameObject> extends GameEvent<T> {
	final public GameObject object;
	final public int spell;
	final public GameObject caster;
	final public int metaMagic;
	final public boolean harmful;
	final public int casterClass;

	public ObjectSpellCastAt(GameObject actor) {
		super(actor);

		object = (GameObject) NWScript.getSpellTargetObject();
		spell = NWScript.getLastSpell();
		caster = (GameObject) NWScript.getLastSpellCaster();
		metaMagic = NWScript.getMetaMagicFeat();
		harmful = NWScript.getLastSpellHarmful();
		casterClass = NWScript.getLastSpellCastClass();
	}
}
