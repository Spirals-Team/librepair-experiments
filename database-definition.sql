create table if not exists data (
    data_uuid uuid primary key,
    mes integer not null,
    ano integer not null
);

create table if not exists cargo (
    cargo_uuid uuid primary key,
    cargo text
);

create table if not exists salario (
    salario_uuid uuid primary key,
    salario_base numeric,
    plano_carreira numeric,
    gratificacao numeric,
    beneficio numeric,
    abono numeric,
    adiantamento numeric,
    ferias numeric,
    decimo_terceiro numeric,
    abatimento numeric,
    descontos numeric,
    salario_bruto numeric,
    salario_liquido numeric
);

create table if not exists funcionario (
    funcionario_uuid uuid primary key,
    data_uuid uuid references data (data_uuid),
    cargo_uuid uuid references cargo (cargo_uuid),
    salario_uuid uuid references salario (salario_uuid),
    nome text not null
);