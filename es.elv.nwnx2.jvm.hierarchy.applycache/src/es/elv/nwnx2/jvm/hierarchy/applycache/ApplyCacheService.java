package es.elv.nwnx2.jvm.hierarchy.applycache;

import java.util.List;

import es.elv.nwnx2.jvm.hierarchy.GameObject;

public interface ApplyCacheService {
	public List<GameObject> getCache();

	/**
	 * Clears the object cache.
	 */
	public void clearCache();

	/**
	 * @return the maximum number of cached objects
	 */
	public int getCacheLimit();

	/**
	 * Sets a new maximum cache size, truncating the current cache if neccessary.
	 * @param newSize maximum of cached objects.
	 */
	public void setCacheLimit(int newSize);
}
