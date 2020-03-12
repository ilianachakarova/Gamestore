package softuni.gamestore.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import softuni.gamestore.domain.entities.Game;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface GameRepository extends JpaRepository<Game, Long> {
    Optional<Game> findGameById(long id);
    List<Game>findAll();
    Game findGameByTitle(String title);

    @Modifying
    @Query("update Game g set g.title =:titleParam")
    int updateGameSetTitle(@Param("titleParam") String titleParam);


    @Modifying
    @Query("update Game g set g.price =:priceParam")
    int updateGameSetPrice(@Param("priceParam") BigDecimal priceParam);

    @Modifying
    @Query("update Game g set g.imageThumbnail =:imageThumbnail")
    int updateGameSetImageThumbnail(@Param("imageThumbnail") String imageThumbnail);

    @Modifying
    @Query("update Game g set g.trailer =:trailer")
    int updateGameSetTrailer(@Param("trailer") String trailer);

    @Modifying
    @Query("update Game g set g.size =:size")
    int updateGameSetSize(@Param("size") double size);

    @Modifying
    @Query("update Game g set g.description =:description")
    int updateGameSetDescription(@Param("description") String description);

    @Modifying
    @Query("update Game g set g.releaseDate =:releaseDate")
    int updateGameSetReleaseDate(@Param("releaseDate") LocalDate releaseDate);

}