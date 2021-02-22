package net.tupi.display.input.impl;

import org.lwjgl.glfw.GLFWCursorPosCallback;
import org.lwjgl.system.CallbackI;

import net.tupi.display.input.MousePosition;

public class MousePositionInput implements MousePosition {

	private GLFWCursorPosCallback mousePositionCallback;

	private static double mouseX;

	private static double mouseY;

	public MousePositionInput() {
		mousePositionCallback = new GLFWCursorPosCallback() {
			public void invoke(long windowId, double xpos, double ypos) {
				{
					//System.out.println(" X: [" + xpos + " ] Y: [" + ypos + "]");
					mouseX = xpos;
					mouseY = ypos;
				}
			}
		};
	}

	@Override
	public void destroy() {
		this.mousePositionCallback.free();
	}

	public double getMouseX() {
		return mouseX;
	}

	public double getMouseY() {
		return mouseY;
	}

	public CallbackI.V getCallback() {
		return mousePositionCallback;
	}

}
