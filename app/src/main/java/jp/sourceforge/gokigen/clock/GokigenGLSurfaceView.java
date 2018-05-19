package jp.sourceforge.gokigen.clock;

import android.content.Context;
import android.graphics.PixelFormat;
import android.opengl.GLSurfaceView;
import android.util.AttributeSet;

public class GokigenGLSurfaceView extends GLSurfaceView
{
    private SquareDrawer mSquareDrawer = null;
    
    /**
     *  コンストラクタ
     *
     */
    public GokigenGLSurfaceView(Context context)
    {
        super(context);
        initializeSelf(context, null);
    }

    /**
     *  コンストラクタ (レイアウトマネージャ経由で呼び出されたときに利用)
     *
     */
    public GokigenGLSurfaceView(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        try
        {
            initializeSelf(context, attrs);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    /**
     *  軸ホルダーを設定する
     *
     */
    public void setOrientationHolder(IOrientationHolder holder)
    {
        mSquareDrawer.setOrientationHolder(holder);
    }

    /**
     *   クラスの初期化処理...レンダラを設定する
     *
     */
    private void initializeSelf(Context context, AttributeSet attrs)
    {
        //setEGLConfigChooser(false);        // これだと画面透過はダメ！

        setEGLContextClientVersion(1);
        super.setEGLConfigChooser(8,8,8, 8, 16, 0);
    	//setEGLConfigChooser(5,6,5, 8, 16, 0);
        setFocusable(true);
        setFocusableInTouchMode(true);

        // OpenGLレンダラ用のユーティリティを生成
        GokigenGLUtilities gLutil = new GokigenGLUtilities(context);

        // レンダラを設定する
        mSquareDrawer = new SquareDrawer(gLutil);
        GokigenGLRenderer renderer = new GokigenGLRenderer(context, mSquareDrawer);
        renderer.setNumberDrawer(new NumberDrawer(gLutil, new TimeValueProvider()));
        setRenderer(renderer);

        // 画面を透過させる
        getHolder().setFormat(PixelFormat.TRANSLUCENT);
    }
}
