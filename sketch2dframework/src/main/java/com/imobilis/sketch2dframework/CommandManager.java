package com.imobilis.sketch2dframework;

import android.util.Log;

import java.util.ArrayList;
import java.util.Stack;

/**
 * Created by Alexsander on 18/08/2016.
 */
public class CommandManager{

    private Stack<Command> undoStack = new Stack<Command>();
    private Stack<Command> redoStack = new Stack<Command>();

    public void execute(Command command)
    {
        try{
            command.execute();
            undoStack.push(command);
            redoStack.clear();
        }
        catch(IllegalStateException ex)
        {
            //Log.d("DEURUIM","EXCEPTION EXECUTE");
        }
    }
    public void undo(){
        if(!undoStack.isEmpty())
        {
            try{
                Command command = undoStack.pop();
                command.undo();
                if(command instanceof DeletePointCommand)
                {
                    if(((DeletePointCommand) command).getIndex()==3)
                        Log.d("Ponto undo 22222","Ponto = "+((DeletePointCommand) command).getPonto().toString());
                }
                redoStack.push(command);
            }
            catch(IllegalStateException ex)
            {
                //Log.d("DEURUIM","EXCEPTION UNDO");
            }
        }

    }
    public void redo(){
        if(!redoStack.isEmpty())
        {
            try{
                Command command = redoStack.pop();
                command.redo();
                undoStack.push(command);
            }
            catch(IllegalStateException ex)
            {
                // Log.d("DEURUIM","EXCEPTION REDO");
            }
        }
    }
    public void removeAllCommands()
    {
        while(!undoStack.isEmpty())
        {
            undoStack.pop();
        }
        while(!redoStack.isEmpty())
        {
            redoStack.pop();
        }
    }
    public Stack<Command> getRedoStack(){
        return redoStack;
    }
    public Stack<Command> getUndoStack(){
        return undoStack;
    }
    public boolean undo_available()
    {
        if(undoStack.size()==0)
            return false;
        return true;
    }
    public boolean redo_available()
    {
        if(redoStack.size()==0)
            return false;
        return true;
    }
}

