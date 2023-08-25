package org.project.dev.review.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.project.dev.review.entity.ReviewEntity;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class FileDto {

    private Long id;

    private String fileOldName;

    private String fileNewName;

    private ReviewEntity reviewEntity;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;

}
