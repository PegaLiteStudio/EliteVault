package com.pegalite.pegaserver.listeners;

import com.pegalite.pegaserver.tasks.PegaTask;

public interface PegaEventCompleteListener<TResult> {

    void onComplete(PegaTask<TResult> task);

}
