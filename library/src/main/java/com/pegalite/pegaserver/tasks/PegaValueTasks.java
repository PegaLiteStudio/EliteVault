package com.pegalite.pegaserver.tasks;

import com.pegalite.pegaserver.PegaDatabaseException;
import com.pegalite.pegaserver.listeners.InternalPegaEventListener;
import com.pegalite.pegaserver.listeners.PegaEventCompleteListener;
import com.pegalite.pegaserver.listeners.PegaEventFailureListener;
import com.pegalite.pegaserver.listeners.PegaEventSuccessListener;

public class PegaValueTasks<TResult> {
    private PegaEventSuccessListener<TResult> pegaEventSuccessListener;
    private PegaEventFailureListener<TResult> pegaEventFailureListener;
    private PegaEventCompleteListener<TResult> pegaEventCompleteListener;

    public PegaValueTasks(InternalPegaEventListener<TResult> internalPegaEventListener) {
        internalPegaEventListener.getListener(new InternalPegaEventListener<TResult>() {
            @Override
            public void onSuccess(PegaTask<TResult> task) {
                if (pegaEventCompleteListener != null) {
                    pegaEventCompleteListener.onComplete(task);
                }
                if (pegaEventSuccessListener != null) {
                    pegaEventSuccessListener.onSuccess(task);
                }
            }

            @Override
            public void onFailure(PegaDatabaseException exception) {
                if (pegaEventCompleteListener != null) {
                    pegaEventCompleteListener.onComplete(new PegaTask<TResult>(false, null,exception));
                }
                if (pegaEventFailureListener != null) {
                    pegaEventFailureListener.onFailure(exception);
                }
            }
        });
    }

    public PegaValueTasks<TResult> addOnSuccessListener(PegaEventSuccessListener<TResult> listener) {
        pegaEventSuccessListener = listener;
        return this;
    }

    public PegaValueTasks<TResult> addOnFailureListener(PegaEventFailureListener<TResult> listener) {
        pegaEventFailureListener = listener;
        return this;
    }

    public PegaValueTasks<TResult> addOnCompleteListener(PegaEventCompleteListener<TResult> listener) {
        pegaEventCompleteListener = listener;
        return this;
    }

    public PegaEventSuccessListener<TResult> getPegaEventSuccessListener() {
        return pegaEventSuccessListener;
    }

    public PegaEventFailureListener<TResult> getPegaEventFailureListener() {
        return pegaEventFailureListener;
    }

    public PegaEventCompleteListener<TResult> getPegaEventCompleteListener() {
        return pegaEventCompleteListener;
    }
}
