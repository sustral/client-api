package com.sustral.clientapi.data.types;

import java.util.NoSuchElementException;

/**
 * Defines the possible scan statuses for use in the ScanEntity class.
 *
 * @author Dilanka Dharmasena
 */
public enum ScanStatusE {
    PENDING_COLLECTION,
    COLLECTION,
    PENDING_ANALYSIS,
    ANALYSIS,
    PENDING_COMPLETE,
    COMPLETE {
        @Override
        public ScanStatusE next() {
            throw new NoSuchElementException("The next() method was called on the terminal status.");
        }
    };

    public static ScanStatusE initial() {
        return values()[0];
    }

    public ScanStatusE next() {
        return values()[ordinal() + 1];
    }

}
