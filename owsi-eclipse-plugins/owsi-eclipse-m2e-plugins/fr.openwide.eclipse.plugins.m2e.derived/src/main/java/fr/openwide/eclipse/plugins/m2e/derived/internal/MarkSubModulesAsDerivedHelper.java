package fr.openwide.eclipse.plugins.m2e.derived.internal;

import java.util.List;

import org.eclipse.core.resources.IFolder;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.SubMonitor;
import org.eclipse.m2e.core.project.IMavenProjectFacade;
import org.eclipse.m2e.core.project.configurator.ProjectConfigurationRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.helpers.MessageFormatter;

public class MarkSubModulesAsDerivedHelper {

	private static final Logger log = LoggerFactory.getLogger(MarkSubModulesAsDerivedHelper.class);

	public static void configure(ProjectConfigurationRequest request, IProgressMonitor monitor) {
		markProjectSubModulesAsDerived(request.getMavenProjectFacade(), monitor);
	}

	public static void markProjectSubModulesAsDerived(IMavenProjectFacade mavenProjectFacade, IProgressMonitor monitor) {
		String projectName = mavenProjectFacade.getProject().getName();
		SubMonitor subMonitor = SubMonitor.convert(monitor, projectName, 1);
		log.info("Mark sub-modules as derived, searching sub-modules in {}...", projectName); //$NON-NLS-1$
		List<String> modules = mavenProjectFacade.getMavenProjectModules();
		for (String module : modules) {
			log.info("Sub-module {} > {} found, trying to setDerived...", projectName, module); //$NON-NLS-1$
			IFolder folder = mavenProjectFacade.getProject().getFolder(module);
			if (folder.exists()) {
				try {
					folder.setDerived(true, subMonitor.newChild(1));
				} catch (Exception e) {
					log.error(
							MessageFormatter.format("Sub-module {} > {}. setDerived failed",
									projectName,
									module).getMessage(), //$NON-NLS-1$
							e);
				}
			} else {
				log.info("Sub-module {} > {}, eclipse reource not found. Aborting", projectName, module); //$NON-NLS-1$
			}
			log.info("Sub-module {} > {} done.", projectName, module); //$NON-NLS-1$
		}
		log.info("Mark sub-modules as derived, {} done", projectName); //$NON-NLS-1$
	}

}
