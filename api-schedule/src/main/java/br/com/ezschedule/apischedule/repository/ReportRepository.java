package br.com.ezschedule.apischedule.repository;

import br.com.ezschedule.apischedule.model.DtoClasses.ReportPaymentsDto;
import br.com.ezschedule.apischedule.model.Report;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReportRepository extends JpaRepository<Report, Integer> {
    @Query(
            "SELECT new br.com.ezschedule.apischedule.model.DtoClasses.ReportPaymentsDto" +
            "(r.id, r.productName, r.category, r.paymentStatus, r.paymentTime, s.tenant.name, s.tenant.residentsBlock, s.tenant.apartmentNumber, " +
            "s.tenant.phoneNumber, s.dateEvent, sa.saloonName, sa.saloonPrice, sa.saloonBlock) " +
            "FROM Report r " +
            "JOIN r.schedule s " +
            "JOIN s.tenant t " +
            "JOIN s.saloon sa " +
            "WHERE sa.condominium.id = :id"
    )
    List<ReportPaymentsDto> findAllReportsCondominium(int id);


    @Query(
            "SELECT new br.com.ezschedule.apischedule.model.DtoClasses.ReportPaymentsDto" +
            "(r.id, r.productName, r.category, r.paymentStatus, r.paymentTime, s.tenant.name, s.tenant.residentsBlock, s.tenant.apartmentNumber, " +
            "s.tenant.phoneNumber, s.dateEvent, sa.saloonName, sa.saloonPrice, sa.saloonBlock) " +
            "FROM Report r " +
            "JOIN r.schedule s " +
            "JOIN s.tenant t " +
            "JOIN s.saloon sa " +
            "WHERE t.id = :id"
    )
    List<ReportPaymentsDto> findAllReportsByTenant(int id);


    @Query("select r from Report r join Tenant t on r.tenant.id = t.id where payment_status = 'Não Pago'")
    List<Report> findAllCondominiumReportsWNoPayment(int id);

    @Query("Select count(c.id) from Condominium c where c.id = :id")
    Integer verifyIfCondominiumExists(int id);

    @Query("Select count(t.id) from Tenant t where t.id = :id")
    Integer verifyIfTenantexists(int id);
}
