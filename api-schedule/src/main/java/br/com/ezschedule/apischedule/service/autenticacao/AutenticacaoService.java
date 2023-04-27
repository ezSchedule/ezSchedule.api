package br.com.ezschedule.apischedule.service.autenticacao;

import br.com.ezschedule.apischedule.model.Tenant;
import br.com.ezschedule.apischedule.repository.TenantRepository;
import br.com.ezschedule.apischedule.service.autenticacao.dto.UsuarioDetalhesDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


import java.util.Optional;

@Service
public class AutenticacaoService implements UserDetailsService {

  @Autowired
  private TenantRepository tenantRepository;

  // Método da interface implementada
  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

    Optional<Tenant> usuarioOpt = tenantRepository.findByEmail(username);

    if (usuarioOpt.isEmpty()) {

      throw new UsernameNotFoundException(String.format("usuario: %s nao encontrado", username));
    }

    return new UsuarioDetalhesDto(usuarioOpt.get());
  }
}
