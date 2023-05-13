package br.com.ezschedule.apischedule.adapter;

import br.com.ezschedule.apischedule.model.*;
import br.com.ezschedule.apischedule.model.DtoClasses.*;
import br.com.ezschedule.apischedule.model.DtoClasses.Response.*;
import br.com.ezschedule.apischedule.model.DtoClasses.UpdateResponse.UpdateForumPostForm;
import br.com.ezschedule.apischedule.model.DtoClasses.UpdateResponse.UpdateScheduleForm;
import br.com.ezschedule.apischedule.service.autenticacao.dto.UsuarioTokenDto;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public interface JsonResponseAdapter {

    public static JsonResponse Dto(Tenant c) {
        return new JsonResponse(
                c.getEmail(),
                c.getCpf(),
                c.getName(),
                c.getResidentsBlock(),
                c.getApartmentNumber(),
                c.getPhoneNumber(),
                c.isAuthenticated(),
                c.isAdmin());

    }

    public static UsuarioTokenDto tenantWTokenDTO(Tenant t, String token) {
        return new UsuarioTokenDto(
                t.getIdUser(),
                t.getEmail(),
                t.getCpf(),
                t.getName(),
                t.getPassword(),
                t.getResidentsBlock(),
                t.getApartmentNumber(),
                t.getPhoneNumber(),
                t.isAuthenticated(),
                t.isAdmin(),
                token,
                t.getCondominium().getId()
        );
    }

    public static TenantDTO tentantDTO(Tenant t) {
        if(t == null){
            return null;
        }
        return new TenantDTO(
                t.getIdUser(),
                t.getEmail(),
                t.getCpf(),
                t.getName(),
                t.getResidentsBlock(),
                t.getApartmentNumber(),
                t.getPhoneNumber(),
                t.isAuthenticated(),
                t.isAdmin());
    }

    public static List<TenantDTO> listTenantDTO(List<Tenant> t) {
        if (t == null) {
            return null;
        }
        return t.stream().map(JsonResponseAdapter::tentantDTO).toList();
    }

    public static TenantResponse tenantResponse(Tenant t){
        if(t == null){
            return null;
        }
        return new TenantResponse(
                t.getIdUser(),
                t.getEmail(),
                t.getCpf(),
                t.getName(),
                t.getResidentsBlock(),
                t.getApartmentNumber(),
                t.getPhoneNumber(),
                t.isAuthenticated(),
                t.isAdmin(),
                listReportDTO(t.getReportList()),
                listScheduleDTO(t.getScheduleList()),
               condominiumDTO(t.getCondominium())
        );
    }

    public static List<TenantResponse> listTenantResponse(List<Tenant> t){
       return t.stream().map(JsonResponseAdapter::tenantResponse).toList();
    }

    public static ServiceDTO serviceDTO(Service s) {
        return new ServiceDTO(
                s.getServiceName(),
                tentantDTO(s.getTenant())
        );
    }

    public static List<ServiceDTO> serviceArrayDTO(int size, ObjectList<Service> serviceVector) {
        List<ServiceDTO> serviceDTOList = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            Service currentService = serviceVector.getByIndex(i);

            serviceDTOList.add(new ServiceDTO(
                            currentService.getServiceName(),
                            tentantDTO(currentService.getTenant())
                    )
            );
        }
        return serviceDTOList;
    }

    public static ForumPost updateForumDTO(UpdateForumPostForm newPost, int id, ForumPost oldPost) {
        return new ForumPost(
                id,
                newPost.getTextContent(),
                newPost.getTypeMessage(),
                oldPost.getDateTimePost(),
                oldPost.getCondominium()
        );
    }

    public static ForumResponse forumResponse(ForumPost f) {
        return new ForumResponse(
                f.getId(),
                f.getTextContent(),
                f.getTypeMessage(),
                f.getDateTimePost(),
                f.isEdited(),
                condominiumDTO(f.getCondominium())
        );
    }

    public static List<ForumResponse> listForumResponse(List<ForumPost> f) {
        return f.stream().map(JsonResponseAdapter::forumResponse).toList();
    }

    public static ForumPost updateForumDTO(UpdateForumPostForm f, int id, LocalDateTime time) {
        return new ForumPost(
                id,
                f.getTextContent(),
                f.getTypeMessage(),
                time,
                true);
    }

    public static ForumDTO forumDto(ForumPost f) {
        return new ForumDTO(
                f.getId(),
                f.getTextContent(),
                f.getTypeMessage(),
                f.getDateTimePost(),
                f.isEdited()
        );
    }

    public static List<ForumDTO> listForumDTO(List<ForumPost> f) {
        if (f == null) {
            return null;
        }
        return f.stream().map(JsonResponseAdapter::forumDto).toList();
    }

    public static SaloonResponse saloonResponse(Saloon s) {
        return new SaloonResponse(
                s.getId(),
                s.getSaloonName(),
                s.getSaloonPrice(),
                s.getSaloonBlock(),
                listScheduleDTO(s.getSchedule()),
                condominiumDTO(s.getCondominium())
        );
    }

    public static List<SaloonResponse> listSaloonResponse(List<Saloon> s) {
        return s.stream().map(JsonResponseAdapter::saloonResponse).toList();
    }

    public static ScheduleDTO scheduleDTO(Schedule s) {
        return new ScheduleDTO(
                s.getId(),
                s.getNameEvent(),
                s.getTypeEvent(),
                s.getDateEvent(),
                s.getTotalNumberGuests()
        );
    }

    public static ScheduleResponse scheduleResponse(Schedule s) {
        return new ScheduleResponse(
                s.getId(),
                s.getNameEvent(),
                s.getTypeEvent(),
                s.getDateEvent(),
                s.getTotalNumberGuests(),
                saloonDTO(s.getSaloon()),
                tentantDTO(s.getTenant())
        );
    }

    public static List<ScheduleResponse> listScheduleResponse(List<Schedule> s) {
        if (s == null) {
            return null;
        }

        return s.stream().map(JsonResponseAdapter::scheduleResponse).toList();
    }

    public static List<ScheduleDTO> listScheduleDTO(List<Schedule> s) {
        if (s == null) {
            return null;
        }

        return s.stream().map(JsonResponseAdapter::scheduleDTO).toList();
    }


    public static Schedule updateScheduleDTO(UpdateScheduleForm u, int id, Saloon saloon, Tenant t) {
        return new Schedule(
                id,
                u.getNameEvent(),
                u.getTypeEvent(),
                u.getDateEvent(),
                u.getTotalNumberGuests(),
                saloon,
                t
        );
    }

    public static ReportResponse reportResponse(Report r) {
        return new ReportResponse(
                r.getId(),
                r.getInvoiceNumber(),
                r.getProductName(),
                r.getCategory(),
                r.getPaymentStatus(),
                r.getPaymentTime(),
                r.getSchedule(),
                condominiumDTO(r.getCondominium()),
                tentantDTO(r.getTenant())
        );
    }

    public static List<ReportResponse> listReportResponse(List<Report> r) {
        return r.stream().map(JsonResponseAdapter::reportResponse).toList();
    }

    public static SaloonDTO saloonDTO(Saloon s) {
        return new SaloonDTO(
                s.getId(),
                s.getSaloonName(),
                s.getSaloonPrice(),
                s.getSaloonBlock()
        );
    }

    public static List<SaloonDTO> listSaloonDTO(List<Saloon> s) {
        if (s == null) {
            return null;
        }
        return s.stream().map(JsonResponseAdapter::saloonDTO).toList();
    }

    public static ReportDTO reportDTO(Report r) {
        return new ReportDTO(
                r.getId(),
                r.getInvoiceNumber(),
                r.getProductName(),
                r.getCategory(),
                r.getPaymentStatus(),
                r.getPaymentTime()
        );
    }

    public static List<ReportCondoDTO> listReportCondoDTO(List<Report> r){
        return r.stream().map(JsonResponseAdapter::reportCondoDTO).toList();
    }

    public static CondominiumDto condominiumDTO(Condominium c) {
        if (c == null) {
            return null;
        }
        return new CondominiumDto(
                c.getId(),
                c.getName(),
                c.getStreet(),
                c.getCity(),
                c.getState(),
                c.getPostalCode(),
                c.getCountry()
        );
    }

    public static CondominiumResponse condominumResponseDTO(Condominium c) {
        return new CondominiumResponse(
                c.getId(),
                c.getName(),
                c.getStreet(),
                c.getCity(),
                c.getState(),
                c.getPostalCode(),
                c.getCountry(),
                listTenantDTO(c.getTenantList()),
                listSaloonDTO(c.getSaloonList()),
                listForumDTO(c.getForumPostList()),
                listReportDTO(c.getReportList())
        );
    }

    public static List<CondominiumResponse> listCondominumDTO(List<Condominium> c) {
        return c.stream().map(JsonResponseAdapter::condominumResponseDTO).toList();
    }

}
