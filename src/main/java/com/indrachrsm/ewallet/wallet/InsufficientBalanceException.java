package com.indrachrsm.ewallet.wallet;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
public class InsufficientBalanceException extends RuntimeException {
    private static final long serialVersionUID = 6476677303483376057L;
}
