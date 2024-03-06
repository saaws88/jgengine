package com.saaws88.jgengine.io;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWVidMode;

public class Window {

  private int width, height;
  private String title;
  private long window;
  public int frames;
  public static long time;
  public Input input;

  public Window(int width, int height, String title) {
    this.width = width;
    this.height = height;
    this.title = title;
  }

  public void create() {
    if (!GLFW.glfwInit()) {
      System.err.println("ERROR: GLFW wasn't initialized");
      return;
    }

    input = new Input();
    window = GLFW.glfwCreateWindow(width, height, title, 0, 0);

    if (window == 0) {
      System.err.println("ERROR: Window wasn't created");
      return;
    }

    GLFWVidMode videoMode = GLFW.glfwGetVideoMode(GLFW.glfwGetPrimaryMonitor());
    GLFW.glfwSetWindowPos(
        window,
        (videoMode.width() - width) / 2,
        (videoMode.height() - height) / 2);

    GLFW.glfwSetKeyCallback(window, input.getKeyboardInputCallback());
    GLFW.glfwSetCursorPosCallback(window, input.getMouseMoveCallback());
    GLFW.glfwSetMouseButtonCallback(window, input.getMouseButtonCallback());

    GLFW.glfwMakeContextCurrent(window);

    GLFW.glfwShowWindow(window);

    GLFW.glfwSwapInterval(1);

  }

  public void update() {
    GLFW.glfwPollEvents();
    frames++;
    if(System.currentTimeMillis() > time + 1000) {
      GLFW.glfwSetWindowTitle(window, title + " | FPS: " + frames);
      time = System.currentTimeMillis();
      frames = 0;
    }
  }

  public void swapBuffers() {
    GLFW.glfwSwapBuffers(window);
  }

  public boolean shoudClose() {
    return GLFW.glfwWindowShouldClose(window);
  }

  public void destroy() {
    input.destroy();
    GLFW.glfwWindowShouldClose(window);
    GLFW.glfwDestroyWindow(window);
    GLFW.glfwTerminate();
  }

}
