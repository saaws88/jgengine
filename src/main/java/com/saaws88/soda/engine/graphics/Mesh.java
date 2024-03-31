package com.saaws88.soda.engine.graphics;

import lombok.Getter;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import org.lwjgl.system.MemoryUtil;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import static org.lwjgl.opengl.GL15.glDeleteBuffers;
import static org.lwjgl.opengl.GL30.glDeleteVertexArrays;

@Getter
public class Mesh {

  private Vertex[] vertices;
  private int[] indices;

  private Material material;

  private int vertexArrayObject;
  private int positionBufferObject;
  private int indexBufferObject;
  private int colorBufferObject;
  private int textureBufferObject;

  public Mesh(Vertex[] vertices, int[] indices, Material material) {

    this.vertices = vertices;
    this.indices = indices;
    this.material = material;

  }


  public void create() {

    material.create();

    vertexArrayObject = GL30.glGenVertexArrays();
    GL30.glBindVertexArray(vertexArrayObject);

    FloatBuffer positionBuffer = MemoryUtil.memAllocFloat(vertices.length * 3);
    float[] positionData = new float[vertices.length * 3];

    for (int i = 0; i < vertices.length; i++) {
      positionData[i * 3] = vertices[i].getPosition().getX();
      positionData[i * 3 + 1] = vertices[i].getPosition().getY();
      positionData[i * 3 + 2] = vertices[i].getPosition().getZ();
    }
    positionBuffer.put(positionData).flip();

    positionBufferObject = storeData(positionBuffer, 0, 3);

    FloatBuffer colorBuffer = MemoryUtil.memAllocFloat(vertices.length * 3);
    float[] colorData = new float[vertices.length * 3];

    for (int i = 0; i < vertices.length; i++) {
      colorData[i * 3] = vertices[i].getColor().getX();
      colorData[i * 3 + 1] = vertices[i].getColor().getY();
      colorData[i * 3 + 2] = vertices[i].getColor().getZ();
    }
    colorBuffer.put(colorData).flip();
    
    colorBufferObject = storeData(colorBuffer, 1, 3);

    FloatBuffer textureBuffer = MemoryUtil.memAllocFloat(vertices.length * 2);
    float[] textureData = new float[vertices.length * 2];

    for (int i = 0; i < vertices.length; i++) {
      textureData[i * 2] = vertices[i].getTexture().getX();
      textureData[i * 2 + 1] = vertices[i].getTexture().getY();
    }
    textureBuffer.put(textureData).flip();

    textureBufferObject = storeData(colorBuffer, 2, 2);

    IntBuffer indicesBuffer = MemoryUtil.memAllocInt(indices.length);
    indicesBuffer.put(indices).flip();


    indexBufferObject = GL15.glGenBuffers();
    GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, indexBufferObject);
    GL15.glBufferData(GL15.GL_ELEMENT_ARRAY_BUFFER, indicesBuffer, GL15.GL_STATIC_DRAW);
    GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, 0);

  }

  private int storeData(FloatBuffer buffer, int index, int size) {

    int bufferID = GL15.glGenBuffers();
    GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, bufferID);
    GL15.glBufferData(GL15.GL_ARRAY_BUFFER, buffer, GL15.GL_STATIC_DRAW);
    GL20.glVertexAttribPointer(index, size, GL11.GL_FLOAT, false, 0, 0);
    GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);

    return bufferID;
  }

  public void destroy() {

    glDeleteBuffers(positionBufferObject);
    glDeleteBuffers(colorBufferObject);
    glDeleteBuffers(indexBufferObject);
    glDeleteBuffers(textureBufferObject);

    glDeleteVertexArrays(vertexArrayObject);

    material.destroy();

  }

}
