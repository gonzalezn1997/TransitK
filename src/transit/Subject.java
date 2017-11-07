/*
 * ----------------------------------------------------------------------------
 * "THE BEER-WARE LICENSE" (Revision 42):
 * <gnabasikat@msoe.edu, gonzalezn@msoe.edu, galluntf@msoe.edu> wrote this file. As long as you retain this notice you
 * can do whatever you want with this stuff. If we meet some day, and you think
 * this stuff is worth it, you can buy us a beer in return Alexander Gnabasik, Noe Gonzalez, Trey Gallun.
 * ----------------------------------------------------------------------------
 */

package transit;


/**
 * All subjects in the GTFS files.
 *
 * @author Alexander Gnabasik
 * @version 1.0
 * @created 05-Oct-2017 12:10:59 PM
 * <p>
 * The Subject which can be observer
 */
public interface Subject {

    /**
     * Attaches and Observer to a Subject.
     *
     * @param observer The observer to attach.
     * @author Alexander Gnabasik
     */
    public void attach(Observer observer);

    /**
     * Detaches an Observer from the Subject.
     *
     * @param observer The observer to detach.
     * @return The observer that was detached.
     * @author Alexander Gnabasik
     */
    public Observer detach(Observer observer);

    /**
     * Calls the Update method of the observer and passes it the subject.
     *
     * @param obj The subject itself.
     * @author Alexander Gnabasik
     */
    public void update(Object obj);

}