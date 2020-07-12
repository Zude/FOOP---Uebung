package sciencelab;

/**
 * Ein Multitool, mit dem Wissenschaftler an ihrer Erfindung arbeiten.
 * 
 * @author kar, mhe, Lars Sander, Alexander Löffler
 * 
 */
public class MultiTool {

    private boolean isFree = true;
    private Scientist owner = null;

    /**
     * Methode, die prüft ob das MultiTool frei ist, und falls nein, so lange wartet, bis es frei
     * ist und es dann reserviert (vergibt).
     * 
     * @param s Der Scientist der das Tool nehmen will
     * @throws InterruptedException Kann vom Wait geworfen werden
     */
    public void takeTool(Scientist s) throws InterruptedException {

        synchronized (this) {

            while (!isFree) {
                this.wait();
            }

            isFree = false;
            owner = s;
        }
    }

    /**
     * Prüft ob das Multitool frei ist
     * 
     * @return isFree
     */
    public synchronized boolean isFree() {
        return isFree;
    }

    /**
     * Gibt das Multitool frei, falls der Übergebe Scientist als Nutzer eingetragen ist
     * 
     * @param s Der Scientist der das Tool freigeben will
     */
    public void setFree(Scientist s) {
        synchronized (this) {
            if (owner.equals(s)) {
                isFree = true;
                this.notifyAll();
            }
        }

    }

}
