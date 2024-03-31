package com.saaws88.soda;

import com.saaws88.soda.engine.graphics.Material;
import com.saaws88.soda.engine.graphics.Mesh;
import com.saaws88.soda.engine.graphics.Renderer;
import com.saaws88.soda.engine.graphics.Shader;
import com.saaws88.soda.engine.graphics.Vertex;
import com.saaws88.soda.engine.math.Vector3f;
import org.lwjgl.glfw.GLFW;

import com.saaws88.soda.engine.io.Input;
import com.saaws88.soda.engine.io.Window;
import org.lwjglx.util.vector.Vector2f;

import static org.lwjgl.glfw.GLFW.GLFW_KEY_ESCAPE;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_F11;
import static org.lwjgl.glfw.GLFW.glfwSetWindowShouldClose;

public class Main implements Runnable {

  public Thread game;
  public static Window window;
  public Renderer renderer;
  public Shader shader;
  public static final int W = 1280, H = 760;

  public Mesh mesh = new Mesh(new Vertex[]{
      new Vertex(new Vector3f(-0.5f, 0.5f, 0.0f), new Vector3f(1.0f, 0.0f, 0.0f), new Vector2f(0.0f, 0.0f)),
      new Vertex(new Vector3f(-0.5f, -0.5f, 0.0f), new Vector3f(0.0f, 1.0f, 0.0f), new Vector2f(0.0f, 0.0f)),
      new Vertex(new Vector3f(0.5f, -0.5f, 0.0f), new Vector3f(0.0f, 0.0f, 1.0f), new Vector2f(0.0f, 0.0f)),
      new Vertex(new Vector3f(0.5f, 0.5f, 0.0f), new Vector3f(1.0f, 1.0f, 0.0f), new Vector2f(0.0f, 0.0f)),
  } , new int[] {
      0, 1, 2,
      0, 3, 2
  },
  new Material("textures/2.png)"));

  public void start() {
    game = new Thread(this, "game");
    game.start();
  }

  public void init() {
    window = new Window(W, H, "Game");
    shader = new Shader("shaders/mainVertex.glsl", "shaders/mainFragment.glsl");
    renderer = new Renderer(shader);
    window.setBackgroundColour(1.0f, 0,0);
    window.create();
    mesh.create();
    shader.create();
  }

  public void run() {
    init();
    while (!window.shouldClose()) {
      update();
      render();
      if (Input.isKeyDown(GLFW_KEY_F11)) {
        window.setFullScreen(!window.isFullScreen());
      }
      if (Input.isKeyDown(GLFW_KEY_ESCAPE)) {
        if (window.isFullScreen()) {
          window.setFullScreen(!window.isFullScreen());
        } else {
          glfwSetWindowShouldClose(window.getWindow(), true);
        }
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

  private void close() {
    window.destroy();
    mesh.destroy();
    shader.destroy();
  }

  public static void main(String[] args) {
    new Main().start();
  }

}