package br.com.projeto01.screenmatch.repositories;

import br.com.projeto01.screenmatch.model.Episode;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EpisodeRepository extends JpaRepository<Episode, UUID> {

}
