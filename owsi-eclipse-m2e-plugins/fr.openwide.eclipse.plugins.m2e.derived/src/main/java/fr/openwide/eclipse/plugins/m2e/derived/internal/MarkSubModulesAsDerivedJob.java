package fr.openwide.eclipse.plugins.m2e.derived.internal;

import static fr.openwide.eclipse.plugins.m2e.derived.internal.MarkSubModulesAsDerivedPlugin.PLUGIN_ID;

import java.util.List;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.ILog;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.SubMonitor;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.m2e.core.MavenPlugin;
import org.eclipse.m2e.core.project.IMavenProjectFacade;
import org.eclipse.m2e.core.project.IMavenProjectRegistry;

public class MarkSubModulesAsDerivedJob extends Job {

	private static final ILog LOGGER = Platform.getLog(MarkSubModulesAsDerivedHelper.class);

	private static final String MAVEN_NATURE_ID = "org.eclipse.m2e.core.maven2Nature";

	private List<IResource> resources;

	public MarkSubModulesAsDerivedJob(String name, List<IResource> containers) {
		super(name);

		this.resources = containers;
	}

	@Override
	protected IStatus run(IProgressMonitor monitor) {
		SubMonitor subMonitor = SubMonitor.convert(monitor, Messages.markSubModulesAsDerived_markingDerived, 0);
		IStatus exitStatus = Status.CANCEL_STATUS;
		LOGGER.log(new Status(IStatus.INFO, PLUGIN_ID, String.format("setDerived started.")));
		
		try {
			subMonitor.setWorkRemaining(resources.size());
			for (IResource resource : resources) {
				if (subMonitor.isCanceled()) {
					LOGGER.log(new Status(IStatus.INFO, PLUGIN_ID, String.format("Monitor canceled, setDerived interrupted.")));
					subMonitor.done();
				} else {
					if (resource instanceof IProject) {
						LOGGER.log(new Status(IStatus.INFO, PLUGIN_ID, String.format("IResource %s is a IProject. Analyzing...", resource.getName())));
						IProject project = (IProject) resource;
						runOnProject(project, subMonitor.newChild(1, SubMonitor.SUPPRESS_NONE));
					} else {
						LOGGER.log(new Status(IStatus.INFO, PLUGIN_ID, String.format("IResource %s is not a project. setDerived ignored.", resource.getName())));
						subMonitor.worked(1);
					}
				}
			}
			
			exitStatus = Status.OK_STATUS;
			return exitStatus;
		} catch (Exception e) {
			LOGGER.log(new Status(IStatus.ERROR, PLUGIN_ID, String.format(Messages.markSubModulesAsDerived_markingDerivedError), e));
			return Status.CANCEL_STATUS;
		} finally {
			subMonitor.done();
		}
	}

	protected void runOnProject(IProject project, IProgressMonitor monitor) {
		SubMonitor projectMonitor = SubMonitor.convert(monitor, project.getName(), 2);
		try {
			if (project.hasNature(MAVEN_NATURE_ID)) {
				LOGGER.log(new Status(IStatus.INFO, PLUGIN_ID, String.format("IProject %s has MavenNature. Analyzing...", project.getName())));
				IMavenProjectRegistry projectManager = MavenPlugin.getMavenProjectRegistry();
				IMavenProjectFacade projectFacade = projectManager.create(project, projectMonitor.newChild(1));
				if (projectFacade != null) {
					LOGGER.log(new Status(IStatus.INFO, PLUGIN_ID, String.format("MavenFacade found for %s. setDerived starts.", project.getName())));
					MarkSubModulesAsDerivedHelper.markProjectSubModulesAsDerived(projectFacade, projectMonitor.newChild(1));
				} else {
					LOGGER.log(new Status(IStatus.ERROR, PLUGIN_ID, String.format("MavenFacade not found on Project %s. setDerived failed.", project.getName()))); //$NON-NLS-1$
				}
			} else {
				LOGGER.log(new Status(IStatus.INFO, PLUGIN_ID, String.format("Project %s has not MavenNature. setDerived ignored.", project.getName())));
			}
		} catch (CoreException e) {
			LOGGER.log(new Status(IStatus.ERROR, PLUGIN_ID, String.format("Fails to retrieve project nature. Project %s ignored.", project.getName(), e)));
		} finally {
			projectMonitor.done();
		}
	}

}
