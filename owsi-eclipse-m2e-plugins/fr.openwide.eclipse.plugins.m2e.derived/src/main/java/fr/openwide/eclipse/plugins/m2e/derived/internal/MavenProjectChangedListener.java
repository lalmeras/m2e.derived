package fr.openwide.eclipse.plugins.m2e.derived.internal;

import java.util.List;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.m2e.core.project.IMavenProjectChangedListener;
import org.eclipse.m2e.core.project.MavenProjectChangedEvent;

public class MavenProjectChangedListener implements IMavenProjectChangedListener {

	@Override
	public void mavenProjectChanged(List<MavenProjectChangedEvent> arg0, IProgressMonitor arg1) {
		Helpers.configure(arg0, arg1);
	}
}
