package es.elv.nwnx2.jvm.hierarchy.activator;

import org.nwnx.nwnx2.jvm.NWEffect;
import org.nwnx.nwnx2.jvm.NWEffect.EffectHandler;

import es.elv.nwnx2.jvm.hierarchy.Effect;
import es.elv.nwnx2.jvm.hierarchy.bundles.Feature;

public class EffectHandlerImpl extends Feature implements EffectHandler {

	@Override
	protected void load() throws Exception {
		NWEffect.registerEffectHandler(this);
	}

	@Override
	protected void unload() throws Exception {
		NWEffect.unregisterEffectHandler(this);
	}

	@Override
	public NWEffect handleEffectClass(NWEffect eff) {
		return new Effect(eff.getEffectId());
	}
}
