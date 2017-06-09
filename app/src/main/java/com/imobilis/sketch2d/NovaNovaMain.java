package com.imobilis.sketch2d;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;

import com.imobilis.sketch2dframework.*;

import java.util.ArrayList;

/**
 * Created by Maicon on 22/11/16.
 */

public class NovaNovaMain extends AppCompatActivity
{
	public static FrameLayout parent;
	public static FrameLayout parent2;
    public Poligono poligono;

	public static ArrayList<Figura> figuras;
    public void callPreview(View v)
    {
		View view = parent;

		Bitmap bitmap = Bitmap.createBitmap(
				view.getWidth(),
				view.getHeight(),
				Bitmap.Config.ARGB_8888
		);
		Canvas canvas = new Canvas(bitmap);
		view.draw(canvas);

        canvas.drawBitmap(bitmap,0,0,new Paint());
		parent2.draw(canvas);




    }
	@Override
	protected void onCreate(Bundle bundle)
	{
		super.onCreate(bundle);
        Sketch2D.commandManager = new CommandManager();
		Sketch2D.context = NovaNovaMain.this;
		setContentView(R.layout.activity_main);
		parent = (FrameLayout)findViewById(R.id.lnDesenho);


        //desenhoAlexsander();
        desenhosMaicon();
    }

    public void desenhosMaicon()
    {
        Point pontoCirculo = new Point(500, 400);
        Circulo circulo = Sketch2D.desenhaCirculo(NovaNovaMain.this, parent, pontoCirculo, 30, true);
        circulo.setMostraTexto(true);
        //circulo.setPontoNoCentro(true);
        //circulo.setTexto("15");
        //circulo.setConfTexto(circulo.getConfiguracoes());

        //int tamanhoLabel = (int) (10 * 1.6 * 18 / 10);

        int tamanhoLabel = 20;

        circulo.setTamTexto(tamanhoLabel);
        circulo.setConfTexto(new Configuracoes(false, Configuracoes.PREENCHIDO, 1, true, Color.RED, 200));
        circulo.getView().invalidate();

        Sketch2D.desenhaTexto(this, parent, new Point(100, 100), "15", 20, Color.RED, true, false, false);
    }
    public void desenhoAlexsander()
    {

/*
        Sketch2D.desenhaCirculo(this, parent, new Point(100, 100),60, false, new Configuracoes(false, Configuracoes.PREENCHIDO, 1, true, Color.WHITE, 250));

        ArrayList<Point> ps = new ArrayList<>();
        ps.add(new Point(100,100));
        Texto t = new Texto(this,ps,"A",40,Color.BLACK,false);
        Sketch2D.desenhaTextoCentrado(t,parent);


        Sketch2D.desenhaCirculo(this, parent, new Point(300, 300),60, false, new Configuracoes(false, Configuracoes.PREENCHIDO, 1, true, Color.WHITE, 250));

        ps = new ArrayList<>();
        ps.add(new Point(300,300));
        t = new Texto(this,ps,"A",40,Color.BLACK,true);
        Sketch2D.desenhaTextoCentrado(t,parent);*/


        /*Sketch2D.desenhaTexto(this,parent,new Point(300,300),"AAA",30, Color.BLACK);
        Sketch2D.desenhaTexto(this,parent,new Point(550,300),"AAA",30, Color.BLACK,true,true,true);
        Sketch2D.desenhaTexto(this,parent,new Point(550,450),"AAA",30, Color.BLACK,true,true,!true);
        Sketch2D.desenhaTexto(this,parent,new Point(650,300),"AAA",30, Color.BLACK,true,!true,true);
        Sketch2D.desenhaTexto(this,parent,new Point(750,300),"AAA",30, Color.BLACK,!true,true,true);*/

        ArrayList<Point> ps = new ArrayList<>();
        ps.add( new Point(300,300));
        Texto t = new Texto(this,ps,"AAA",30, Color.BLACK,true,true,true);
        Sketch2D.desenhaTextoCentrado(t,parent);

        ps = new ArrayList<>();
        ps.add( new Point(400,300));
        Texto t2 = new Texto(this,ps,"AAA",30, Color.BLACK,true,true,!true);
        Sketch2D.desenhaTextoCentrado(t2,parent);
		/*Sketch2D.desenhaPoligonoAberto(this,parent,ps,false);
		ps = new ArrayList<>();
        ps.add(new Point(200,200));
        ps.add(new Point(170,280));
        ps.add(new Point(160,430));
        ps.add(new Point(140,500));
        ps = desloca(ps,300,0);
        for(int i=0;i<ps.size()-1;i++)
        {
            int j = i+1;
            ArrayList<Point> ps2 = new ArrayList<>();
            ps2.add(new Point(ps.get(i)));
            ps2.add(new Point(ps.get(j)));
            Sketch2D.desenhaLinha(this,parent,ps2,false);

        }*/
    }


