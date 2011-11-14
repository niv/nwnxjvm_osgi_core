package es.elv.nwnx2.jvm.hierarchy.activator;

import org.nwnx.nwnx2.jvm.NWObject;
import org.nwnx.nwnx2.jvm.NWScript;
import org.nwnx.nwnx2.jvm.NWObject.ObjectHandler;
import org.nwnx.nwnx2.jvm.constants.AssociateType;
import org.nwnx.nwnx2.jvm.constants.ObjectType;
import org.nwnx.nwnx2.jvm.constants.Weather;

import es.elv.nwnx2.jvm.hierarchy.AOE;
import es.elv.nwnx2.jvm.hierarchy.Ammunition;
import es.elv.nwnx2.jvm.hierarchy.AnimalCompanion;
import es.elv.nwnx2.jvm.hierarchy.Area;
import es.elv.nwnx2.jvm.hierarchy.Armor;
import es.elv.nwnx2.jvm.hierarchy.DM;
import es.elv.nwnx2.jvm.hierarchy.Door;
import es.elv.nwnx2.jvm.hierarchy.Encounter;
import es.elv.nwnx2.jvm.hierarchy.Familiar;
import es.elv.nwnx2.jvm.hierarchy.HealersKit;
import es.elv.nwnx2.jvm.hierarchy.Helmet;
import es.elv.nwnx2.jvm.hierarchy.Invalid;
import es.elv.nwnx2.jvm.hierarchy.MeleeWeapon;
import es.elv.nwnx2.jvm.hierarchy.MiscItem;
import es.elv.nwnx2.jvm.hierarchy.MiscUnequippableItem;
import es.elv.nwnx2.jvm.hierarchy.Module;
import es.elv.nwnx2.jvm.hierarchy.NPC;
import es.elv.nwnx2.jvm.hierarchy.Placeable;
import es.elv.nwnx2.jvm.hierarchy.Player;
import es.elv.nwnx2.jvm.hierarchy.Potion;
import es.elv.nwnx2.jvm.hierarchy.RangedWeapon;
import es.elv.nwnx2.jvm.hierarchy.Rod;
import es.elv.nwnx2.jvm.hierarchy.Scroll;
import es.elv.nwnx2.jvm.hierarchy.Shield;
import es.elv.nwnx2.jvm.hierarchy.Sound;
import es.elv.nwnx2.jvm.hierarchy.Stave;
import es.elv.nwnx2.jvm.hierarchy.Store;
import es.elv.nwnx2.jvm.hierarchy.ThievesTools;
import es.elv.nwnx2.jvm.hierarchy.ThrownWeapon;
import es.elv.nwnx2.jvm.hierarchy.TrapItem;
import es.elv.nwnx2.jvm.hierarchy.Trigger;
import es.elv.nwnx2.jvm.hierarchy.UFO;
import es.elv.nwnx2.jvm.hierarchy.UnclassifiedItem;
import es.elv.nwnx2.jvm.hierarchy.Wand;
import es.elv.nwnx2.jvm.hierarchy.Waypoint;
import es.elv.nwnx2.jvm.hierarchy.bundles.Feature;
import es.elv.nwnx2.jvm.hierarchy.bundles.ObjectClassService;

public class ObjectHandlerImpl extends Feature implements ObjectHandler {

	@Override
	protected void load() throws Exception {
		NWObject.registerObjectHandler(this);
	}

	@Override
	protected void unload() throws Exception {
		NWObject.unregisterObjectHandler(this);
	}

