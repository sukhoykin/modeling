package modeling.workbench.ui;

import modeling.workbench.model.Project;
import modeling.workbench.model.ProjectModel;

public class ProjectPresenter implements ProjectSupervisor {

	private WorkbenchWindow window;

	private Project project, result;
	private ProjectDialog dialog;

	private boolean isRename;

	public ProjectPresenter(WorkbenchWindow window) {
		this.window = window;
	}

	public Project createProject() {

		isRename = false;
		result = null;

		project = new Project("");

		dialog = new ProjectDialog(window);
		
		dialog.setSupervisor(this);
		dialog.setModel(project);
		
		dialog.open();

		return result;
	}

	public void renameProject(Project project) {

		isRename = true;
		this.project = project;

		dialog = new ProjectDialog(window);
		
		dialog.setSupervisor(this);
		dialog.setModel(project);
		
		dialog.open();
	}

	@Override
	public boolean isRename() {
		return isRename;
	}

	@Override
	public String validateName(String name) {

		if (name.length() == 0) {

			return "Project name is empty";

		} else {

			for (ProjectModel project : window.getModel().getProjects()) {

				if (project.getName().equals(name)) {
					return "Duplicate project name";
				}
			}
		}

		return null;
	}

	@Override
	public void submit(String name) {
		
		project.setName(name);
		result = project;
		
		dialog.dispose();
	}

	@Override
	public void cancel() {
		dialog.dispose();
	}
}