    public Point pontoAnterior;
    public Figura figuraFormada;
    public Circulo circuloCentro,circuloMovendo;
    public void touch()
    {
        parent.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction())
                {
                    case MotionEvent.ACTION_DOWN:
                        pontoAnterior = new Point((int)event.getX(),(int)event.getY());

                        Configuracoes c1 = new Configuracoes();
                        c1.setEstilo(Configuracoes.PREENCHIDO);
                        circuloCentro = Sketch2D.desenhaCirculo(NovaNovaMain.this,parent,pontoAnterior,4,false,c1);
                        return true;
                    case MotionEvent.ACTION_MOVE:
                        if(figuraFormada!=null)
                            Sketch2D.removeDesenho(figuraFormada);
                        if(circuloMovendo!=null)
                            Sketch2D.removeDesenho(circuloMovendo);

                        Point pontoFuroMag = null;
                        ArrayList<Point> pontos = new ArrayList<Point>();
                        pontos.add(new Point(pontoAnterior));
                        Point finger = new Point((int) event.getX(), (int) event.getY());


                        pontos.add(finger);

                        Configuracoes c = new Configuracoes();
                        c.setTamLinha(1);
                        float dist =(float) Math.sqrt(Math.pow((pontos.get(1).x-pontos.get(0).x),2)+Math.pow((pontos.get(1).y-pontos.get(0).y),2));
                        pontos.set(0, new Point(pontos.get(0).x - 90, pontos.get(0).y - 90));
                        pontos.set(1,new Point(pontos.get(1).x-90,pontos.get(1).y-90));
                        Point aux = pontos.get(0);
                        pontos.set(0, pontos.get(1));
                        pontos.set(1, aux);
                        figuraFormada = Sketch2D.desenhaLinha(NovaNovaMain.this,parent, pontos, false, new Configuracoes(true, Configuracoes.LINHA, 1, true, Sketch2D.corDistancia, 180), true);
                        circuloMovendo = Sketch2D.desenhaCirculo(NovaNovaMain.this,parent, new Point(pontoAnterior), dist, false);

                        return true;
                    case MotionEvent.ACTION_UP:
                        if(figuraFormada!=null)
                            Sketch2D.removeDesenho(figuraFormada);
                        if(circuloMovendo!=null)
                            Sketch2D.removeDesenho(circuloMovendo);
                        if(circuloCentro!=null)
                            Sketch2D.removeDesenho(circuloCentro);
                        circuloMovendo = null;
                        figuraFormada=null;
                        circuloCentro = null;
                        return true;
                }
                return false;
            }
        });
    }
	public ArrayList<Point> desloca(ArrayList<Point> ps,int a,int b)
	{
		for(Point p:ps)
		{
			p.set(p.x+a,p.y+b);
		}
		return ps;
	}

	public void setup()
	{
		figuras = new ArrayList<>();
		ArrayList<Integer> cores = new ArrayList<>();
		cores.add(Color.BLACK);
		cores.add(Color.BLUE);
		cores.add(Color.YELLOW);
		cores.add(Color.GREEN);
		cores.add(Color.RED);
		cores.add(Color.GRAY);
		cores.add(Color.WHITE);
		cores.add(Color.CYAN);
		cores.add(Color.MAGENTA);

		for(int i = 0;i<100;i++)
		{
			for(int j = 0;j<10;j++)
			{
				//Sketch2D.desenhaCirculo(NovaNovaMain.this, parent, new Point((95 * i) + 5, (95 * j) + 5), 60.0f, false, new Configuracoes(false, Configuracoes.LINHA, 1, true, cores.get((i+j)%9), 255));
				//ArrayList<Point> p = new ArrayList<>();
				//p.add(new Point((95*i) + 5, (95 * j) + 5));
				//figuras.add(new Circulo(NovaNovaMain.this, p, 30.0f, false));
			}
		}
        Sketch2D.proporcao = 35;
		Sketch2D.limpaFiguras(Sketch2D.EXCLUI);
		Circulo c = Sketch2D.desenhaCirculo(NovaNovaMain.this, parent, new Point((100), (100)), 22.0f, false, new Configuracoes(false, Configuracoes.LINHA, 1, true, cores.get(0), 255));
		c.setPontoNoCentro(true);
		for(Figura f : Sketch2D.getFiguras())
		{
			if(f instanceof Circulo)
				Log.d("PONTO INICIAL: ", "(" + f.getView().getX() + ", " + f.getView().getY() + ")");
		}

        parent.invalidate();
	}
}
