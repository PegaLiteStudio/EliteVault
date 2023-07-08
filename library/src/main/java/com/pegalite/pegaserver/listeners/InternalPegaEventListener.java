package com.pegalite.pegaserver.listeners;

import com.pegalite.pegaserver.PegaDatabaseException;
import com.pegalite.pegaserver.PegaSnapshot;
import com.pegalite.pegaserver.tasks.PegaTask;

public abstract class InternalPegaEventListener<TResult> {
   public void onSuccess(PegaTask<TResult>  task){}

    public void onFailure(PegaDatabaseException exception){}

    public void getListener(InternalPegaEventListener<TResult> listener){}
}
