package com.pegalite.pegaserver.listeners;

import com.pegalite.pegaserver.tasks.PegaTask;


public interface PegaEventFailureListener<TResult> {

    void onFailure(Exception exception);

}
