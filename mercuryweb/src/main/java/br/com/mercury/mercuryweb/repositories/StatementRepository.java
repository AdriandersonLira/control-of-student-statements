package br.com.mercury.mercuryweb.repositories;

import br.com.mercury.mercuryweb.models.Statement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StatementRepository extends JpaRepository<Statement, Long> {
}
