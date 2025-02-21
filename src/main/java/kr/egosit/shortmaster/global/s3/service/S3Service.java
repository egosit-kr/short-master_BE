package kr.egosit.shortmaster.global.s3.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.S3Object;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class S3Service {

    @Value("${cloud.aws.s3.bucket}")
    private String bucketName;
    private final AmazonS3 amazonS3;

    /**
     * S3에 파일 업로드
     */
    public String uploadImage(MultipartFile image) throws IOException {
        String fileName = UUID.randomUUID() + "_" + image.getOriginalFilename(); // 고유한 파일 이름 생성

        // 메타데이터 설정
        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentType(image.getContentType());
        metadata.setContentLength(image.getSize());

        // S3에 파일 업로드 요청 생성
        PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, fileName, image.getInputStream(), metadata);

        // S3에 파일 업로드
        amazonS3.putObject(putObjectRequest);

        return getPublicUrl(fileName);
    }

    /**
     * S3에서 파일 다운로드
     */
    public S3Object downloadFile(String fileName) {
        return amazonS3.getObject(bucketName, fileName);
    }

    // 파일 삭제
    public void deleteFile(String fileName) {
        amazonS3.deleteObject(bucketName, fileName);
    }

    private String getPublicUrl(String fileName) {
        return String.format("https://%s.s3.%s.amazonaws.com/%s", bucketName, amazonS3.getRegionName(), fileName);
    }

}