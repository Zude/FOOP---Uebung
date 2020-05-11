package context;

import java.util.HashMap;

import values.Value;

/**
 * Enthält eine Menge von Variablenbelegungen und Zugriffsmethoden darauf.
 * 
 * Eine Variablenbelegung setzt sich zusammen aus einem Namen und einem mathematischen Wert.
 * 
 * @author kar, mhe, Lars Sander, Alexander Loeffler
 */
public class Context<V extends Value<V>> {
	
	private HashMap<String, V> contextMap;
	
	public Context () { 
		contextMap = new HashMap<String, V>(); 
	}

    /**
     * Setzt ein neues Name-Wert-Paar im Kontext. Ein Name-Wert Paar, dessen Name bereits vorhanden
     * ist, wird überschrieben. Der Kontext selbst wird zudem als Rückgabewert gesetzt.
     * 
     * @param name Name
     * @param value mathematischer Wert
     * @return this
     */
    public Context<V> setValue(String name, V value) {
    	assert (value != null && name != null);
    	       
        contextMap.put(name, value);
    	
    	return this;
    }

    /**
     * Liefert den mathematischen Wert der Variablen mit dem übergebenen Namen. Sollte es kein
     * Element mit dem Namen geben, so wird eine ElementNotFoundException ausgelöst.
     * 
     * @param name Name der gesuchten Variable
     * @return mathematischer Wert der gesuchten Variable
     * @throws ElementNotFoundException Der Name ist nicht im Kontext vorhanden
     */
    public V getValue(String name) throws ElementNotFoundException {
    	assert (name != null);
    	
    	if (!has(name)) {
    		throw new ElementNotFoundException(name);
    	}
    
    	return contextMap.get(name);
    }

    /**
     * Prüft ob der Name im Kontext vorhanden ist.
     * 
     * @param name zu prüfender Name
     * @return true, wenn der Name vorhanden ist, sonst false
     */
    public boolean has(String name) {
    	return contextMap.containsKey(name);
    }

}
