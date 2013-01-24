package modeling.workbench.model;

import modeling.workbench.Observer;

public interface ProjectObserver extends Observer {

	void setName(String name);

	void addDiagram(DiagramModel diagram);

	void removeDiagram(DiagramModel diagram);
}
