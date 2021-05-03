package model;

/**
 * Laser Class
 * by: Syed Manal
 */
public class Laser implements Item {

    private int status; //0 for neither, 1 for laser, 2 for beam
    private boolean active; // if the Laser is active
    public int count; // how many instances of the laser beam there are
    private int neighbors; // how many lasers is this laser facing

    /**
     * Constructor, which builds either null, laser or a laser beam. Count and neighbor always begin at 0 and activity is dependent
     * on if the laser is nothing or if it is either laser or beam
     * @param status the type to be built
     */
    public Laser(int status){
        neighbors =0;
        count =0;
        this.status = status;
        if(status==0){
            active=false;
        }else{
            active= true;
        }
    }

    /**
     * Set the status of laser to either null, Laser or Laser Beam
     * @param status 0, 1 or 2 depending on what it is being changed into
     */
    @Override
    public void setStatus(int status){
        this.status= status;
    }

    /**
     * what type this laser is
     * @return the status
     */
    @Override
    public int getStatus(){
        return status;
    }

    /**
     * Name to be used on board, symbol changes based on status
     * @return a string that represents the type of laser
     */
    @Override
    public String getName() {
        if(status ==0){
            return " . ";
        }else if(status ==1){
            return " L ";
        }
        return " * ";
    }

    /**
     * if the laser is facing any other lasers
     * @return if the number of lasers that this laser is facing is 0
     */
    @Override
    public boolean isValid() {
        return neighbors == 0;
    }

    /**
     * if this piece is active or not
     * @return active
     */
    @Override
    public boolean isActive() {
        return active;
    }

    /**
     * using the validity, increment or decrement neighbor number
     * @param valid true or false depending on if there is a neighbor
     */
    @Override
    public void setValid( boolean valid){
        if (!valid){
            neighbors++;
        }else{
            neighbors--;
        }
    }

    /**
     * set this piece to active or inactive
     * @param active true/false depending on what it is being set to
     */
    public void setActive(boolean active) {
        this.active = active;
    }

    /**
     * add to the number of beams by a given amount
     * @param count how much count is being changed by
     */
    @Override
    public void addCount(int count) {
        this.count += count;
    }

    /**
     * the number of beams
     * @return count
     */
    @Override
    public int getCount() {
        return count;
    }
}
