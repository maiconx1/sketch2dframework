package com.imobilis.sketch2d;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
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
		parent2 = (FrameLayout)findViewById(R.id.lnDesenho2);
        //setup();
		Circulo circulo = Sketch2D.desenhaCirculo(this, parent, new Point(400, 300), 110, true, new Configuracoes(true, Configuracoes.LINHA, 1, true, Color.BLUE, 0));
		circulo.setCruz(true);
		circulo.getView().invalidate();

        /*ArrayList<Point> ps = new ArrayList<>();
        ps.add(new Point(100,100));
        Texto t = new Texto(this,ps,"A",20,Color.RED);
        Sketch2D.desenhaTextoCentrado(t,parent);

		ps = new ArrayList<>();
        ps.add(new Point(200,200));
        ps.add(new Point(170,280));
        ps.add(new Point(160,430));
        ps.add(new Point(140,500));

		Sketch2D.desenhaPoligonoAberto(this,parent,ps,false);
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
		c.setCruz(true);
		for(Figura f : Sketch2D.getFiguras())
		{
			if(f instanceof Circulo)
				Log.d("PONTO INICIAL: ", "(" + f.getView().getX() + ", " + f.getView().getY() + ")");
		}

        parent.invalidate();
	}
}
