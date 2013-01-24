package modeling.workbench.model;

import java.util.List;

import modeling.workbench.Model;

public interface ProjectModel extends Model {

	String getName();

	List<DiagramModel> getDiagrams();
}
