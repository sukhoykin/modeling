package modeling.workbench.ui;

import modeling.workbench.Component;
import modeling.workbench.model.DiagramModel;
import modeling.workbench.model.WorkareaModel;
import modeling.workbench.model.WorkareaObserver;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;

public class WorkbenchWorkarea extends Component<Composite, WorkareaModel>
		implements WorkareaObserver {

	private WorkbenchSupervisor supervisor;
	
	public WorkbenchWorkarea(Composite parent) {

		super(new Composite(parent, SWT.NONE));
		getWidget().setLayout(new FillLayout());
	}

	public void setSupervisor(WorkbenchSupervisor supervisor) {
		this.supervisor = supervisor;
	}

	@Override
	public void openDiagram(DiagramModel diagram) {
		// TODO Auto-generated method stub

	}

	@Override
	public void closeDiagram(DiagramModel diagram) {
		// TODO Auto-generated method stub

	}
}
