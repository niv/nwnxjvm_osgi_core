package es.elv.nwnx2.jvm.events.chatlistener.impl;

import org.nwnx.nwnx2.jvm.constants.ObjectType;
import org.nwnx.nwnx2.jvm.constants.Shape;

import es.elv.nwnx2.jvm.events.chatlistener.CreatureChatTalkHeard;
import es.elv.nwnx2.jvm.events.chatlistener.CreatureChatWhisperHeard;
import es.elv.nwnx2.jvm.hierarchy.Creature;
import es.elv.nwnx2.jvm.hierarchy.bundles.Feature;
import es.elv.nwnx2.jvm.hierarchy.bundles.HierarchyService;
import es.elv.nwnx2.jvm.hierarchy.events.Handler;
import es.elv.nwnx2.jvm.nwnx.chat.CreatureChatTalk;
import es.elv.nwnx2.jvm.nwnx.chat.CreatureChatWhisper;
import es.elv.osgi.base.Autolocate;

public class GameEventProviderImpl extends Feature {
	@Autolocate
	private HierarchyService hs;

	@Override
	protected void load() throws Exception {
		hs = locateServiceFor(HierarchyService.class);
	}

	@Handler void onChatTalk(CreatureChatTalk e) {
		for (Creature g : e.creature.getNear(Shape.SPHERE, 20f, ObjectType.CREATURE, Creature.class))
			if (!e.creature.equals(g) && e.creature.hears(g))
				hs.sendEvent(new CreatureChatTalkHeard(g, e.creature, e.text));

	}

	@Handler void onChatWhisper(CreatureChatWhisper e) {
		for (Creature g : e.creature.getNear(Shape.SPHERE, 3f, ObjectType.CREATURE, Creature.class))
			if (!e.creature.equals(g) && e.creature.hears(g))
				hs.sendEvent(new CreatureChatWhisperHeard(g, e.creature, e.text));
	}

	/* cannot implement yet, need faction helpers
	@Handler void onChatWhisper(CreatureChatParty e) {
		for (Creature g : e.creature.getNear(Shape.SPHERE, 5f, ObjectType.CREATURE, Creature.class))
			hs.sendEvent(new CreatureChatTalkHeard(g, e.creature, e.text));
	}*/
}
