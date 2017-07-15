/**
 * 
 */
package org.tch.ste.infra.repository;

import java.util.List;

/**
 * Class for paging and sorting data.
 * 
 * @author Karthik.
 * 
 */
public class PageSort {
    /**
     * Sort Types.
     * 
     * @author Karthik.
     * 
     */
    public enum SortType {
        /**
         * Ascending.
         */
        ASCENDING(" ASC"),
        /**
         * Descending.
         */
        DESCENDING(" DESC");

        private String sortType;

        private SortType(String type) {
            this.sortType = type;
        }

        @Override
        public String toString() {
            return this.sortType;
        }
    }

    private Integer start;
    private Integer max;
    private SortType sortType = SortType.ASCENDING;
    private List<String> sortCriteria;

    /**
     * @return start Integer - Get the field.
     */
    public Integer getStart() {
        return start;
    }

    /**
     * @param start
     *            Integer - Set the field start.
     */
    public void setStart(Integer start) {
        this.start = start;
    }

    /**
     * @return max Integer - Get the field.
     */
    public Integer getMax() {
        return max;
    }

    /**
     * @param max
     *            Integer - Set the field max.
     */
    public void setMax(Integer max) {
        this.max = max;
    }

    /**
     * @return sortType SortType - Get the field.
     */
    public SortType getSortType() {
        return sortType;
    }

    /**
     * @param sortType
     *            SortType - Set the field sortType.
     */
    public void setSortType(SortType sortType) {
        this.sortType = sortType;
    }

    /**
     * @return sortCriteria List<String> - Get the field.
     */
    public List<String> getSortCriteria() {
        return sortCriteria;
    }

    /**
     * @param sortCriteria
     *            List<String> - Set the field sortCriteria.
     */
    public void setSortCriteria(List<String> sortCriteria) {
        this.sortCriteria = sortCriteria;
    }

}
