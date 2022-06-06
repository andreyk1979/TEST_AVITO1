package com.amr.project.onStratUp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

@Component
public class InitData {
    private final EntityManagerFactory entityManagerFactory;
    private final ImageStartUtil imageStartUtil;

    @Autowired
    public InitData(EntityManagerFactory entityManagerFactory, ImageStartUtil imageStartUtil) {
        this.entityManagerFactory = entityManagerFactory;
        this.imageStartUtil = imageStartUtil;
    }

    @PostConstruct
    public void postConstruct() {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        imageStartUtil.fillTableWithImages(entityManager);
        entityManager.close();
    }
}
