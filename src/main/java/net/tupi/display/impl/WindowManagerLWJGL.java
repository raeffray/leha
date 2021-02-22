//package net.tupi.display.impl;
//
//import static org.lwjgl.glfw.Callbacks.glfwFreeCallbacks;
//import static org.lwjgl.glfw.GLFW.GLFW_CONTEXT_VERSION_MAJOR;
//import static org.lwjgl.glfw.GLFW.GLFW_CONTEXT_VERSION_MINOR;
//import static org.lwjgl.glfw.GLFW.GLFW_OPENGL_CORE_PROFILE;
//import static org.lwjgl.glfw.GLFW.GLFW_OPENGL_FORWARD_COMPAT;
//import static org.lwjgl.glfw.GLFW.GLFW_OPENGL_PROFILE;
//import static org.lwjgl.glfw.GLFW.GLFW_RESIZABLE;
//import static org.lwjgl.glfw.GLFW.GLFW_VISIBLE;
//import static org.lwjgl.glfw.GLFW.glfwCreateWindow;
//import static org.lwjgl.glfw.GLFW.glfwDefaultWindowHints;
//import static org.lwjgl.glfw.GLFW.glfwDestroyWindow;
//import static org.lwjgl.glfw.GLFW.glfwGetPrimaryMonitor;
//import static org.lwjgl.glfw.GLFW.glfwGetVideoMode;
//import static org.lwjgl.glfw.GLFW.glfwInit;
//import static org.lwjgl.glfw.GLFW.glfwMakeContextCurrent;
//import static org.lwjgl.glfw.GLFW.glfwPollEvents;
//import static org.lwjgl.glfw.GLFW.glfwSetErrorCallback;
//import static org.lwjgl.glfw.GLFW.glfwShowWindow;
//import static org.lwjgl.glfw.GLFW.glfwSwapBuffers;
//import static org.lwjgl.glfw.GLFW.glfwSwapInterval;
//import static org.lwjgl.glfw.GLFW.glfwTerminate;
//import static org.lwjgl.glfw.GLFW.glfwWindowHint;
//import static org.lwjgl.glfw.GLFW.glfwWindowShouldClose;
//import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
//import static org.lwjgl.opengl.GL11.GL_DEPTH_BUFFER_BIT;
//import static org.lwjgl.opengl.GL11.GL_FALSE;
//import static org.lwjgl.opengl.GL11.GL_TRUE;
//import static org.lwjgl.opengl.GL11.glClear;
//import static org.lwjgl.opengl.GL11.glClearColor;
//import static org.lwjgl.system.MemoryUtil.NULL;
//
//import org.lwjgl.Version;
//import org.lwjgl.glfw.GLFW;
//import org.lwjgl.glfw.GLFWErrorCallback;
//import org.lwjgl.glfw.GLFWVidMode;
//import org.lwjgl.opengl.GL;
//
//import net.tupi.display.Window;
//import net.tupi.display.WindowManager;
//import net.tupi.input.Input;
//
//public class WindowManagerLWJGL implements WindowManager {
//
//	private Window window;
//	
//	private long windowId;
//
//	public int frames;
//
//	public static long time;
//
//	public WindowManagerLWJGL(Window window) {
//		super();
//		this.window = window;
//	}
//
//	@Override
//	public void init() {
//		System.out.println("Hello LWJGL " + Version.getVersion() + "!");
//		windowId = this.createWindow(this.window.getWidth(), this.window.getHeight(), this.window.getName());
//	}
//
//	public void update() {
//		// This line is critical for LWJGL's interoperation with GLFW's
//		// OpenGL context, or any context that is managed externally.
//		// LWJGL detects the context that is current in the current thread,
//		// creates the ContextCapabilities instance and makes the OpenGL
//		// bindings available for use.
//		GL.createCapabilities();
//
//		// Set the clear color
//		float[] colors = this.window.getColorAsFloatArray();
//
//		glClearColor(colors[0], colors[1], colors[2], 0.0f);
//
//		GLFW.glfwPollEvents();
//
//		frames++;
//
//		//if (System.currentTimeMillis() > time + 750) {
//			GLFW.glfwSetWindowTitle(windowId, this.window.getName() + " | FPS: " + frames);
//
//			System.out.println("looping");
//			glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT); // clear the framebuffer
//
//			// Poll for window events. The key callback above will only be
//			// invoked during this call.
//			glfwPollEvents();
//
//			time = System.currentTimeMillis();
//			frames = 0;
//		//}
//	}
//
//	public void swapBuffers() {
//		glfwSwapBuffers(windowId); // swap the color buffers
//	}
//
//
//	public boolean shouldClose() {
//		return glfwWindowShouldClose(windowId);
//	}
//
//	public void terminate() {
//		try {
//			glfwFreeCallbacks(windowId);
//			glfwDestroyWindow(windowId);
//		} finally {
//			glfwTerminate();
//			glfwSetErrorCallback(null).free();	// TODO: handle finally clause
//		}
//		
//	}
//	
//	public long createWindow(int width, int height, String name) {
//
//		// Setup an error callback. The default implementation
//		// will print the error message in System.err.
//		GLFWErrorCallback.createPrint(System.err).set();
//
//		// Initialize GLFW. Most GLFW functions will not work before doing this.
//		if (!glfwInit()) {
//			throw new IllegalStateException("Unable to initialize GLFW");
//		}
//
//		// Configure our window
//		glfwDefaultWindowHints(); // optional, the current window hints are already the default
//		glfwWindowHint(GLFW_VISIBLE, GL_FALSE); // the window will stay hidden after creation
//		glfwWindowHint(GLFW_RESIZABLE, GL_TRUE); // the window will be resizable
//
//		// not sure if that's necessary..
//		glfwWindowHint(GLFW_OPENGL_PROFILE, GLFW_OPENGL_CORE_PROFILE);
//		glfwWindowHint(GLFW_OPENGL_FORWARD_COMPAT, GL_TRUE);
//		glfwWindowHint(GLFW_CONTEXT_VERSION_MAJOR, 3);
//		glfwWindowHint(GLFW_CONTEXT_VERSION_MINOR, 2);
//
//		// Create the window
//		long windowId = glfwCreateWindow(width, height,name, NULL, NULL);
//		if (windowId == NULL) {
//			throw new RuntimeException("Failed to create the GLFW window");
//		}
//
//		// Setup a key callback. It will be called every time a key is pressed, repeated
//		// or released.
//		setCallbacks(windowId);
//		
//		// Get the resolution of the primary monitor
//		GLFWVidMode videoMode = glfwGetVideoMode(glfwGetPrimaryMonitor());
//		// Center our window
//		GLFW.glfwSetWindowPos(windowId, (videoMode.width() - width) / 2, (videoMode.height() - height) / 2);
//
//		// Make the OpenGL context current
//		glfwMakeContextCurrent(windowId);
//		// Enable v-sync
//		glfwSwapInterval(1);
//
//		// Make the window visible
//		glfwShowWindow(windowId);
//		return windowId;
//	}
//	
//	private void setCallbacks(long windowId) {
//		Input input = new Input();
//
//		GLFW.glfwSetKeyCallback(windowId, input.getKeyboardCallback());
//		GLFW.glfwSetCursorPosCallback(windowId, input.getMouseMoveCallback());
//		GLFW.glfwSetMouseButtonCallback(windowId, input.getMouseButtonsCallback());
//		
//	}
//
//
//}
