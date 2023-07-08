package com.pegalite.pegaserver.listeners;

import com.pegalite.pegaserver.PegaDatabaseException;
import com.pegalite.pegaserver.PegaSnapshot;

public interface PegaResponseManagerListener {
    void onSuccess(PegaSnapshot snapshot);

    void onFailure(PegaDatabaseException exception);
}
