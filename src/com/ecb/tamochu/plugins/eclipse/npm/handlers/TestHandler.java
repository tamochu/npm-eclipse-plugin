package com.ecb.tamochu.plugins.eclipse.npm.handlers;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.debug.core.DebugPlugin;
import org.eclipse.debug.core.ILaunchManager;
import org.eclipse.debug.core.IStreamListener;
import org.eclipse.debug.core.Launch;
import org.eclipse.debug.core.model.IProcess;
import org.eclipse.debug.core.model.IStreamMonitor;
import org.eclipse.ui.console.ConsolePlugin;
import org.eclipse.ui.console.IConsole;
import org.eclipse.ui.console.IConsoleManager;
import org.eclipse.ui.console.MessageConsole;
import org.eclipse.ui.console.MessageConsoleStream;

import com.ecb.tamochu.plugins.eclipse.npm.plugin.Activator;
import com.ecb.tamochu.plugins.eclipse.npm.preference.INpmConstants;
import com.ecb.tamochu.plugins.eclipse.npm.utils.NpmUtil;

/**
 * Our sample handler extends AbstractHandler, an IHandler base class.
 *
 * @see org.eclipse.core.commands.IHandler
 * @see org.eclipse.core.commands.AbstractHandler
 */
public class TestHandler extends AbstractHandler {
	final String CONSOLE_NAME = "npm-console";

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		String npmPath = Activator.getDefault().getPreferenceStore().getString(INpmConstants.NPM_PATH);

		IProject proj = NpmUtil.getPrjoect(event);
		ConsolePlugin consolePlugin = ConsolePlugin.getDefault();
		IConsoleManager consoleManager = consolePlugin.getConsoleManager();
		IConsole[] consoles = consoleManager.getConsoles();
		MessageConsole console = null;
		for (IConsole consoleElm : consoles) {
			if (CONSOLE_NAME.equals(consoleElm.getName())) {
				console = (MessageConsole) consoleElm;
				break;
			}
		}
		if (console == null) {
			console = new MessageConsole(CONSOLE_NAME, "consoleType", null, "UTF-8", false);
			consoleManager.addConsoles(new IConsole[] { console });
		}

		final MessageConsoleStream out = console.newMessageStream();

		try {
			Process rawProc = DebugPlugin.exec(new String[] {npmPath + "\\npm.cmd", "--version"}, proj.getLocation().toFile());

			IProcess proc = DebugPlugin.newProcess(new Launch(null, ILaunchManager.RUN_MODE, null), rawProc, null);
			proc.getStreamsProxy().getOutputStreamMonitor().addListener(new IStreamListener() {
				@Override
				public void streamAppended(String text, IStreamMonitor monitor) {
					System.out.println(NpmUtil.getEncodingFromString(text));
					out.println(text);
				}
			});
		} catch (CoreException exc) {
			exc.printStackTrace();
		}

		return null;
	}
}
