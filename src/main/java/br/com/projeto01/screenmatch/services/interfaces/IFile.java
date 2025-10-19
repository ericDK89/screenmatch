package br.com.projeto01.screenmatch.services.interfaces;

import java.io.IOException;

public interface IFile {

  void write(String filename, String data) throws IOException;
}
