package com.diop.dynamodb.log.errors.exception;

public class CustomerErrorConstants {
    // Erreurs fonctionelles
    public static final String ERR_MOVIE_NOT_FOUND = "ERR-MOVE-FUNC-00001";
    public static final String ERR_RESOURCE_NOT_FOUND = "ERR-MOVE-FUNC-00002";


    // Erreurs techniques
    public static final String ERR_OP_GOAL_LEGAL_NOT_FOUND = "ERR-CUS-TECH-00001";


    private CustomerErrorConstants() {
        throw new IllegalStateException("error constant class");
    }
}
