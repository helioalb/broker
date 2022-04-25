CREATE TABLE public.bids
(
    id bigserial NOT NULL,
    wallet_code VARCHAR(36) NOT NULL,
    quantity NUMERIC(10, 4) NOT NULL,
    price NUMERIC(10, 4) NOT NULL,
    created_at timestamp(6) without time zone NOT NULL,
    PRIMARY KEY (id)
);

CREATE INDEX index_bids_on_price_created_at ON public.bids(price DESC, created_at ASC);
