package br.com.ezschedule.apischedule.controller;

import br.com.ezschedule.apischedule.Csv.CsvTenant;
import br.com.ezschedule.apischedule.Csv.ListaObj;
import br.com.ezschedule.apischedule.adapter.JsonResponseAdapter;
import br.com.ezschedule.apischedule.email.SendMail;
import br.com.ezschedule.apischedule.messages.EmailMessages;
import br.com.ezschedule.apischedule.model.DtoClasses.Response.TenantResponse;
import br.com.ezschedule.apischedule.model.DtoClasses.UpdateResponse.UpdateTenantForm;
import br.com.ezschedule.apischedule.model.Tenant;
import br.com.ezschedule.apischedule.model.DtoClasses.UpdateResponse.UpdatePasswordForm;
import br.com.ezschedule.apischedule.repository.TenantRepository;
import br.com.ezschedule.apischedule.service.TenantService;
import br.com.ezschedule.apischedule.service.autenticacao.dto.UsuarioLoginDto;
import br.com.ezschedule.apischedule.service.autenticacao.dto.UsuarioTokenDto;
import com.azure.core.http.rest.Response;
import com.azure.core.util.BinaryData;
import com.azure.storage.blob.BlobClient;
import com.azure.storage.blob.BlobContainerClient;
import com.azure.storage.blob.BlobContainerClientBuilder;
import com.azure.storage.blob.models.BlockBlobItem;
import com.azure.storage.blob.options.BlobParallelUploadOptions;
import io.swagger.annotations.Api;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.*;

@Api(value = "Condômino", produces = MediaType.APPLICATION_JSON_VALUE, tags = {"condomino"}, description = "requisições relacionadas a condônimo")
@RestController
@RequestMapping("/users")
public class TenantController {

    @Autowired
    private TenantRepository tenantRepository;

    @Autowired
    private TenantService tenantService;
    @Autowired
    private SendMail sendMail;
    List<Tenant> listUsers = new ArrayList<>();
    private String token = "";

    //Show all user's
    @ApiResponse(responseCode = "204", description =
            "Não há usuários cadastrados.", content = @Content(schema = @Schema(hidden = true)))
    @ApiResponse(responseCode = "200", description = "usuários encontrados.")
    @GetMapping
    public ResponseEntity<List<TenantResponse>> showAllUsers() {
        List<Tenant> users = this.tenantRepository.findAll();
        if (users.isEmpty()) {
            return ResponseEntity.status(204).build();
        } else {
            return ResponseEntity.status(200).body(JsonResponseAdapter.listTenantResponse(users));
        }

    }

    //Register new user
    @ApiResponse(responseCode = "201", description =
            "Usuário cadastrado", content = @Content(schema = @Schema(hidden = true)))
    @PostMapping
    public ResponseEntity<Void> register(@RequestBody Tenant newUser) {
        this.tenantService.criar(newUser);
        return ResponseEntity.status(201).build();
    }

    //login for user
    @ApiResponse(responseCode = "404", description =
            "senha ou email incorretos", content = @Content(schema = @Schema(hidden = true)))
    @ApiResponse(responseCode = "200", description = "usuário logado.")
    @PostMapping("/login")
    public ResponseEntity<UsuarioTokenDto> login(@RequestBody UsuarioLoginDto usuarioLoginDto) {
        UsuarioTokenDto usuarioTokenDto = this.tenantService.autenticar(usuarioLoginDto);
        return ResponseEntity.status(200).body(usuarioTokenDto);
    }

    //Delete user by id
    @ApiResponse(responseCode = "404", description =
            "Usuário não encontrado.", content = @Content(schema = @Schema(hidden = true)))
    @ApiResponse(responseCode = "200", description = "usuário deletado com sucesso.")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> removeById(@PathVariable Integer id) {
        if (tenantRepository.existsById(id)) {
            this.tenantRepository.deleteById(id);
            return ResponseEntity.status(204).build();
        }
        return ResponseEntity.status(404).build();
    }

    //logout
    @ApiResponse(responseCode = "200", description =
            "Logout realizado", content = @Content(schema = @Schema(hidden = true)))
    @PostMapping("/logout/{email}")
    public ResponseEntity<Void> logout(@PathVariable String email) {
        Object user = this.tenantRepository.logoutUser(email);
        if (user.equals(1)) {
            return ResponseEntity.status(200).build();
        } else {
            return ResponseEntity.status(401).build();
        }
    }

    @ApiResponse(responseCode = "404", description =
            "senhas iguais", content = @Content(schema = @Schema(hidden = true)))
    @ApiResponse(responseCode = "200", description = "senha atualizada")
    @PutMapping
    public ResponseEntity<Object> updatePassword(@RequestBody UpdatePasswordForm updatePasswordForm) {
        if (updatePasswordForm.getPassword().equals(updatePasswordForm.getNewPassword())) {
            return ResponseEntity.status(404).build();
        } else {
            Object user = this.tenantRepository.updatePasswordUser(updatePasswordForm.getEmail(), updatePasswordForm.getNewPassword());
            return ResponseEntity.status(200).build();
        }
    }

