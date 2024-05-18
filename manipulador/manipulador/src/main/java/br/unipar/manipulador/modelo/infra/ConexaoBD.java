package br.unipar.manipulador.modelo.infra;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class ConexaoBD {
    
    private static EntityManagerFactory emf;
    private static EntityManager em;
    
    public static EntityManagerFactory getEntityManagerFactory() {
        emf = Persistence.createEntityManagerFactory("br.unipar_manipulador");
        return emf;
    }
    
    public static void closeEntityManagerFactory() {
        if (emf != null && emf.isOpen()) {
            emf.close();
        }
    }
    
    public static EntityManager getEntityManager() {
        if (em == null || !em.isOpen()) {
            em = ConexaoBD.getEntityManagerFactory().createEntityManager();
        }
        return em;
    }
    
    public static void closeEntityManager() {
        if (em != null || em.isOpen()) {
            em.close();
        }
    }
}
