CREATE TABLE public.partitions
(
    id bigserial NOT NULL,
    wallet_id bigint NOT NULL,
    CONSTRAINT fk_partitions_wallets_code FOREIGN KEY (wallet_id) REFERENCES public.wallets (id),
    asset VARCHAR(5) NOT NULL,
    created_at timestamp(6) without time zone NOT NULL,
    updated_at timestamp(6) without time zone NOT NULL,
    CONSTRAINT unique_asset_by_wallet UNIQUE (asset, wallet_id),
    PRIMARY KEY (id)
);

CREATE INDEX index_partitions_on_wallet_id ON public.partitions (wallet_id);

