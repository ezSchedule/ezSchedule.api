package br.com.ezschedule.apischedule.repository;

import br.com.ezschedule.apischedule.model.Tenant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Repository
public interface TenantRepository extends JpaRepository<Tenant, Integer> {

    @Query(value = "SELECT id_user, email, cpf, name, residents_block, apartment_number, phone_number, is_authenticated, is_admin  FROM Tenant", nativeQuery = true)
    List<Object> listUserTenant();

    @Transactional
    @Modifying
    @Query(value = "UPDATE Tenant SET is_authenticated = 1 WHERE email = ?1 AND password = ?2 ", nativeQuery = true)
    Object userAuthenticated(String email, String password);

    @Transactional
    @Modifying
    @Query(value = "UPDATE Tenant SET is_authenticated = 0 WHERE email = ?1 ", nativeQuery = true)
    Object logoutUser(String email);

    @Transactional
    @Modifying
    @Query(value = "UPDATE Tenant SET password = ?3 WHERE email = ?1 AND password = ?2 ", nativeQuery = true)
    Object updatePasswordUser(String email, String password, String newPassword);



    //Existe pelo email
    Boolean existsByEmail(String email);


    Optional<Tenant> findByEmail(String email);
}
