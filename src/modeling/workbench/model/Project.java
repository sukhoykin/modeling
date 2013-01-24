package modeling.workbench.model;

import java.util.ArrayList;
import java.util.List;

import modeling.workbench.Entity;

public class Project extends Entity<ProjectObserver> implements ProjectModel,
		ProjectObserver {

	private String name;
	private List<DiagramModel> diagrams = new ArrayList<DiagramModel>();

	public Project(String name) {
		setName(name);
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public List<DiagramModel> getDiagrams() {
		return diagrams;
	}

	public void setName(String name) {

		notNull(name);

		this.name = name;

		for (ProjectObserver observer : getObservers()) {
			observer.setName(name);
			observer.modelChanged();
		}
	}

	@Override
	public void addDiagram(DiagramModel diagram) {

		notNull(diagram);
		notContain(diagrams, diagram);

		diagrams.add(diagram);

		for (ProjectObserver observer : getObservers()) {
			observer.addDiagram(diagram);
			observer.modelChanged();
		}
	}

	@Override
	public void removeDiagram(DiagramModel diagram) {

		if (diagrams.remove(diagram)) {

			for (ProjectObserver observer : getObservers()) {
				observer.removeDiagram(diagram);
				observer.modelChanged();
			}
		}
	}

	@Override
	protected void updateObserver(ProjectObserver observer) {

		observer.setName(getName());

		for (DiagramModel diagram : getDiagrams()) {
			observer.addDiagram(diagram);
		}
	}
}
