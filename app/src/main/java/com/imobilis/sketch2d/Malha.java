package com.imobilis.sketch2d;

import android.graphics.Point;

/**
 * Criado por Maicon em 07/06/2016.
 */
public class Malha
{
	public static final int ESTAGIADA = 0, QUADRATICA = 1;
	private int tipo, qtdLinha, qtdColuna;
	private double distLinha, distColuna;
	private Point inicio, fim;
	private boolean direcao;
	private double angulo, hipotenusa;
	private double dx, dy;

	public Malha(int tipo, double distLinha, double distColuna, Point inicio, Point fim, boolean direcao, int qtdLinha, int qtdColuna)
	{
		this.tipo = tipo;
		this.distLinha = distLinha;
		this.distColuna = distColuna;
		this.inicio = inicio;
		this.fim = fim;
		this.direcao = direcao;
		double dy = fim.y - inicio.y;
		double dx = fim.x - inicio.x;
		if(fim.x == inicio.x) angulo = Math.PI/2;
		else angulo = Math.atan(dy/dx);
		//hipotenusa = Math.sqrt(Math.pow(fim.y - inicio.y, 2) + Math.pow(fim.x - inicio.x, 2));
		hipotenusa = distColuna;
		dx = hipotenusa*Math.cos(angulo);
		dy = hipotenusa*Math.sin(angulo);
		this.qtdLinha = qtdLinha;
		this.qtdColuna = qtdColuna;
	}

	public double getDx()
	{
		return dx;
	}

	public void setDx(double dx)
	{
		this.dx = dx;
	}

	public double getDy()
	{
		return dy;
	}

	public void setDy(double dy)
	{
		this.dy = dy;
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

	public int getQtdLinha()
	{
		return qtdLinha;
	}

	public void setQtdLinha(int qtdLinha)
	{
		this.qtdLinha = qtdLinha;
	}

	public int getQtdColuna()
	{
		return qtdColuna;
	}

	public void setQtdColuna(int qtdColuna)
	{
		this.qtdColuna = qtdColuna;
	}

	public double getAngulo()
	{
		return angulo;
	}

	public void setAngulo(double angulo)
	{
		this.angulo = angulo;
	}

	public double getHipotenusa()
	{
		return hipotenusa;
	}

	public void setHipotenusa(double hipotenusa)
	{
		this.hipotenusa = hipotenusa;
	}
}
