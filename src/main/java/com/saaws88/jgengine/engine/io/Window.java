package com.saaws88.jgengine.engine.io;

import com.saaws88.jgengine.engine.math.Vector3f;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.glfw.GLFWWindowSizeCallback;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL11;

import static org.lwjgl.glfw.GLFW.glfwCreateWindow;
import static org.lwjgl.glfw.GLFW.glfwDestroyWindow;
import static org.lwjgl.glfw.GLFW.glfwGetPrimaryMonitor;
import static org.lwjgl.glfw.GLFW.glfwGetVideoMode;
import static org.lwjgl.glfw.GLFW.glfwGetWindowSize;
import static org.lwjgl.glfw.GLFW.glfwInit;
import static org.lwjgl.glfw.GLFW.glfwMakeContextCurrent;
import static org.lwjgl.glfw.GLFW.glfwPollEvents;
import static org.lwjgl.glfw.GLFW.glfwSetCursorPosCallback;
import static org.lwjgl.glfw.GLFW.glfwSetKeyCallback;
import static org.lwjgl.glfw.GLFW.glfwSetMouseButtonCallback;
import static org.lwjgl.glfw.GLFW.glfwSetScrollCallback;
import static org.lwjgl.glfw.GLFW.glfwSetWindowMonitor;
import static org.lwjgl.glfw.GLFW.glfwSetWindowPos;
import static org.lwjgl.glfw.GLFW.glfwSetWindowSizeCallback;
import static org.lwjgl.glfw.GLFW.glfwSetWindowTitle;
import static org.lwjgl.glfw.GLFW.glfwShowWindow;
import static org.lwjgl.glfw.GLFW.glfwSwapBuffers;
import static org.lwjgl.glfw.GLFW.glfwSwapInterval;
import static org.lwjgl.glfw.GLFW.glfwTerminate;
import static org.lwjgl.glfw.GLFW.glfwWindowShouldClose;
import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_DEPTH_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.glClear;
import static org.lwjgl.opengl.GL11.glClearColor;
import static org.lwjgl.opengl.GL11.glViewport;

public class Window {

  private int width, height;
  private String title;
  private long window;
  private int frames;
  private static long time;
  private Input input;
  private Vector3f background = new Vector3f(0,0,0);
  private GLFWWindowSizeCallback sizeCallback;
  private boolean isResized;
  private boolean isFullScreen;
  private int[] windowPosX = new int[1], windowPosY = new int[1];

  public Window(int width, int height, String title) {

    this.width = width;
    this.height = height;
    this.title = title;

  }

  public void create() {
    if(!glfwInit()) {
      System.err.println("ERROR: GLFW wasn't initialized");
      return;
    }

    input = new Input();
    window = glfwCreateWindow(width, height, title, isFullScreen ? glfwGetPrimaryMonitor() : 0, 0);

    if (window == 0) {
      System.err.println("ERROR: Window wasn't created");
      return;
    }

    GLFWVidMode videoMode = glfwGetVideoMode(glfwGetPrimaryMonitor());
    windowPosX[0] = (videoMode.width() - width) / 2;
    windowPosY[0] = (videoMode.width() - height) / 2;
    glfwSetWindowPos(window, windowPosX[0], windowPosY[0]);
    glfwMakeContextCurrent(window);
    GL.createCapabilities();
    GL11.glEnable(GL11.GL_DEPTH_TEST);

    createCallbacks();

    glfwShowWindow(window);

    glfwSwapInterval(1);

    time = System.currentTimeMillis();

  }

  private void createCallbacks() {
    sizeCallback = new GLFWWindowSizeCallback() {
      @Override
      public void invoke(long window, int width, int height) {

        width = width;
        height = height;
        isResized = true;
      }
    };

    glfwSetKeyCallback(window, input.getKeyboardInputCallback());
    glfwSetCursorPosCallback(window, input.getMouseMoveCallback());
    glfwSetMouseButtonCallback(window, input.getMouseButtonCallback());
    glfwSetScrollCallback(window, input.getMouseScroll());
    glfwSetWindowSizeCallback(window, sizeCallback);

  }

  public void update() {
    if (isResized) {
      glViewport(0, 0, width, height);
      isResized = false;
    }
    glClearColor(background.getX(), background.getY(), background.getZ(), 1.0f);
    glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
    glfwPollEvents();
    frames++;
    if (System.currentTimeMillis() > time + 1000) {
      glfwSetWindowTitle(window, title + " | FPS: " + frames);
      time = System.currentTimeMillis();
      frames = 0;
    }

  }
  public void swapBuffers() {
    glfwSwapBuffers(window);
  }

  public boolean shouldClose() {
    return glfwWindowShouldClose(window);
  }

  public void destroy() {
    input.destroy();
    sizeCallback.free();
    glfwWindowShouldClose(window);
    glfwDestroyWindow(window);
    glfwTerminate();
  }

  public void setBackgroundColour(float r, float g, float b) {
    background.setCoords(r,g,b);
  }

  public boolean isFullScreen() {
    return isFullScreen;
  }

  public void setFullScreen(boolean fullScreen) {
    isFullScreen = fullScreen;
    isResized = true;
    if (isFullScreen) {
      glfwGetWindowSize(window, windowPosX, windowPosY);
      glfwSetWindowMonitor(window, GLFW.glfwGetPrimaryMonitor(), 0, 0, width, height, 0);
    } else {
      glfwSetWindowMonitor(window, 0, windowPosX[0], windowPosY[0], width, height, 0);
    }
  }

  public int getWidth() {
    return width;
  }

  public int getHeight() {
    return height;
  }

  public String getTitle() {
    return title;
  }

  public long getWindow() {
    return window;
  }
}
