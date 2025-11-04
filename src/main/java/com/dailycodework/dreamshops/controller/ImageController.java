package com.dailycodework.dreamshops.controller;

import com.dailycodework.dreamshops.Exception.ResourceeNotFoundException;
import com.dailycodework.dreamshops.Response.ApiResponse;
import com.dailycodework.dreamshops.dao.ImageDto;
import com.dailycodework.dreamshops.entity.Image;
import com.dailycodework.dreamshops.service.Image.IimageService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.sql.SQLException;
import java.util.List;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@RestController
@RequiredArgsConstructor
@RequestMapping("${api.prefix}/images")
public class ImageController {
    public final IimageService iimageService;

    @GetMapping("/image/download/{id}")
    public ResponseEntity<Resource> downloadImage(@PathVariable Long id) throws SQLException {
        Image image = iimageService.getImageById(id);

        // Lấy dữ liệu ảnh từ BLOB
        ByteArrayResource resource = new ByteArrayResource(
                image.getImage().getBytes(1, (int) image.getImage().length())
        );

        // Trả file cho client tải về
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(image.getTypeName()))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + image.getFileName() + "\"")
                .body(resource);
    }


    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ApiResponse>deleteImage(@PathVariable Long id){
        try {
            Image image =iimageService.getImageById(id);
            if(image !=null){
                iimageService.deleteImage(id);
                return ResponseEntity.ok(new ApiResponse("OK",null));
            }
        }catch (ResourceeNotFoundException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(e.getMessage(),null));
        }return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse("Delete feu  ",null));
    }
    @PostMapping("/upload")
    public ResponseEntity<ApiResponse>savedImage(@RequestParam Long id, @RequestParam List<MultipartFile> files){
        try {
            List<ImageDto> images=iimageService.saveImage(id,files);
            return ResponseEntity.ok(new ApiResponse("OK",images));
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse(e.getMessage(),null));
        }
    }
    @PutMapping("/image/{id}/update")
    public ResponseEntity<ApiResponse> updateImage(@PathVariable Long id, @RequestParam("file") MultipartFile file) {
        try {
            Image image = iimageService.getImageById(id);
            if(image != null) {
                iimageService.updateImage( id,file);
                return ResponseEntity.ok(new ApiResponse("Update success!", null));
            }
        } catch (ResourceeNotFoundException e) {
            return  ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
        return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse("Update failed!", INTERNAL_SERVER_ERROR));
    }

}
