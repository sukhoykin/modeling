package modeling.workbench;

public interface Model {

	void addObserver(Observer observer);

	void removeObserver(Observer observer);
}
