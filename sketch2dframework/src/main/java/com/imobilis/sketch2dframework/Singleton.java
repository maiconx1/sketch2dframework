package com.imobilis.sketch2dframework;

import java.util.ArrayList;

/**
 * Criado por Maicon em 25/04/2016.
 */
public class Singleton
{
	private ArrayList<Figura> figuras;
	private static Singleton instance = null;

	private Singleton()
	{
		figuras = new ArrayList<>();
	}

	public static Singleton getInstance()
	{
		if(Singleton.instance == null) instance = new Singleton();
		return Singleton.instance;
	}

	public ArrayList<Figura> getFiguras()
	{
		return figuras;
	}

	public void addFigura(Figura figura)
	{
		this.figuras.add(figura);
	}

	public void remFigura(int index)
	{
		this.figuras.remove(index);
	}

	public Figura getFigura(int index)
	{
		return this.figuras.get(index);
	}
}
