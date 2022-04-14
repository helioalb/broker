CREATE TABLE public.wallets
(
    id bigserial NOT NULL,
    code VARCHAR(36) NOT NULL,
    created_at timestamp(6) without time zone NOT NULL default (timezone('utc', now())),
    updated_at timestamp(6) without time zone NOT NULL default (timezone('utc', now())),
    PRIMARY KEY (id)
);

CREATE UNIQUE INDEX index_wallets_on_code ON public.wallets (code);
