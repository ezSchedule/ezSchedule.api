package br.com.ezschedule.apischedule.controller;

import br.com.ezschedule.apischedule.csv.CsvForTenant;
import br.com.ezschedule.apischedule.csv.ListObject;
import br.com.ezschedule.apischedule.adapter.JsonResponseAdapter;
import br.com.ezschedule.apischedule.email.SendMail;
import br.com.ezschedule.apischedule.messages.EmailMessages;
import br.com.ezschedule.apischedule.model.Condominium;
import br.com.ezschedule.apischedule.model.DtoClasses.CreateTenant.CreateTenant;
import br.com.ezschedule.apischedule.model.DtoClasses.Response.TenantResponse;
import br.com.ezschedule.apischedule.model.DtoClasses.UpdateResponse.UpdateTenantForm;
import br.com.ezschedule.apischedule.model.Tenant;
import br.com.ezschedule.apischedule.model.DtoClasses.UpdateResponse.UpdatePasswordForm;
import br.com.ezschedule.apischedule.observer.RowObject;
import br.com.ezschedule.apischedule.observer.PileObject;
import br.com.ezschedule.apischedule.repository.ServiceRepository;
import br.com.ezschedule.apischedule.repository.TenantRepository;
import br.com.ezschedule.apischedule.service.autenticacao.TenantService;
import br.com.ezschedule.apischedule.service.autenticacao.dto.UsuarioLoginDto;
import br.com.ezschedule.apischedule.service.autenticacao.dto.UsuarioTokenDto;
import br.com.ezschedule.apischedule.txt.Txt;
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
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

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
    @Autowired
    private ServiceRepository serviceRepository;
    @Autowired
    private Txt txt;
    List<Tenant> listUsers = new ArrayList<>();
    private String token = "";

    @Autowired
    private PasswordEncoder passwordEncoder;

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
    public ResponseEntity<Void> register(@ModelAttribute CreateTenant newUser) throws IOException {

        Integer idCondominium = DecryptToken(newUser.getCondominium());

        if (idCondominium != null) {
            Tenant t = JsonResponseAdapter.tenantWImg(newUser, new Condominium(idCondominium));

            this.tenantService.criar(t);

            if (newUser.getNameBlobImage() != null && newUser.getNameBlobImage().getSize() > 0) {
                uploadImage(t.getId(), newUser.getNameBlobImage());
            }
            return ResponseEntity.status(201).build();
        }
        return ResponseEntity.status(404).build();
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

    @PutMapping("/forgot-password")
    public ResponseEntity<Object> forgotPassword(@RequestBody UpdatePasswordForm updatePasswordForm) {
        Optional<Tenant> tenant = this.tenantRepository.findByEmail(updatePasswordForm.getEmail());
        if (tenant.isPresent()) {
            tenant.get().setPassword(this.tenantService.encryptPassword(updatePasswordForm.getPassword()));
            this.tenantRepository.save(tenant.get());
            return ResponseEntity.status(200).build();
        }
        return ResponseEntity.status(404).build();
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
    public ResponseEntity<TenantResponse> updateTenantInformation(@RequestParam int id, @ModelAttribute UpdateTenantForm newTenant) throws IOException {
        Optional<Tenant> oldTenant = tenantRepository.findById(id);
        if (oldTenant.isPresent()) {
            tenantRepository.save(JsonResponseAdapter.updateTenant(oldTenant.get(), newTenant));
            if (oldTenant.get().getNameBlobImage() != null) {
                if(newTenant.getImage() != null && newTenant.getImage().getSize() > 0) {
                    deleteImage(oldTenant.get().getId());
                    uploadImage(oldTenant.get().getId(), newTenant.getImage());
                }
            } else if (newTenant.getImage() != null && newTenant.getImage().getSize() > 0) {
                uploadImage(oldTenant.get().getId(), newTenant.getImage());
            }
            return ResponseEntity.status(200).body(JsonResponseAdapter.tenantResponse(oldTenant.get()));
        }
        return ResponseEntity.status(204).build();
    }

    @GetMapping("/generate-csv/{idCondominium}/{order}")
    public ResponseEntity<byte[]> generatorCsv(@PathVariable int idCondominium, @PathVariable String order) {
        List<Tenant> tenants = tenantRepository.findAllTenantsCondominium(idCondominium);

        if (!tenants.isEmpty()) {

            ListObject<Tenant> tenantsReturn = new ListObject<Tenant>(tenants.size());

            PileObject<Tenant> pileTenant = new PileObject<>(tenants.size());

            RowObject<Tenant> rowTenant = new RowObject<>(tenants.size());

            if(order.equals("desc")){
                for (Tenant tenant : tenants) {
                    tenantsReturn.add(tenant);
                }
            }

            if(order.equals("asc")){
                for (Tenant tenant : tenants) {
                    pileTenant.push(tenant);
                }

                while (!pileTenant.isEmpty()){
                    rowTenant.insert(pileTenant.pop());
                }

                while (!rowTenant.isEmpty()){
                    tenantsReturn.add(rowTenant.poll());
                }
            }

            CsvForTenant.saveArchiveCsv(tenantsReturn, "Tenants");
            return CsvForTenant.searchArchive("Tenants");
        }
        return ResponseEntity.status(404).build();
    }

    @CrossOrigin("*")
    @PostMapping("/save-image/{idUser}")
    public ResponseEntity<Boolean> uploadImage(@PathVariable int idUser, @RequestParam MultipartFile image) throws IOException {

        byte[] bytes = image.getBytes();
        if (bytes.length == 0) {
            throw new IOException("Imagen not content blob");
        }

        Optional<Tenant> tenant = tenantRepository.findById(idUser);

        if (tenant.isEmpty()) {
            throw new RuntimeException("User not content, id: " + idUser);
        }

        String fileName = LocalDateTime.now() + image.getOriginalFilename();

        String constr = "DefaultEndpointsProtocol=https;" +
                "AccountName=ezscheduleusersimages;" +
                "AccountKey=eRxvjOGc3dgv3c/RmOON9btEOLFBq3VxsFcNuwKHoZD9wpzjPhPza4M28jSA+yHls1qvcYVETS5b+AStDiQKtQ==;" +
                "EndpointSuffix=core.windows.net";

        BlobContainerClient container = new BlobContainerClientBuilder()
                .connectionString(constr)
                .containerName("ezschedules")
                .buildClient();

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

        return ResponseEntity.status(200).body(true);
    }

    @GetMapping("/get-image-blob/{idUser}")
    public ResponseEntity<byte[]> getImageBlob(@PathVariable int idUser) throws IOException {

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

    @GetMapping("/generate-token/{id}")
    public ResponseEntity<String> generateEncryptedToken(@PathVariable int id) {

        if (tenantRepository.existsById(id)) {

            String idCondominium = String.valueOf(tenantRepository.findById(id).get().getCondominium().getId());

            String encodedId = passwordEncoder.encode(idCondominium);
            return ResponseEntity.status(200).body(encodedId);
        }
        return ResponseEntity.status(404).build();
    }

    public Integer DecryptToken(String encodedId) {
        List<Integer> idList = tenantRepository.findAllCondominiumIdFromTenants();

        for (int i = 0; i < idList.size(); i++) {
            if (passwordEncoder.matches(String.valueOf(idList.get(i)), encodedId)) {
                return idList.get(i);
            }
        }
        return null;
    }

    @GetMapping("/get-image/{idUser}")
    public ResponseEntity<String> getImage(@PathVariable int idUser) {

        String pathBase = "https://ezscheduleusersimages.blob.core.windows.net/ezschedules/";

        Optional<Tenant> tenant = tenantRepository.findById(idUser);

        if (!tenant.isPresent()) {
            return ResponseEntity.status(404).body("User not found");
        }

        if (tenant.get().getNameBlobImage() == null || tenant.get().getNameBlobImage() == "") {
            return ResponseEntity.status(404).body("User not content image");
        }

        String blobName = tenant.get().getNameBlobImage();

        String constr = "DefaultEndpointsProtocol=https;" +
                "AccountName=ezscheduleusersimages;" +
                "AccountKey=eRxvjOGc3dgv3c/RmOON9btEOLFBq3VxsFcNuwKHoZD9wpzjPhPza4M28jSA+yHls1qvcYVETS5b+AStDiQKtQ==;" +
                "EndpointSuffix=core.windows.net";

        BlobContainerClient container = new BlobContainerClientBuilder()
                .connectionString(constr)
                .containerName("ezschedules")
                .buildClient();

        Optional<BlobClient> blob = Optional.of(container.getBlobClient(blobName));

        if (!blob.get().exists()) {
            return ResponseEntity.status(204).build();
        }

        String pathImage = pathBase + tenant.get().getNameBlobImage();

        return ResponseEntity.status(200).body(pathImage);
    }

    @PutMapping("/update-image/{idUser}")
    public ResponseEntity<String> updateImage(@PathVariable int idUser, @RequestParam MultipartFile image) throws IOException {

        Optional<Tenant> tenant = tenantRepository.findById(idUser);

        if (!tenant.isPresent()) {
            return ResponseEntity.status(404).body("User not found");
        }

        if (tenant.get().getNameBlobImage() == null || tenant.get().getNameBlobImage() == "") {
            return ResponseEntity.status(404).body("User not content image");
        }

        if (image.isEmpty()) {
            return ResponseEntity.status(404).build();
        }

        String nameBlobOriginal = tenant.get().getNameBlobImage();

        String constr = "DefaultEndpointsProtocol=https;" +
                "AccountName=ezscheduleusersimages;" +
                "AccountKey=eRxvjOGc3dgv3c/RmOON9btEOLFBq3VxsFcNuwKHoZD9wpzjPhPza4M28jSA+yHls1qvcYVETS5b+AStDiQKtQ==;" +
                "EndpointSuffix=core.windows.net";

        BlobContainerClient container = new BlobContainerClientBuilder()
                .connectionString(constr)
                .containerName("ezschedules")
                .buildClient();

        Optional<BlobClient> blob = Optional.of(container.getBlobClient(nameBlobOriginal));

        boolean delete = blob.get().deleteIfExists();

        if (delete) {

            String nameUpdate = LocalDateTime.now() + image.getOriginalFilename();

            byte[] imageNewBytes = image.getBytes();

            Optional<BlobClient> blobUpdate = Optional.of(container.getBlobClient(nameUpdate));

            Response<BlockBlobItem> response =
                    blobUpdate.get().uploadWithResponse(
                            new BlobParallelUploadOptions(new ByteArrayInputStream(imageNewBytes), imageNewBytes.length),
                            Duration.ofHours(5),
                            null);

            if (response.getStatusCode() != 201) {
                throw new IOException("Update failed");
            }

            tenant.get().setNameBlobImage(nameUpdate);

            tenantRepository.save(tenant.get());

            return ResponseEntity.status(200).body("Update success!");

        }

        return ResponseEntity.status(400).body("Update failed");

    }

    @DeleteMapping("/delete-image/{idUser}")
    public ResponseEntity<String> deleteImage(@PathVariable int idUser) {

        Optional<Tenant> tenant = tenantRepository.findById(idUser);

        if (!tenant.isPresent()) {
            return ResponseEntity.status(404).body("User not found");
        }

        if (tenant.get().getNameBlobImage() == null || tenant.get().getNameBlobImage() == "") {
            return ResponseEntity.status(404).body("User not content image");
        }

        String nameBlobOriginal = tenant.get().getNameBlobImage();

        String constr = "DefaultEndpointsProtocol=https;" +
                "AccountName=ezscheduleusersimages;" +
                "AccountKey=eRxvjOGc3dgv3c/RmOON9btEOLFBq3VxsFcNuwKHoZD9wpzjPhPza4M28jSA+yHls1qvcYVETS5b+AStDiQKtQ==;" +
                "EndpointSuffix=core.windows.net";

        BlobContainerClient container = new BlobContainerClientBuilder()
                .connectionString(constr)
                .containerName("ezschedules")
                .buildClient();

        Optional<BlobClient> blob = Optional.of(container.getBlobClient(nameBlobOriginal));

        boolean delete = blob.get().deleteIfExists();

        if (delete) {

            tenant.get().setNameBlobImage(null);

            tenantRepository.save(tenant.get());

            return ResponseEntity.status(200).body("Remove success");

        }

        return ResponseEntity.status(400).body("Remove falied");

    }

    @PostMapping("/import-txt")
    public ResponseEntity<Boolean> saveByTxt(@RequestParam MultipartFile file){

        String fileName = txt.save(file);

        boolean listService = txt.readTxt(fileName);

        return ResponseEntity.status(200).body(listService);
    }

    @GetMapping("/export-txt/{idCondominium}")
    public ResponseEntity<byte[]> exportTxt(@PathVariable int idCondominium){

        String name = txt.writeTxt(idCondominium);

        if(name == "not value in list services"){
            return ResponseEntity.status(204).build();
        }

        return txt.searchArchiveTxt(name);
    }

}
