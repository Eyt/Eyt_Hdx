package com.eyt.search;


import java.util.LinkedList;
import java.util.Random;
import java.util.Vector;

import android.content.Context;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils; //动画包
import android.view.animation.Interpolator;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.FrameLayout;
import android.widget.TextView;


public class KeywordsFlow extends FrameLayout implements OnGlobalLayoutListener{
	/* FrameLayout对子组件进行定点放置的实现：
	 * 在FrameLayout.LayoutParams上。该类有相关属性为leftMargin及topMargin。
	 * 要将子组件左上角定点放置在其父组件中的(x,y)处，仅需对leftMargin赋值为x，对topMargin赋值为y
    */
	public static final int IDX_X = 0;  
    public static final int IDX_Y = 1;  
    public static final int IDX_TXT_LENGTH = 2;  
    public static final int IDX_DIS_Y = 3;  
    //由外至内的动画
    public static final int ANIMATION_IN = 1;  
    //由内至外的动画
    public static final int ANIMATION_OUT = 2;  
    //位移动画类型：从外围移动到坐标点 
    public static final int OUTSIDE_TO_LOCATION = 1;  
    //位移动画类型：从坐标点移动到外围
    public static final int LOCATION_TO_OUTSIDE = 2;  
    //位移动画类型：从中心点移动到坐标点
    public static final int CENTER_TO_LOCATION = 3;  
    //位移动画类型：从坐标点移动到中心点  
    public static final int LOCATION_TO_CENTER = 4;  
    public static final long ANIM_DURATION = 800l;  //动画的持续时间
    public static final int MAX = 10;  
    public static final int TEXT_SIZE_MAX = 25;  
    public static final int TEXT_SIZE_MIN = 15;  
    private OnClickListener itemClickListener;  
    private static Interpolator interpolator;  
    private static AlphaAnimation animAlpha2Opaque;  
    private static AlphaAnimation animAlpha2Transparent;  
    private static ScaleAnimation animScaleLarge2Normal, animScaleNormal2Large, animScaleZero2Normal,  animScaleNormal2Zero;  
    //存储显示的关键字  
    private Vector<String> vecKeywords;  
    private int width, height;  
    /** 
     * go2Show()中被赋为true，标识开发人员触发其始动画显示
     * 本标识的作用是防止在填充keywrods未完成的过程中获取到width和height后提前启动动画
     * 在show()方法中其被赋值为false
     * 真正能够动画显示的必要条件：width, height不为0 
     */  
    private boolean enableShow;  
    private Random random;  
    /** 
     * @see ANIMATION_IN 
     * @see ANIMATION_OUT 
     * @see OUTSIDE_TO_LOCATION 
     * @see LOCATION_TO_OUTSIDE 
     * @see LOCATION_TO_CENTER 
     * @see CENTER_TO_LOCATION 
     * */  
    private int txtAnimInType, txtAnimOutType;  
    //近一次启动动画显示的时间
    private long lastStartAnimationTime;  
    //动画运行时间
    private long animDuration;  
  
    public KeywordsFlow(Context context, AttributeSet attrs, int defStyle) {  
        super(context, attrs, defStyle);  
        init();  
    }  
  
    public KeywordsFlow(Context context, AttributeSet attrs) {  
        super(context, attrs);  
        init();  
    }  
  
    public KeywordsFlow(Context context) {  
        super(context);  
        init();  
    }  
  
    private void init() {  
        lastStartAnimationTime = 0l;  
        animDuration = ANIM_DURATION;  
        random = new Random();  
        vecKeywords = new Vector<String>(MAX);  
        getViewTreeObserver().addOnGlobalLayoutListener(this);  
        interpolator = AnimationUtils.loadInterpolator(getContext(), android.R.anim.decelerate_interpolator);  
        animAlpha2Opaque = new AlphaAnimation(0.0f, 1.0f);  
        animAlpha2Transparent = new AlphaAnimation(1.0f, 0.0f);  
        animScaleLarge2Normal = new ScaleAnimation(2, 1, 2, 1);  
        animScaleNormal2Large = new ScaleAnimation(1, 2, 1, 2);  
        animScaleZero2Normal = new ScaleAnimation(0, 1, 0, 1);  
        animScaleNormal2Zero = new ScaleAnimation(1, 0, 1, 0);  
    }  
  
    public long getDuration() {  
        return animDuration;  
    }  
  
    public void setDuration(long duration) {  
        animDuration = duration;  
    }  
  
