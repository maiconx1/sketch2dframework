package com.imobilis.sketch2d;

import android.graphics.Point;

/**
 * Criado por Maicon em 07/06/2016.
 */
public class Malha
{
	public static final int ESTAGIADA = 0, QUADRATICA = 1;
	private int tipo;
	private double distLinha, distColuna;
	private Point inicio, fim;
	private boolean direcao;

	public Malha(int tipo, double distLinha, double distColuna, Point inicio, Point fim, boolean direcao)
	{
		this.tipo = tipo;
		this.distLinha = distLinha;
		this.distColuna = distColuna;
		this.inicio = inicio;
		this.fim = fim;
		this.direcao = direcao;
	}

	public int getTipo()
	{
		return tipo;
	}

	public void setTipo(int tipo)
	{
		this.tipo = tipo;
	}

	public double getDistLinha()
	{
		return distLinha;
	}

	public void setDistLinha(double distLinha)
	{
		this.distLinha = distLinha;
	}

	public double getDistColuna()
	{
		return distColuna;
	}

	public void setDistColuna(double distColuna)
	{
		this.distColuna = distColuna;
	}

	public Point getInicio()
	{
		return inicio;
	}

	public void setInicio(Point inicio)
	{
		this.inicio = inicio;
	}

	public Point getFim()
	{
		return fim;
	}

	public void setFim(Point fim)
	{
		this.fim = fim;
	}

	public boolean isDirecao()
	{
		return direcao;
	}

	public void setDirecao(boolean direcao)
	{
		this.direcao = direcao;
	}
}
