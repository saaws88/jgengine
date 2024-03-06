package com.saaws88.jgengine;

import org.lwjgl.glfw.GLFW;

import com.saaws88.jgengine.io.Input;
import com.saaws88.jgengine.io.Window;

public class Main implements Runnable {

  public Thread game;
  public static Window window;
  public static final int W = 1280, H = 760;

  public void start() {
    game = new Thread(this, "game");
    game.start();
  }

  public static void init() {
    window = new Window(W, H, "Game");
    window.create();
  }

  public void run() {
    init();
    while (!window.shoudClose()) {
      update();
      render();
      if (Input.isKeyDown(GLFW.GLFW_KEY_ESCAPE)) {
        return;
      }
    }
    window.destroy();
  }

  private void update() {
    window.update();
    if (Input.isButtonDown(GLFW.GLFW_MOUSE_BUTTON_LEFT)) {
      System.out.format("X: %f, Y: %f \n", Input.getMouseX(), Input.getMouseY());
    }
  }

  private void render() {
    window.swapBuffers();
  }

  public static void main(String[] args) {
    new Main().start();
  }
}