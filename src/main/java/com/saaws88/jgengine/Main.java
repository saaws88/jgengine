package com.saaws88.jgengine;

import com.saaws88.jgengine.engine.graphics.Mesh;
import com.saaws88.jgengine.engine.graphics.Renderer;
import com.saaws88.jgengine.engine.graphics.Vertex;
import com.saaws88.jgengine.engine.math.Vector3f;
import org.lwjgl.glfw.GLFW;

import com.saaws88.jgengine.engine.io.Input;
import com.saaws88.jgengine.engine.io.Window;

public class Main implements Runnable {

  public Thread game;
  public static Window window;
  public Renderer renderer;
  public static final int W = 1280, H = 760;

  public Mesh mesh = new Mesh(new Vertex[]{
      new Vertex(new Vector3f(-0.5f, 0.5f, 0.0f)),
      new Vertex(new Vector3f(-0.5f, -0.5f, 0.0f)),
      new Vertex(new Vector3f(0.5f, -0.5f, 0.0f)),
      new Vertex(new Vector3f(0.5f, 0.5f, 0.0f)),
  } , new int[] {
      0, 1, 2,
      0, 3, 2
  });

  public void start() {
    game = new Thread(this, "game");
    game.start();
  }

  public void init() {
    window = new Window(W, H, "Game");
    renderer = new Renderer();
    window.setBackgroundColour(1.0f, 0,0);
    window.create();
    mesh.create();
  }

  public void run() {
    init();
    while (!window.shouldClose() && !Input.isKeyDown(GLFW.GLFW_KEY_ESCAPE)) {
      update();
      render();
      if (Input.isKeyDown(GLFW.GLFW_KEY_F11)) {
        window.setFullScreen(!window.isFullScreen());
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
    renderer.renderMesh(mesh);
    window.swapBuffers();
  }

  public static void main(String[] args) {
    new Main().start();
  }

}