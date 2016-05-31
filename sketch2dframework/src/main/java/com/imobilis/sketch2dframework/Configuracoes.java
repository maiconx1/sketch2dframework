package com.imobilis.sketch2dframework;

import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

/**
 * Criado por Maicon em 26/04/2016.
 */
public class Configuracoes implements Parcelable
{
	private DashPathEffect dashPathEffect;
	private Paint.Style style;
	private int tamLinha;
	private boolean antiAlias, pontilhado;
	private Paint paint;
	private int cor, alpha;
	private float escala;
	private int zoom;
	private int estilo;
	public static int corPadrao = Color.BLACK, alphaPadrao = 255, tamLinhaPadrao = 1, estiloPadrao = Configuracoes.PREENCHIDO;
	public static float escalaPadrao = 1;
	public static boolean pontilhadoPadrao = false, antiAliasPadrao = true;

	public static final int PREENCHIDO = 0, LINHA = 1;

	/**
	 * Cria uma configuração com os valores padrões setados.
	 */
	public Configuracoes()
	{
		//setPontilhado(false);
		setPontilhado(Configuracoes.pontilhadoPadrao);
		//setEstilo(Configuracoes.PREENCHIDO);
		setEstilo(Configuracoes.estiloPadrao);
		//setTamLinha(1);
		setTamLinha(Configuracoes.tamLinhaPadrao);
		//setAntiAlias(true);
		setAntiAlias(Configuracoes.antiAliasPadrao);
		//setCor(Color.BLACK);
		setCor(Configuracoes.corPadrao);
		//setAlpha(255);
		setAlpha(Configuracoes.alphaPadrao);
		//setEscala(1);
		setEscala(Configuracoes.escalaPadrao);

		setZoom(1);
		setPaint();
	}

	/**
	 * Cria uma configuração de acordo com os parâmetros passados.
	 *
	 * @param pontilhado define se a linha é ou não pontilhada. Não funciona para estilos preenchidos.
	 * @param estilo define se a figura será desenhada em forma de linha ou preenchida.
	 * @param tamLinha define o tamanho da linha desenhada.
	 * @param antiAlias define se a figura terá anti alias ou não.
	 * @param cor define a cor da figura.
	 * @param alpha define a transparência da figura (transparente 0 - 255 opaco).
	 */
	public Configuracoes(boolean pontilhado, int estilo, int tamLinha, boolean antiAlias, int cor, int alpha)
	{
		setPontilhado(pontilhado);
		setEstilo(estilo);
		setTamLinha(tamLinha);
		setAntiAlias(antiAlias);
		setCor(cor);
		setAlpha(alpha);
		setEscala(1);
		setZoom(1);
		setPaint();
	}

	/**
	 * Cria uma configuração de acordo com os parâmetros passados.
	 *
	 * @param pontilhado define se a linha é ou não pontilhada. Não funciona para estilos preenchidos.
	 * @param estilo define se a figura será desenhada em forma de linha ou preenchida.
	 * @param tamLinha define o tamanho da linha desenhada.
	 * @param antiAlias define se a figura terá anti alias ou não.
	 * @param cor define a cor da figura.
	 * @param alpha define a transparência da figura (transparente 0 - 255 opaco).
	 * @param escala define a escala da figura.
	 * @param zoom define o zoom na figura.
	 */
	public Configuracoes(boolean pontilhado, int estilo, int tamLinha, boolean antiAlias, int cor, int alpha, int escala, int zoom)
	{
		this(pontilhado, estilo, tamLinha, antiAlias, cor, alpha);
		setEscala(escala);
		setZoom(zoom);
	}

	protected Configuracoes(Parcel in)
	{
		tamLinha = in.readInt();
		antiAlias = in.readByte() != 0;
		pontilhado = in.readByte() != 0;
		cor = in.readInt();
		alpha = in.readInt();
		escala = in.readFloat();
		zoom = in.readInt();
		estilo = in.readInt();
	}

	public static void setAlphaPadrao(int alphaPadrao)
	{
		Configuracoes.alphaPadrao = alphaPadrao;
	}

	public static void setTamLinhaPadrao(int tamLinhaPadrao)
	{
		Configuracoes.tamLinhaPadrao = tamLinhaPadrao;
	}

