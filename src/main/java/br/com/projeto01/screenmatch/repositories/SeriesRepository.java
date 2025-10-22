package br.com.projeto01.screenmatch.repositories;

import br.com.projeto01.screenmatch.model.Series;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface SeriesRepository extends JpaRepository<Series, UUID> {

  @Query("SELECT DISTINCT s FROM Series s LEFT JOIN FETCH s.episodes")
  List<Series> findAllWithEpisodes();

  @Query("SELECT s FROM Series s " +
      "LEFT JOIN FETCH s.episodes e " +
      "WHERE UPPER(s.title) LIKE UPPER(%:title%) " +
      "ORDER BY s.title, e.number")
  Optional<Series> findByTitleContainingIgnoreCase(String title);

  @Query("select s from Series s " +
      "LEFT JOIN FETCH s.episodes e " +
      "where array_contains(s.actors, :name)")
  List<Series> findByActorsContainingIgnoreCase(@Param("name") String name);
}
