package es.elv.nwnx2.jvm.hierarchy;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.nwnx.nwnx2.jvm.NWLocation;
import org.nwnx.nwnx2.jvm.NWObject;
import org.nwnx.nwnx2.jvm.NWScript;
import org.nwnx.nwnx2.jvm.NWVector;
import org.nwnx.nwnx2.jvm.Scheduler;
import org.nwnx.nwnx2.jvm.constants.ObjectType;
import org.nwnx.nwnx2.jvm.constants.Shape;

public abstract class GameObject extends NWObject {
	protected boolean cacheable = true;

	public boolean isCacheable() {
		return cacheable;
	}

	public GameObject(int oid) {
		super(oid);
	}

	public String getName() {
		return NWScript.getName(this, false);
	}
	public String getOriginalName() {
		return NWScript.getName(this, true);
	}

	public void setName(String sNewName) {
		NWScript.setName(this, sNewName);
	}

	public String getResRef() {
		return NWScript.getResRef(this);
	}

	public String getTag() {
		return NWScript.getTag(this);
	}

	public NWLocation getLocation() {
		return NWScript.getLocation(this);
	}

	public boolean isPlot() {
		return NWScript.getPlotFlag(this);
	}
	public void setPlot(boolean plot) {
		NWScript.setPlotFlag(this, plot);
	}

	public void destroy() {
		destroy(0);
	}

	public void destroy(long delay) {
		NWScript.destroyObject(this, (float) (((double) delay) / 1000));
	}

	public void copy(NWLocation toLocation) {
		NWScript.copyObject(this, toLocation, null, "");
	}



	public GameObject getObject(String key) {
		return (GameObject) NWScript.getLocalObject(this, key);
	}
	public void setObject(String key, GameObject value) {
		NWScript.setLocalObject(this, key, value);
	}
	public NWLocation getLocation(String key) {
		return NWScript.getLocalLocation(this, key);
	}
	public void setLocation(String key, NWLocation value) {
		NWScript.setLocalLocation(this, key, value);
	}

	public String getString(String key) {
		return NWScript.getLocalString(this, key);
	}
	public void setString(String key, String value) {
		NWScript.setLocalString(this, key, value);
	}
	public int getInt(String key) {
		return NWScript.getLocalInt(this, key);
	}
	public void setInt(String key, int value) {
		NWScript.setLocalInt(this, key, value);
	}
	public boolean getBoolean(String key) {
		return NWScript.getLocalInt(this, "_b_" + key) > 0;
	}
	public void setBoolean(String key, boolean value) {
		NWScript.setLocalInt(this, "_b_" + key, value ? 1 : 0);
	}
	public float getFloat(String key) {
		return NWScript.getLocalFloat(this, key);
	}
	public void setFloat(String key, float value) {
		NWScript.setLocalFloat(this, key, value);
	}
	public long getLong(String key) {
		try {
			return Long.parseLong(NWScript.getLocalString(this, "_l_" + key));
		} catch (NumberFormatException e) {
			return 0;
		}
	}
	public void setLong(String key, long value) {
		NWScript.setLocalString(this, "_l_" + key, String.valueOf(value));
	}

	public List<GameObject> getNear(float distance) {
		return getNear(Shape.SPHERE, distance, ObjectType.ALL, GameObject.class);
	}

	public List<GameObject> getNear(int shapeType, float distance) {
		return getNear(shapeType, distance, ObjectType.ALL, GameObject.class);
	}

	@SuppressWarnings("unchecked")
	public <T extends GameObject> List<T> getNear(int shapeType, float distance,
			int objectFilter, Class<T> classOf) {

		NWObject[] objectsInShape = NWScript.getObjectsInShape(shapeType, distance,
				getLocation(), false, objectFilter, NWVector.ORIGIN);
		List<T> ret = new LinkedList<T>();

		for (NWObject o : objectsInShape)
			if (classOf.isAssignableFrom(o.getClass()))
				ret.add((T) o);

		return ret;
	}


	protected Map<String, String> toStringContextParams() {
		Map<String, String> ret = new HashMap<String, String>();

		ret.put("ref", getResRef());
		ret.put("tag", getTag());
		ret.put("name", getName());

		return ret;
	}

	@Override
	public String toString() {
		String param = "no context";
		if (Scheduler.isInContextThread()) {
			param = "";
			for (Entry<String, String> elem : toStringContextParams().entrySet())
				param += (elem.getKey() + "=" + elem.getValue()) + ",";
			if (param.length() > 0)
				param = param.substring(0, param.length() - 1);
		}

		return String.format("%s(0x%x)[%s]",
				this.getClass().getName(), this.getObjectId(), param);
	}
}
