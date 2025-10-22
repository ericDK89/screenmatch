package br.com.projeto01.screenmatch.utils;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class GsonTypeAdapter extends TypeAdapter<LocalDate> {

  private final DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE_TIME;

  @Override
  public void write(JsonWriter jsonWriter, LocalDate localDate) throws IOException {
    if (jsonWriter == null) {
      jsonWriter.nullValue();
    } else {
      jsonWriter.value(formatter.format(localDate));
    }
  }

  @Override
  public LocalDate read(JsonReader jsonReader) throws IOException {
    if (jsonReader.peek() == null) {
      return null;
    }
    String dateString = jsonReader.nextString();
    return LocalDate.parse(dateString, formatter);
  }
}
