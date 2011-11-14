package es.elv.nwnx2.jvm.hierarchy;

import org.nwnx.nwnx2.jvm.NWScript;
import org.nwnx.nwnx2.jvm.NWVector;


public class Sound extends GameObject {

	public Sound(int oid) {
		super(oid);
	}


	public void play() {
		NWScript.soundObjectPlay(this);
	}
	public void stop() {
		NWScript.soundObjectStop(this);
	}

	public void setPosition(NWVector v) {
		NWScript.soundObjectSetPosition(this, v);
	}

	public void setVolume(int v) {
		NWScript.soundObjectSetVolume(this, v);
	}

}