	@Override
	public NWObject handleObjectClass(NWObject obj, boolean valid,
			int objectType, String resRef, String tag) {
		final int oid = obj.getObjectId();

		for (ObjectClassService s : locateServicesFor(ObjectClassService.class)) {
			NWObject o = s.handleObjectClass(obj, valid, objectType, resRef, tag);
			if (o != null)
				return o;
		}

		if (oid == 0)
			return Module.getInstance();

		switch (objectType) {
			case ObjectType.PLACEABLE: return new Placeable(oid);
			case ObjectType.STORE: return new Store(oid);
			case ObjectType.TRIGGER: return new Trigger(oid);
			case ObjectType.DOOR: return new Door(oid);
			case ObjectType.WAYPOINT: return new Waypoint(oid);
			case ObjectType.ENCOUNTER: return new Encounter(oid);
			case ObjectType.AREA_OF_EFFECT: return new AOE(oid);
			case ObjectType.ITEM: {
				int category = 0;

				int baseType = NWScript.getBaseItemType(obj);

				try { category = Integer.parseInt(NWScript.get2DAString("baseitems", "Category", baseType)); }
					catch (NumberFormatException e) { }
				switch (category) {
					case 1: return new MeleeWeapon(oid);
					case 2: return new RangedWeapon(oid);
					case 3: return new Shield(oid);
					case 4: return new Armor(oid);
					case 5: return new Helmet(oid);
					case 6: return new Ammunition(oid);
					case 7: return new ThrownWeapon(oid);
					case 8: return new Stave(oid);
					case 9: return new Potion(oid);
					case 10: return new Scroll(oid);
					case 11: return new ThievesTools(oid);
					case 12: return new MiscItem(oid);
					case 13: return new Wand(oid);
					case 14: return new Rod(oid);
					case 15: return new TrapItem(oid);
					case 16: return new MiscUnequippableItem(oid);
					// case 17: return new Container(oid)
					case 19: return new HealersKit(oid);

					default: return new UnclassifiedItem(oid);

				}
			}
		}


		if (NWScript.getWeather(obj) != Weather.INVALID)
			return new Area(oid);

		/*
		 * TRUE if oCreature is a DM (Dungeon Master) avatar. Returns FALSE for
		 * everything else, including creatures controlled by the DM.
		 */
		if (NWScript.getIsDM(obj))
			return new DM(oid);

		switch (NWScript.getAssociateType(obj)) {
			case AssociateType.ANIMALCOMPANION: return new AnimalCompanion(oid);
			case AssociateType.FAMILIAR: return new Familiar(oid);
			case AssociateType.DOMINATED: return new NPC(oid);
			case AssociateType.HENCHMAN: return new NPC(oid);
			case AssociateType.SUMMONED: return new NPC(oid);
		}

		/*
		 * Returns TRUE if the creature oCreature is currently possessed by a DM character.
		 * Note: GetIsDMPossessed() will return FALSE if oCreature is the DM character.
		 */
		if (NWScript.getIsDMPossessed(obj))
			return new NPC(oid);

		/* Resref is always "" for player characters.
		 * A player controlled character is treated as one of the following: a PC, DM avatars,
		 * familiars possessed by PCs, monsters possessed by DMs.
		 */
		if (oid >= 0x7f000000)
			return new Player(oid);

		/*
		 * Empty resref: copy of player.  Non-empty: created from template.
		 */
		if (objectType == ObjectType.CREATURE)
			return new NPC(oid);

		if (objectType == ObjectType.ALL)
			return new Sound(oid);

		/*
		 * There seems to be a bug/undocumented feature in nwserver that sometimes spawns
		 * an object at the location just where a player logged out, of type ALL
		 * and with resref = "", tag = "" and name of the player.
		 */
		if (resRef.equals("") && tag.equals("") && !NWScript.getName(obj, true).equals("") &&
					NWScript.getDescription(obj, true, true).equals(""))
			return new UFO(oid);

		if (objectType == 0)
			return new Invalid(oid); // INVALID, hardcoded for now

		log.warn(String.format("Cannot map in hierarchy: oid=%8x otype=%d resref=%s tag=%s name=%s",
				oid, objectType, resRef, tag, NWScript.getName(obj, false)));

		return null;
	}
}
