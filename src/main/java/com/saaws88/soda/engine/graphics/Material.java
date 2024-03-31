package com.saaws88.soda.engine.graphics;

import lombok.Getter;
import lombok.Setter;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;

import java.io.IOException;

import static org.lwjgl.opengl.GL11.GL_LINEAR;
import static org.lwjgl.opengl.GL11.glDeleteTextures;

@Getter
@Setter
public class Material {

  private static int FILE_NAME_INDEX = 0;
  private static int FILE_EXTENSION_INDEX = 1;


  private Texture texture;
  private float width, height;
  private int textureID;

  public Material(String path) {
    try {
      texture =
          TextureLoader.getTexture
              (path.split("[.]")[FILE_EXTENSION_INDEX],
              Material.class.getClassLoader().getResourceAsStream(path),
              GL_LINEAR);
    } catch (IOException e) {
      System.err.println("Can't find texture at " + path);
    }
  }

  public void create() {

    width = texture.getWidth();
    height = texture.getHeight();
    textureID = texture.getTextureID();

  }

  public void destroy() {

    glDeleteTextures(textureID);

  }
}
