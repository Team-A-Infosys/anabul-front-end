package team.kucing.anabulshopcare.resources.rest;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import team.kucing.anabulshopcare.exception.ResourceNotFoundException;
import team.kucing.anabulshopcare.service.uploadimg.ImageProductService;
import team.kucing.anabulshopcare.service.uploadimg.UserAvatarService;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@RestController
@AllArgsConstructor
@Tag(name = "06. File Controller")
public class FileController {
    private ImageProductService imageProductService;

    private UserAvatarService userAvatarService;

    @GetMapping("/images-prod/{fileName:.+}")
    public ResponseEntity<Resource> fileProduct(@PathVariable String fileName, HttpServletRequest request) {
        return getResourceResponseEntity(request, imageProductService.loadFileAsResource(fileName), fileName);
    }

    @GetMapping("/images-ava/{fileName:.+}")
    public ResponseEntity<Resource> fileUser(@PathVariable String fileName, HttpServletRequest request) {
        return getResourceResponseEntity(request, userAvatarService.loadFileAsResource(fileName), fileName);
    }

    private ResponseEntity<Resource> getResourceResponseEntity(HttpServletRequest request, Resource resource2, @PathVariable String fileName) {
        Resource resource = resource2;

        String contentType;
        try {
            contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
        } catch (IOException ex) {
            throw new ResourceNotFoundException("File not found");
        }

        if(contentType == null) {
            contentType = "application/octet-stream";
        }

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
    }
}