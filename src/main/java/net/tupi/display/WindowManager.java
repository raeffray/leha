package net.tupi.display;

import net.tupi.display.input.impl.Input;

public interface WindowManager {

	public void init();

	public void update();
	
	public void terminate();
	
	public void swapBuffers();

	public boolean shouldClose();
	
	public void setFullScreen(boolean fullScreen);
	
	public Input getInputs();
	
	public boolean isFullScreen();
	
}
