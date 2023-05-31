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

    @Query(value = "SELECT t FROM Tenant t WHERE t.condominium.id = :id")
    List<Tenant> findAllTenantsCondominium(int id);

    @Transactional
    @Modifying
    @Query(value = "UPDATE Tenant t SET t.isAuthenticated = 1 WHERE t.email = :email")
    void userAuthenticated(String email);

    @Transactional
    @Modifying
    @Query(value = "UPDATE Tenant t SET t.isAuthenticated = 0 WHERE t.email = :email")
    Integer logoutUser(String email);

    @Transactional
    @Modifying
    @Query(value = "UPDATE Tenant t SET t.password = :newPassword WHERE t.email = :email")
    Object updatePasswordUser(String email, String newPassword);

    @Query("Update Tenant t set t.subscribed = :isSubscribed where t.id = :id")
    void subscribeOrUnsubTenant(Integer isSubscribed,int id);

    @Query("Select t from Tenant t where t.subscribed = 1 and t.condominium.id = :id")
    List<Tenant> findSubscribedTenants(int id);


    //Existe pelo email
    Boolean existsByEmail(String email);

    @Query("Select distinct(t.condominium.id) from Tenant t ")
    List<Integer> findAllCondominiumIdFromTenants();

    Optional<Tenant> findByEmail(String email);
}
