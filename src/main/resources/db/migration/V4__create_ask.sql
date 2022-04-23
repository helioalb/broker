CREATE TABLE public.asks
(
    id bigserial NOT NULL,
    wallet_code VARCHAR(36) NOT NULL,
    quantity NUMERIC(10, 4) NOT NULL,
    price NUMERIC(10, 4) NOT NULL,
    created_at timestamp(6) without time zone NOT NULL default (timezone('utc', now())),
    PRIMARY KEY (id)
);

CREATE INDEX index_asks_on_price_created_at ON public.asks (price ASC, created_at ASC);
