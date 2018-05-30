package de.digitalcollections.blueprints.crud.backend.impl.jpa.entity;

import static com.mysema.query.types.PathMetadataFactory.*;

import com.mysema.query.types.path.*;

import com.mysema.query.types.PathMetadata;
import javax.annotation.Generated;
import com.mysema.query.types.Path;
import com.mysema.query.types.path.PathInits;


/**
 * QUserImplJpa is a Querydsl query type for UserImplJpa
 */
@Generated("com.mysema.query.codegen.EntitySerializer")
public class QUserImplJpa extends EntityPathBase<UserImplJpa> {

    private static final long serialVersionUID = -844086234L;

    public static final QUserImplJpa userImplJpa = new QUserImplJpa("userImplJpa");

    public final StringPath email = createString("email");

    public final BooleanPath enabled = createBoolean("enabled");

    public final StringPath firstname = createString("firstname");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath lastname = createString("lastname");

    public final StringPath passwordHash = createString("passwordHash");

    public final ListPath<RoleImplJpa, QRoleImplJpa> roles = this.<RoleImplJpa, QRoleImplJpa>createList("roles", RoleImplJpa.class, QRoleImplJpa.class, PathInits.DIRECT2);

    public QUserImplJpa(String variable) {
        super(UserImplJpa.class, forVariable(variable));
    }

    public QUserImplJpa(Path<? extends UserImplJpa> path) {
        super(path.getType(), path.getMetadata());
    }

    public QUserImplJpa(PathMetadata<?> metadata) {
        super(UserImplJpa.class, metadata);
    }

}

