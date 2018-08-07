
START TRANSACTION;

alter table product_entity add column "state" varchar (255) ;

update product_entity set state = "old"."state"
from
(
select
    id,
    status,
    CASE status
    when 'ACTIVE' THEN 'SIGNED'
    when 'INACTIVE' then 'SIGNED'
    when 'PENDING' then 'QUOTE'
    end as "state"
from product_entity)
as old where product_entity.id = old.id;


alter table user_entity
    add column insuance_active_to timestamp,
    add column insurance_active_from timestamp,
    add column insurance_state varchar(255);

update user_entity set insurance_state = "old"."state"
from
(
select
    id,
    insurance_status,
    CASE insurance_status
    when 'ACTIVE' THEN 'SIGNED'
    when 'INACTIVE' then 'SIGNED'
    when 'PENDING' then 'QUOTE'
    end as "state"
from user_entity)
as old where user_entity.id = old.id;

COMMIT;