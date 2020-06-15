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

    // TODO Methode, die prüft ob das MultiTool frei ist, und falls nein,
    // so lange wartet, bis es frei ist und es dann reserviert (vergibt).
    public void takeTool(Scientist s) throws InterruptedException {

        synchronized (this) {

            while (!isFree) {
                this.wait();
            }

            isFree = false;
            owner = s;
        }
    }

    // TODO Methode, die prüft, ob das Multitool gerade frei ist.
    public boolean isFree() {
        return isFree;
    }

    // TODO Methode, die das Multitool freigibt (darf nur funktionieren, wenn das Multitool
    // gerade dem freigebenden Wissenschaftler gehört).
    public void setFree(Scientist s) {
        synchronized (this) {
            if (owner.equals(s)) {
                isFree = true;
                this.notifyAll();
                ;
            }
        }

    }

    // TODO Wann machen die Notifys wirklich sinn? Nach jeder Änderung der Variable? Was würde
    // passieren wenn das Tool wartet und nicht der Scientist? Könnte im Scientist dann noch ein
    // Wait eingebaut sein oder gäbe es dann für das Notify keine option mehr?

}