    public boolean feedKeyword(String keyword) {  
        boolean result = false;  
        if (vecKeywords.size() < MAX) {  
            result = vecKeywords.add(keyword);  
        }  
        return result;  
    }  
  
    /** 
     * 初始始动画显示
     * 之前已经存在的TextView将会显示出动画
     *   return ：正常显示动画返回true；反之为false。返回false的原因：
     *         1.时间上不允许，受lastStartAnimationTime的制约； 
     *         2.未获取到width和height
     */  
    public boolean go2Show(int animType) {  
        if (System.currentTimeMillis() - lastStartAnimationTime > animDuration) {  
            enableShow = true;  
            if (animType == ANIMATION_IN) {
                txtAnimInType = OUTSIDE_TO_LOCATION;  
                txtAnimOutType = LOCATION_TO_CENTER;  
            } else if (animType == ANIMATION_OUT) {  
                txtAnimInType = CENTER_TO_LOCATION;  
                txtAnimOutType = LOCATION_TO_OUTSIDE;  
            }  
            disapper();  
            boolean result = show();  
            return result;  
        }  
        return false;  
    }  
  
    private void disapper() {  
        int size = getChildCount();  
        for (int i = size - 1; i >= 0; i--) {  
            final TextView txt = (TextView) getChildAt(i);  
            if (txt.getVisibility() == View.GONE) {  
                removeView(txt);  
                continue;  
            }  
            FrameLayout.LayoutParams layParams = (LayoutParams) txt.getLayoutParams();  
            int[] xy = new int[] { layParams.leftMargin, layParams.topMargin, txt.getWidth() };  
            AnimationSet animSet = getAnimationSet(xy, (width >> 1), (height >> 1), txtAnimOutType);  
            txt.startAnimation(animSet);  
            animSet.setAnimationListener(new AnimationListener() {  
                @Override
				public void onAnimationStart(Animation animation) {  
                }  
  
                @Override
				public void onAnimationRepeat(Animation animation) {  
                }  
  
                @Override
				public void onAnimationEnd(Animation animation) {  
                    txt.setOnClickListener(null);  
                    txt.setClickable(false);  
                    txt.setVisibility(View.GONE);  
                }  
            });  
        }  
    }  
  
    private boolean show() {  
        if (width > 0 && height > 0 && vecKeywords != null && vecKeywords.size() > 0 && enableShow) {  
            enableShow = false;  
            lastStartAnimationTime = System.currentTimeMillis(); 
            //找到中心
            int xCenter = width >> 1, yCenter = height >> 1;  
            //关键字的个数
            int size = vecKeywords.size();  
            int xItem = width / size, yItem = height / size;
             Log.d("ANDROID_LAB", "--------------------------width=" + width +  
             " height=" + height + "  xItem=" + xItem  
             + " yItem=" + yItem + "---------------------------");  
            LinkedList<Integer> listX = new LinkedList<Integer>(), listY = new LinkedList<Integer>();  
            for (int i = 0; i < size; i++) {  
                // 准备随机数，分别对应x/y轴位置  
                listX.add(i * xItem);
                listY.add(i * yItem + (yItem >> 2));
                Log.e("Search", "ListX:"+(i * xItem)+"#listY:"+(i * yItem + (yItem >> 2)));
            }  
  
            LinkedList<TextView> listTxtTop = new LinkedList<TextView>();  
            LinkedList<TextView> listTxtBottom = new LinkedList<TextView>();  
            for (int i = 0; i < size; i++) {  
                String keyword = vecKeywords.get(i);  
                // 随机颜色  
                int ranColor = 0xff000000 | random.nextInt(0x0077ffff);  
                // 随机位置  
                int xy[] = randomXY(random, listX, listY, xItem);  
                // 随机字体大小  
                int txtSize = TEXT_SIZE_MIN + random.nextInt(TEXT_SIZE_MAX - TEXT_SIZE_MIN + 1);  
                // 实例化TextView  
                final TextView txt = new TextView(getContext());  
                txt.setOnClickListener(itemClickListener);  
                txt.setText(keyword);  
                txt.setTextColor(ranColor);  
               txt.setTextSize(TypedValue.COMPLEX_UNIT_SP, txtSize);  
                txt.setShadowLayer(1, 1, 1, 0xdd696969);  
                txt.setGravity(Gravity.CENTER);  
                
  /*  对关键字坐标的随机分配:
   *  设定关键字最多为10个，在布局的X Y轴上各自进行10等分。
   *  每个关键字依照其添加顺序随机各自在X轴和Y轴上选择等分后的10点中的某个点为margin的值。
   *  此值为糙值，需要对X轴进行越界修正，对Y轴进行向中心靠拢修正。
  */
                // 获取文本长度  
                Paint paint = txt.getPaint();  
                int strWidth = (int) Math.ceil(paint.measureText(keyword));  
                xy[IDX_TXT_LENGTH] = strWidth;  
                // 第一次修正:修正x坐标  
                if (xy[IDX_X] + strWidth > width - (xItem >> 1)) {  
                    int baseX = width - strWidth;  
                    // 减少文本右边缘一样的概率  
                    xy[IDX_X] = baseX - xItem + random.nextInt(xItem >> 1);  
                } else if (xy[IDX_X] == 0) {  
                    // 减少文本左边缘一样的概率  
                    xy[IDX_X] = Math.max(random.nextInt(xItem), xItem / 3);  
                }  
                xy[IDX_DIS_Y] = Math.abs(xy[IDX_Y] - yCenter);  
                txt.setTag(xy);  
                if (xy[IDX_Y] > yCenter) {  
                    listTxtBottom.add(txt);  
                } else {  
                    listTxtTop.add(txt);  
                }  
            }  
            attach2Screen(listTxtTop, xCenter, yCenter, yItem);  
            attach2Screen(listTxtBottom, xCenter, yCenter, yItem);  
            return true;  
        }  
        return false;  
    }  
    
