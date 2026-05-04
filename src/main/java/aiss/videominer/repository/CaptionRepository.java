package aiss.videominer.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import aiss.videominer.model.Caption;

@Repository
public interface CaptionRepository extends JpaRepository<Caption, String> {
    Page<Caption> findByLanguage(String language, Pageable pageable);
    List<Caption> findByLanguage(String language);


}