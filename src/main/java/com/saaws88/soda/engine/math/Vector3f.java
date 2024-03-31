package com.saaws88.soda.engine.math;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class Vector3f {

  private float x, y, z;

  public void setCoords(float x, float y, float z) {

    this.x = x;
    this.y = y;
    this.z = z;

  }

}
