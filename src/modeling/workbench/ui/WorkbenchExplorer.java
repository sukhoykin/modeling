package modeling.workbench.ui;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import modeling.workbench.Component;
import modeling.workbench.model.DiagramModel;
import modeling.workbench.model.DiagramObserver;
import modeling.workbench.model.ProjectModel;
import modeling.workbench.model.ProjectObserver;
import modeling.workbench.model.WorkbenchModel;
import modeling.workbench.model.WorkbenchObserver;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.custom.CTabItem;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeItem;

public class WorkbenchExplorer extends Component<CTabFolder, WorkbenchModel>
		implements WorkbenchObserver, SelectionListener, KeyListener {

	private CTabItem item;
	private Tree tree;

	private Menu projectMenu;
	private MenuItem renameItem, deleteItem;

	private HashMap<ProjectModel, ProjectItem> projects = new HashMap<ProjectModel, ProjectItem>();

	private WorkbenchSupervisor supervisor;

	public WorkbenchExplorer(Composite parent) {

		super(new CTabFolder(parent, SWT.BORDER));

		CTabFolder folder = getWidget();
		folder.setSimple(false);
		folder.setTabHeight(24);
		folder.setSingle(true);
		folder.setSelectionBackground(parent.getDisplay().getSystemColor(
				SWT.COLOR_TITLE_INACTIVE_BACKGROUND_GRADIENT));

		item = new CTabItem(folder, SWT.NONE);
		item.setText("Project Explorer");

		tree = new Tree(folder, SWT.BORDER | SWT.SINGLE);

		item.setControl(tree);
		folder.setSelection(0);

		projectMenu = new Menu(tree);
		renameItem = new MenuItem(projectMenu, SWT.PUSH);
		renameItem.setText("&Rename");
		deleteItem = new MenuItem(projectMenu, SWT.PUSH);
		deleteItem.setText("&Delete");

		tree.setMenu(projectMenu);
		tree.addSelectionListener(this);
		tree.addKeyListener(this);
	}

	public void setSupervisor(WorkbenchSupervisor supervisor) {
		this.supervisor = supervisor;
	}

	@Override
	public void setModel(WorkbenchModel model) {

		for (Map.Entry<ProjectModel, ProjectItem> project : projects.entrySet()) {
			project.getValue().dispose();
		}

		super.setModel(model);
	}

	private ProjectItem getSelectedProjectItem() {

		if (tree.getSelectionCount() == 1) {

			TreeItem selected = tree.getSelection()[0];

			while (selected.getParentItem() != null) {
				selected = selected.getParentItem();
			}

			for (Entry<ProjectModel, ProjectItem> project : projects.entrySet()) {
				if (project.getValue().getWidget().equals(selected)) {
					return project.getValue();
				}
			}
		}

		return null;
	}

	@Override
	public void addProject(ProjectModel project) {

		int index = supervisor.getProjectSortIndex(project);

		ProjectItem projectItem = new ProjectItem(index);
		projectItem.setModel(project);

		projects.put(project, projectItem);
	}

	@Override
	public void setSelectedProject(ProjectModel project) {

		if (project != null) {

			TreeItem item = projects.get(project).getWidget();

			if (tree.getSelectionCount() == 0
					|| !item.equals(tree.getSelection()[0])) {
				tree.setSelection(item);
			}
		}
	}

	@Override
	public void setProjectFile(ProjectModel project, File file) {
	}

	@Override
	public void removeProject(ProjectModel project) {

		if (projects.containsKey(project)) {
			projects.remove(project).dispose();
		}
	}

	@Override
	public void widgetSelected(SelectionEvent e) {

		ProjectModel project = null;
		ProjectItem projectItem = getSelectedProjectItem();

		if (projectItem != null) {
			project = projectItem.getModel();
		}

		supervisor.selectProject(project);
	}

	@Override
	public void widgetDefaultSelected(SelectionEvent e) {
	}

	@Override
	public void keyPressed(KeyEvent e) {
	}

	@Override
	public void keyReleased(KeyEvent e) {

		if (e.character == SWT.DEL) {
			supervisor.deleteProject();
		}
	}

	public class ProjectItem extends Component<TreeItem, ProjectModel>
			implements ProjectObserver {

		private HashMap<DiagramModel, DiagramItem> diagrams = new HashMap<DiagramModel, DiagramItem>();

		public ProjectItem(int index) {

			super(new TreeItem(tree, SWT.NONE, index));

			getWidget().setImage(
					new Image(tree.getDisplay(), ProjectItem.class
							.getResourceAsStream("/folder.png")));
		}

		@Override
		public void setName(String name) {
			getWidget().setText(name);
		}

		@Override
		public void addDiagram(DiagramModel diagram) {

			DiagramItem diagramItem = new DiagramItem(getWidget());
			diagramItem.setModel(diagram);

			diagrams.put(diagram, diagramItem);
		}

		@Override
		public void removeDiagram(DiagramModel diagram) {

			if (diagrams.containsKey(diagram)) {
				diagrams.remove(diagram).dispose();
			}
		}

		public class DiagramItem extends Component<TreeItem, DiagramModel>
				implements DiagramObserver {

			public DiagramItem(TreeItem parent) {
				super(new TreeItem(parent, SWT.NONE));
			}

			@Override
			public void setName(String name) {
				getWidget().setText(name);
			}
		}
	}
}
