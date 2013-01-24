package modeling.workbench;

import java.util.Collection;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public abstract class Entity<O extends Observer> implements Model, Observer {

	private List<O> observers = new CopyOnWriteArrayList<O>();

	@Override
	public void addObserver(Observer observer) {

		@SuppressWarnings("unchecked")
		O concreteObserver = (O) observer;

		if (!observers.contains(concreteObserver)) {

			observers.add(concreteObserver);
			updateObserver(concreteObserver);
		}
	}

	/**
	 * addObserver(...) { updateObserver(observer, this); updateObserver(O
	 * observer, Observable<O> observable);
	 * 
	 * @param observer
	 */
	protected abstract void updateObserver(O observer);

	protected List<O> getObservers() {
		return observers;
	}

	@Override
	public void removeObserver(Observer observer) {
		observers.remove(observer);
	}

	@Override
	public void modelChanged() {
	}

	protected void notNull(Object object) {

		if (object == null) {
			throw new IllegalArgumentException("argument cannot be null");
		}
	}

	protected void notContain(Collection<?> collection, Object object) {

		if (collection.contains(object)) {
			throw new IllegalArgumentException("duplicate value not allowed");
		}
	}
}
