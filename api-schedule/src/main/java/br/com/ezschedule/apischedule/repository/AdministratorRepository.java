package br.com.ezschedule.apischedule.repository;

import br.com.ezschedule.apischedule.model.Administrator;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Repository
public interface AdministratorRepository extends JpaRepository<Administrator, Integer> {
    List<Administrator> findAll();

    @Transactional
    @Modifying
    @Query(value = "UPDATE tenant SET is_authenticated = 0 WHERE email = ?1 ", nativeQuery = true)
    Administrator logoutUser(String email);

    @Transactional
    @Modifying
    @Query(value = "UPDATE tenant SET password = ?3 WHERE email = ?1 AND password = ?2 ", nativeQuery = true)
    Administrator updatePasswordUser(String email, String password, String newPassword);


    Optional<Administrator> findByEmail(String email);
}
