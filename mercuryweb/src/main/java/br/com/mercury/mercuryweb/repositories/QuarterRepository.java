package br.com.mercury.mercuryweb.repositories;

import br.com.mercury.mercuryweb.models.Quarter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QuarterRepository extends JpaRepository<Quarter, Long> {
}
