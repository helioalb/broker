CREATE TABLE public.asks
(
    id bigserial NOT NULL,
    wallet_code VARCHAR(36) NOT NULL,
    quantity NUMERIC(10, 4) NOT NULL,
    price NUMERIC(10, 4) NOT NULL,
    traded_with VARCHAR(36),
    traded_quantity NUMERIC(10, 4),
    created_at timestamp(6) without time zone NOT NULL default (timezone('utc', now())),
    updated_at timestamp(6) without time zone NOT NULL default (timezone('utc', now())),
    PRIMARY KEY (id)
);

CREATE INDEX index_asks_on_trade_with_price_updated_at ON public.asks (traded_with NULLS FIRST, price ASC, updated_at ASC);
