package net.tupi.display.impl;

import static org.lwjgl.glfw.Callbacks.glfwFreeCallbacks;
import static org.lwjgl.glfw.GLFW.GLFW_FALSE;
import static org.lwjgl.glfw.GLFW.GLFW_RESIZABLE;
import static org.lwjgl.glfw.GLFW.GLFW_TRUE;
import static org.lwjgl.glfw.GLFW.GLFW_VISIBLE;
import static org.lwjgl.glfw.GLFW.glfwCreateWindow;
import static org.lwjgl.glfw.GLFW.glfwDefaultWindowHints;
import static org.lwjgl.glfw.GLFW.glfwDestroyWindow;
import static org.lwjgl.glfw.GLFW.glfwGetPrimaryMonitor;
import static org.lwjgl.glfw.GLFW.glfwGetVideoMode;
import static org.lwjgl.glfw.GLFW.glfwGetWindowSize;
import static org.lwjgl.glfw.GLFW.glfwInit;
import static org.lwjgl.glfw.GLFW.glfwMakeContextCurrent;
import static org.lwjgl.glfw.GLFW.glfwPollEvents;
import static org.lwjgl.glfw.GLFW.glfwSetErrorCallback;
import static org.lwjgl.glfw.GLFW.glfwSetWindowPos;
import static org.lwjgl.glfw.GLFW.glfwShowWindow;
import static org.lwjgl.glfw.GLFW.glfwSwapBuffers;
import static org.lwjgl.glfw.GLFW.glfwSwapInterval;
import static org.lwjgl.glfw.GLFW.glfwTerminate;
import static org.lwjgl.glfw.GLFW.glfwWindowHint;
import static org.lwjgl.glfw.GLFW.glfwWindowShouldClose;
import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_DEPTH_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.glClear;
import static org.lwjgl.opengl.GL11.glClearColor;
import static org.lwjgl.system.MemoryStack.stackPush;
import static org.lwjgl.system.MemoryUtil.NULL;

import java.nio.IntBuffer;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWCursorPosCallbackI;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.glfw.GLFWKeyCallbackI;
import org.lwjgl.glfw.GLFWMouseButtonCallbackI;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.opengl.GL;
import org.lwjgl.system.MemoryStack;

import net.tupi.display.Window;
import net.tupi.display.WindowManager;
import net.tupi.display.input.impl.Input;

public class WindowManagerLWJGLNew implements WindowManager {

	private Window window;

	private long windowId;

	private Input inputs;

	public int frames;

	public static long time;

	public boolean fullScreen;

	public boolean resized;

	private int[] windowPosX = new int[1], windowPosY = new int[1];

	public WindowManagerLWJGLNew(Window window) {
		super();
		this.inputs = new Input();
		this.window = window;
	}

	public Input getInputs() {
		return inputs;
	}

	public boolean isFullScreen() {
		return fullScreen;
	}

	@Override
	public void init() {
		// Setup an error callback. The default implementation
		// will print the error message in System.err.
		GLFWErrorCallback.createPrint(System.err).set();

		// Initialize GLFW. Most GLFW functions will not work before doing this.
		if (!glfwInit())
			throw new IllegalStateException("Unable to initialize GLFW");

		// Configure GLFW
		glfwDefaultWindowHints(); // optional, the current window hints are already the default
		glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE); // the window will stay hidden after creation
		glfwWindowHint(GLFW_RESIZABLE, GLFW_TRUE); // the window will be resizable

		// Create the window
		this.windowId = glfwCreateWindow(window.getWidth(), window.getHeight(), window.getName(), NULL, NULL);
		if (windowId == NULL)
			throw new RuntimeException("Failed to create the GLFW window");

		// Set the inputs callbacks 
		setCallbacks(windowId);

		// Get the thread stack and push a new frame
		try (MemoryStack stack = stackPush()) {
			IntBuffer pWidth = stack.mallocInt(1); // int*
			IntBuffer pHeight = stack.mallocInt(1); // int*

			// Get the window size passed to glfwCreateWindow
			glfwGetWindowSize(windowId, pWidth, pHeight);

			// Get the resolution of the primary monitor
			GLFWVidMode vidmode = glfwGetVideoMode(glfwGetPrimaryMonitor());

			windowPosX[0] = (vidmode.width() - window.getWidth()) / 2;
			windowPosY[0] = (vidmode.height() - window.getHeight()) / 2;

			// Center the window
			glfwSetWindowPos(windowId, windowPosX[0], windowPosY[0]);
		} // the stack frame is popped automatically

		// Make the OpenGL context current
		glfwMakeContextCurrent(windowId);

		// Enable v-sync
		glfwSwapInterval(1);

		// Make the window visible
		glfwShowWindow(windowId);
	}

	public void update() {
		// This line is critical for LWJGL's interoperation with GLFW's
		// OpenGL context, or any context that is managed externally.
		// LWJGL detects the context that is current in the current thread,
		// creates the GLCapabilities instance and makes the OpenGL
		// bindings available for use.
		GL.createCapabilities();

		float[] colors = this.window.getColorAsFloatArray();

		// Set the clear color
		glClearColor(colors[0], colors[1], colors[2], 0.0f);

		// Run the rendering loop until the user has attempted to close
		// the window or has pressed the ESCAPE key.

		glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT); // clear the framebuffer

		// Poll for window events. The key callback above will only be
		// invoked during this call.
		glfwPollEvents();

	}

	public void swapBuffers() {
		glfwSwapBuffers(windowId); // swap the color buffers
	}

	public boolean shouldClose() {
		return glfwWindowShouldClose(windowId);
	}

	public void terminate() {
		try {
			// Free the window callbacks and destroy the window
			glfwFreeCallbacks(this.windowId);
			glfwDestroyWindow(this.windowId);

		} finally {
			// Terminate GLFW and free the error callback
			glfwTerminate();
			glfwSetErrorCallback(null).free();
		}

	}
	
	// not working properly
	public void setFullScreen(boolean isFullscreen) {
		this.fullScreen = isFullscreen;

		int windowWidth = this.window.getWidth();
		int windowHeight = this.window.getHeight();

		resized = true;

		if (fullScreen) {
			GLFW.glfwGetWindowPos(windowId, windowPosX, windowPosY);
			GLFW.glfwSetWindowMonitor(windowId, GLFW.glfwGetPrimaryMonitor(), 0, 0, windowWidth, windowHeight, 0);
		} else {
			this.fullScreen = false;
			GLFW.glfwSetWindowMonitor(windowId, 0, windowPosX[0], windowPosY[0], windowWidth, windowHeight, 0);
		}
	}

	private void setCallbacks(long windowId) {
		GLFW.glfwSetKeyCallback(windowId, (GLFWKeyCallbackI) inputs.getKeyboardCallback());
		GLFW.glfwSetCursorPosCallback(windowId, (GLFWCursorPosCallbackI) inputs.getMouseMoveCallback());
		GLFW.glfwSetMouseButtonCallback(windowId, (GLFWMouseButtonCallbackI) inputs.getMouseButtonsCallback());
	}

}
