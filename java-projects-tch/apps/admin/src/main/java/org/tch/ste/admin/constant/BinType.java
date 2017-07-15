/**
 * 
 */
package org.tch.ste.admin.constant;

/**
 * Bin Type.
 * 
 * @author Karthik.
 * 
 */
public enum BinType {
    /**
     * Real Bin.
     */
    REAL_BIN("R"),
    /**
     * Token Bin.
     */
    TOKEN_BIN("T");

    private String strRep;

    /**
     * Private Constructor.
     * 
     * @param value
     *            String - The value.
     */
    private BinType(String value) {
        this.strRep = value;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Enum#toString()
     */
    @Override
    public String toString() {
        return this.strRep;
    }
}
