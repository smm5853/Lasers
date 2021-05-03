package model;

/**
 * Item Interface to be implemented by Pillar and Laser
 * By: Syed Manal
 */
public interface Item {

    /**
     * gets name of item to be used on board
     * @return String with name on board
     */
    public String getName();

    /**
     * if the given item violates any rules
     * @return true or false depending on if any rules are violated
     */
    public boolean isValid();

    /**
     * if the item is active or not
     * @return true or false depending on if item is active
     */
    public boolean isActive();

    /**
     * To be used by laser to change the type of laser
     * @param status 0, 1 or 2 depending on what it is being changed into
     */
    public void setStatus(int status);

    /**
     * adding a count, varies per item
     * @param count how much count is being changed by
     */
    public void addCount(int count);

    /**
     * getter for count
     * @return the count
     */
    public int getCount();

    /**
     * getter for status, to be used by Laser
     * @return the status
     */
    public int getStatus();

    /**
     * set the given item to active or inactive
     * @param active true/false depending on what it is being set to
     */
    public void setActive(boolean active);

    /**
     * set the given item to a valid or invalid item depending on if any rules are violated
     * @param valid true or false depending of what it is being set to
     */
    public void setValid(boolean valid);
}
