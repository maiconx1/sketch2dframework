package com.imobilis.sketch2dframework;

import android.graphics.Point;
import android.util.Log;
import android.widget.FrameLayout;

import java.util.ArrayList;

/**
 * Created by Alexsander on 30/08/2016.
 */
public class MoveCommandClip implements Command{

    private Figura f;
    private int indexLinhaClip;
    private float  dx,dy;
    private ArrayList<Integer> pontosx = new ArrayList<>();
    private ArrayList<Integer> pontosy = new ArrayList<>();
    private ArrayList<Integer> pontosescaladosx = new ArrayList<>();
    private ArrayList<Integer> pontosescaladosy = new ArrayList<>();

    public MoveCommandClip(Figura f,int indexLinhaClip,float xViewAnterior,float yViewAnterior)
    {
        this.indexLinhaClip = indexLinhaClip;
        this.f = f;
        this.dx = f.getView().getX() - xViewAnterior;
        this.dy = f.getView().getY() - yViewAnterior;

        packArray();
    }

    public void linhas_vizinhas()
    {
        if(Figura.clip && f instanceof Linha)
        {

            int index_clip=0;

            if(((Linha)f).getIndex_poligono()!=-1)
                index_clip = ((Linha)f).getIndex_poligono();
            int index_depois= index_clip+1>=Figura.linhas_clip.size()?0:index_clip+1;
            int index_antes = index_clip-1<0?Figura.linhas_clip.size()-1:index_clip-1;


            Linha la = Figura.linhas_clip.get(index_antes);
            la.getPontos().set(1, f.getPontos().get(0));
            int width =Math.abs(f.getPontos().get(0).x-la.getPontos().get(0).x);
            int height =Math.abs(f.getPontos().get(0).y - la.getPontos().get(0).y);
            if(width<60)
                width=100;
            if(height<60)
                height=100;
            la.getView().setLayoutParams(new FrameLayout.LayoutParams(width,height));
            la.getView().invalidate();



            la = Figura.linhas_clip.get(index_depois);
            la.getPontos().set(0, f.getPontos().get(1));
            width =Math.abs(f.getPontos().get(1).x-la.getPontos().get(1).x);
            height =Math.abs(f.getPontos().get(1).y-la.getPontos().get(1).y);
            if(width<40)
                width=40;
            if(height<40)
                height=40;
            la.getView().setLayoutParams(new FrameLayout.LayoutParams(width, height));
            Log.d("Clipando22", "Size = " + la.getView().getWidth() + " " + la.getView().getHeight());
            la.getView().invalidate();

        }
    }

    public void execute()
    {
        f = Figura.linhas_clip.get(indexLinhaClip);
        execute_move();
        int index_clip = ((Linha)f).getIndex_poligono();
        Figura.poligono_editando.getPontos().set(index_clip,new Point(f.getPontos().get(0).x,f.getPontos().get(0).y));
        Figura.poligono_editando.getPontos().set(index_clip + 1 < Figura.poligono_editando.getPontos().size() ? index_clip + 1 : 0,new Point(f.getPontos().get(1).x,f.getPontos().get(1).y));
        Figura.poligono_editando.getView().invalidate();

        linhas_vizinhas();


        //TODO2;
    }
    public void redo()
    {
        execute();
    }
    public void undo()
    {
        f = Figura.linhas_clip.get(indexLinhaClip);
        undo_move();

        int index_clip = ((Linha)f).getIndex_poligono();
        Figura.poligono_editando.getPontos().set(index_clip,new Point(pontosx.get(0),pontosy.get(0)));
        Figura.poligono_editando.getPontos().set(index_clip + 1 < Figura.poligono_editando.getPontos().size() ? index_clip + 1 : 0,new Point(pontosx.get(1),pontosy.get(1)));
        Figura.poligono_editando.getView().invalidate();

        linhas_vizinhas();
    }


    public void execute_move()
    {
        move(1);
        f.getView().invalidate();
    }

    public void undo_move() {

        move(0);
        f.getView().invalidate();
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
        return "MOVE COMMAND CLIP";
    }


}
