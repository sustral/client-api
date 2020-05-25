package com.sustral.clientapi.utils;

import java.util.ArrayList;
import java.util.List;

/**
 * This class helps to manage a paginated request.
 *
 * This class is not thread safe!
 *
 * @author Dilanka Dharmasena
 */
public class PaginationManager<T> {

    private final int firstPageFirstIndex;
    private final int lastPageLastIndex;
    private final int firstPageIndex;
    private final int lastPageIndex;
    private final ArrayList<List<T>> pages;

    /**
     * Constructs a PaginationManager for a request that 1) begins at 'offset' (inclusive), 2) requests a
     * 'limit' number of objects, and 3) where each database query returns at most 'pageSize' objects.
     *
     * @param offset        an int >= 0; the index of the first object that the requester has yet to receive
     * @param limit         an int >= 1; the number of objects the requester wants beyond the offset
     * @param pageSize      an int >= 1; the max number of objects each query can receive
     */
    public PaginationManager(int offset, int limit, int pageSize) {
        if (offset < 0 || limit < 1 || pageSize < 1) {
            throw new IllegalArgumentException("An invalid parameter was sent to PaginationManager.PaginationManager.");
        }

        this.firstPageFirstIndex = offset % pageSize;
        this.lastPageLastIndex = (offset + limit - 1) % pageSize;

        this.firstPageIndex = offset / pageSize;
        this.lastPageIndex = (offset + limit - 1) / pageSize;

        this.pages = new ArrayList<>();
    }

    /**
     * Returns the first and last page indices the caller should retrieve.
     *
     * @return  an int array; [0] is the first index, [1] is the last index
     */
    public int[] getFirstAndLastPageIndices() {
        return new int[]{firstPageIndex, lastPageIndex};
    }

    /**
     * Adds the given page to the collection that is to be returned later.
     *
     * Assumes that the caller is following the indices returned in getFirstAndLastPageIndices
     * and is not submitting null pages.
     *
     * @param nextPage  a List of Objects; the next page in the sequence
     */
    public void addPage(List<T> nextPage) {
        pages.add(nextPage);
    }

    /**
     * Combines all the pages submitted and trims the unnecessary items from the first page and last page.
     *
     * The list may contain fewer items than the requester asked for if the submitted pages (originally sourced
     * from the db) did not contain enough items.
     *
     * @return  the list of objects requested; will never be null
     */
    public List<T> getFinalResults() {
        ArrayList<T> finalResults = new ArrayList<>();

        for (int i = 0; i < pages.size(); i++) {
            List<T> page = pages.get(i);

            if (i == 0) {
                if (page.size() > firstPageFirstIndex) {
                    finalResults.addAll(page.subList(firstPageFirstIndex, page.size()));
                }
            } else if (i == (lastPageIndex - firstPageIndex)) {
                finalResults.addAll(page.subList(0, Math.min(page.size(), lastPageLastIndex + 1)));
            } else {
                finalResults.addAll(page);
            }
        }

        return finalResults;
    }

}
