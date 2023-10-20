package br.com.ezschedule.apischedule.service.autenticacao;

import br.com.ezschedule.apischedule.adapter.JsonResponseAdapter;
import br.com.ezschedule.apischedule.email.SendMail;
import br.com.ezschedule.apischedule.messages.EmailMessages;
import br.com.ezschedule.apischedule.model.DtoClasses.Response.TenantResponse;
import br.com.ezschedule.apischedule.model.DtoClasses.UpdateResponse.UpdateTenantForm;
import br.com.ezschedule.apischedule.model.Schedule;
import br.com.ezschedule.apischedule.model.Tenant;
import br.com.ezschedule.apischedule.repository.TenantRepository;
import br.com.ezschedule.apischedule.security.jwt.GerenciadorTokenJwt;
import br.com.ezschedule.apischedule.service.autenticacao.dto.UsuarioLoginDto;
import br.com.ezschedule.apischedule.service.autenticacao.dto.UsuarioTokenDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class TenantService {

  @Autowired
  private PasswordEncoder passwordEncoder;

  @Autowired
  private TenantRepository tenantRepository;

  @Autowired
  private GerenciadorTokenJwt gerenciadorTokenJwt;

  @Autowired
  private AuthenticationManager authenticationManager;

  @Autowired
  private SendMail sendMail;

  private String token = "";

  public void criar(Tenant t) {

    String senhaCriptografada = passwordEncoder.encode(t.getPassword());
    t.setPassword(senhaCriptografada);

    this.tenantRepository.save(t);
  }

  public ResponseEntity<TenantResponse> findById(int id){
    Optional<Tenant> tenant = tenantRepository.findById(id);
    if(tenant.isPresent()){
      if(!tenant.get().getNameBlobImage().isBlank() && tenant.get().getNameBlobImage() != null){
        tenant.get().setNameBlobImage(tenant.get().getNameBlobImage());
      }

      return ResponseEntity.status(200).body(JsonResponseAdapter.tenantResponse(tenant.get()));
    }
    return ResponseEntity.status(404).build();
  }

  public String encryptPassword(String password) {
    return passwordEncoder.encode(password);
  }

  public UsuarioTokenDto autenticar(UsuarioLoginDto usuarioLoginDto) {

    final UsernamePasswordAuthenticationToken credentials = new UsernamePasswordAuthenticationToken(
            usuarioLoginDto.getEmail(), usuarioLoginDto.getPassword());

    final Authentication authentication = this.authenticationManager.authenticate(credentials);

    Tenant usuarioAutenticado =
            tenantRepository.findByEmail(usuarioLoginDto.getEmail())
                    .orElseThrow(
                            () -> new ResponseStatusException(404, "Email do usuário não cadastrado", null)
                    );

    SecurityContextHolder.getContext().setAuthentication(authentication);

    final String token = gerenciadorTokenJwt.generateToken(authentication);

    tenantRepository.userAuthenticated(usuarioLoginDto.getEmail());

    return JsonResponseAdapter.tenantWTokenDTO(usuarioAutenticado,token);
  }

  public List<Tenant> listAllTenant() {
    return tenantRepository.findAll();
  }

  public Integer logoutTenant(String email) {
    return this.tenantRepository.logoutUser(email);
  }

  public Object updatePasswordTenant(String email, String password) {
    return this.tenantRepository.updatePasswordUser(email, password);
  }

  public ResponseEntity<Void> passwordRecover(String email) {

    Optional<Tenant> tenant = tenantRepository.findByEmail(email);

    if (tenant.isPresent()){
      this.token = UUID.randomUUID().toString().replace("-", "");
      this.sendMail.send(email, EmailMessages.createTitle(tenant.get()), EmailMessages.messageRecoveryPassword(tenant.get(), this.token));
      return ResponseEntity.status(200).build();
    }

    return ResponseEntity.status(404).build();
  }

  public Boolean removeTenantById(Integer id) {

    if (tenantRepository.existsById(id)) {
      this.tenantRepository.deleteById(id);
      return true;
    }
    return false;

  }

//  public TenantResponse tenantUpdateInformation(Integer id, UpdateTenantForm newTenant) {
//
//    Optional<Tenant> oldTenant = tenantRepository.findById(id);
//    if (oldTenant.isPresent()) {
//      Tenant t = oldTenant.get();
//      Tenant updatedTenant = new Tenant(
//              id,
//              newTenant.getEmail(),
//              newTenant.getCpf(),
//              t.getPassword(),
//              newTenant.getName(),
//              newTenant.getResidentsBlock(),
//              newTenant.getApartmentNumber(),
//              newTenant.getPhoneNumber(),
//              t.getIsAdmin(),
//              t.getReportList(),
//              t.getScheduleList(),
//              t.getCondominium()
//      );
//      Tenant tenant = tenantRepository.save(updatedTenant);
//      return JsonResponseAdapter.tenantResponse(tenant);
//    }
//    return null;
//
//  }
}