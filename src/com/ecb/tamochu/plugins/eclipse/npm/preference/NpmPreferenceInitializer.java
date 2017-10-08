package com.ecb.tamochu.plugins.eclipse.npm.preference;

import org.eclipse.core.runtime.preferences.AbstractPreferenceInitializer;
import org.eclipse.jface.preference.IPreferenceStore;

import com.ecb.tamochu.plugins.eclipse.npm.plugin.Activator;

public class NpmPreferenceInitializer extends AbstractPreferenceInitializer {

	@Override
	public void initializeDefaultPreferences() {
		IPreferenceStore store = Activator.getDefault().getPreferenceStore();
		store.setDefault(INpmConstants.NPM_PATH, "C:\\Program Files\\nodejs");
	}

}
