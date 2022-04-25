CREATE TABLE public.transactions
(
    id bigserial NOT NULL,
    amount NUMERIC(10, 4) NOT NULL,
    type VARCHAR(20) NOT NULL,
    partition_id bigint NOT NULL,
    CONSTRAINT fk_transactions_partitions_partition_id FOREIGN KEY (partition_id) REFERENCES public.partitions (id),
    created_at timestamp(6) without time zone NOT NULL,
    PRIMARY KEY(id)
);
