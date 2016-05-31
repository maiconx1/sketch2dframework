package com.imobilis.sketch2d;

import android.graphics.Color;
import android.graphics.Point;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.FrameLayout;
import android.widget.NumberPicker;
import android.widget.Spinner;

import com.imobilis.sketch2dframework.Configuracoes;
import com.imobilis.sketch2dframework.Figura;
import com.imobilis.sketch2dframework.Sketch2D;
import com.rarepebble.colorpicker.ColorPickerView;

import java.util.ArrayList;

/**
 * Criado por Maicon em 02/05/2016.
 */
public class ConfActivity extends AppCompatActivity implements NumberPicker.OnValueChangeListener
{
	private Configuracoes configuracoes;
	private ColorPickerView picker;
	private Figura f;
	private int figura;
	private float raio;
	private ArrayList<Point> pontos;
	private int index;

	public static final int CIRCULO = 0, LINHA = 1, POLIGONO = 2;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_configuracoes);
		getSupportActionBar().setDisplayShowTitleEnabled(false);
		configuracoes = getIntent().getParcelableExtra("CONFIGURACAO");
		figura = getIntent().getIntExtra("FIGURA", ConfActivity.LINHA);
		String allPoints = getIntent().getStringExtra("PONTOS");
		raio = getIntent().getFloatExtra("RAIO", 0);
		index = getIntent().getIntExtra("INDEX", 0);
		findViewById(R.id.lnColorPicker).setVisibility(View.GONE);
		picker = (ColorPickerView)findViewById(R.id.colorPicker);
		setup();


		FrameLayout frm = (FrameLayout)findViewById(R.id.frmDesenho);
		pontos = new ArrayList<>();
		for(String ponto : allPoints.split("//"))
		{
			pontos.add(new Point(Integer.parseInt(ponto.split("/")[0]), Integer.parseInt(ponto.split("/")[1])));
		}
		Point menorXY = getMenoresPontos(pontos);
		int difX = (int)(menorXY.x - raio), difY = (int)(menorXY.y - raio);
		for(Point p : pontos)
		{
			p.x -= difX;
			p.y -= difY;
		}
		switch(figura)
		{
			case ConfActivity.CIRCULO:
				Sketch2D.desenhaCirculo(this, frm, new Point(pontos.get(0)), raio, false, configuracoes);
				break;
			default:
			case ConfActivity.LINHA:
				Sketch2D.desenhaLinha(this, frm, pontos, false, configuracoes);
				break;
			case ConfActivity.POLIGONO:
				Sketch2D.desenhaPoligono(this, frm, pontos, false, configuracoes);
				break;
		}
		f = Sketch2D.getFiguras().get(Sketch2D.getFiguras().size() - 1);
	}

	@Override
	public void onBackPressed()
	{
		if(findViewById(R.id.lnColorPicker).getVisibility() == View.VISIBLE)
		{
			findViewById(R.id.lnColorPicker).setVisibility(View.GONE);
		}
		else
		{
			super.onBackPressed();
		}
	}

	private Point getMenoresPontos(ArrayList<Point> pontos)
	{
		Point menor = new Point(pontos.get(0).x, pontos.get(0).y);
		for(Point p : pontos)
		{
			Log.d("CONFACTIVITY", "PONTO: " + p);
			if(menor.x > p.x)
			{
				Log.d("CONFACTIVITY", menor.x + " > " + p.x);
				menor.x = p.x;
			}
			if(menor.y > p.y)
			{
				menor.y = p.y;
			}
		}
		Log.d("CONFACTIVITY: ", "MENOR PONTO = " + menor);
		return menor;
	}

	private Point getMaioresPontos(ArrayList<Point> pontos)
	{
		Point maior = new Point(pontos.get(0).x, pontos.get(0).y);
		for(Point p : pontos)
		{
			if(maior.x < p.x)
			{
				maior.x = p.x;
			}
			if(maior.y < p.y)
			{
				maior.y = p.y;
			}
		}
		return maior;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.menu_conf, menu);
		return true;
	}

	private void setup()
	{
		((CheckBox)findViewById(R.id.chkPontilhado)).setChecked(configuracoes.isPontilhado());
		NumberPicker pck = (NumberPicker)findViewById(R.id.pckLinha);
		pck.setMinValue(1);
		pck.setMaxValue(30);
		pck.setValue(configuracoes.getTamLinha());
		pck.setOnValueChangedListener(this);
		((CheckBox)findViewById(R.id.chkAlias)).setChecked(configuracoes.isAntiAlias());
		final String[] str={getString(R.string.preenchido), getString(R.string.linha)};
		ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, str);
		spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		((Spinner)findViewById(R.id.spnEstilo)).setAdapter(spinnerArrayAdapter);
		((Spinner) findViewById(R.id.spnEstilo)).setSelection(configuracoes.getEstilo());
		((Spinner)findViewById(R.id.spnEstilo)).setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
		{
			@Override
			public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
			{
				configuracoes.setEstilo(position);
				atualizaDesenho();
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent)
			{

			}
		});
		findViewById(R.id.lnCor).setBackgroundColor(configuracoes.getCor());
		configuracoes = new Configuracoes(configuracoes.isPontilhado(), configuracoes.getEstilo(), configuracoes.getTamLinha(), configuracoes.isAntiAlias(), configuracoes.getCor(), configuracoes.getAlpha());
	}

	@Override
	public void onResume()
	{
		super.onResume();
		Log.d("CONFACTIVITY: ", "INICIA RESUME");
		//linha.getView().invalidate();
	}

	private void atualizaDesenho()
	{
		Point menor = getMenoresPontos(pontos), maior = getMaioresPontos(pontos);
		/*FrameLayout.LayoutParams lp = new FrameLayout.LayoutParams(maior.x - menor.x - 100, maior.y - menor.y - 100);
		f.getView().setLayoutParams(lp);
		f.getView().setX(100);
		f.getView().setY(100);*/
		int med[] = Figura.tamLayout(f);
		f.getView().setLayoutParams(new FrameLayout.LayoutParams(med[0], med[1]));
		//f.getView().invalidate();
		Log.d("CONFACTIVITY: ", "X = " + f.getView().getX() + "//Y = " + f.getView().getY());

	}

	public void pontilhado(View view)
	{
		f.getConfiguracoes().setPontilhado(((CheckBox) view).isChecked());
		configuracoes.setPontilhado(((CheckBox) view).isChecked());
		atualizaDesenho();
	}

	public void antiAlias(View view)
	{
		f.getConfiguracoes().setAntiAlias(((CheckBox) view).isChecked());
		configuracoes.setAntiAlias(((CheckBox) view).isChecked());
		atualizaDesenho();
	}

	public void selecionaCor(View view)
	{
		picker.setColor(((ColorDrawable) view.getBackground()).getColor());
		findViewById(R.id.lnColorPicker).setVisibility(View.VISIBLE);
	}

	@Override
	public void onValueChange(NumberPicker picker, int oldVal, int newVal)
	{
		f.getConfiguracoes().setTamLinha(newVal);
		configuracoes.setTamLinha(newVal);
		atualizaDesenho();
	}

	public void escolhe(View view)
	{
		findViewById(R.id.lnColorPicker).setVisibility(View.GONE);
		Color color = new Color();
		findViewById(R.id.lnCor).setBackgroundColor(picker.getColor());

		f.getConfiguracoes().setCor(picker.getColor());
		configuracoes.setCor(picker.getColor());
		Log.d("CONFACTIVITY: ", "ALPHA = " + picker.getAlpha());
		atualizaDesenho();
	}

	public void cancela(View view)
	{
		findViewById(R.id.lnColorPicker).setVisibility(View.GONE);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item)
	{
		switch(item.getItemId())
		{
			case R.id.cancelar:
				super.onBackPressed();
				break;
			case R.id.salvar:
				Sketch2D.getFiguras().get(index).setConfiguracoes(configuracoes);
				int med[] = Figura.tamLayout(Sketch2D.getFiguras().get(index));
				Sketch2D.getFiguras().get(index).getView().setLayoutParams(new FrameLayout.LayoutParams(med[0], med[1]));
				super.onBackPressed();
				break;
		}
		return false;
	}
}
