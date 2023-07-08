package com.pegalite.pegaserver.listeners;

import com.pegalite.pegaserver.PegaSnapshot;
import com.pegalite.pegaserver.tasks.PegaTask;

public interface PegaEventSuccessListener<TResult>  {

    void onSuccess(PegaTask<TResult>  task);

}
