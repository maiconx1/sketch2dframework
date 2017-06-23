
package com.imobilis.sketch2dframework;

/**
 * Created by Alexsander on 18/08/2016.
 */

import android.graphics.Point;
import android.util.Log;
import android.graphics.Point;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

/**
 * Created by Alexsander on 18/08/2016.
 */
public class DeletePointCommand implements Command{
    private Circulo f;
    private Point ponto;
    int x, y;
    private int index;
    private int indexSketch;
    public DeletePointCommand(Circulo f,Point ponto,int index)
    {
        this.f = f;
        setPonto(ponto);
        this.index=index;
        this.x = ponto.x;
        this.y = ponto.y;

        this.indexSketch = Sketch2D.getFiguras().indexOf(f);
        Log.d("Ponto undo construtor", "index = " +index+" index Sketch = "+indexSketch);

    }
    private void setPonto(Point ponto)
    {
        this.ponto=ponto;
    }
    public Point getPonto()
    {
        return this.ponto;
    }
    public int getIndex()
    {
        return index;
    }

    public void execute()
    {
        Figura.poligono_editando.getPontos().remove(index);
        Figura.poligono_editando.getView().invalidate();


        //Figura.indexCirculos.get(index).getView().setVisibility(View.GONE);
        Figura.indexCirculos.remove(index);
        Sketch2D.getFiguras().get(indexSketch).getView().setVisibility(View.GONE);
        Figura.reindexaPontos(1, index);
        //Figura.gambsCircleFix();
    }

    public void undo() {
        /*Figura.poligono_editando.getPontos().add(f.getIndex_poligono(),f.getPontos().get(0));
        Figura.poligono_editando.getView().invalidate();
        Figura.indexCirculos.add(f.getIndex_poligono(), f);
        Figura.indexCirculos.get(f.getIndex_poligono()).getView().setVisibility(View.VISIBLE);
        f.reindexaPontos(2, f.getIndex_poligono());
        Figura.poligono_editando.getView().invalidate();*/
        int cont=0;
        for(int i=0;i<Figura.indexCirculosSize0;i++)
        {
            if(Sketch2D.getFiguras().get(i).getView().getVisibility() == View.GONE)
            {
                cont++;
            }
        }

        Point ponto = new Point(getPonto().x,getPonto().y);
        Figura.poligono_editando.getPontos().add(index, ponto);
        Figura.poligono_editando.getView().invalidate();
        //Tem q reindexa antes de adicionar um circulo, para evitar que tenha dois com mesmo indice
        Figura.reindexaPontos(5, index);
        /*ArrayList<Point> pontos = new ArrayList<>();
        pontos.add(ponto);
        Circulo c = new Circulo(f.getActivity(),pontos,f.getRaio(),true);*/


        //Encontrando a figura nas ultimas figuras desenhadas. Ex: index=0 é o primeiro a ser desenhado dos indexCirculosSize.
        //int indexSketch = Sketch2D.getFiguras().size()-Figura.indexCirculosSize0+index;



        Log.d("Ponto undo", "pontos invisi = " + cont+" Sketch = "+indexSketch+ " index = " + index + " pontos = " + ponto.toString() + " x = " + x + " y = " + y);
        f.getPontos().set(0, ponto);
        Figura.indexCirculos.add(index, f);
        //Sketch2D.desenhaCirculo(f.getActivity(),f.getParent(),false)



        Sketch2D.getFiguras().get(indexSketch).getView().setVisibility(View.VISIBLE);



        //Figura.indexCirculos.get(index).getView().setVisibility(View.VISIBLE);
        //Figura.indexCirculos.get(index).getView().invalidate();
        //f.getView().setVisibility(View.VISIBLE);
       // f.getView().invalidate();


        Figura.poligono_editando.getView().invalidate();
        //Figura.gambsCircleFix();
    }
    public void redo()
    {
        execute();
    }

    public String toString()
    {
        return "index = "+index+" ponto = "+getPonto().toString();
    }

    public String showName()
    {
        return "DELETE POINT COMMAND";
    }

}



















/*
package com.imobilis.sketch2dframework;

*/
/**
 * Created by Alexsander on 18/08/2016.
 *//*


import android.graphics.Point;
import android.util.Log;
import android.graphics.Point;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

*/
/**
 * Created by Alexsander on 18/08/2016.
 *//*

public class DeletePointCommand implements Command{
    private Circulo f;
    private Point ponto;
    private int index;
    public DeletePointCommand(Circulo f,Point ponto,int index)
    {
        this.f = f;
        this.ponto=ponto;
        this.index=index;
    }

    public void execute()
    {
        Figura.poligono_editando.getPontos().remove(f.getIndex_poligono());
        Figura.poligono_editando.getView().invalidate();

        int index = f.getIndex_poligono();
        Figura.indexCirculos.get(index).getView().setVisibility(View.GONE);
        Figura.indexCirculos.remove(index);
        Figura.reindexaPontos(1, index);

    }

    public void undo() {
        */
/*Figura.poligono_editando.getPontos().add(f.getIndex_poligono(),f.getPontos().get(0));
        Figura.poligono_editando.getView().invalidate();
        Figura.indexCirculos.add(f.getIndex_poligono(), f);
        Figura.indexCirculos.get(f.getIndex_poligono()).getView().setVisibility(View.VISIBLE);
        f.reindexaPontos(2, f.getIndex_poligono());
        Figura.poligono_editando.getView().invalidate();*//*



        Figura.poligono_editando.getPontos().add(f.getIndex_poligono(), f.getPontos().get(0));
        Figura.poligono_editando.getView().invalidate();
        //Tem q reindexa antes de adicionar um circulo, para evitar que tenha dois com mesmo indice
        Figura.reindexaPontos(2, f.getIndex_poligono());
        Figura.indexCirculos.add(f.getIndex_poligono(), f);

        Figura.indexCirculos.get(f.getIndex_poligono()).getView().setVisibility(View.VISIBLE);
        f.getView().setVisibility(View.VISIBLE);
        Figura.poligono_editando.getView().invalidate();
    }
    public void redo()
    {
        execute();
    }

}



*/







