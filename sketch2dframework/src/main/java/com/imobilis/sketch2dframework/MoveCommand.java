package com.imobilis.sketch2dframework;
import android.graphics.Point;
import android.util.Log;

import java.util.ArrayList;

/**
 * Created by Alexsander on 18/08/2016.
 */
public class MoveCommand implements Command{
    private Figura f;
    private float  dx,dy;
    private ArrayList<Point> pontos = new ArrayList<>();
    private ArrayList<Integer> pontosx = new ArrayList<>();
    private ArrayList<Integer> pontosy = new ArrayList<>();
    private ArrayList<Point> pontosescalados = new ArrayList<>();
    private ArrayList<Integer> pontosescaladosx = new ArrayList<>();
    private ArrayList<Integer> pontosescaladosy = new ArrayList<>();
    public MoveCommand(Figura f,float xViewAnterior,float yViewAnterior)
    {
        this.f = f;
        this.dx = f.getView().getX() - xViewAnterior;
        this.dy = f.getView().getY() - yViewAnterior;

        packArray();
    }

    public void execute()
    {
        move(1);
        f.getView().invalidate();
    }

    public void undo() {

        move(0);
        f.getView().invalidate();
    }
    public void redo()
    {
        execute();
    }
    public void move(int voltando)
    {
        Log.d("IndoIndo","dx = "+dx+" dy = "+dy);
        int cont=0;
        for(Point p : f.getPontos())
        {

            Log.d("DEUBOM","Valor = "+((dx)*voltando));
            p.x = (int)(pontosx.get(cont)+(dx)*voltando);
            p.y = (int)(pontosy.get(cont)+(dy)*voltando);
            cont++;
        }
        cont=0;
        for(Point p : f.getPontosEscalados())
        {
            p.x = (int)(pontosescaladosx.get(cont)+(dx)*voltando);
            p.y = (int)(pontosescaladosy.get(cont)+(dy)*voltando);
            cont++;
        }

        f.deslocaFigura((int) (dx) * voltando, (int) (dy) * voltando);
    }
    /*public void move(int voltando)
    {
        Log.d("IndoIndo","dx = "+dx+" dy = "+dy);
        for(Point p : pontos)
        {
            Log.d("DEUBOM","Valor = "+((dx)*voltando));
            p.x += (dx)*voltando;
            p.y += (dy)*voltando;
        }
        for(Point p : pontosescalados)
        {
            p.x += (dx)*voltando;
            p.y += (dy)*voltando;
        }

        f.deslocaFigura((int) (dx) * voltando, (int) (dy) * voltando);
    }
    */
    public void packArray()
    {
        for(Point p : f.getPontos())
        {
            pontosx.add(p.x);
            pontosy.add(p.y);
        }
        for(Point p : f.getPontosEscalados())
        {
            pontosescaladosx.add(p.x);
            pontosescaladosy.add(p.y);
        }
    }

    public String showName()
    {
        return "MOVE COMMAND";
    }

}


