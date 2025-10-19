package br.com.projeto01.screenmatch.services.interfaces;

public interface IConverData {

  <T> T fromJson(String json, Class<T> clazz);

  String toJson(Object data);

}
