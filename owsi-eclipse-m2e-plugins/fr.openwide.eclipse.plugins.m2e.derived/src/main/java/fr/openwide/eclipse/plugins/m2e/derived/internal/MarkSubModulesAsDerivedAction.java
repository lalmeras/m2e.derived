package fr.openwide.eclipse.plugins.m2e.derived.internal;

import java.util.List;

import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.IObjectActionDelegate;
import org.eclipse.ui.IWorkbenchPart;

public class MarkSubModulesAsDerivedAction implements IObjectActionDelegate {

	private IStructuredSelection selection;

	@Override
	public void run(IAction action) {
		if (selection != null) {
			@SuppressWarnings("unchecked")
			final List<IResource> containers = selection.toList();
			Job job = new MarkSubModulesAsDerivedJob(Messages.markSubModulesAsDerived_markingDerived, containers);
			job.schedule();
		}
	}

	

	@Override
	public void selectionChanged(IAction action, ISelection selection) {
		if (selection instanceof IStructuredSelection) {
			this.selection = (IStructuredSelection) selection;
		}
	}

	@Override
	public void setActivePart(IAction action, IWorkbenchPart targetPart) {
	}

}