    @GetMapping("/recovery-password/{email}")
    public ResponseEntity<Void> recoveryPassword(@PathVariable String email) {

        Optional<Tenant> tenant = tenantRepository.findByEmail(email);

        if (tenant.isPresent()) {

            this.token = UUID.randomUUID().toString().replace("-", "");
            this.sendMail.send(email, EmailMessages.createTitle(tenant.get()), EmailMessages.messageRecoveryPassword(tenant.get(), this.token));

            return ResponseEntity.status(200).build();
        }
        return ResponseEntity.status(404).build();
    }

    @GetMapping("/input-token/{tokenInput}")
    public ResponseEntity<Void> insertingToken(@PathVariable String tokenInput) {
        if (tokenInput.equals(this.token)) {
            token = "";
            return ResponseEntity.status(200).build();
        }

        return ResponseEntity.status(404).build();

    }

    @PutMapping("/update-tenant")
    public ResponseEntity<TenantResponse> updateTenantInformation(@RequestParam int id, @RequestBody UpdateTenantForm newTenant) {
        Optional<Tenant> oldTenant = tenantRepository.findById(id);
        if (oldTenant.isPresent()) {
            Tenant t = oldTenant.get();
            Tenant updatedTenant = new Tenant(
                    id,
                    newTenant.getEmail(),
                    newTenant.getCpf(),
                    t.getPassword(),
                    newTenant.getName(),
                    newTenant.getResidentsBlock(),
                    newTenant.getApartmentNumber(),
                    newTenant.getPhoneNumber(),
                    t.isAdmin(),
                    t.getReportList(),
                    t.getScheduleList(),
                    t.getCondominium()
            );
            Tenant tenant = tenantRepository.save(updatedTenant);
            return ResponseEntity.status(200).body(JsonResponseAdapter.tenantResponse(tenant));
        }
        return ResponseEntity.status(204).build();
    }

    @GetMapping("/gerar-csv-tenants")
    public ResponseEntity<byte[]> generatorCsv() {
        List<Tenant> tenants = tenantRepository.findAll();
        if (!tenants.isEmpty()) {
            ListaObj<Tenant> tenantsReturn = new ListaObj<Tenant>(tenants.size());
            for (Tenant tenant : tenants) {
                tenantsReturn.adiciona(tenant);
            }
            CsvTenant.gravaArquivoCsvTenant(tenantsReturn, "Tenants");
            return CsvTenant.buscarArquivo("Tenants");
        }
        return ResponseEntity.status(404).build();
    }

    @CrossOrigin("*")
    @PostMapping("/save-image/{idUser}")
    public String uploadImage(@PathVariable int idUser, @RequestParam MultipartFile image) throws IOException {

        byte[] bytes = image.getBytes();
        if (bytes.length == 0) {
            throw new IOException("Imagen not content blob");
        }

        Optional<Tenant> tenant = tenantRepository.findById(idUser);

        if (tenant.isEmpty()) {
            throw new RuntimeException("User not content, id: " + idUser);
        }

        String constr = "DefaultEndpointsProtocol=https;" +
                "AccountName=ezscheduleusersimages;" +
                "AccountKey=eRxvjOGc3dgv3c/RmOON9btEOLFBq3VxsFcNuwKHoZD9wpzjPhPza4M28jSA+yHls1qvcYVETS5b+AStDiQKtQ==;" +
                "EndpointSuffix=core.windows.net";

        BlobContainerClient container = new BlobContainerClientBuilder()
                .connectionString(constr)
                .containerName("ezschedules")
                .buildClient();

        String fileName = LocalDateTime.now() + image.getOriginalFilename();

        BlobClient blob = container.getBlobClient(fileName);

        Response<BlockBlobItem> response =
                blob.uploadWithResponse(
                        new BlobParallelUploadOptions(new ByteArrayInputStream(bytes), bytes.length),
                        Duration.ofHours(5),
                        null);

        if (response.getStatusCode() != 201) {
            throw new IOException("request failed");
        }

        tenant.get().setNameBlobImage(fileName);

        tenantRepository.save(tenant.get());

        return "ok";
    }

    @GetMapping("/get-image/{idUser}")
    public ResponseEntity<byte[]> getImage(@PathVariable int idUser) throws IOException {

        Optional<Tenant> tenant = tenantRepository.findById(idUser);

        String blobName = tenant.get().getNameBlobImage();

        String constr = "DefaultEndpointsProtocol=https;" +
                "AccountName=ezscheduleusersimages;" +
                "AccountKey=eRxvjOGc3dgv3c/RmOON9btEOLFBq3VxsFcNuwKHoZD9wpzjPhPza4M28jSA+yHls1qvcYVETS5b+AStDiQKtQ==;" +
                "EndpointSuffix=core.windows.net";

        BlobContainerClient container = new BlobContainerClientBuilder()
                .connectionString(constr)
                .containerName("ezschedules")
                .buildClient();

        BlobClient blob = container.getBlobClient(blobName);

        BinaryData binary = blob.downloadContent();
        byte[] byteImage = binary.toBytes();

        return ResponseEntity.status(200).body(byteImage);

    }

}
