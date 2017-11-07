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
 * Display observers
 *
 * @author Alexander Gnabasik
 * @version 1.0
 * @created 05-Oct-2017 12:10:51 PM
 * <p>
 * The Observer to observer a Subject.
 */
public interface Observer {

    /**
     * Updates the observer.
     *
     * @param obj The subject it is attached to.
     * @author Alexander Gnabasik
     */
    public void update(Object obj);

}