    /* 对随机分配的坐标进行向中心靠拢:
     * 操作将修正Y轴坐标。
     *由于随机分配中，可能出现某个关键字在朝中心点方向上的空间中再没有其它关键字了，此时该关键字在Y轴上应该朝中心点靠拢。
    */
  
    // 修正TextView的Y坐标，将其添加到容器上  
    private void attach2Screen(LinkedList<TextView> listTxt, int xCenter, int yCenter, int yItem) {  
        int size = listTxt.size();  
        sortXYList(listTxt, size);  
        for (int i = 0; i < size; i++) {  
            TextView txt = listTxt.get(i);  
            int[] iXY = (int[]) txt.getTag();  
    
            // 第二次修正:修正y坐标  
            int yDistance = iXY[IDX_Y] - yCenter;  
            // 对于靠近中心点的，其值不会大于yItem 
            // 对于可以一路下降到中心点的，调整的大小 
            int yMove = Math.abs(yDistance);  
            inner: for (int k = i - 1; k >= 0; k--) {  
                int[] kXY = (int[]) listTxt.get(k).getTag();  
                int startX = kXY[IDX_X];  
                int endX = startX + kXY[IDX_TXT_LENGTH];  
                // y轴以中心点为分隔线 
                if (yDistance * (kXY[IDX_Y] - yCenter) > 0) {  
                   
                    if (isXMixed(startX, endX, iXY[IDX_X], iXY[IDX_X] + iXY[IDX_TXT_LENGTH])) {  
                        int tmpMove = Math.abs(iXY[IDX_Y] - kXY[IDX_Y]);  
                        if (tmpMove > yItem) {  
                            yMove = tmpMove;  
                        } else if (yMove > 0) {  
                            // 取消默认值  
                            yMove = 0;  
                        }  
         
                        break inner;  
                    }  
                }  
            }  
          
            if (yMove > yItem) {  
                int maxMove = yMove - yItem;  
                int randomMove = random.nextInt(maxMove);  
                int realMove = Math.max(randomMove, maxMove >> 1) * yDistance / Math.abs(yDistance);  
                iXY[IDX_Y] = iXY[IDX_Y] - realMove;  
                iXY[IDX_DIS_Y] = Math.abs(iXY[IDX_Y] - yCenter);  
                // 已经调整过前i个需要再次排  
                sortXYList(listTxt, i + 1);  
            }  
            FrameLayout.LayoutParams layParams = new FrameLayout.LayoutParams(android.view.ViewGroup.LayoutParams.WRAP_CONTENT,  
                    android.view.ViewGroup.LayoutParams.WRAP_CONTENT);  
            layParams.gravity = Gravity.LEFT | Gravity.TOP;  
            layParams.leftMargin = iXY[IDX_X];  
            layParams.topMargin = iXY[IDX_Y];  
            addView(txt, layParams);  
            // 动画  
            AnimationSet animSet = getAnimationSet(iXY, xCenter, yCenter, txtAnimInType);  
            txt.startAnimation(animSet);  
        }  
    }  
    
