package org.example.db.repo.support;

import org.example.db.repo.MongodbRepository;
import org.example.utils.NullSafeUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.repository.query.MongoEntityInformation;
import org.springframework.data.mongodb.repository.support.SimpleMongoRepository;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class MongodbRepositoryImpl<T, I extends Serializable> extends SimpleMongoRepository<T, I> implements MongodbRepository<T, I> {

    private final MongoOperations mongoOperations;
    private final MongoEntityInformation<T, I> entityInformation;

    public MongodbRepositoryImpl(final MongoEntityInformation<T, I> entityInformation, final MongoOperations mongoOperations) {
        super(entityInformation, mongoOperations);
        this.entityInformation = entityInformation;
        this.mongoOperations = mongoOperations;
    }


    @Override
    public List<T> findAll(Query query) {
        return findFromDbByQuery(query);
    }

    @Override
    public Page<T> findAllWithRequestedFields(Query query, Pageable pageable, String... filedKeyArray) {
        if (Objects.nonNull(query)) {
            if (Objects.nonNull(filedKeyArray))
                query.fields().include(filedKeyArray);
            return findAll(query, pageable);
        }
        return Page.empty();
    }

    @Override
    public Page<T> findAll(Query query, Pageable pageable) {
        long total = findCountFromDbByQuery(query);
        if (NullSafeUtils.isValidNaturalNo(total)) {
            List<T> content = findFromDbByQuery(query.with(pageable));
            return new PageImpl<T>(content, pageable, total);
        } else {
            return Page.empty();
        }
    }

    @Override
    public List<T> findAllDataOnlyForExport(Query query, Pageable pageable) {
        return findFromDbByQuery(query.with(pageable));
    }

    /*88888888888888888888888888888888888888888888888888888888 private methods 888888888888888888888888888888888888888888888888888888888888888888888888*/
    private List<T> findFromDbByQuery(Query query) {
        if (NullSafeUtils.ifAmNotNull(query)) {
            return mongoOperations.find(query, entityInformation.getJavaType(), entityInformation.getCollectionName());
        } else {
            return new ArrayList<>(0);
        }
    }

    private long findCountFromDbByQuery(Query query) {
        if (NullSafeUtils.ifAmNotNull(query)) {
            return mongoOperations.count(query, entityInformation.getJavaType(), entityInformation.getCollectionName());
        } else {
            return 0L;
        }
    }

}