package com.dailycodework.dreamshops.service.Image;

import com.dailycodework.dreamshops.dao.ImageDto;
import com.dailycodework.dreamshops.entity.Image;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface IimageService {
    Image getImageById(Long id);
    void deleteImage(Long id);
    List<ImageDto> saveImage(Long productId,List<MultipartFile>files);
    void updateImage(Long id, MultipartFile file);

}
