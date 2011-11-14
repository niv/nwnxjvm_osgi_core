package es.elv.nwnx2.jvm.hierarchy;

import java.util.HashSet;
import java.util.Set;

import org.nwnx.nwnx2.jvm.NWScript;
import org.nwnx.nwnx2.jvm.constants.Ability;
import org.nwnx.nwnx2.jvm.constants.Action;
import org.nwnx.nwnx2.jvm.constants.ObjectType;
import org.nwnx.nwnx2.jvm.constants.Talkvolume;

public class Creature extends GameObject {
	public static final GameObjectFactory<Creature> factory =
		new GameObjectFactory<Creature>(ObjectType.CREATURE);

	public Creature(int oid) {
		super(oid);
	}

	public int getRacialType() {
		return NWScript.getRacialType(this);
	}
	public float getWeight() {
		return NWScript.getWeight(this);
	}

	public boolean getCommandable() {
		return NWScript.getCommandable(this);
	}
	public void setCommandable(boolean commandable) {
		NWScript.setCommandable(commandable, this);
	}
	public boolean getLootable() {
		return NWScript.getLootable(this);
	}
	public void setLootable(boolean lootable) {
		NWScript.setLootable(this, lootable);
	}

	public int getStr() {
		return NWScript.getAbilityScore(this, Ability.STRENGTH, false);
	}
	public int getDex() {
		return NWScript.getAbilityScore(this, Ability.DEXTERITY, false);
	}
	public int getCon() {
		return NWScript.getAbilityScore(this, Ability.CONSTITUTION, false);
	}
	public int getWis() {
		return NWScript.getAbilityScore(this, Ability.WISDOM, false);
	}
	public int getInt() {
		return NWScript.getAbilityScore(this, Ability.INTELLIGENCE, false);
	}
	public int getCha() {
		return NWScript.getAbilityScore(this, Ability.CHARISMA, false);
	}
	public int getStrMod() {
		return NWScript.getAbilityModifier(Ability.STRENGTH, this);
	}
	public int getDexMod() {
		return NWScript.getAbilityModifier(Ability.DEXTERITY, this);
	}
	public int getConMod() {
		return NWScript.getAbilityModifier(Ability.CONSTITUTION, this);
	}
	public int getWisMod() {
		return NWScript.getAbilityModifier(Ability.WISDOM, this);
	}
	public int getIntMod() {
		return NWScript.getAbilityModifier(Ability.INTELLIGENCE, this);
	}
	public int getChaMod() {
		return NWScript.getAbilityModifier(Ability.CHARISMA, this);
	}
	public int getArcaneSpellFailure() {
		return NWScript.getArcaneSpellFailure(this);
	}

	public int getXP() {
		return NWScript.getXP(this);
	}
	public void setXP(int xp) {
		NWScript.getXP(this);
	}

	public int getAppearance() {
		return NWScript.getAppearanceType(this);
	}
	public void setAppearance(int appearance) {
		NWScript.setCreatureAppearanceType(this, appearance);
	}
	public int getPhenoType() {
		return NWScript.getPhenoType(this);
	}
	public void setPhenoType(int phenoType) {
		NWScript.setPhenoType(phenoType, this);
	}

	public Set<CreatureClassLevels> getClasses() {
		Set<CreatureClassLevels> ret = new HashSet<CreatureClassLevels>();
		int cls = 255;
		for (int idx = 1; idx <= 3; idx++)
			if ((cls = NWScript.getClassByPosition(idx, this)) != 255)
				ret.add(new CreatureClassLevels(cls, NWScript.getLevelByClass(cls, this)));
		return ret;
	}

	public boolean isDead() {
		return NWScript.getIsDead(this);
	}

	public boolean isBusy() {
		return NWScript.getCurrentAction(this) != Action.INVALID;
	}

	private void assignSpeak(final String text, final boolean asAction, final int talkVolume) {
		assign(new Runnable() {
			@Override
			public void run() {
				if (asAction)
					NWScript.actionSpeakString(text, talkVolume);
				else
					NWScript.speakString(text, talkVolume);
			}
		});
	}

	/**
	 * Makes this Creature say text immediately, bypassing the
	 * action queue.
	 */
	public void sayNow(String text) {
		assignSpeak(text, false, Talkvolume.TALK);
	}

	/**
	 * Sends a message to this Creature. Does not do anything player-visible
	 * if this Creature is not a player(possessed) creature.
	 */
	public void message(String text) {
		NWScript.sendMessageToPC(this, text);
	}

	/**
	 * Makes this Creature say text immediately, bypassing the
	 * action queue.
	 */
	public void whisperNow(String text) {
		assignSpeak(text, false, Talkvolume.WHISPER);
	}

	/**
	 * Makes this Creature say text immediately, bypassing the
	 * action queue.
	 */
	public void shoutNow(String text) {
		assignSpeak(text, false, Talkvolume.SHOUT);
	}

	/**
	 * Makes this Creature say text as an action.
	 */
	public void say(final String text) {
		assignSpeak(text, true, Talkvolume.TALK);
	}

	/**
	 * Makes this Creature say text as an action.
	 */
	public void whisper(final String text) {
		assignSpeak(text, true, Talkvolume.WHISPER);
	}

	/**
	 * Makes this Creature say text as an action.
	 */
	public void shout(final String text) {
		assignSpeak(text, true, Talkvolume.SHOUT);
	}

	public boolean hears(GameObject other) {
		return other.getLocation().area().equals(this.getLocation().area()) &&
			NWScript.getObjectHeard(other, this);
	}

	public boolean sees(GameObject other) {
		return other.getLocation().area().equals(this.getLocation().area()) &&
			NWScript.getObjectSeen(other, this);
	}
}
