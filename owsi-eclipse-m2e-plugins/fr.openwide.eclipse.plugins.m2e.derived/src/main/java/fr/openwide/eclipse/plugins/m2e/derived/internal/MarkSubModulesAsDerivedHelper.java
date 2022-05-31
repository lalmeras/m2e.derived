package fr.openwide.eclipse.plugins.m2e.derived.internal;

import static fr.openwide.eclipse.plugins.m2e.derived.internal.MarkSubModulesAsDerivedPlugin.PLUGIN_ID;

import java.util.List;

import org.eclipse.core.resources.IFolder;
import org.eclipse.core.runtime.ILog;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.SubMonitor;
import org.eclipse.m2e.core.project.IMavenProjectFacade;
import org.eclipse.m2e.core.project.configurator.ProjectConfigurationRequest;

public class MarkSubModulesAsDerivedHelper {

	private static final ILog LOGGER = Platform.getLog(MarkSubModulesAsDerivedHelper.class);

	public static void configure(ProjectConfigurationRequest request, IProgressMonitor monitor) {
		markProjectSubModulesAsDerived(request.getMavenProjectFacade(), monitor);
	}

	public static void markProjectSubModulesAsDerived(IMavenProjectFacade mavenProjectFacade, IProgressMonitor monitor) {
		String projectName = mavenProjectFacade.getProject().getName();
		SubMonitor subMonitor = SubMonitor.convert(monitor, projectName, 1);
		
		LOGGER.log(new Status(IStatus.INFO, PLUGIN_ID, String.format("Mark sub-modules as derived, searching sub-modules in %s...", projectName))); //$NON-NLS-1$
		List<String> modules = mavenProjectFacade.getMavenProjectModules();
		for (String module : modules) {
			LOGGER.log(new Status(IStatus.INFO, PLUGIN_ID, String.format("Sub-module %s > %s found, trying to setDerived...", projectName, module))); //$NON-NLS-1$
			IFolder folder = mavenProjectFacade.getProject().getFolder(module);
			if (folder.exists()) {
				try {
					folder.setDerived(true, subMonitor.newChild(1));
				} catch (Exception e) {
					LOGGER.log(new Status(IStatus.ERROR,
							PLUGIN_ID,
							String.format("Sub-module %s > %s. setDerived failed",
									projectName,
									module), //$NON-NLS-1$
							e)
					);
				}
			} else {
				LOGGER.log(new Status(IStatus.INFO, PLUGIN_ID, String.format("Sub-module %s > %s, eclipse reource not found. Aborting", projectName, module))); //$NON-NLS-1$
			}
			LOGGER.log(new Status(IStatus.INFO, PLUGIN_ID, String.format("Sub-module %s > %s done.", projectName, module))); //$NON-NLS-1$
		}
		LOGGER.log(new Status(IStatus.INFO, PLUGIN_ID, String.format("Mark sub-modules as derived, %s done", projectName))); //$NON-NLS-1$
	}

}
