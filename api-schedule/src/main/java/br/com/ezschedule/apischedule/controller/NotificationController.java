package br.com.ezschedule.apischedule.controller;

import br.com.ezschedule.apischedule.model.UserNotification;
import br.com.ezschedule.apischedule.service.NotificationService;
import io.swagger.annotations.Api;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;

@Api(value = "Notification", produces = MediaType.APPLICATION_JSON_VALUE, tags = {"notification"}, description = "requisições relacionadas as notificações")
@RestController
@RequestMapping("${uri.notification}")
public class NotificationController {

    @Autowired
    NotificationService notificationService;

    @ApiResponse(responseCode = "201", description = "notificação enviada", content = @Content(schema = @Schema(hidden = true)))
    @PostMapping
    public ResponseEntity<Void> newNotification(@RequestBody @Valid UserNotification post) {
        return notificationService.send(post);
    }
}
