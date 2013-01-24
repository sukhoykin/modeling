package modeling.workbench.ui;

import modeling.workbench.Window;
import modeling.workbench.model.DiagramModel;
import modeling.workbench.model.ProjectModel;
import modeling.workbench.model.ProjectObserver;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.RowData;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

public class ProjectDialog extends Window<ProjectModel> implements
		ProjectObserver, ModifyListener, SelectionListener {

	private Text name;
	private Button submit, cancel;

	private ProjectSupervisor supervisor;

	public ProjectDialog(WorkbenchWindow parent) {

		super(parent.getWidget(), SWT.DIALOG_TRIM | SWT.APPLICATION_MODAL);

		Shell shell = getWidget();

		GridLayout grid = new GridLayout(2, false);
		grid.marginWidth = 10;
		grid.marginHeight = 10;
		shell.setLayout(grid);

		Label nameLabel = new Label(shell, SWT.NONE);
		nameLabel.setText("Nam&e:");

		name = new Text(shell, SWT.BORDER);
		GridData gridData = new GridData(250, SWT.DEFAULT);
		name.setLayoutData(gridData);

		Composite control = new Composite(shell, SWT.NONE);
		gridData = new GridData();
		gridData.horizontalSpan = 2;
		gridData.horizontalAlignment = SWT.RIGHT;
		control.setLayoutData(gridData);
		control.setLayout(new RowLayout(SWT.HORIZONTAL));

		if (shell.getDisplay().getDismissalAlignment() == SWT.LEFT) {
			submit = new Button(control, SWT.NONE);
			cancel = new Button(control, SWT.NONE);
		} else {
			cancel = new Button(control, SWT.NONE);
			submit = new Button(control, SWT.NONE);
		}

		submit.setLayoutData(new RowData(100, SWT.DEFAULT));

		cancel.setText("&Cancel");
		cancel.setLayoutData(new RowData(100, SWT.DEFAULT));

		shell.pack();

		name.addModifyListener(this);
		name.addSelectionListener(this);
		submit.addSelectionListener(this);
		cancel.addSelectionListener(this);

		setSupervisor(new DefaultSupervisor());
	}

	public void setSupervisor(ProjectSupervisor supervisor) {

		this.supervisor = supervisor;

		if (supervisor.isRename()) {

			getWidget().setText("Rename Project");
			submit.setText("Re&name");

		} else {

			getWidget().setText("New Project");
			submit.setText("C&reate");
		}
	}

	@Override
	public void setName(String name) {

		this.name.setText(name);

		if (supervisor.isRename()) {
			this.name.setSelection(0, name.length());
		}
	}

	@Override
	public void addDiagram(DiagramModel model) {
	}

	@Override
	public void removeDiagram(DiagramModel model) {
	}

	@Override
	public void modifyText(ModifyEvent e) {

		String reason = supervisor.validateName(name.getText());

		if (reason == null) {
			submit.setEnabled(true);
		} else {
			submit.setEnabled(false);
		}
	}

	@Override
	public void widgetSelected(SelectionEvent e) {

		Object source = e.getSource();

		if (source.equals(submit)) {

			supervisor.submit(name.getText());

		} else if (source.equals(cancel)) {

			supervisor.cancel();
		}
	}

	@Override
	public void widgetDefaultSelected(SelectionEvent e) {

		if (submit.isEnabled()) {
			supervisor.submit(name.getText());
		}
	}

	private class DefaultSupervisor implements ProjectSupervisor {

		@Override
		public boolean isRename() {
			return false;
		}

		@Override
		public String validateName(String name) {
			return null;
		}

		@Override
		public void submit(String name) {
		}

		@Override
		public void cancel() {
		}
	}
}
