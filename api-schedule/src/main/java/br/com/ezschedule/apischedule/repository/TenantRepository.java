package br.com.ezschedule.apischedule.repository;

import br.com.ezschedule.apischedule.model.Tenant;
import br.com.ezschedule.apischedule.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@Repository
public interface TenantRepository extends JpaRepository<Tenant, Integer> {

    @Transactional
    @Modifying
    @Query(value = "UPDATE Tenant SET is_authenticated = 1 WHERE email = ?1", nativeQuery = true)
    void userAuthenticated(String email);

    @Transactional
    @Modifying
    @Query(value = "UPDATE Tenant SET is_authenticated = 0 WHERE email = ?1 ", nativeQuery = true)
    Tenant logoutUser(String email);

    @Transactional
    @Modifying
    @Query(value = "UPDATE Tenant SET password = :newPassword WHERE email = :email")
    Object updatePasswordUser(String email, String newPassword);



    //Existe pelo email
    Boolean existsByEmail(String email);


//    Tenant findTop1Bysubscribed(boolean b);

    Optional<Tenant> findByEmail(String email);
}
