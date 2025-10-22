package br.com.projeto01.screenmatch.services;

import br.com.projeto01.screenmatch.services.interfaces.IConverData;
import br.com.projeto01.screenmatch.utils.GsonTypeAdapter;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;
import java.time.LocalDate;
import org.springframework.stereotype.Service;

@Service
public class GsonConvertData implements IConverData {

  public <T> T fromJson(String json, Class<T> clazz) {
    try {
      Gson gson = new GsonBuilder().registerTypeAdapter(LocalDate.class, new GsonTypeAdapter())
          .create();
      return gson.fromJson(json, clazz);
    } catch (JsonSyntaxException e) {
      throw new RuntimeException(e);
    }
  }

  public String toJson(Object data) {
    try {
      Gson gson = new GsonBuilder().registerTypeAdapter(LocalDate.class, new GsonTypeAdapter())
          .setPrettyPrinting().create();
      return gson.toJson(data);
    } catch (JsonSyntaxException e) {
      throw new RuntimeException(e);
    }
  }
}
