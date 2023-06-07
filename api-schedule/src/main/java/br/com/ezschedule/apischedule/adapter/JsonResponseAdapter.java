package br.com.ezschedule.apischedule.adapter;

import br.com.ezschedule.apischedule.model.*;
import br.com.ezschedule.apischedule.model.DtoClasses.*;
import br.com.ezschedule.apischedule.model.DtoClasses.CreateTenant.CreateTenant;
import br.com.ezschedule.apischedule.model.DtoClasses.Response.*;
import br.com.ezschedule.apischedule.model.DtoClasses.UpdateResponse.UpdateForumPostForm;
import br.com.ezschedule.apischedule.model.DtoClasses.UpdateResponse.UpdateSaloonForm;
import br.com.ezschedule.apischedule.model.DtoClasses.UpdateResponse.UpdateScheduleForm;
import br.com.ezschedule.apischedule.model.DtoClasses.UpdateResponse.UpdateTenantForm;
import br.com.ezschedule.apischedule.service.autenticacao.dto.UsuarioTokenDto;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public interface JsonResponseAdapter {

    static Tenant tenantWImg(CreateTenant newUser, Condominium c) {
        return new Tenant(
                newUser.getEmail(),
                newUser.getCpf(),
                newUser.getPassword(),
                newUser.getName(),
                newUser.getResidentsBlock(),
                newUser.getApartmentNumber(),
                newUser.getPhoneNumber(),
                newUser.getIsAdmin(),
                newUser.getSubscribed(),
                newUser.getReportList(),
                newUser.getScheduleList(),
                c,
                newUser.getServices()

        );
    }

    static UsuarioTokenDto tenantWTokenDTO(Tenant t, String token) {
        return new UsuarioTokenDto(
                t.getId(),
                t.getEmail(),
                t.getCpf(),
                t.getName(),
                t.getPassword(),
                t.getResidentsBlock(),
                t.getApartmentNumber(),
                t.getPhoneNumber(),
                t.isAuthenticated(),
                t.getIsAdmin(),
                token,
                t.getCondominium().getId(),
                t.getNameBlobImage()
        );
    }

    static TenantDTO tenantDTO(Tenant t) {
        if (t == null) {
            return null;
        }
        return new TenantDTO(
                t.getId(),
                t.getEmail(),
                t.getCpf(),
                t.getName(),
                t.getResidentsBlock(),
                t.getApartmentNumber(),
                t.getPhoneNumber(),
                t.isAuthenticated(),
                t.getIsAdmin(),
                t.getNameBlobImage());
    }

    static List<TenantDTO> listTenantDTO(List<Tenant> t) {
        if (t == null) {
            return null;
        }
        return t.stream().map(JsonResponseAdapter::tenantDTO).toList();
    }

    static TenantResponse tenantResponse(Tenant t) {
        if (t == null) {
            return null;
        }
        return new TenantResponse(
                t.getId(),
                t.getEmail(),
                t.getCpf(),
                t.getName(),
                t.getResidentsBlock(),
                t.getApartmentNumber(),
                t.getPhoneNumber(),
                t.isAuthenticated(),
                t.getIsAdmin(),
                listReportDTO(t.getReportList()),
                listScheduleDTO(t.getScheduleList()),
                condominiumDTO(t.getCondominium()),
                listServiceDTO(t.getServices()),
                t.getNameBlobImage(),
                t.getSubscribed()
        );
    }

    static List<TenantResponse> listTenantResponse(List<Tenant> t) {
        return t.stream().map(JsonResponseAdapter::tenantResponse).toList();
    }

    static Tenant updateTenant(Tenant t, UpdateTenantForm u) {
        return new Tenant(
                t.getId(),
                u.getEmail(),
                u.getCpf(),
                t.getPassword(),
                u.getName(),
                u.getResidentsBlock(),
                u.getApartmentNumber(),
                u.getPhoneNumber(),
                t.getNameBlobImage(),
                t.getIsAdmin(),
                t.getReportList(),
                t.getScheduleList(),
                t.getCondominium(),
                t.getServices()
        );
    }

    static ServiceDTO serviceDTO(Service s) {
        return new ServiceDTO(
                s.getId(),
                s.getServiceName()
        );
    }

    static ServiceResponse serviceResponse(Service s) {
        return s != null ? new ServiceResponse(
                s.getId(),
                s.getServiceName(),
                tenantDTO(s.getTenant())
        ) : null;
    }

    static List<ServiceResponse> listServiceResponse(List<Service> services) {
        return services.stream().map(JsonResponseAdapter::serviceResponse).toList();
    }

    static List<ServiceDTO> serviceArrayDTO(int size, ObjectList<Service> serviceVector) {
        List<ServiceDTO> serviceDTOList = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            Service currentService = serviceVector.getByIndex(i);

            serviceDTOList.add(new ServiceDTO(
                            currentService.getId(),
                            currentService.getServiceName()
                    )
            );
        }
        return serviceDTOList;
    }

    static ForumPost updateForumDTO(UpdateForumPostForm newPost, int id, ForumPost oldPost) {
        return new ForumPost(
                id,
                newPost.getTextContent(),
                newPost.getTypeMessage(),
                oldPost.getDateTimePost(),
                oldPost.getCondominium()
        );
    }

    static ForumResponse forumResponse(ForumPost f) {
        return new ForumResponse(
                f.getId(),
                f.getTextContent(),
                f.getTypeMessage(),
                f.getDateTimePost().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")),
                f.isEdited(),
                condominiumDTO(f.getCondominium())
        );
    }

    static List<ForumResponse> listForumResponse(List<ForumPost> f) {
        return f.stream().map(JsonResponseAdapter::forumResponse).toList();
    }

    static ForumDTO forumDto(ForumPost f) {
        return new ForumDTO(
                f.getId(),
                f.getTextContent(),
                f.getTypeMessage(),
                f.getDateTimePost().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")),
                f.isEdited()
        );
    }

    static List<ForumDTO> listForumDTO(List<ForumPost> f) {
        if (f == null) {
            return null;
        }
        return f.stream().map(JsonResponseAdapter::forumDto).toList();
    }

    static SaloonResponse saloonResponse(Saloon s) {
        return new SaloonResponse(
                s.getId(),
                s.getSaloonName(),
                s.getSaloonPrice(),
                s.getSaloonBlock(),
                listScheduleDTO(s.getSchedule()),
                condominiumDTO(s.getCondominium())
        );
    }

    static List<SaloonResponse> listSaloonResponse(List<Saloon> s) {
        return s.stream().map(JsonResponseAdapter::saloonResponse).toList();
    }

    static ScheduleDTO scheduleDTO(Schedule s) {
        if(s == null) return null;
        return new ScheduleDTO(
                s.getId(),
                s.getNameEvent(),
                s.getTypeEvent(),
                s.getDateEvent().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")),
                s.getTotalNumberGuests()
        );
    }

    static ScheduleResponse scheduleResponse(Schedule s) {
        return new ScheduleResponse(
                s.getId(),
                s.getNameEvent(),
                s.getTypeEvent(),
                s.getDateEvent().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")),
                s.getIsCanceled(),
                s.getTotalNumberGuests(),
                saloonDTO(s.getSaloon()),
                tenantDTO(s.getTenant())
        );
    }

    static List<ScheduleResponse> listScheduleResponse(List<Schedule> s) {
        if (s == null) {
            return null;
        }

        return s.stream().map(JsonResponseAdapter::scheduleResponse).toList();
    }

    static List<ScheduleDTO> listScheduleDTO(List<Schedule> s) {
        if (s == null) {
            return null;
        }

        return s.stream().map(JsonResponseAdapter::scheduleDTO).toList();
    }

    static List<ServiceDTO> listServiceDTO(List<Service> s) {
        if (s == null) {
            return null;
        }

        return s.stream().map(JsonResponseAdapter::serviceDTO).toList();
    }

    static Schedule updateScheduleDTO(UpdateScheduleForm u, int id, Saloon saloon, Tenant t) {
        return new Schedule(
                id,
                u.getNameEvent(),
                u.getTypeEvent(),
                u.getIsCanceled(),
                u.getDateEvent(),
                u.getTotalNumberGuests(),
                saloon,
                t
        );
    }

    static ReportResponse reportResponse(Report r) {
        return new ReportResponse(
                r.getId(),
                r.getInvoiceNumber(),
                r.getProductName(),
                r.getCategory(),
                r.getPaymentStatus(),
                r.getPaymentTime() != null ? r.getPaymentTime().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")) : null,
                scheduleDTO(r.getSchedule()),
                condominiumDTO(r.getCondominium()),
                tenantDTO(r.getTenant())
        );
    }

    static List<ReportResponse> listReportResponse(List<Report> r) {
        return r.stream().map(JsonResponseAdapter::reportResponse).toList();
    }

    static SaloonDTO saloonDTO(Saloon s) {
        return new SaloonDTO(
                s.getId(),
                s.getSaloonName(),
                s.getSaloonPrice(),
                s.getSaloonBlock()
        );
    }

    static Saloon UpdateSaloon(UpdateSaloonForm u ,Saloon s){
        return new Saloon(
                s.getId(),
                u.getSaloonName(),
                u.getSaloonPrice(),
                u.getSaloonBlock(),
                s.getCondominium(),
                s.getSchedule()
        );
    }

    static List<SaloonDTO> listSaloonDTO(List<Saloon> s) {
        if (s == null) {
            return null;
        }
        return s.stream().map(JsonResponseAdapter::saloonDTO).toList();
    }

    static ReportDTO reportDTO(Report r) {
        return new ReportDTO(
                r.getId(),
                r.getInvoiceNumber(),
                r.getProductName(),
                r.getCategory(),
                r.getPaymentStatus(),
                r.getPaymentTime() != null ? r.getPaymentTime().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")) : null
        );
    }

    static List<ReportDTO> listReportDTO(List<Report> r) {
        if (r == null) {
            return null;
        }
        return r.stream().map(JsonResponseAdapter::reportDTO).toList();
    }

    static CondominiumDto condominiumDTO(Condominium c) {
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

    static CondominiumResponse condominiumResponseDTO(Condominium c) {
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

    static List<CondominiumResponse> listCondominiumDTO(List<Condominium> c) {
        return c.stream().map(JsonResponseAdapter::condominiumResponseDTO).toList();
    }

}
