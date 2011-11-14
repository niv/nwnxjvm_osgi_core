package es.elv.nwnx2.jvm.idlestate.impl;

import java.util.Map;
import java.util.WeakHashMap;

import es.elv.nwnx2.jvm.events.game.CreatureHeartbeat;
import es.elv.nwnx2.jvm.hierarchy.Creature;
import es.elv.nwnx2.jvm.hierarchy.PlayerCreature;
import es.elv.nwnx2.jvm.hierarchy.bundles.Every;
import es.elv.nwnx2.jvm.hierarchy.bundles.Feature;
import es.elv.nwnx2.jvm.hierarchy.events.Handler;
import es.elv.nwnx2.jvm.idlestate.IdleStateListener;
import es.elv.nwnx2.jvm.idlestate.IdleStateService;

public class IdleStateServiceImpl extends Feature implements IdleStateService {

	private Map<Creature, Long> lastActivityAt =
		new WeakHashMap<Creature, Long>();

	private Map<Creature, Long> lastMovementAt =
		new WeakHashMap<Creature, Long>();

	@Override
	public long idleFor(Creature c) {
		if (lastActivityAt.containsKey(c))
			return System.currentTimeMillis() - lastActivityAt.get(c);
		else
			return 0;
	}

	@Override
	public long immobileFor(Creature c) {
		if (lastMovementAt.containsKey(c))
			return System.currentTimeMillis() - lastMovementAt.get(c);
		else
			return 0;
	}

	@Override
	public void resetIdle(Creature c) {
		if (lastActivityAt.containsKey(c)) {
			for (IdleStateListener l : locateServicesFor(IdleStateListener.class)) {
				l.creatureActive(c, System.currentTimeMillis() - lastActivityAt.get(c));
			}
			lastActivityAt.remove(c);
		}
	}

	@Override
	public void resetImmobile(Creature c) {
		if (lastMovementAt.containsKey(c)) {
			for (IdleStateListener l : locateServicesFor(IdleStateListener.class)) {
				l.creatureMoved(c, System.currentTimeMillis() - lastMovementAt.get(c));
			}
			lastMovementAt.remove(c);
		}
		c.setLocation("idleStateLastLoc", c.getLocation());
	}

	@Handler protected void onEvent(CreatureHeartbeat hb) {
		checkCreature(hb.creature);
	}

	@Every(interval=3000) protected void iteratePlayers() {
		for (PlayerCreature p : PlayerCreature.all())
			checkCreature(p);
	}

	private void checkCreature(Creature creature) {
		if (!creature.getLocation().equals(creature.getLocation("idleStateLastLoc"))) {
			resetImmobile(creature);
		} else {
			for (IdleStateListener l : locateServicesFor(IdleStateListener.class)) {
				if (immobileFor(creature) > l.getImmobileTimeout())
					l.creatureBecomesImmobile(creature, immobileFor(creature));
			}
		}

		if (creature.isBusy()) {
			resetIdle(creature);
		} else {
			for (IdleStateListener l : locateServicesFor(IdleStateListener.class)) {
				if (idleFor(creature) > l.getIdleTimeout())
					l.creatureBecomesIdle(creature, immobileFor(creature));
			}
		}
	}


}
