package modeling.workbench.model;

import java.util.List;

import modeling.workbench.Model;

public interface WorkareaModel extends Model {

	List<DiagramModel> getDiagrams();
}