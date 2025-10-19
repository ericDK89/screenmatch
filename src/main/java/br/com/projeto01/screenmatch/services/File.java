package br.com.projeto01.screenmatch.services;

import br.com.projeto01.screenmatch.services.interfaces.IFile;
import java.io.FileWriter;
import java.io.IOException;
import org.springframework.stereotype.Service;

@Service
public class File implements IFile {

  @Override
  public void write(String filename, String data) throws IOException {
    try (FileWriter writer = new FileWriter(filename)) {
      writer.write(data);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }
}
