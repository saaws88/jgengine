package com.saaws88.soda.engine.graphics;

import com.saaws88.soda.engine.utils.FileUtils;

import static org.lwjgl.opengl.GL11C.GL_FALSE;
import static org.lwjgl.opengl.GL20.GL_COMPILE_STATUS;
import static org.lwjgl.opengl.GL20.GL_FRAGMENT_SHADER;
import static org.lwjgl.opengl.GL20.GL_LINK_STATUS;
import static org.lwjgl.opengl.GL20.GL_VALIDATE_STATUS;
import static org.lwjgl.opengl.GL20.GL_VERTEX_SHADER;
import static org.lwjgl.opengl.GL20.glAttachShader;
import static org.lwjgl.opengl.GL20.glCompileShader;
import static org.lwjgl.opengl.GL20.glCreateShader;
import static org.lwjgl.opengl.GL20.glCreateProgram;
import static org.lwjgl.opengl.GL20.glDeleteProgram;
import static org.lwjgl.opengl.GL20.glDeleteShader;
import static org.lwjgl.opengl.GL20.glGetProgramInfoLog;
import static org.lwjgl.opengl.GL20.glGetProgrami;
import static org.lwjgl.opengl.GL20.glGetShaderInfoLog;
import static org.lwjgl.opengl.GL20.glGetShaderi;
import static org.lwjgl.opengl.GL20.glLinkProgram;
import static org.lwjgl.opengl.GL20.glShaderSource;
import static org.lwjgl.opengl.GL20.glUseProgram;
import static org.lwjgl.opengl.GL20.glValidateProgram;

public class Shader {
  private String vertexShaderFile, fragmentShaderFile;
  private int vertexID, fragmentID, programID;
  public Shader(String vertexShaderFilePath, String fragmentShaderFilePath) {
    vertexShaderFile = FileUtils.loadAsString(vertexShaderFilePath);
    fragmentShaderFile = FileUtils.loadAsString(fragmentShaderFilePath);
  }

  public void create() {

    programID = glCreateProgram();

    vertexID = glCreateShader(GL_VERTEX_SHADER);

    glShaderSource(vertexID, vertexShaderFile);
    glCompileShader(vertexID);

    if (glGetShaderi(vertexID, GL_COMPILE_STATUS) == GL_FALSE) {
      System.err.println("Fragment Shader: " + glGetShaderInfoLog(vertexID));
      return;
    }

    fragmentID = glCreateShader(GL_FRAGMENT_SHADER);

    glShaderSource(fragmentID, fragmentShaderFile);
    glCompileShader(fragmentID);

    if (glGetShaderi(fragmentID, GL_COMPILE_STATUS) == GL_FALSE) {
      System.err.println("Fragment Shader: " + glGetShaderInfoLog(fragmentID));
      return;
    }

    glAttachShader(programID, vertexID);
    glAttachShader(programID, fragmentID);

    glLinkProgram(programID);
    if(glGetProgrami(programID, GL_LINK_STATUS) == GL_FALSE) {
      System.err.println("Program Linking: " + glGetProgramInfoLog(programID));
      return;
    }

    glValidateProgram(programID);
    if(glGetProgrami(programID, GL_VALIDATE_STATUS) == GL_FALSE) {
      System.err.println("Program Validation: " + glGetProgramInfoLog(programID));
      return;
    }

    glDeleteShader(vertexID);
    glDeleteShader(fragmentID);

  }

  public void bind() {
    glUseProgram(programID);
  }

  public void unbind() {
    glUseProgram(0);
  }

  public void destroy() {
    glDeleteProgram(programID);
  }

}
