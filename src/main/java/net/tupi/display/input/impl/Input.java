package net.tupi.display.input.impl;

import net.tupi.display.input.Keyboard;
import net.tupi.display.input.MouseButtons;
import net.tupi.display.input.MousePosition;

public class Input {

	private Keyboard keysInput;

	private MouseButtons mouseButtonsInput;

	private MousePosition mousePositionInput;

	public Input() {
		keysInput = new KeyboardInput();
		mouseButtonsInput = new MouseButtonsInput();
		mousePositionInput = new MousePositionInput();
	}

	public boolean isKeyDown(int key) {
		return this.keysInput.isKeyDown(key);
	}

	public boolean isButtonDown(int button) {
		return this.mouseButtonsInput.isButtomDown(button);
	}

	public void destroy() {
		keysInput.destroy();
		mouseButtonsInput.destroy();
		mousePositionInput.destroy();
	}

	public double getMouseX() {
		return this.mousePositionInput.getMouseX();
	}

	public double getMouseY() {
		return this.mousePositionInput.getMouseY();
	}

	public Object getKeyboardCallback() {
		return keysInput.getCallback();
	}

	public Object getMouseMoveCallback() {

		return mousePositionInput.getCallback();
	}

	public Object getMouseButtonsCallback() {
		return mouseButtonsInput.getCallback();
	}
}
