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

    @Query(value = "SELECT id_user, email, cpf, name, residents_block, apartment_number, phone_number, is_authenticated, is_admin  FROM Client", nativeQuery = true)
    List<Object> listUserAdministrator();

    @Transactional
    @Modifying
    @Query(value = "UPDATE Client SET is_authenticated = 1 WHERE email = ?1 AND password = ?2 ", nativeQuery = true)
    Object userAuthenticated(String email, String password);

    @Transactional
    @Modifying
    @Query(value = "UPDATE Client SET is_authenticated = 0 WHERE email = ?1 ", nativeQuery = true)
    Object logoutUser(String email);

    @Transactional
    @Modifying
    @Query(value = "UPDATE Client SET password = ?3 WHERE email = ?1 AND password = ?2 ", nativeQuery = true)
    Object updatePasswordUser(String email, String password, String newPassword);


    Optional<Administrator> findByEmail(String email);
}
