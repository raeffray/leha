package net.tupi;

import org.lwjgl.glfw.GLFW;

import net.tupi.display.Window;

public class MainLeha {

	private Window window;

	private int HEIGHT = 1024;

	private int WIDTH = 2028;

	public Thread game;

	public MainLeha() {
		this.window = new Window(HEIGHT, WIDTH, "8EBDF2", "lena hana");
	}

	public void run() {

		try {
			while (!window.shouldClose()) {
				System.out.println("looping" );
				updateWindow();
				render();
				if (window.getInputs().isKeyDown(GLFW.GLFW_KEY_F1)) {
					window.setFullScreen();
				}
			}
		} finally {
			window.terminate();
		}

	}

	private void updateWindow() {
		this.window.update();
	}

	private void render() {
		this.window.swapBuffers();
	}

	public static void main(String[] args) {
		new MainLeha().run();
	}

}