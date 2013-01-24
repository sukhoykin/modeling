package modeling.workbench.ui;

import modeling.workbench.model.ProjectModel;

public interface WorkbenchSupervisor {

	void newProject();

	void openProject();

	void selectProject(ProjectModel project);

	void saveProject();

	void saveAsProject();

	void renameProject();

	void deleteProject();

	void exit();

	int getProjectSortIndex(ProjectModel project);
}