	public static void setEstiloPadrao(int estiloPadrao)
	{
		Configuracoes.estiloPadrao = estiloPadrao;
	}

	public static void setEscalaPadrao(float escalaPadrao)
	{
		Configuracoes.escalaPadrao = escalaPadrao;
	}

	public static void setPontilhadoPadrao(boolean pontilhadoPadrao)
	{
		Configuracoes.pontilhadoPadrao = pontilhadoPadrao;
	}

	public static void setAntiAliasPadrao(boolean antiAliasPadrao)
	{
		Configuracoes.antiAliasPadrao = antiAliasPadrao;
	}

	public static void setCorPadrao(int corPadrao)
	{
		Configuracoes.corPadrao = corPadrao;
	}

	public static final Creator<Configuracoes> CREATOR = new Creator<Configuracoes>()
	{
		@Override
		public Configuracoes createFromParcel(Parcel in)
		{
			return new Configuracoes(in);
		}

		@Override
		public Configuracoes[] newArray(int size)
		{
			return new Configuracoes[size];
		}
	};

	public void setPontilhado(boolean pontilhado)
	{
		this.pontilhado = pontilhado;
		if(pontilhado)
		{
			setDashPathEffect(new DashPathEffect(new float[]{5,5}, (float)1.0));
		}
		else
		{
			dashPathEffect = null;
		}
	}

	public boolean isPontilhado()
	{
		return pontilhado;
	}

	public void setPaint()
	{
		paint = new Paint();
		paint.setPathEffect(getDashPathEffect());
		paint.setStyle(getStyle());
		paint.setStrokeWidth(getTamLinha());
		paint.setAntiAlias(isAntiAlias());
		paint.setColor(getCor());
		//paint.setAlpha(getAlpha());
	}

	public DashPathEffect getDashPathEffect()
	{
		return dashPathEffect;
	}

	public void setDashPathEffect(DashPathEffect dashPathEffect)
	{
		this.dashPathEffect = dashPathEffect;
	}

	public Paint.Style getStyle()
	{
		return style;
	}

	public void setStyle(Paint.Style style)
	{
		this.style = style;
	}

	public int getTamLinha()
	{
		return tamLinha;
	}

	public void setTamLinha(int tamLinha)
	{
		if(tamLinha < 1)
		{
			tamLinha = 1;
		}
		this.tamLinha = tamLinha;
	}

	public boolean isAntiAlias()
	{
		return antiAlias;
	}

	public void setAntiAlias(boolean antiAlias)
	{
		this.antiAlias = antiAlias;
	}

	public Paint getPaint()
	{
		setPaint();
		return paint;
	}

	public void setCor(int cor)
	{
		this.cor = cor;
	}

	public int getCor()
	{
		return cor;
	}

	public void setAlpha(int alpha)
	{
		String hexColor = String.format("#%06X", (0xFFFFFF & getCor()));
		String hexAlpha = String.format("#%06X", (0xFF & alpha));
		String hex = "#" + hexAlpha.substring(5, 7) + hexColor.substring(1, 7);
		int cor = Color.parseColor(hex);
		setCor(cor);
		this.alpha = alpha;
	}

	public int getAlpha()
	{
		return alpha;
	}

	public void setEscala(float escala)
	{
		this.escala = escala;
	}

	public float getEscala()
	{
		return escala;
	}

	public void setZoom(int zoom)
	{
		this.zoom = zoom;
	}

	public int getZoom()
	{
		return zoom;
	}

	public void setEstilo(int estilo)
	{
		this.estilo = estilo;
		switch(estilo)
		{
			default:
			case Configuracoes.PREENCHIDO:
				style = Paint.Style.FILL_AND_STROKE;
				break;
			case Configuracoes.LINHA:
				style = Paint.Style.STROKE;
				break;
		}
	}

	public int getEstilo()
	{
		return estilo;
	}

	@Override
	public int describeContents()
	{
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags)
	{
		dest.writeInt(tamLinha);
		dest.writeByte((byte) (antiAlias ? 1 : 0));
		dest.writeByte((byte) (pontilhado ? 1 : 0));
		dest.writeInt(cor);
		dest.writeInt(alpha);
		dest.writeFloat(escala);
		dest.writeInt(zoom);
		dest.writeInt(estilo);
	}
}
