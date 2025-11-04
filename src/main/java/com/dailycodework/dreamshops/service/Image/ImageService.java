package com.dailycodework.dreamshops.service.Image;

import com.dailycodework.dreamshops.Exception.ResourceeNotFoundException;
import com.dailycodework.dreamshops.Responsitory.ImageRepository;
import com.dailycodework.dreamshops.dao.ImageDto;
import com.dailycodework.dreamshops.entity.Image;
import com.dailycodework.dreamshops.entity.Product;
import com.dailycodework.dreamshops.service.Product.IProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.sql.rowset.serial.SerialBlob;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ImageService implements IimageService{
        private final ImageRepository imageRepository;
        private final IProductService productService;
        @Override
        public Image getImageById(Long id) {
                return imageRepository.findById(id)
                        .orElseThrow((()->new ResourceeNotFoundException("not found image")));
        }

        @Override
        public void deleteImage(Long id) {
        imageRepository.findById(id).ifPresentOrElse(imageRepository::delete,
                ()->new ResourceeNotFoundException("not found Image"));
        }

        @Override
        public List<ImageDto> saveImage(Long productId, List<MultipartFile> files) {
                Product product=productService.getProductById(productId);
                List<ImageDto>savedImageDto=new ArrayList<>();
                for (MultipartFile file :files){
                        try{
                                Image image=new Image();
                                image.setFileName(file.getOriginalFilename());
                                image.setTypeName(file.getContentType());
                                image.setImage(new SerialBlob(file.getBytes()));
                                image.setProduct(product);
                                Image saveImage=imageRepository.save(image);

                                ImageDto imageDto=new ImageDto();
                                imageDto.setId(saveImage.getId());
                                imageDto.setFileName(saveImage.getFileName());
                                imageDto.setDownloadUrl("/api/v1/images/image/download/"+saveImage.getId());
                                savedImageDto.add(imageDto);

                        }catch (IOException| SQLException e){
                                throw new RuntimeException("ERROR saving image"+e.getMessage());
                        }
                }
                return savedImageDto;
        }

        @Override
        public void updateImage(Long id, MultipartFile file) {
                Image image=getImageById(id);
                try{
                        image.setFileName(file.getOriginalFilename());
                        image.setTypeName(file.getContentType());
                        image.setImage(new SerialBlob(file.getBytes()));
                        imageRepository.save(image);
                }catch (IOException|SQLException e){
                        throw  new RuntimeException(e.getMessage());
                }

        }
}
