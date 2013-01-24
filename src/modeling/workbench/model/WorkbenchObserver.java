package modeling.workbench.model;

import java.io.File;

import modeling.workbench.Observer;

public interface WorkbenchObserver extends Observer {

	void addProject(ProjectModel project);

	void removeProject(ProjectModel project);

	void setProjectFile(ProjectModel project, File file);

	void setSelectedProject(ProjectModel project);
}
