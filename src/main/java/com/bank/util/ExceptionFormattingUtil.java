package com.bank.util;

import com.bank.exceptions.ResourceNotFoundException;

public class ExceptionFormattingUtil {
    public static ResourceNotFoundException accountNotFoundException(Long accountId){
        return new ResourceNotFoundException("AccountId " + accountId + " not found");
    }
}
