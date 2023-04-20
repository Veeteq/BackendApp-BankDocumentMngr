package com.veeteq.finance.bankdocument.model;

import java.util.Arrays;

public enum OperationType {
    PRZELEW_MTRANSFER_WYCHODZACY("PRZELEW MTRANSFER WYCHODZACY"),
    PRZELEW_NA_TWOJE_CELE("PRZELEW NA TWOJE CELE"),
    PRZELEW_WEWNETRZNY_PRZYCH_mDM("PRZELEW WEWNĘTRZNY PRZYCH. (mDM)"),
    PRZELEW_WEWNETRZNY_WYCHODZ_mDM("PRZELEW WEWNĘTRZNY WYCHODZ. (mDM)"),
    PRZELEW_WEWNETRZNY_WYCHODZACY("PRZELEW WEWNĘTRZNY WYCHODZĄCY"),
    PRZELEW_ZEWNETRZNY_PRZYCHODZACY("PRZELEW ZEWNĘTRZNY PRZYCHODZĄCY"),
    PRZELEW_ZEWNETRZNY_WYCHODZACY("PRZELEW ZEWNĘTRZNY WYCHODZĄCY"),
    WYPLATA_W_BANKOMACIE("WYPŁATA W BANKOMACIE"),
    ZAKUP_PRZY_UZYCIU_KARTY("ZAKUP PRZY UŻYCIU KARTY");

    private String code;

    OperationType(String code) {
        this.code = code;
    }

    public static OperationType findByCode(final String code){
        return Arrays.stream(values())
                .filter(el -> el.code.equals(code))
                .findFirst()
                .get();
    }

    public String getCode() {
        return code;
    }

}
