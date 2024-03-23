package com.saaws88.jgengine.engine.graphics;

import com.saaws88.jgengine.engine.math.Vector3f;

public class Vertex {

  private Vector3f position;

  public Vertex(Vector3f position) {
    this.position = position;
  }

  public Vector3f getPosition() {
    return position;
  }

}
