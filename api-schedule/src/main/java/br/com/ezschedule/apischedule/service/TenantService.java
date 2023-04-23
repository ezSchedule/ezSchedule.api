package br.com.ezschedule.apischedule.service;

import br.com.ezschedule.apischedule.adapter.JsonResponseAdapter;
import br.com.ezschedule.apischedule.model.Tenant;
import br.com.ezschedule.apischedule.repository.TenantRepository;
import br.com.ezschedule.apischedule.security.jwt.GerenciadorTokenJwt;
import br.com.ezschedule.apischedule.service.autenticacao.dto.UsuarioLoginDto;
import br.com.ezschedule.apischedule.service.autenticacao.dto.UsuarioTokenDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

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

  public void criar(Tenant t) {

    String senhaCriptografada = passwordEncoder.encode(t.getPassword());
    t.setPassword(senhaCriptografada);

    this.tenantRepository.save(t);
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

    return JsonResponseAdapter.tenantWTokenDTO(usuarioAutenticado,token);
  }
}