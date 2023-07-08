package com.pegalite.pegaserver.tasks;

import com.pegalite.pegaserver.PegaDatabaseException;

public class PegaTask<TResult> {
    private final boolean isSuccessful;
    private TResult result;
    private PegaDatabaseException exception;

    public PegaTask(boolean isSuccessful) {
        this.isSuccessful = isSuccessful;

    }

    public PegaTask(boolean isSuccessful, TResult result, PegaDatabaseException exception) {
        this.isSuccessful = isSuccessful;
        this.result = result;
        this.exception = exception;
    }

    public TResult getResult() {
        return result;
    }

    public boolean isSuccessful() {
        return isSuccessful;
    }

    public PegaDatabaseException getException() {
        return exception;
    }


}
