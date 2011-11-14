package es.elv.nwnx2.jvm.hierarchy;

import java.io.File;
import java.io.IOException;

/**
 * Receives exceptions that would otherwise go back to nwserver.
 * They usually point to bugs in your plugin code or inside frameworks.
 *
 * @param Exception e the logged Exception
 * @param File dumpPath the path were exception debug data can be dumped to;
 *   may be null if dumps are not wanted or the path does not exist
 */
public interface ExceptionListener {
	public void onException(Throwable e, File dumpPath) throws IOException;
}
