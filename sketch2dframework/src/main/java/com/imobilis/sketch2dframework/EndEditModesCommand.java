package com.imobilis.sketch2dframework;

import android.app.Activity;
import android.graphics.Point;

import java.util.ArrayList;

/**
 * Created by Alexsander on 30/08/2016.
 */
public class EndEditModesCommand implements Command {

    private Figura f;
    private Activity act;
    private SketchParent parent;
    private Point tamanho;
    private ArrayList<Circulo> circulos_exclusao;
    public static final int EDITAR_ARESTAS = 1;
    public static final int EXCLUIR_ARESTAS=2;
    private int mode=EDITAR_ARESTAS;
    public EndEditModesCommand(int mode,Figura f, Activity act, SketchParent parent, Point tamanho)
    {
        this.mode = mode;
        this.f = f;
        this.act = act;
        this.parent = parent;
        this.tamanho = tamanho;
    }


    @Override
    public void execute() {
        if(Figura.indexCirculosSize0>0)
        {
            ArrayList<Circulo> aux = new ArrayList<>();
            circulos_exclusao = new ArrayList<>();
            for(int i=0;i<Figura.indexCirculosSize0;i++)
            {
                Circulo c = ((Circulo)(Sketch2D.removeDesenho(Sketch2D.getFiguras().size() - 1)));
               // aux.add(c);
                circulos_exclusao.add(c);
            }
            Figura.indexCirculosSize0=-1;
            Figura.removendo_pontos=false;
        }

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

    @Override
    public void undo() {
        if(mode==EDITAR_ARESTAS)
        {
            new StartEditarArestaCommand(f,act,parent,tamanho).execute();
        }
        if(mode==EXCLUIR_ARESTAS)
        {
            //DEVEM SER ADICIONADOS AO SKETCH2D DE TRAS PRA FRENTE POIS COMO FORAM REMOVIDOS DE TRAS PRA FRENTE ESTAO NA ORDEM INVERSA DO DESENHADO.
            for(int i=circulos_exclusao.size()-1;i>=0;i--)
            {

                Circulo f = circulos_exclusao.get(i);
                f.setTipoExcluir(true);
                Sketch2D.desenhaCirculo(f.getActivity(), parent, f.getPontos().get(0), f.getRaio(), true, f.getConfiguracoes());
                Sketch2D.getFiguras().get(Sketch2D.getFiguras().size()-1).getView().setVisibility(f.getView().getVisibility());
                ((Circulo)Sketch2D.getFiguras().get(Sketch2D.getFiguras().size()-1)).setTipoExcluir(true);
                ((Circulo)Sketch2D.getFiguras().get(Sketch2D.getFiguras().size()-1)).setIndex_poligono(f.getIndex_poligono());

            }
            Figura.indexCirculos = circulos_exclusao;
            new StartExcluirPontosCommand(f,act,parent,true).execute();
        }
    }

    @Override
    public void redo() {
        execute();
    }

    @Override
    public String showName() {
        return "END EDIT MODES COMMAND";
    }
}
