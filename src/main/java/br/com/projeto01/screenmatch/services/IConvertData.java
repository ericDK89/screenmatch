package br.com.projeto01.screenmatch.services;

public interface IConvertData {
  <T> T fromJson(String json, Class<T> clazz);

  String toJson(Object data);
}
