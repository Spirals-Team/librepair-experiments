package de.digitalcollections.blueprints.crud.backend.impl.jpa.entity;

import static com.mysema.query.types.PathMetadataFactory.*;

import com.mysema.query.types.path.*;

import com.mysema.query.types.PathMetadata;
import javax.annotation.Generated;
import com.mysema.query.types.Path;


/**
 * QOperationImplJpa is a Querydsl query type for OperationImplJpa
 */
@Generated("com.mysema.query.codegen.EntitySerializer")
public class QOperationImplJpa extends EntityPathBase<OperationImplJpa> {

    private static final long serialVersionUID = -1261011170L;

    public static final QOperationImplJpa operationImplJpa = new QOperationImplJpa("operationImplJpa");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath name = createString("name");

    public QOperationImplJpa(String variable) {
        super(OperationImplJpa.class, forVariable(variable));
    }

    public QOperationImplJpa(Path<? extends OperationImplJpa> path) {
        super(path.getType(), path.getMetadata());
    }

    public QOperationImplJpa(PathMetadata<?> metadata) {
        super(OperationImplJpa.class, metadata);
    }

}

