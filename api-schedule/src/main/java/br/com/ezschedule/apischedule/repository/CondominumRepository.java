package br.com.ezschedule.apischedule.repository;

import br.com.ezschedule.apischedule.model.Condominium;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface CondominumRepository extends JpaRepository<Condominium,Integer> {
    @Query("SELECT COUNT(t) FROM Tenant t WHERE t.condominium.id = :idCondominium")
    Integer amountTenantsCondominium(Integer idCondominium);

    @Query("SELECT COUNT(DISTINCT t.apartmentNumber) FROM Tenant t WHERE t.condominium.id = :idCondominium")
    Integer amountApartmentsCondominium(Integer idCondominium);

    @Query("SELECT COUNT(s) FROM Saloon s WHERE s.condominium.id = :idCondominium")
    Integer amountSaloonsCondominium(Integer idCondominium);
}
