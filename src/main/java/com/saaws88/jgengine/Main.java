package com.saaws88.jgengine;

public class Main implements Runnable {

  public Thread game;

  public void start() {
    game = new Thread(this, "game");
    game.start();
  }

  public static void init() {
    System.out.println("Initializing game");
  }

  public void run() {
    init();
    while (true) {
      update();
      render();
    }
  }

  private void update() {
    System.out.println("Updating game!");
  }

  private void render() {
    System.out.println("Rendering game!");
  }

  public static void main(String[] args) {
    new Main().start();
  }
}