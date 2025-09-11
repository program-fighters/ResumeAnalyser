package org.example.db.repo;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.NoRepositoryBean;

import java.io.Serializable;
import java.util.List;

@NoRepositoryBean
public interface MongodbRepository<T, I extends Serializable> extends MongoRepository<T, I> {
    String MONGODB_REPO_PCK_NAME = "org.example.db.repo";

    Page<T> findAll(Query query, Pageable pageable);

    List<T> findAll(Query query);

    Page<T> findAllWithRequestedFields(Query query, Pageable pageable, String... filedKeyArray);

    List<T> findAllDataOnlyForExport(Query query, Pageable pageable);
}