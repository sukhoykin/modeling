package modeling;

import modeling.workbench.model.Workarea;
import modeling.workbench.model.Workbench;
import modeling.workbench.ui.WorkbenchPresenter;

public class Modeling {

	private WorkbenchPresenter presenter;

	public static void main(String[] args) {
		new Modeling().start();
	}

	public Modeling() {

		Workarea workarea = new Workarea();
		Workbench workbench = new Workbench(workarea);

		presenter = new WorkbenchPresenter(workbench);
	}

	public void start() {
		presenter.startWorkbench();
	}
}
