package fr.openwide.eclipse.plugins.m2e.derived.internal;

import java.util.List;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.handlers.HandlerUtil;

public class MarkSubModulesAsDerivedHandler extends AbstractHandler {

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		IStructuredSelection selection = HandlerUtil.getCurrentStructuredSelection(event);
		if (selection != null) {
			@SuppressWarnings("unchecked")
			final List<IResource> containers = selection.toList();
			Job job = new MarkSubModulesAsDerivedJob(Messages.markSubModulesAsDerived_markingDerived, containers);
			job.schedule();
		}
		return null;
	}

}
