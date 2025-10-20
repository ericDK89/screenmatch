package br.com.projeto01.screenmatch.repositories;

import br.com.projeto01.screenmatch.model.Series;
import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface SeriesRepository extends JpaRepository<Series, UUID> {

  @Query("SELECT DISTINCT s FROM Series s LEFT JOIN FETCH s.episodes")
  List<Series> findAllWithEpisodes();

  Series findByTitle(String title);
}
