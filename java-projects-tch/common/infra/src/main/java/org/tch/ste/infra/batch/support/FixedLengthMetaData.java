/**
 * 
 */
package org.tch.ste.infra.batch.support;

/**
 * Metadata required to process files. Note: This class has an ordering that is
 * inconsistent with equals.
 * 
 * @author Karthik.
 * 
 */
public class FixedLengthMetaData implements Comparable<FixedLengthMetaData> {

    private int offset;
    private int length;
    private String fieldName;
    private Class<?> fieldType;

    /**
     * Returns the field offset.
     * 
     * @return offset int - Get the field.
     */
    public int getOffset() {
        return offset;
    }

    /**
     * Sets the field offset.
     * 
     * @param offset
     *            int - Set the field offset.
     */
    public void setOffset(int offset) {
        this.offset = offset;
    }

    /**
     * Returns the field length.
     * 
     * @return length int - Get the field.
     */
    public int getLength() {
        return length;
    }

    /**
     * Sets the field length.
     * 
     * @param length
     *            int - Set the field length.
     */
    public void setLength(int length) {
        this.length = length;
    }

    /**
     * Returns the field fieldName.
     * 
     * @return fieldName String - Get the field.
     */
    public String getFieldName() {
        return fieldName;
    }

    /**
     * Sets the field fieldName.
     * 
     * @param fieldName
     *            String - Set the field fieldName.
     */
    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    /**
     * Returns the field fieldType.
     * 
     * @return fieldType Class<?> - Get the field.
     */
    public Class<?> getFieldType() {
        return fieldType;
    }

    /**
     * Sets the field fieldType.
     * 
     * @param fieldType
     *            Class<?> - Set the field fieldType.
     */
    public void setFieldType(Class<?> fieldType) {
        this.fieldType = fieldType;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Comparable#compareTo(java.lang.Object)
     */
    @Override
    public int compareTo(FixedLengthMetaData other) {
        int retVal = 0;
        if (this.offset < other.offset) {
            retVal = -1;
        } else if (this.offset > other.offset) {
            retVal = 1;
        }
        return retVal;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        return prime * result + ((fieldName == null) ? 0 : fieldName.hashCode());
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        FixedLengthMetaData other = (FixedLengthMetaData) obj;
        if (fieldName == null) {
            if (other.fieldName != null)
                return false;
        } else if (!fieldName.equals(other.fieldName))
            return false;
        return true;
    }

}
