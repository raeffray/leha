package net.tupi.display;

import net.tupi.display.impl.ColorTranslator;
import net.tupi.display.impl.WindowManagerLWJGLNew;
import net.tupi.display.input.impl.Input;

public class Window {

	private int height;

	private int width;

	private String hexaColor;

	private String name;

	private WindowManager windowManager;

	/***
	 * 
	 * @param height integer value
	 * @param width  integer value
	 * @param color  RGB hex representation i.e FFFCBD
	 * 
	 */
	public Window(int height, int width, String hexaColor, String name) {
		super();
		this.height = height;
		this.width = width;
		this.hexaColor = hexaColor;
		this.name = name;
		this.windowManager = new WindowManagerLWJGLNew(this);
		this.windowManager.init();
	}

	public int getHeight() {
		return height;
	}

	public int getWidth() {
		return width;
	}

	public String getName() {
		return name;
	}

	public float[] getColorAsFloatArray() {
		return ColorTranslator.hexToRgb(this.hexaColor);
	}

	public boolean shouldClose() {
		return windowManager.shouldClose();
	}

	public void swapBuffers() {
		this.windowManager.swapBuffers();
	}

	public void update() {
		this.windowManager.update();
	}
	
	public void setFullScreen() {
		this.windowManager.setFullScreen(!this.isFullScreen());
	}
	
	public Input getInputs() {
		return this.windowManager.getInputs();
	}

	public void terminate() {
		this.windowManager.terminate();
	}
	
	public boolean isFullScreen() {
		return this.windowManager.isFullScreen();
	}

}
