package br.com.mytennis.mytennis.repository;

import br.com.mytennis.mytennis.model.TennisModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TennisRepository extends JpaRepository<TennisModel, Integer> {

    public Page<TennisModel> findByNameContainsIgnoreCaseOrderByName(String name, Pageable pageable);

}
