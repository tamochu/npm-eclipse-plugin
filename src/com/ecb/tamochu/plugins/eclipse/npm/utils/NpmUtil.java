package com.ecb.tamochu.plugins.eclipse.npm.utils;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.handlers.HandlerUtil;
import org.mozilla.universalchardet.UniversalDetector;

public class NpmUtil {
	public static IProject getPrjoect(ExecutionEvent event) throws ExecutionException {
		IWorkbenchWindow window = HandlerUtil.getActiveWorkbenchWindowChecked(event);
		IStructuredSelection ss = (StructuredSelection) window.getSelectionService().getSelection();

		if (ss.getFirstElement() instanceof IAdaptable) {
			IResource rsc = ((IAdaptable) ss.getFirstElement()).getAdapter(IResource.class);
			if (rsc instanceof IProject) {
				IProject proj = (IProject) rsc;
				return proj;
			}
		}

		return null;
	}

	public static String getEncodingFromString(String text) {
		UniversalDetector detector = new UniversalDetector(null);
		String encoding = null;

		try {
			InputStream is = new ByteArrayInputStream(text.getBytes());

			byte[] buf = new byte[1024];
			int nread;
			while ((nread = is.read(buf)) > 0 && !detector.isDone()) {
				detector.handleData(buf, 0, nread);
			}
			detector.dataEnd();
			encoding = detector.getDetectedCharset();

			detector.reset();
		} catch (IOException exc) {
			exc.printStackTrace();
		}

		return encoding;
	}
}
