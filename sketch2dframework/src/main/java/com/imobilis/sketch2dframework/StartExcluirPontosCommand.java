package com.imobilis.sketch2dframework;

/**
 * Created by Alexsander on 18/08/2016.
 */

import android.app.Activity;
import android.graphics.Color;
import android.graphics.Point;
import android.util.Log;
import android.widget.FrameLayout;

import java.util.ArrayList;

/**
 * Created by Alexsander on 18/08/2016.
 */
public class StartExcluirPontosCommand implements Command{
    private Figura f;
    private Activity act;
    private SketchParent parent;
    private boolean endModeUndo;

    public StartExcluirPontosCommand(Figura f,Activity act, SketchParent parent,boolean endModeUndo) {
        this.f = f;
        this.act = act;
        this.parent = parent;
        this.endModeUndo = endModeUndo;
    }

    public void execute()
    {
        ArrayList<Circulo> circulos = new ArrayList<>();
        if(endModeUndo)
        {
            //Pois o metodo endMode ja adiciona os indexCirculos anteriores entao é so recuperar;
            circulos = Figura.indexCirculos;
        }
        else
        {
            for(int i=0;i<f.getPontos().size();i++)
            {
                Point p = f.getPontos().get(i);
                ArrayList<Point> ps = new ArrayList<>();
                ps.add(p);
                Circulo c = new Circulo(act,ps,10,true);
                c.setIndex_poligono(i);
                c.setTipoExcluir(true);
                c.getConfiguracoes().setCor(Color.RED);
                c.getConfiguracoes().setEstilo(Configuracoes.LINHA);
                c.getConfiguracoes().setTamLinha(1);
                circulos.add(c);
                Sketch2D.desenhaFigura(act,c,parent);

            }
        }
        Sketch2D.setDistanciaParaLinha(0);
        Figura.removendo_pontos=true;
        Figura.indexCirculosSize0 = circulos.size();
        Figura.indexCirculos = circulos;
        Figura.poligono_editando = f;
    }

    public void undo() {
        if(Figura.indexCirculosSize0>0)
        {
            Log.d("Ponto undo"," removendo todos size = "+Figura.indexCirculosSize0);
            for(int i=0;i<Figura.indexCirculosSize0;i++)
            {
                Sketch2D.removeDesenho(Sketch2D.getFiguras().size()-1);
            }
            Figura.indexCirculosSize0=-1;
            Figura.removendo_pontos=false;
        }
    }
    public void redo()
    {
        execute();
    }

    public String showName()
    {
        return "START EXCLUIR PONTOS";
    }

}



