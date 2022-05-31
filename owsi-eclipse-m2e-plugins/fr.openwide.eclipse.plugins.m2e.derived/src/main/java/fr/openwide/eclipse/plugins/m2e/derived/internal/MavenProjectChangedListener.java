package fr.openwide.eclipse.plugins.m2e.derived.internal;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.m2e.core.project.IMavenProjectChangedListener;
import org.eclipse.m2e.core.project.MavenProjectChangedEvent;

public class MavenProjectChangedListener implements IMavenProjectChangedListener {

	@Override
	public void mavenProjectChanged(MavenProjectChangedEvent[] arg0, IProgressMonitor arg1) {
		MarkSubModulesAsDerivedHelper.configure(arg0, arg1);
	}
}
