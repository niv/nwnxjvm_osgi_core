package es.elv.nwnx2.jvm.hierarchy.bundles;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.nwnx.nwnx2.jvm.ScheduledEvery.Policy;
import org.nwnx.nwnx2.jvm.ScheduledEvery.Timing;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Every {
	public long interval();
	public Policy policy() default Policy.AS_AVAILABLE;
	public Timing timing() default Timing.BEFORE;
}
