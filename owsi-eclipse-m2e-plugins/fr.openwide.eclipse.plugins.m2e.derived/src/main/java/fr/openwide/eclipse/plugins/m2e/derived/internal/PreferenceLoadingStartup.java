package fr.openwide.eclipse.plugins.m2e.derived.internal;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Map;

import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.preferences.ConfigurationScope;
import org.eclipse.core.runtime.preferences.IExportedPreferences;
import org.eclipse.core.runtime.preferences.IPreferenceFilter;
import org.eclipse.core.runtime.preferences.InstanceScope;
import org.eclipse.osgi.service.datalocation.Location;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IStartup;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PreferenceLoadingStartup implements IStartup {

	private static final Logger log = LoggerFactory.getLogger(MarkSubModulesAsDerivedJob.class);

	@Override
	public void earlyStartup() {
		log.warn("startup");
		final String location = System.getProperty("prefloader.location");
		if (location != null) {
			log.info("prefloader prepare loading of {}", location);
			Display.getDefault().syncExec(new Runnable() {
				@Override
				public void run() {
					try {
						Location eclipseLocation = Platform.getInstallLocation();
						String path;
						if (!location.startsWith("/")) {
							path = eclipseLocation.getURL().toString() + location;
						} else {
							path = "file://" + location;
						}
						URL prefUrl = new URL(path);
						log.warn("prefloader loading {}", prefUrl.toString());
						InputStream in = null;
						try {
							in = prefUrl.openStream();
							IPreferenceFilter[] filters = new IPreferenceFilter[1];

							filters[0] = new IPreferenceFilter() {
								@Override
								public String[] getScopes() {
									return new String[] { InstanceScope.SCOPE, ConfigurationScope.SCOPE };
								}

								@SuppressWarnings({ "unchecked", "rawtypes" })
								@Override
								public Map getMapping(String scope) {
									return null;
								}
							};
							IExportedPreferences prefs = Platform.getPreferencesService().readPreferences(in);
//							prefs.node("/instance").removeNode();
							Platform.getPreferencesService().applyPreferences(prefs, filters);
							log.warn("preferences loaded");
						} catch (IOException e) {
							log.error("prefloader cannot load {}", prefUrl.toString());
						} finally {
							if (in != null) {
								in.close();
							}
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			});
		} else {
			log.info("prefloader not configured; ignored");
		}
	}

}
