package modeling.workbench.model;

import java.io.File;
import java.util.List;
import java.util.Map;

import modeling.workbench.Model;

public interface WorkbenchModel extends Model {

	WorkareaModel getWorkarea();

	List<ProjectModel> getProjects();

	Map<ProjectModel, File> getProjectFiles();

	File getProjectFile(ProjectModel project);

	ProjectModel getSelectedProject();
}
