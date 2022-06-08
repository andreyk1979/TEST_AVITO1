package com.amr.project.exception;

import lombok.extern.slf4j.Slf4j;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.TypedQuery;

@Slf4j
public class Util {

    public static <T> T getSingleResult(TypedQuery<T> query) {
        try {
            return query.getSingleResult();
        } catch (NonUniqueResultException | NoResultException ex) {
            log.warn(ex.getMessage());
        } catch (Exception exception) {
            log.error(exception.getMessage());
        }
        return null;
    }
}