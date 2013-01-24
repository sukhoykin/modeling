package modeling.workbench.ui;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import modeling.workbench.model.Project;
import modeling.workbench.model.ProjectModel;
import modeling.workbench.model.Workbench;
import modeling.workbench.model.WorkbenchObserver;

public class WorkbenchPresenter implements WorkbenchSupervisor {

	private Workbench workbench;
	private WorkbenchWindow window;

	private ProjectSort projectSort = new ProjectSort();

	private ProjectPresenter projectPresenter;

	public WorkbenchPresenter(Workbench workbench) {

		this.workbench = workbench;

		window = new WorkbenchWindow();
		window.setSupervisor(this);

		projectPresenter = new ProjectPresenter(window);
	}

	public void startWorkbench() {

		workbench.addObserver(projectSort);

		window.setModel(workbench);
		window.open();

		workbench.removeObserver(projectSort);
	}

	@Override
	public void newProject() {

		Project project = projectPresenter.createProject();

		if (project != null) {
			workbench.addProject(project);
		}
	}

	@Override
	public void openProject() {
		// TODO Auto-generated method stub

	}

	@Override
	public void selectProject(ProjectModel project) {
		System.out.println("selectProject: " + project.getName());
		workbench.setSelectedProject(project);
	}

	@Override
	public void saveProject() {
		// TODO Auto-generated method stub

	}

	@Override
	public void saveAsProject() {
		// TODO Auto-generated method stub

	}

	@Override
	public void renameProject() {
		// TODO Auto-generated method stub

	}

	@Override
	public void deleteProject() {
		workbench.removeProject(workbench.getSelectedProject());
	}

	@Override
	public int getProjectSortIndex(ProjectModel project) {
		return projectSort.indexOf(project.getName());
	}

	@Override
	public void exit() {
		window.dispose();
	}

	@SuppressWarnings("serial")
	public class ProjectSort extends ArrayList<String> implements
			WorkbenchObserver {

		@Override
		public void addProject(ProjectModel project) {

			add(project.getName());

			Collections.sort(this, new Comparator<String>() {
				@Override
				public int compare(String p1, String p2) {
					return p1.compareToIgnoreCase(p2);
				}
			});
		}

		@Override
		public void setSelectedProject(ProjectModel project) {
		}

		@Override
		public void setProjectFile(ProjectModel project, File file) {
		}

		@Override
		public void removeProject(ProjectModel project) {
			remove(project.getName());
		}

		@Override
		public void modelChanged() {
		}
	}
}