  /*     动画的实现:
   *     每个TextView的动画都有包括三部分：伸缩动画ScaleAnimation、
   *     透明度渐变动画AlphaAnimation及位移动画TranslateAnimation。
   *     以上三个动画中除了位移动画是独立的，其它两种动画都是可以共用的。三种动画的组合
   *     使用AnimationSet拼装在一起同时作用在TextView上。
  */
    public AnimationSet getAnimationSet(int[] xy, int xCenter, int yCenter, int type) {  
        AnimationSet animSet = new AnimationSet(true);  
        animSet.setInterpolator(interpolator);  
        if (type == OUTSIDE_TO_LOCATION) {  
            animSet.addAnimation(animAlpha2Opaque);  
            animSet.addAnimation(animScaleLarge2Normal);  
            TranslateAnimation translate = new TranslateAnimation(  
                    (xy[IDX_X] + (xy[IDX_TXT_LENGTH] >> 1) - xCenter) << 1, 0, (xy[IDX_Y] - yCenter) << 1, 0);  
            animSet.addAnimation(translate);  
        } else if (type == LOCATION_TO_OUTSIDE) {  
            animSet.addAnimation(animAlpha2Transparent);  
            animSet.addAnimation(animScaleNormal2Large);  
            TranslateAnimation translate = new TranslateAnimation(0,  
                    (xy[IDX_X] + (xy[IDX_TXT_LENGTH] >> 1) - xCenter) << 1, 0, (xy[IDX_Y] - yCenter) << 1);  
            animSet.addAnimation(translate);  
        } else if (type == LOCATION_TO_CENTER) {  
            animSet.addAnimation(animAlpha2Transparent);  
            animSet.addAnimation(animScaleNormal2Zero);  
            TranslateAnimation translate = new TranslateAnimation(0, (-xy[IDX_X] + xCenter), 0, (-xy[IDX_Y] + yCenter));  
            animSet.addAnimation(translate);  
        } else if (type == CENTER_TO_LOCATION) {  
            animSet.addAnimation(animAlpha2Opaque);  
            animSet.addAnimation(animScaleZero2Normal);  
            TranslateAnimation translate = new TranslateAnimation((-xy[IDX_X] + xCenter), 0, (-xy[IDX_Y] + yCenter), 0);  
            animSet.addAnimation(translate);  
        }  
        animSet.setDuration(animDuration);  
        return animSet;  
    }  
  
    /** 
     * 根据与中心点的距离由近到远进行冒泡排序 
     *起始位置
     *待排序的数组  
     */  
    private void sortXYList(LinkedList<TextView> listTxt, int endIdx) {  
        for (int i = 0; i < endIdx; i++) {  
            for (int k = i + 1; k < endIdx; k++) {  
                if (((int[]) listTxt.get(k).getTag())[IDX_DIS_Y] < ((int[]) listTxt.get(i).getTag())[IDX_DIS_Y]) {  
                    TextView iTmp = listTxt.get(i);  
                    TextView kTmp = listTxt.get(k);  
                    listTxt.set(i, kTmp);  
                    listTxt.set(k, iTmp);  
                }  
            }  
        }  
    }  
  
    //A线段与B线段所代表的直线在X轴映射上是否有交集
    private boolean isXMixed(int startA, int endA, int startB, int endB) {  
        boolean result = false;  
        if (startB >= startA && startB <= endA) {  
            result = true;  
        } else if (endB >= startA && endB <= endA) {  
            result = true;  
        } else if (startA >= startB && startA <= endB) {  
            result = true;  
        } else if (endA >= startB && endA <= endB) {  
            result = true;  
        }  
        return result;  
    }  
  
    private int[] randomXY(Random ran, LinkedList<Integer> listX, LinkedList<Integer> listY, int xItem) {  
        int[] arr = new int[4];  
        arr[IDX_X] = listX.remove(ran.nextInt(listX.size()));  
        arr[IDX_Y] = listY.remove(ran.nextInt(listY.size()));  
        return arr;  
    }  
  
    @Override
	public void onGlobalLayout() {  
        int tmpW = getWidth();  
        int tmpH = getHeight();  
        if (width != tmpW || height != tmpH) {  
            width = tmpW;  
            height = tmpH;  
            show();  
        }  
    }  
  
    public Vector<String> getKeywords() {  
        return vecKeywords;  
    }  
  
    public void rubKeywords() {  
        vecKeywords.clear();  
    }  
  
    //直接清除所有的TextView。在清除之前不会显示动画
    public void rubAllViews() {  
        removeAllViews();  
    }  
  
    public void setOnItemClickListener(OnClickListener listener) {  
        itemClickListener = listener;  
    }  
  

}
