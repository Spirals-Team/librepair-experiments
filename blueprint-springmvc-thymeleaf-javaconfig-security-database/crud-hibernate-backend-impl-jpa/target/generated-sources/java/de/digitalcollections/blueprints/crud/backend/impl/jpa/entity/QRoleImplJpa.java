package de.digitalcollections.blueprints.crud.backend.impl.jpa.entity;

import static com.mysema.query.types.PathMetadataFactory.*;

import com.mysema.query.types.path.*;

import com.mysema.query.types.PathMetadata;
import javax.annotation.Generated;
import com.mysema.query.types.Path;
import com.mysema.query.types.path.PathInits;


/**
 * QRoleImplJpa is a Querydsl query type for RoleImplJpa
 */
@Generated("com.mysema.query.codegen.EntitySerializer")
public class QRoleImplJpa extends EntityPathBase<RoleImplJpa> {

    private static final long serialVersionUID = 88877339L;

    public static final QRoleImplJpa roleImplJpa = new QRoleImplJpa("roleImplJpa");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath name = createString("name");

    public final ListPath<OperationImplJpa, QOperationImplJpa> operations = this.<OperationImplJpa, QOperationImplJpa>createList("operations", OperationImplJpa.class, QOperationImplJpa.class, PathInits.DIRECT2);

    public QRoleImplJpa(String variable) {
        super(RoleImplJpa.class, forVariable(variable));
    }

    public QRoleImplJpa(Path<? extends RoleImplJpa> path) {
        super(path.getType(), path.getMetadata());
    }

    public QRoleImplJpa(PathMetadata<?> metadata) {
        super(RoleImplJpa.class, metadata);
    }

}

