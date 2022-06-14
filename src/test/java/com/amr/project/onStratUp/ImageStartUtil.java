package com.amr.project.onStratUp;

import com.amr.project.model.entity.Image;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;

@Component
public class ImageStartUtil {
    /*
    Если если хочешь добавить новые/другие картинки, внеси названия картинок в файле imagesList.txt
    Обрати внимание, что из файла imagesList.txt название считывается построчно, поэтому тут, к сожалению,
     нужно быть внимательным.
    */
    private final String path = "images";
    private final ClassLoader classLoader = getClass().getClassLoader();

    private void createImageFromPath(EntityManager entityManager) {
        File file = new File(classLoader.getResource(path).getFile());
        Image image;
        List<String> listOfFileNamesOfImages = getListOfFileNameOfImages();
        try {
            for (String images : listOfFileNamesOfImages) {
                image = new Image();
                image.setPicture(Files.readAllBytes(Paths.get(file + "/" + images)));
                entityManager.persist(image);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private List<String> getListOfFileNameOfImages() {
        File file = new File(Objects.requireNonNull(classLoader.getResource(path)).getFile());
        List<String> listOfImages = new ArrayList<>();
        try {
            Scanner scanner = new Scanner(new FileInputStream(file + "/imagesList.txt"));
            while (scanner.hasNextLine()) {
                listOfImages.add(scanner.nextLine());
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return listOfImages;
    }

    public void fillTableWithImages(EntityManager entityManager) {
        entityManager.getTransaction().begin();
        createImageFromPath(entityManager);
        entityManager.getTransaction().commit();
    }

}
