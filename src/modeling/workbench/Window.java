package modeling.workbench;

import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

public abstract class Window<M extends Model> extends Component<Shell, M> {

	private static Display display = new Display();

	public Window() {
		super(new Shell(display));
	}

	public Window(int style) {
		super(new Shell(display, style));
	}

	public Window(Shell parent) {
		super(new Shell(parent));
	}

	public Window(Shell parent, int style) {
		super(new Shell(parent, style));
	}

	public void open() {

		Shell shell = getWidget();

		Rectangle bounds;
		Point size = shell.getSize();

		if (shell.getParent() == null) {
			bounds = display.getBounds();
		} else {
			bounds = shell.getParent().getBounds();
		}

		shell.setLocation(bounds.x + (bounds.width - size.x) / 2, bounds.y
				+ (bounds.height - size.y) / 2);

		shell.open();

		while (!shell.isDisposed()) {
			if (!shell.getDisplay().readAndDispatch()) {
				shell.getDisplay().sleep();
			}
		}

		if (display.getShells().length == 0) {
			display.dispose();
		}
	}
}
