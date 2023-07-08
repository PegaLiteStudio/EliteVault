package com.pegalite.pegaserver;

public interface PegaSnapshotEventListener {

    void onDataChange(PegaSnapshot snapshot);

    void onFailure(PegaDatabaseException exception);


}
