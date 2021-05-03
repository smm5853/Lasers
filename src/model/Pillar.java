package model;

/**
 * Pillar Class implements Item
 * By: Syed Manal
 */
public class Pillar implements Item {
    private int status; //total number of outlets
    private int count; //number of current connected lasers

    /**
     * Constructor 1, if number of outlets is defined
     * @param status the number of outlets this Pillar has
     */
    public Pillar(int status){
        this.status = status;
        count = 0;
    }

    /**
     * Constructor 2, if number of outlets is undefined
     */
    public Pillar(){
        status = -1;
        count = -1;
    }

    /**
     * get the number of outlets it has, or X if that number is undefined
     * @return the number of outlets
     */
    @Override
    public String getName() {
        if(status>0){
            return " " + status + " ";
        }else{
            return " X ";
        }
    }

    /**
     * If a laser is activated or deactivated, the count must increase or decrease by one assuming there is a
     * fixed number of outlets. If there isn't, do nothing.
     * @param amount the amount that count is being changed by (will always be 1 or -1)
     */
    @Override
    public void addCount(int amount){
        if(status > 0){
            count += amount;
        }
    }

    /**
     * getter for number of connected lasers
     * @return the count
     */
    @Override
    public int getCount() {
        return count;
    }

    /**
     * getter for number of outlets
     * @return the status
     */
    @Override
    public int getStatus() {
        return status;
    }

    /**
     * Ignored as this will always be active
     * @param active true/false depending on what it is being set to
     */
    @Override
    public void setActive(boolean active) {

    }

    /**
     * Ignored as validity cannot be changed unless it is checked
     * @param valid true or false depending of what it is being set to
     */
    @Override
    public void setValid(boolean valid) {

    }

    /**
     * checks if the Pillar is violating any rules. If the Pillar has an undefined status, it will always follow rules.
     * @return true or false depending on if the rules are followed or only true if the Pillar has no defined status.
     */
    @Override
    public boolean isValid(){
        if(status>0) {
            return status == count;
        }else{
            return true;
        }
    }

    /**
     * checks if the Pillar is active, which it always will be
     * @return true, Pillar is never inactive
     */
    @Override
    public boolean isActive() {
        return true;
    }

    /**
     * setter for status, not ever used by this class
     * @param status 0, 1 or 2 depending on what it is being changed into
     */
    @Override
    public void setStatus(int status) {
        this.status = status;
    }

}
