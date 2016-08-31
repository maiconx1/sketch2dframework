package com.imobilis.sketch2dframework;

/**
 * Created by Alexsander on 18/08/2016.
 */
public interface Command{

    void execute();
    void undo();
    void redo();
    String showName();


}

