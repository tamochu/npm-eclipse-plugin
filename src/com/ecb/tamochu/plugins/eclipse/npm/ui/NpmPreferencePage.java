package com.ecb.tamochu.plugins.eclipse.npm.ui;


import org.eclipse.jface.preference.DirectoryFieldEditor;
import org.eclipse.jface.preference.FieldEditorPreferencePage;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;

import com.ecb.tamochu.plugins.eclipse.npm.plugin.Activator;
import com.ecb.tamochu.plugins.eclipse.npm.preference.INpmConstants;

public class NpmPreferencePage extends FieldEditorPreferencePage implements IWorkbenchPreferencePage {
//	private static final Logger logger = Logger.getLogger(NpmPreferencePage.class);

	public NpmPreferencePage() {
		super(FieldEditorPreferencePage.GRID);
		setDescription("npmのパス等を設定します。");
		setPreferenceStore(Activator.getDefault().getPreferenceStore());
	}

	@Override
	protected void createFieldEditors() {
		{
			addField(new DirectoryFieldEditor(INpmConstants.NPM_PATH, "&NpmDir", getFieldEditorParent()));
		}
	}

	@Override
	public void init(IWorkbench workbench) {
	}

}
