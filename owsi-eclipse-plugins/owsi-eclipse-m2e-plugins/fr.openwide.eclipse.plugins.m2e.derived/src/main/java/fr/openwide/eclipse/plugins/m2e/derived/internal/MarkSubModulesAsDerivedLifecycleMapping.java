package fr.openwide.eclipse.plugins.m2e.derived.internal;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.m2e.core.project.IMavenProjectFacade;
import org.eclipse.m2e.core.project.configurator.AbstractBuildParticipant;
import org.eclipse.m2e.core.project.configurator.AbstractLifecycleMapping;
import org.eclipse.m2e.core.project.configurator.AbstractProjectConfigurator;
import org.eclipse.m2e.core.project.configurator.ILifecycleMappingConfiguration;
import org.eclipse.m2e.core.project.configurator.MojoExecutionKey;
import org.eclipse.m2e.core.project.configurator.ProjectConfigurationRequest;

public class MarkSubModulesAsDerivedLifecycleMapping extends AbstractLifecycleMapping {
	
	@Override
	public void configure(ProjectConfigurationRequest request,
			IProgressMonitor monitor) {
		MarkSubModulesAsDerivedHelper.configure(request, monitor);
	}

	@Override
	public Map<MojoExecutionKey, List<AbstractBuildParticipant>> getBuildParticipants(
			IMavenProjectFacade project, IProgressMonitor monitor)
			throws CoreException {
		return Collections.emptyMap();
	}

	@Override
	public List<AbstractProjectConfigurator> getProjectConfigurators(
			IMavenProjectFacade project, IProgressMonitor monitor)
			throws CoreException {
		return Collections.emptyList();
	}

	@Override
	public boolean hasLifecycleMappingChanged(IMavenProjectFacade newFacade,
			ILifecycleMappingConfiguration oldConfiguration,
			IProgressMonitor monitor) {
		return false;
	}

}
