package board;

public class Tools {
	private boolean mouseListener;
		
	public Tools(boolean listener) {
		this.mouseListener = listener;
	}		
	public boolean isMouseListeneri() {
		return mouseListener;
	}
	public void setMouseListener(boolean mouseListener) {
		this.mouseListener = mouseListener;
	}			
}
