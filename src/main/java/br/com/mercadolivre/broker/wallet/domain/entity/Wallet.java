package br.com.mercadolivre.broker.wallet.domain.entity;

public class Wallet {

    private String code;

    public Wallet(String code) {
        setCode(code);
    }

    private static final int UUID_LENGTH = 36;

    private void setCode(String code) {
        if (code.length() != UUID_LENGTH)
            throw new IllegalArgumentException("wallet code invalid");
        this.code = code;
    }

    public String getCode() {
        return this.code;
    }

}
