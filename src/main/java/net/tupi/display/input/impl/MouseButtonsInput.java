package net.tupi.display.input.impl;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWMouseButtonCallback;

import net.tupi.display.input.MouseButtons;

public class MouseButtonsInput implements MouseButtons {

	private boolean[] buttons = new boolean[GLFW.GLFW_MOUSE_BUTTON_LAST];

	private GLFWMouseButtonCallback mouseButtonCallback;

	public MouseButtonsInput() {
		this.mouseButtonCallback = new GLFWMouseButtonCallback() {
			public void invoke(long windowId, int button, int action, int mods) {
				buttons[button] = (action != GLFW.GLFW_RELEASE);
			}
		};
	}

	public boolean[] getButtons() {
		return this.getButtons();
	}

	public Object getCallback() {
		return this.mouseButtonCallback;
	}

	@Override
	public void destroy() {
		this.mouseButtonCallback.free();

	}

	@Override
	public boolean isButtomDown(int button) {
		return this.buttons[button];
	}

}
