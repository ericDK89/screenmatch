package br.com.projeto01.screenmatch.services;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import org.springframework.stereotype.Service;

@Service
public class GsonConvertData implements IConvertData {
  private final Gson gson = new Gson();

  public <T> T fromJson(String json, Class<T> clazz) {
    try {
      return gson.fromJson(json, clazz);
    } catch (JsonSyntaxException e) {
      throw new RuntimeException(e);
    }
  }
}
