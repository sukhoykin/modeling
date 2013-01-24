package modeling.workbench;

import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.widgets.Widget;

public abstract class Component<W extends Widget, M extends Model> implements
		DisposeListener, Observer {

	private W widget;
	private M model;

	public Component(W widget) {

		this.widget = widget;
		widget.addDisposeListener(this);
	}

	public void setModel(M model) {

		if (this.model != null) {
			this.model.removeObserver(this);
		}

		this.model = model;

		if (model != null) {
			model.addObserver(this);
		}
	}

	public W getWidget() {
		return widget;
	}

	public M getModel() {
		return model;
	}

	public void dispose() {
		widget.dispose();
	}

	@Override
	public void widgetDisposed(DisposeEvent e) {
		setModel(null);
	}

	@Override
	public void modelChanged() {
	}
}
