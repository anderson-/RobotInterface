/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package observable;

/**
 *
 * @author anderson
 */
public interface Observable <E,V> {
    
    public void attach (Observer <E,V> observer);
    
}
