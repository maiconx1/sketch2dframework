package com.imobilis.sketch2dframework;

/**
 * Created by Alexsander on 18/08/2016.
 */

import android.app.Activity;
import android.graphics.Color;
import android.graphics.Point;
import android.view.View;
import android.widget.FrameLayout;

import java.util.ArrayList;

/**
 * Created by Alexsander on 18/08/2016.
 */
public class StartEditarArestaCommand implements Command{
    private Figura f;
    private Activity act;
    private SketchParent parent;
    private Point tamanho;

    public StartEditarArestaCommand(Figura f, Activity act, SketchParent parent, Point tamanho) {
        this.f = f;
        this.act = act;
        this.parent = parent;
        this.tamanho = tamanho;
    }

    public void execute()
    {
        ArrayList<Linha> linhas = new ArrayList<>();

        for(int i=0;i<f.getPontos().size()-1;i++)
        {
            Point a = f.getPontos().get(i);
            Point b = f.getPontos().get(i+1);
            ArrayList<Point> pontos = new ArrayList<>();
            pontos.add(a);
            pontos.add(b);
            Configuracoes c = new Configuracoes();
            c.setCor(Color.GREEN);
            linhas.add(Sketch2D.desenhaLinha(act, parent, pontos, true, c));
            ((Linha)Sketch2D.getFiguras().get(Sketch2D.getFiguras().size()-1)).setIndex_poligono(i);

        }
        Configuracoes c = new Configuracoes();
        c.setCor(Color.GREEN);
        Point a = f.getPontos().get(f.getPontos().size()-1);
        Point b = f.getPontos().get(0);
        ArrayList<Point> pontos = new ArrayList<>();
        pontos.add(a);
        pontos.add(b);
        linhas.add(Sketch2D.desenhaLinha(act, parent, pontos, true, c));
        ((Linha)Sketch2D.getFiguras().get(Sketch2D.getFiguras().size()-1)).setIndex_poligono(linhas.size() - 1);

        f.getView().setLayoutParams(new FrameLayout.LayoutParams(tamanho.x, tamanho.y));

        Figura.linhas_clip = linhas;
        Figura.poligono_editando =f;
        Figura.clip=true;
    }

    public void undo() {
        if(Figura.clip)
        {
            Figura.clip=false;
            while(Figura.linhas_clip.size()>0)
            {
                Sketch2D.removeDesenho(Figura.linhas_clip.get(0));
                Figura.linhas_clip.remove(0);
            }
        }
    }
    public void redo()
    {
        execute();
    }

    public String showName()
    {
        return "START EDITAR ARESTA";
    }

}



