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
		Circulo c = Sketch2D.desenhaCirculo(NovaNovaMain.this, parent, new Point((100), (100)), 11.0f, false, new Configuracoes(false, Configuracoes.LINHA, 1, true, cores.get(0), 255));
		c.setCruz(true);
		for(Figura f : Sketch2D.getFiguras())
		{
			if(f instanceof Circulo)
				Log.d("PONTO INICIAL: ", "(" + f.getView().getX() + ", " + f.getView().getY() + ")");
		}

        parent.invalidate();
	}
}
