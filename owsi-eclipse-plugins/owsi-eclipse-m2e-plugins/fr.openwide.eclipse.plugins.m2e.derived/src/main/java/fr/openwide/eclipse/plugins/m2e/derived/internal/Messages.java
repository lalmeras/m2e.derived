package fr.openwide.eclipse.plugins.m2e.derived.internal;

import org.eclipse.osgi.util.NLS;

public class Messages extends NLS {
	private static final String BUNDLE_NAME = "fr.openwide.eclipse.plugins.m2e.derived.internal.messages"; //$NON-NLS-1$
	public static String markSubModulesAsDerived_markingDerived;
	public static String markSubModulesAsDerived_markingDerivedError;
	static {
		// initialize resource bundle
		NLS.initializeMessages(BUNDLE_NAME, Messages.class);
	}

	private Messages() {
	}
}
