package com.imobilis.sketch2d;

import android.graphics.Color;
import android.graphics.Point;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.FrameLayout;

import com.imobilis.sketch2dframework.*;

import java.util.ArrayList;

/**
 * Created by Maicon on 22/11/16.
 */

public class NovaNovaMain extends AppCompatActivity
{
	private FrameLayout parent;

	public static ArrayList<Figura> figuras;

	@Override
	protected void onCreate(Bundle bundle)
	{
		super.onCreate(bundle);
		Sketch2D.context = NovaNovaMain.this;
		setContentView(R.layout.activity_main);
		parent = (FrameLayout)findViewById(R.id.lnDesenho);

		setup();
	}

	public void setup()
	{
		ArrayList<Point> pontos = new ArrayList<>();
		pontos.add(new Point(100, 100));
		pontos.add(new Point(400, 400));
		Linha l = Sketch2D.desenhaLinha(this, parent, pontos, false);
		Linha.Equacao eq = new Linha.Equacao(-(1/l.getEquacao().getM()), 250, 250);
		double y1 = eq.getY(400), y2 = eq.getY(100);
		Log.d("EQUACAO", eq.getM() + "//" + eq.getA() + "//" + eq.getB() + "//" + y1 + "//" + y2);
		pontos = new ArrayList<>();
		pontos.add(new Point(100, (int)y2));
		pontos.add(new Point(400, (int)y1));
		Sketch2D.desenhaLinha(this, parent, pontos, false);
		/*figuras = new ArrayList<>();
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
		Sketch2D.limpaFiguras(Sketch2D.EXCLUI);
		Sketch2D.desenhaCirculo(NovaNovaMain.this, parent, new Point((100), (100)), 60.0f, false, new Configuracoes(false, Configuracoes.LINHA, 1, true, cores.get(0), 255));
		for(Figura f : Sketch2D.getFiguras())
		{
			if(f instanceof Circulo)
				Log.d("PONTO INICIAL: ", "(" + f.getView().getX() + ", " + f.getView().getY() + ")");
		}*/
	}
}
