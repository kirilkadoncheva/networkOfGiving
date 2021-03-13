package com.example.demo.web;

import com.example.demo.data.ImageModel;
import com.example.demo.impl.ImageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.zip.DataFormatException;
import java.util.zip.Deflater;
import java.util.zip.Inflater;

@CrossOrigin(origins = "http://localhost:4200",allowedHeaders = "*")
@RestController
@RequestMapping(value = "/images", produces = MediaType.APPLICATION_JSON_VALUE)
public class ImagesController {

    @Autowired
    ImageRepository imageRepository;

    @PostMapping("/upload/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void uploadImage(@RequestParam("imageFile") MultipartFile file, @PathVariable("id") int id) throws IOException {
        System.out.println("Original Image Byte Size - " + file.getBytes().length);
        ImageModel img = new ImageModel(file.getOriginalFilename(),id,
                compressBytes(file.getBytes()));
        imageRepository.saveImage(img);
        //return ResponseEntity.status(HttpStatus.OK);
    }

    @GetMapping(path = { "/get/{id}" })
    public ImageModel getImage(@PathVariable("id") int id) throws IOException {
        final ImageModel retrievedImage = imageRepository.getImageById(id);
        byte[] compressed = retrievedImage.getPicByte();
        retrievedImage.setPicByte(decompressBytes(compressed));

        return retrievedImage;
    }

    public static byte[] compressBytes(byte[] data) {
        Deflater deflater = new Deflater();
        deflater.setInput(data);
        deflater.finish();
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream(data.length);
        byte[] buffer = new byte[1024];
        while (!deflater.finished()) {
            int count = deflater.deflate(buffer);
            outputStream.write(buffer, 0, count);
        }
        try {
            outputStream.close();
        } catch (IOException e) {
        }
        System.out.println("Compressed Image Byte Size - " + outputStream.toByteArray().length);
        return outputStream.toByteArray();
    }
    // uncompress the image bytes before returning it to the angular application
    public static byte[] decompressBytes(byte[] data) {
        Inflater inflater = new Inflater();
        inflater.setInput(data);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream(data.length);
        byte[] buffer = new byte[1024];
        try {
            while (!inflater.finished()) {
                int count = inflater.inflate(buffer);
                outputStream.write(buffer, 0, count);
            }
            outputStream.close();
        } catch (IOException ioe) {
        } catch (DataFormatException e) {
        }
        return outputStream.toByteArray();
    }


}
