package com.sustral.clientapi.data.types;

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
    COMPLETE
}
