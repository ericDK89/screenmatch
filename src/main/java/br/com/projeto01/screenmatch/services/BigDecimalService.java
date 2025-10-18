package br.com.projeto01.screenmatch.services;

import java.math.BigDecimal;
import java.math.RoundingMode;
import org.springframework.stereotype.Service;

@Service
public class BigDecimalService {

  public double round(double value) {
    BigDecimal bd = new BigDecimal(value);
    bd = bd.setScale(2, RoundingMode.CEILING);
    return bd.doubleValue();
  }
}
