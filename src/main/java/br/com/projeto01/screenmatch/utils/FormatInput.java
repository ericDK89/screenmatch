package br.com.projeto01.screenmatch.utils;

import org.springframework.stereotype.Service;

@Service
public class FormatInput {

  public String execute(String str) {
    return str.replace(" ", "+");
  }
}
