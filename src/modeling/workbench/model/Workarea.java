package modeling.workbench.model;

import java.util.ArrayList;
import java.util.List;

import modeling.workbench.Entity;

public class Workarea extends Entity<WorkareaObserver> implements
		WorkareaModel, WorkareaObserver {

	private List<DiagramModel> diagrams = new ArrayList<DiagramModel>();

	@Override
	public List<DiagramModel> getDiagrams() {
		return diagrams;
	}

	@Override
	public void openDiagram(DiagramModel diagram) {

		notNull(diagram);
		notContain(diagrams, diagram);

		diagrams.add(diagram);

		for (WorkareaObserver observer : getObservers()) {
			observer.openDiagram(diagram);
		}
	}

	@Override
	public void closeDiagram(DiagramModel diagram) {

		if (diagrams.remove(diagram)) {

			for (WorkareaObserver observer : getObservers()) {
				observer.closeDiagram(diagram);
			}
		}
	}

	@Override
	protected void updateObserver(WorkareaObserver observer) {

		for (DiagramModel diagram : getDiagrams()) {
			observer.openDiagram(diagram);
		}
	}
}
