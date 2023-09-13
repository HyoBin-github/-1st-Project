package org.project.dev.member.service;

import org.project.dev.member.dto.ImageResponseDto;
import org.project.dev.member.dto.ImageUploadDto;
import org.springframework.stereotype.Service;

public interface ImageService {

    void upload(ImageUploadDto imageUploadDto, String memberEmail);

    void deleteImage(String memberEmail);
    ImageResponseDto findImage(String memberEmail);
}
