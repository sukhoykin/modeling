package modeling.workbench.model;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import modeling.workbench.Entity;

public class Workbench extends Entity<WorkbenchObserver> implements
		WorkbenchModel, WorkbenchObserver {

	private WorkareaModel workarea;

	private List<ProjectModel> projects = new ArrayList<ProjectModel>();
	private Map<ProjectModel, File> projectFiles = new HashMap<ProjectModel, File>();

	private ProjectModel selectedProject;

	public Workbench(WorkareaModel workarea) {

		notNull(workarea);

		this.workarea = workarea;
	}

	@Override
	public WorkareaModel getWorkarea() {
		return workarea;
	}

	@Override
	public List<ProjectModel> getProjects() {
		return projects;
	}

	@Override
	public Map<ProjectModel, File> getProjectFiles() {
		return projectFiles;
	}

	@Override
	public File getProjectFile(ProjectModel project) {
		return projectFiles.get(project);
	}

	@Override
	public ProjectModel getSelectedProject() {
		return selectedProject;
	}

	@Override
	public void addProject(ProjectModel project) {

		notNull(project);
		notContain(projects, project);

		projects.add(project);

		for (WorkbenchObserver observer : getObservers()) {
			observer.addProject(project);
			observer.modelChanged();
		}
	}

	@Override
	public void removeProject(ProjectModel project) {

		if (projects.remove(project)) {

			for (WorkbenchObserver observer : getObservers()) {
				observer.removeProject(project);
				observer.modelChanged();
			}
		}
	}

	@Override
	public void setProjectFile(ProjectModel project, File file) {

		projectFiles.put(project, file);

		for (WorkbenchObserver observer : getObservers()) {
			observer.setProjectFile(project, file);
			observer.modelChanged();
		}
	}

	@Override
	public void setSelectedProject(ProjectModel project) {

		selectedProject = project;

		for (WorkbenchObserver observer : getObservers()) {
			observer.setSelectedProject(project);
			observer.modelChanged();
		}
	}

	@Override
	protected void updateObserver(WorkbenchObserver observer) {

		for (ProjectModel project : getProjects()) {
			observer.addProject(project);
		}

		for (Map.Entry<ProjectModel, File> file : getProjectFiles().entrySet()) {
			observer.setProjectFile(file.getKey(), file.getValue());
		}

		observer.setSelectedProject(getSelectedProject());
	}
}
