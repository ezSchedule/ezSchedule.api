package br.com.ezschedule.apischedule.adapter;

import br.com.ezschedule.apischedule.model.*;
import br.com.ezschedule.apischedule.model.DtoClasses.*;
import br.com.ezschedule.apischedule.model.DtoClasses.CondominiumResponse.CondominiumResponseDto;
import br.com.ezschedule.apischedule.model.DtoClasses.CondominiumResponse.ForumCondoDTO;
import br.com.ezschedule.apischedule.model.DtoClasses.CondominiumResponse.ReportCondoDTO;
import br.com.ezschedule.apischedule.model.DtoClasses.CondominiumResponse.SaloonCondoDTO;
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

    public static UsuarioTokenDto tenantWTokenDTO(Tenant t,String token){
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
                token
        );
    }

    public static TenantResponse tentantDTO(Tenant t){
        return new TenantResponse(
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
    public static List<TenantResponse> listTenantDTO(List<Tenant> t){
        return t.stream().map(JsonResponseAdapter::tentantDTO).toList();
    }

    public static ServiceDTO serviceDTO(Service s){
        return  new ServiceDTO(
                s.getServiceName(),
                JsonResponseAdapter.tentantDTO(s.getTenant())
        );
    }

    public static List<ServiceDTO> serviceArrayDTO(int size , ObjectList<Service> serviceVector){
        List<ServiceDTO> serviceDTOList = new ArrayList<>();
        for(int i =0; i < size;i++){
            Service currentService = serviceVector.getByIndex(i);

            serviceDTOList.add(new ServiceDTO(
                            currentService.getServiceName(),
                            JsonResponseAdapter.tentantDTO(currentService.getTenant())
                    )
            );
        }
        return serviceDTOList;
    }

    public static ForumPost updateForumDTO(UpdateForumPostForm newPost,int id, ForumPost oldPost){
        return new ForumPost(
                id,
                newPost.getTextContent(),
                newPost.getTypeMessage(),
                oldPost.getDateTimePost(),
                oldPost.getCondominium()
        );
    }

    public static ForumDTO forumDTO(ForumPost f){
        return new ForumDTO(
                f.getId(),
                f.getTextContent(),
                f.getTypeMessage(),
                f.getDateTimePost(),
                f.isEdited(),
                condominiumDTO(f.getCondominium())
        );
    }

    public static List<ForumDTO> listForumDTO(List<ForumPost> f){
        return f.stream().map(JsonResponseAdapter::forumDTO).toList();
    }

    public static ForumPost updateForumDTO(UpdateForumPostForm f, int id, LocalDateTime time){
        return new ForumPost(
                id,
                f.getTextContent(),
                f.getTypeMessage(),
                time,
                true);
    }

    public static ForumCondoDTO forumCondoDTO(ForumPost f){
         return new ForumCondoDTO(
                 f.getId(),
                 f.getTextContent(),
                 f.getTypeMessage(),
                 f.getDateTimePost(),
                 f.isEdited()
         );
    }

    public static List<ForumCondoDTO> listForumCondoDTO(List<ForumPost> f){
        return f.stream().map(JsonResponseAdapter::forumCondoDTO).toList();
    }

    public static SaloonDTO saloonDTO(Saloon s){
        return new SaloonDTO(
                s.getId(),
                s.getSaloonName(),
                s.getSaloonPrice(),
                s.getSaloonBlock(),
                s.getSchedule(),
                condominiumDTO(s.getCondominium())
        );
    }

    public static List<SaloonDTO> listSaloonDTO(List<Saloon> s){
        return s.stream().map(JsonResponseAdapter::saloonDTO).toList();
    }

    public static Schedule scheduleDTO(UpdateScheduleForm u,int id,List<Saloon> saloonList,Tenant t){
        return new Schedule(
                id,
                u.getNameEvent(),
                u.getTypeEvent(),
                u.getDateEvent(),
                u.getTotalNumberGuests(),
                saloonList,
                t
        );
    }

    public static ReportDTO reportDTO(Report r){
        return new ReportDTO(
                r.getId(),
                r.getInvoiceNumber(),
                r.getProductName(),
                r.getCategory(),
                r.getPaymentStatus(),
                r.getPaymentTime(),
                r.getSchedule(),
                condominiumDTO(r.getCondominium()),
                r.getTenant() != null ?tentantDTO(r.getTenant()) : null
        );
    }

    public static List<ReportDTO> listReportDTO(List<Report> r){
        return r.stream().map(JsonResponseAdapter::reportDTO).toList();
    }

    public static SaloonCondoDTO saloonCondoDTO(Saloon s){
        return new SaloonCondoDTO(
                s.getId(),
                s.getSaloonName(),
                s.getSaloonPrice(),
                s.getSaloonBlock(),
                s.getSchedule()
        );
    }

    public static List<SaloonCondoDTO> listSaloonCondoDTO(List<Saloon> s){
        return s.stream().map(JsonResponseAdapter::saloonCondoDTO).toList();
    }

    public static ReportCondoDTO reportCondoDTO(Report r){
        return new ReportCondoDTO(
                r.getId(),
                r.getInvoiceNumber(),
                r.getProductName(),
                r.getCategory(),
                r.getPaymentStatus(),
                r.getPaymentTime(),
                r.getSchedule(),
                r.getTenant()
        );
    }

    public static List<ReportCondoDTO> listReportCondoDTO(List<Report> r){
        return r.stream().map(JsonResponseAdapter::reportCondoDTO).toList();
    }

    public static CondominiumDto condominiumDTO(Condominium c){
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

    public static CondominiumResponseDto condominumResponseDTO(Condominium c){
        return new CondominiumResponseDto(
                c.getId(),
                c.getName(),
                c.getStreet(),
                c.getCity(),
                c.getState(),
                c.getPostalCode(),
                c.getCountry(),
                listTenantDTO(c.getTenantList()),
                listSaloonCondoDTO(c.getSaloonList()),
                listForumCondoDTO(c.getForumPostList()),
                listReportCondoDTO(c.getReportList())
        );
    }

    public static List<CondominiumResponseDto> listCondominumDTO(List<Condominium> c){
        return c.stream().map(JsonResponseAdapter::condominumResponseDTO).toList();
    }

}
