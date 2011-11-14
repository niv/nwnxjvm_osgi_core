package es.elv.nwnx2.jvm.crashdetector.impl;

import java.util.Dictionary;
import java.util.HashMap;
import java.util.Map;

import org.nwnx.nwnx2.jvm.Color;
import org.nwnx.nwnx2.jvm.NWLocation;
import org.nwnx.nwnx2.jvm.NWObject;
import org.nwnx.nwnx2.jvm.NWScript;
import org.osgi.service.cm.ConfigurationException;
import org.osgi.service.cm.ManagedService;

import es.elv.nwnx2.jvm.events.game.AreaEnter;
import es.elv.nwnx2.jvm.events.game.PlayerLogin;
import es.elv.nwnx2.jvm.events.game.PlayerLogout;
import es.elv.nwnx2.jvm.hierarchy.PlayerCreature;
import es.elv.nwnx2.jvm.hierarchy.Waypoint;
import es.elv.nwnx2.jvm.hierarchy.bundles.Feature;
import es.elv.nwnx2.jvm.hierarchy.events.Handler;

public class CrashDetectorImpl extends Feature implements ManagedService {

	/**
	 * Reset players to the starting location after this many failed
	 * login attempts.
	 */
	private int failCountReset;

	/**
	 * The tag of the waypoint to rescue to.
	 */
	private String waypointTag;

	/**
	 * How many seconds between login->login-attempts to let pass for them to be failed logins.
	 */
	private int loginTimeout;

	/**
	 * Message to notify transported players with.
	 */
	private String message;

	private Map<PlayerCreature, Integer> observedFails =
		new HashMap<PlayerCreature, Integer>();

	private Map<PlayerCreature, Long> lastLoginAt =
		new HashMap<PlayerCreature, Long>();

	private Map<PlayerCreature, NWLocation> lastSeenLocation =
		new HashMap<PlayerCreature, NWLocation>();


	@Handler void login(final PlayerLogin e) {
		lastSeenLocation.remove(e.player);
		lastLoginAt.put(e.player, System.currentTimeMillis());

		// TODO: refactor into hierarchy API
		final NWObject wp = NWScript.getWaypointByTag(waypointTag);
		if (null == wp || !(wp instanceof Waypoint)) {
			log.error("Cannot find waypoint.");
			return;
		}

		if (observedFails.containsKey(e.player) && observedFails.get(e.player) >= failCountReset) {
			observedFails.remove(e.player);
			// TODO: refactor into hierarchy API
			e.player.assign(new Runnable() {
				@Override
				public void run() {
					NWScript.clearAllActions(true);
					NWScript.jumpToObject(wp, false);
					NWScript.floatingTextStringOnCreature(Color.FIREBRICK.color(message), e.player, false);
				}
			});
			log.info("Resetting " + e.player.toString() + " because of " + failCountReset + " failed login attempts.");
		}
	}

	@Handler void logout(PlayerLogout e) {
		// Player hasn't been with us before. Never mind. (plugin loaded while player was online)
		if (!lastLoginAt.containsKey(e.player))
			return;

		long onlineTime = System.currentTimeMillis() - lastLoginAt.get(e.player);

		if (
				// The player hasn't even managed to enter an area before leaving again.
				!lastSeenLocation.containsKey(e.player) ||

				// OR the player has been online for less than (configured amount) of seconds.
				(onlineTime < 1000 * loginTimeout)
		) {

			log.info(e.player + " failed to login properly.");
			// Increase failed login counter.
			if (!observedFails.containsKey(e.player))
				observedFails.put(e.player, 1);
			else
				observedFails.put(e.player, 1 + observedFails.get(e.player));
		} else {

			// We assume that was a successful login/logout session. Clear cached data.
			observedFails.remove(e.player);
			lastSeenLocation.remove(e.player);
			lastLoginAt.remove(e.player);
		}
	}

	@Handler void areaEnter(final AreaEnter e) {
		// He managed to enter an area. Lets store the location for later comparison on logout.
		if (e.entering instanceof PlayerCreature)
			lastSeenLocation.put((PlayerCreature) e.entering, e.entering.getLocation());
	}

	@Override
	public void updated(@SuppressWarnings("rawtypes") Dictionary c) throws ConfigurationException {
		if (null == c)
			return;

		failCountReset = (Integer) c.get("fail_count_reset");
		waypointTag = (String) c.get("waypoint_tag");
		loginTimeout = (Integer) c.get("login_timeout");
		message = (String) c.get("message");
	}

}