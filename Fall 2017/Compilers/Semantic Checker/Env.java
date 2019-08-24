// Brad Westhafer
// CMPSC 470
// Project 3

import java.nio.file.FileAlreadyExistsException;
import java.util.HashMap;

public class Env {
    private HashMap<String, Object> symbolTable = new HashMap<>();
    public Env prev;
    public Env(Env prev)
    {
        this.prev = prev;
    }
    public void Put(String name, Object value)
    {
        symbolTable.put(name, value);
    }
    public void Put(String name, Object value, boolean createSymbol) throws Exception {
        if(createSymbol && symbolTable.containsKey(name)) {
            throw new FileAlreadyExistsException("");
        }
        symbolTable.put(name, value);
    }
    public Object Get(String name)
    {
        if(symbolTable.containsKey(name)) {
            return symbolTable.get(name);
        }
        if(prev == null) {
            return null;
        }
        return prev.Get(name);
    }
}
