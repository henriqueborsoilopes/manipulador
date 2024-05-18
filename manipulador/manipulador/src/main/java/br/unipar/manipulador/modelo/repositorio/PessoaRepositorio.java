package br.unipar.manipulador.modelo.repositorio;

import br.unipar.manipulador.modelo.dto.PaginaDTO;
import br.unipar.manipulador.modelo.entidade.Pessoa;
import br.unipar.manipulador.modelo.infra.ConexaoBD;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.TypedQuery;
import java.util.List;

public class PessoaRepositorio {
    
    public Pessoa inserir(Pessoa pessoa) {
        EntityManager em = ConexaoBD.getEntityManager();
        EntityTransaction transaction = em.getTransaction();
        transaction.begin();
        pessoa = em.merge(pessoa);
        transaction.commit();
        return pessoa;
    }
    
    public PaginaDTO acharTodosPaginado(String nome, int numPagina, int tamPagina) {
        EntityManager em = ConexaoBD.getEntityManager();
        
        String query = "SELECT DISTINCT obj FROM Pessoa obj WHERE obj.nome LIKE :nome ORDER BY obj.nome ASC";
        TypedQuery<Pessoa> authorQuery = em.createQuery(query, Pessoa.class);
        authorQuery.setParameter("nome", "%" + nome + "%");
        authorQuery.setFirstResult(numPagina * tamPagina);
        authorQuery.setMaxResults(tamPagina);
        
        TypedQuery<Long> countQuery = em.createQuery("SELECT COUNT(obj) FROM Pessoa obj WHERE obj.nome LIKE :nome", Long.class);
        countQuery.setParameter("nome", "%" + nome + "%");
        long totalElementos = countQuery.getSingleResult();
        
        List<Pessoa> clientes = authorQuery.getResultList();
        
        ConexaoBD.closeEntityManager();
        
        PaginaDTO<Pessoa> entidades = new PaginaDTO(numPagina, tamPagina, totalElementos);
        entidades.getConteudo().addAll(clientes);
        
        return entidades;
    }
}
