package modeling.workbench.model;

import modeling.workbench.Observer;

public interface WorkareaObserver extends Observer {

	void openDiagram(DiagramModel diagram);

	void closeDiagram(DiagramModel diagram);
}
