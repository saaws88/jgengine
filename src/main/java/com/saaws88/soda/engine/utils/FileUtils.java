package com.saaws88.soda.engine.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class FileUtils {

  public static String loadAsString(String path) {
    StringBuilder result = new StringBuilder();
    try {
      BufferedReader br = new BufferedReader(new InputStreamReader(FileUtils.class.getClassLoader().getResourceAsStream(path)));
      String line;

      while ((line = br.readLine()) != null) {
        result.append(line + "\n");
      }
    } catch(IOException e) {
      System.err.println("Couldn't find file at " + path);
    }
    return result.toString();
  }
}
