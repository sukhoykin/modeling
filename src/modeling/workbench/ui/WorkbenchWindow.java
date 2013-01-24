package modeling.workbench.ui;

import java.io.File;

import modeling.workbench.Window;
import modeling.workbench.model.ProjectModel;
import modeling.workbench.model.WorkbenchModel;
import modeling.workbench.model.WorkbenchObserver;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;

public class WorkbenchWindow extends Window<WorkbenchModel> implements
		WorkbenchObserver {

	private final String appname = "Modeling";

	private MainMenu menu;
	private SashForm sash;

	private WorkbenchExplorer explorer;
	private WorkbenchWorkarea workarea;

	private WorkbenchSupervisor supervisor;

	public WorkbenchWindow() {

		getWidget().setText(appname);

		menu = new MainMenu();

		FillLayout layout = new FillLayout(SWT.HORIZONTAL);
		layout.marginWidth = 5;
		layout.marginHeight = 5;

		getWidget().setLayout(layout);

		sash = new SashForm(getWidget(), SWT.SMOOTH | SWT.HORIZONTAL);
		sash.setSashWidth(5);
		sash.setVisible(false);

		explorer = new WorkbenchExplorer(sash);
		workarea = new WorkbenchWorkarea(sash);

		sash.setWeights(new int[] { 14, 86 });

		setSupervisor(new DefaultSupervisor());
	}

	public void setSupervisor(WorkbenchSupervisor supervisor) {

		this.supervisor = supervisor;

		explorer.setSupervisor(supervisor);
		workarea.setSupervisor(supervisor);
	}

	@Override
	public void setModel(WorkbenchModel model) {

		explorer.setModel(model);
		workarea.setModel(model != null ? model.getWorkarea() : null);

		super.setModel(model);
	}

	@Override
	public void addProject(ProjectModel project) {

		if (!sash.isVisible()) {
			sash.setVisible(true);
		}
	}

	@Override
	public void removeProject(ProjectModel project) {

		if (getModel().getProjects().size() == 0) {
			sash.setVisible(false);
		}
	}

	@Override
	public void setProjectFile(ProjectModel project, File file) {
	}

	@Override
	public void setSelectedProject(ProjectModel project) {

		menu.setSaveEnabled(getModel().getProjectFile(project) != null);
		menu.setSaveAsEnabled(project != null);
	}

	private class MainMenu implements SelectionListener {

		private Menu menu, file, help, fileNew, fileNewPop;
		private MenuItem fileItem, helpItem, fileNewItem, fileOpenItem,
				fileSaveItem, fileSaveAsItem, fileExitItem, fileNewProjectItem,
				popNewProjectItem;

		public MainMenu() {

			menu = new Menu(getWidget(), SWT.BAR);
			fileItem = new MenuItem(menu, SWT.CASCADE);
			fileItem.setText("&File");
			helpItem = new MenuItem(menu, SWT.CASCADE);
			helpItem.setText("&Help");

			file = new Menu(fileItem);
			fileNewItem = new MenuItem(file, SWT.CASCADE);
			fileNewItem.setText("&New\tAlt+Shift+N");
			fileNewItem.setAccelerator(SWT.ALT + SWT.SHIFT + 'N');
			fileOpenItem = new MenuItem(file, SWT.PUSH);
			fileOpenItem.setText("&Open...\tCtrl-O");
			fileOpenItem.setAccelerator(SWT.CTRL + 'O');
			fileSaveItem = new MenuItem(file, SWT.PUSH);
			fileSaveItem.setText("&Save\tCtrl-S");
			fileSaveItem.setAccelerator(SWT.CTRL + 'S');
			fileSaveAsItem = new MenuItem(file, SWT.PUSH);
			fileSaveAsItem.setText("Save &As...");
			new MenuItem(file, SWT.SEPARATOR);
			fileExitItem = new MenuItem(file, SWT.PUSH);
			fileExitItem.setText("E&xit");

			fileNew = new Menu(fileNewItem);
			fileNewProjectItem = new MenuItem(fileNew, SWT.PUSH);
			fileNewProjectItem.setText("P&roject...");
			fileNewPop = new Menu(getWidget(), SWT.POP_UP);
			popNewProjectItem = new MenuItem(fileNewPop, SWT.PUSH);
			popNewProjectItem.setText("P&roject...");

			fileNewItem.addSelectionListener(this);
			fileOpenItem.addSelectionListener(this);
			fileSaveItem.addSelectionListener(this);
			fileSaveAsItem.addSelectionListener(this);
			fileExitItem.addSelectionListener(this);

			fileNewProjectItem.addSelectionListener(this);
			popNewProjectItem.addSelectionListener(this);

			fileItem.setMenu(file);
			fileNewItem.setMenu(fileNew);
			helpItem.setMenu(help);

			getWidget().setMenuBar(menu);
		}

		private void setSaveEnabled(boolean enabled) {
			fileSaveItem.setEnabled(enabled);
		}

		private void setSaveAsEnabled(boolean enabled) {
			fileSaveAsItem.setEnabled(enabled);
		}

		@Override
		public void widgetSelected(SelectionEvent e) {

			Object source = e.getSource();

			if (source.equals(fileNewItem)) {

				fileNewPop.setVisible(true);

			} else if (source.equals(fileNewProjectItem)
					|| source.equals(popNewProjectItem)) {

				supervisor.newProject();

			} else if (source.equals(fileOpenItem)) {

				supervisor.openProject();

			} else if (source.equals(fileSaveItem)) {

				supervisor.saveProject();

			} else if (source.equals(fileSaveAsItem)) {

				supervisor.saveAsProject();

			} else if (source.equals(fileExitItem)) {
				supervisor.exit();
			}
		}

		@Override
		public void widgetDefaultSelected(SelectionEvent e) {
		}
	}

	private class DefaultSupervisor implements WorkbenchSupervisor {

		@Override
		public void newProject() {
		}

		@Override
		public void openProject() {
		}

		@Override
		public void selectProject(ProjectModel project) {
		}

		@Override
		public void saveProject() {
		}

		@Override
		public void saveAsProject() {
		}

		@Override
		public void renameProject() {
		}

		@Override
		public void deleteProject() {
		}

		@Override
		public void exit() {
		}

		@Override
		public int getProjectSortIndex(ProjectModel project) {
			return 0;
		}
	}
}
