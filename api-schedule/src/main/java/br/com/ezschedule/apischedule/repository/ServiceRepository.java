package br.com.ezschedule.apischedule.repository;

import br.com.ezschedule.apischedule.model.DtoClasses.Response.TenantResponse;
import br.com.ezschedule.apischedule.model.Service;
import br.com.ezschedule.apischedule.model.Tenant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;
import java.util.List;

public interface ServiceRepository extends JpaRepository<Service, Integer> {

    List<Service> findAllByServiceName(String name);

    @Modifying
    @Transactional
    @Query(value = "UPDATE service SET service_name = :serviceName where id = :id", nativeQuery = true)
    void updateService(int id, String serviceName);


    @Query("SELECT s FROM Service s WHERE s.tenant.condominium.id = :id")
    List<Service> listService(int id);

}
