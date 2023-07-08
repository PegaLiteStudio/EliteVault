package com.pegalite.pegaserver;

import com.pegalite.pegaserver.tasks.PegaValueTasks;

import org.json.JSONArray;

public class PegaDatabaseReference extends EliteQuery {


    private PegaDatabaseReference parentDatabaseReference;
    private final String root;

    public PegaDatabaseReference(String root) {
        super(root);
        this.root = root;
        setCurrentDatabaseReference(this);
    }

    public PegaDatabaseReference(String root, PegaDatabaseReference parentDatabaseReference) {
        super(root);
        this.parentDatabaseReference = parentDatabaseReference;
        this.root = root;
        setCurrentDatabaseReference(this);
    }

    public PegaDatabaseReference child(String child) {
        if (child.contains("/")) {
            throw new RuntimeException("Child cannot contain /");
        }
        if (root.isEmpty()) {
            return new PegaDatabaseReference(child, this);
        }
        return new PegaDatabaseReference(root + "/" + child, this);
    }

    public PegaDatabaseReference push() {
        if (root.isEmpty()) {
            return new PegaDatabaseReference(generatePushID(PUSH_ID_LENGTH), this);
        }
        return new PegaDatabaseReference(root + "/" + generatePushID(PUSH_ID_LENGTH), this);
    }

    public PegaValueTasks<Void> setValue(Object o) {

        if (o instanceof JSONArray) {
            throw new RuntimeException("Unable to use Array as Data");
        } else if (!canDirectlyParsable(o)) {
            return setDataValue(convertToJSONObject(o));
        }

        return setDataValue(o);

    }


    public void addChildEventListener(PegaSnapshotEventListener listener) {
        createServerSnapshot(true, listener);
    }


    public void addListenerForSingleValueEvent(PegaSnapshotEventListener listener) {
        createServerSnapshot(false, listener);
    }

    public PegaDatabaseReference getParent() {
        return parentDatabaseReference;
    }
}
