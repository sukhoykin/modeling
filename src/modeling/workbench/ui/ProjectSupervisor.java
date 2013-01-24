package modeling.workbench.ui;

public interface ProjectSupervisor {

	public boolean isRename();

	public String validateName(String name);

	public void submit(String name);

	public void cancel();
}
