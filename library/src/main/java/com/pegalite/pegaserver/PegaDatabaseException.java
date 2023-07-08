package com.pegalite.pegaserver;

import androidx.annotation.RestrictTo;

/**
 * This error is thrown when the Firebase Database library is unable to operate on the input it has
 * been given.
 */
public class PegaDatabaseException extends RuntimeException {

    /**
     * <strong>For internal use</strong>
     *
     * @param message A human readable description of the error
     * @hide
     */
    @RestrictTo(RestrictTo.Scope.LIBRARY_GROUP)
    public PegaDatabaseException(String message) {
        super(message);
    }

    /**
     * <strong>For internal use</strong>
     *
     * @param message A human readable description of the error
     * @param cause   The underlying cause for this error
     * @hide
     */
    @RestrictTo(RestrictTo.Scope.LIBRARY_GROUP)
    public PegaDatabaseException(String message, Throwable cause) {
        super(message, cause);
    }
}
