package br.com.projeto01.screenmatch.repositories;

import br.com.projeto01.screenmatch.model.Series;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SeriesRepository extends JpaRepository<Series, UUID> {

}
