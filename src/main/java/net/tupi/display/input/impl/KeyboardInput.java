package net.tupi.display.input.impl;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWKeyCallback;

import net.tupi.display.input.Keyboard;

public class KeyboardInput implements Keyboard {

	private boolean[] keys = new boolean[GLFW.GLFW_KEY_LAST];

	private GLFWKeyCallback keyboardCallback;

	public KeyboardInput() {
		this.keyboardCallback = new GLFWKeyCallback() {
			public void invoke(long windowId, int key, int scancode, int action, int mods) {
				keys[key] = (action != GLFW.GLFW_RELEASE);
			}
		};
	}

	@Override
	public void destroy() {
		keyboardCallback.free();
	}

	public Object getCallback() {
		return this.keyboardCallback;
	}

	@Override
	public boolean isKeyDown(int key) {
		// TODO Auto-generated method stub
		return this.keys[key];
	}
}
