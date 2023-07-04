package com.veeteq.finance.bankdocument.model;

import java.util.Arrays;

public enum OperationType {
    PRZELEW_DO_BM_MBANKU("PRZELEW DO BM MBANKU"),
    PRZELEW_MTRANSFER_WYCHODZACY("PRZELEW MTRANSFER WYCHODZACY"),
    PRZELEW_NA_TWOJE_CELE("PRZELEW NA TWOJE CELE"),
    PRZELEW_PODATKOWY("PRZELEW PODATKOWY"),
    PRZELEW_PRZYSZŁY_PODATKOWY("PRZELEW PRZYSZŁY PODATKOWY"),
    PRZELEW_WEWNETRZNY_PRZYCH_mDM("PRZELEW WEWNĘTRZNY PRZYCH. (mDM)"),
    PRZELEW_WEWNETRZNY_WYCHODZ_mDM("PRZELEW WEWNĘTRZNY WYCHODZ. (mDM)"),
    PRZELEW_WEWNETRZNY_WYCHODZACY("PRZELEW WEWNĘTRZNY WYCHODZĄCY"),
    PRZELEW_WLASNY("PRZELEW WŁASNY"),
    PRZELEW_ZEWNETRZNY_PRZYCHODZACY("PRZELEW ZEWNĘTRZNY PRZYCHODZĄCY"),
    PRZELEW_ZEWNETRZNY_WYCHODZACY("PRZELEW ZEWNĘTRZNY WYCHODZĄCY"),
    WYPLATA_W_BANKOMACIE("WYPŁATA W BANKOMACIE"),
    ZAKUP_PRZY_UZYCIU_KARTY("ZAKUP PRZY UŻYCIU KARTY"),
    ZERWANIE_LOKATY_TERMINOWEJ("ZERWANIE LOKATY TERMINOWEJ");

    private String code;

    OperationType(String code) {
        this.code = code;
    }

    public static OperationType findByCode(final String code){
        return Arrays.stream(values())
                .filter(el -> el.code.equals(code))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Operation type not defined for: " + code));
    }

    public String getCode() {
        return code;
    }

}
