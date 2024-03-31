package com.saaws88.soda.engine.graphics;

import com.saaws88.soda.engine.math.Vector3f;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.lwjglx.util.vector.Vector2f;

@AllArgsConstructor
@Getter
public class Vertex {

  private Vector3f position, color;
  private Vector2f texture;

}
