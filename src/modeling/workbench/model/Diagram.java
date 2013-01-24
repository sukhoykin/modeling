package modeling.workbench.model;

import modeling.workbench.Entity;

public class Diagram extends Entity<DiagramObserver> implements DiagramModel,
		DiagramObserver {

	private String name;

	public Diagram(String name) {
		setName(name);
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public void setName(String name) {

		notNull(name);

		this.name = name;

		for (DiagramObserver observer : getObservers()) {
			observer.setName(name);
			observer.modelChanged();
		}
	}

	@Override
	protected void updateObserver(DiagramObserver observer) {
		observer.setName(getName());
	}
}
