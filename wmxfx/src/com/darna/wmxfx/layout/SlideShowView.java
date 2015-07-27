package com.darna.wmxfx.layout;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.darna.wmxfx.R;
import com.darna.wmxfx.net.NetGetAd;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;

@SuppressLint("HandlerLeak") public class SlideShowView extends FrameLayout{
	
	private ImageLoader imageLoader = ImageLoader.getInstance();
	//轮播图图片数量  
    //private final static int IMAGE_COUNT = 5;  
    //自动轮播的时间间隔  
    //private final static int TIME_INTERVAL = 5;
    //自动轮播启用开关  
    private final static boolean isAutoPlay = true;
    //自定义轮播图的资源  
    private List<String> imageUrls = new ArrayList<String>();  
    //放轮播图片的ImageView 的list  
    private List<ImageView> imageViewsList; 
    //放圆点的View的list  
    private List<View> dotViewsList;  
    
    private ViewPager viewPager;  
    //当前轮播页  
    private int currentItem  = 0;  
    //定时任务  
    private ScheduledExecutorService scheduledExecutorService; 
    
    private Context context; 
    
  //Handler
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            // TODO Auto-generated method stub
            super.handleMessage(msg);
            viewPager.setCurrentItem(currentItem);
        }
    };
    public SlideShowView(Context context) {
    	super(context);
        //this(context,null);
    }
    public SlideShowView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }
	public SlideShowView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		this.context = context;
		initImageLoader(context);//图片组件初始化
		initData();//初始化数据
		if(isAutoPlay){
            startPlay();
        }
	}
	
	public void clear(){
		if(!imageUrls.equals(null)){
			imageUrls.clear();
		}
		if (!imageViewsList.equals(null)) {
			imageViewsList.clear();
		}
		if (!dotViewsList.equals(null)) {
			dotViewsList.clear();
		}
	}
	
	/** 
     * 开始轮播图切换 
     */  
    private void startPlay(){  
        scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();  
        scheduledExecutorService.scheduleAtFixedRate(new SlideShowTask(), 1, 4, TimeUnit.SECONDS);  
    }
	
	/** 
     * 初始化相关Data 
     */  
    public void initData(){ 
        imageViewsList = new ArrayList<ImageView>();  
        dotViewsList = new ArrayList<View>();  
  
        // 一步任务获取图片  
        //new GetListTask().execute("");
        
        new NetGetAd(new NetGetAd.SuccessCallback() {
			@Override
			public void onSuccess(List<String> ads) {
				imageUrls = ads;
				for (int i = 0; i < imageUrls.size(); i++) {
					System.out.println(imageUrls.get(i));
				}
				initUI(context);
			}
		}, new NetGetAd.FailCallbck() {
			@Override
			public void onFail(String errorCode) {
				Toast.makeText(context, "error", Toast.LENGTH_SHORT).show();
			}
		});
    } 
    
    /** 
     * 初始化Views等UI 
     */  
    private void initUI(Context context){  
        if(imageUrls == null || imageUrls.size() == 0)  
            return;  
          
        LayoutInflater.from(context).inflate(R.layout.layout_slideshow, this, true);  
          
        LinearLayout dotLayout = (LinearLayout)findViewById(R.id.dotLayout);  
        dotLayout.removeAllViews();  
          
        // 热点个数与图片特殊相等  
        for (int i = 0; i < imageUrls.size(); i++) {  
            ImageView view =  new ImageView(context);  
            view.setTag(imageUrls.get(i));  
            if(i==0)//给一个默认图  
                view.setBackgroundResource(R.drawable.viewbackimg);  
            view.setScaleType(ScaleType.CENTER_CROP);  
            imageViewsList.add(view);  
              
            ImageView dotView =  new ImageView(context);  
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT);  
            params.leftMargin = 4;  
            params.rightMargin = 4;  
            dotLayout.addView(dotView, params);  
            dotViewsList.add(dotView);  
        }  
          
        viewPager = (ViewPager) findViewById(R.id.viewPager);  
        viewPager.setFocusable(true);  
          
        viewPager.setAdapter(new MyPagerAdapter());  
        viewPager.setOnPageChangeListener(new MyPageChangeListener());  
    }  
    
    /** 
     * 填充ViewPager的页面适配器 
     *  
     */  
    private class MyPagerAdapter  extends PagerAdapter{

    	@Override
    	public void destroyItem(ViewGroup container, int position, Object object) {
    		((ViewPager)container).removeView(imageViewsList.get(position));
    	}
    	
    	@Override
        public Object instantiateItem(View container, int position) {
        	ImageView imageView = imageViewsList.get(position);

			imageLoader.displayImage(imageView.getTag() + "", imageView);//第三方组件显示图片
        	
            ((ViewPager)container).addView(imageViewsList.get(position));
            return imageViewsList.get(position);
        }
    	
		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return imageViewsList.size();
		}

		@Override
		public boolean isViewFromObject(View view, Object object) {
			// TODO Auto-generated method stub
			return view == object;
		}
    	
    }
    
    /** 
     * ViewPager的监听器 
     * 当ViewPager中页面的状态发生改变时调用 
     *  
     */  
    private class MyPageChangeListener implements OnPageChangeListener{  
  
        boolean isAutoPlay = false;  
  
        @Override  
        public void onPageScrollStateChanged(int arg0) {  
            // TODO Auto-generated method stub  
            switch (arg0) {  
            case 1:// 手势滑动，空闲中  
                isAutoPlay = false;  
                break;  
            case 2:// 界面切换中  
                isAutoPlay = true;  
                break;  
            case 0:// 滑动结束，即切换完毕或者加载完毕  
                // 当前为最后一张，此时从右向左滑，则切换到第一张  
                if (viewPager.getCurrentItem() == viewPager.getAdapter().getCount() - 1 && !isAutoPlay) {  
                    viewPager.setCurrentItem(0);  
                }  
                // 当前为第一张，此时从左向右滑，则切换到最后一张  
                else if (viewPager.getCurrentItem() == 0 && !isAutoPlay) {  
                    viewPager.setCurrentItem(viewPager.getAdapter().getCount() - 1);  
                }  
                break;  
        }  
        }  
  
        @Override  
        public void onPageScrolled(int arg0, float arg1, int arg2) {  
            // TODO Auto-generated method stub  
              
        }  
  
        @Override  
        public void onPageSelected(int pos) {  
            // TODO Auto-generated method stub  
              
            currentItem = pos;  
            for(int i=0;i < dotViewsList.size();i++){  
                if(i == pos){  
                    ((View)dotViewsList.get(pos)).setBackgroundResource(R.drawable.dot_focus);  
                }else {  
                    ((View)dotViewsList.get(i)).setBackgroundResource(R.drawable.dot_blur);  
                }  
            }  
        }  
          
    }  
    
    /** 
     *执行轮播图切换任务 
     * 
     */  
    private class SlideShowTask implements Runnable{  
  
        @Override  
        public void run() {  
            // TODO Auto-generated method stub  
            synchronized (viewPager) {  
                currentItem = (currentItem+1)%imageViewsList.size();  
                handler.obtainMessage().sendToTarget();  
            }  
        }  
          
    }  
    
    /** 
     * 异步任务,获取数据 
     * 整个方法都是用来获取图片的URL地址的
     *  
     */  
    /*
    class GetListTask extends AsyncTask<String, Integer, Boolean> {  
  
        @Override  
        protected Boolean doInBackground(String... params) {  
            try {  
                // 这里一般调用服务端接口获取一组轮播图片，下面是从百度找的几个图片  
                  
                imageUrls = new String[]{  
                		"http://www.wmxfx.com/images/AD/index/main1.jpg?v=150306",
						"http://www.wmxfx.com/images/AD/index/main3.jpg?v=150306",
						"http://www.wmxfx.com/images/AD/index/main5.jpg?v=150306",
						"http://www.wmxfx.com/images/AD/index/main2.jpg?v=150306",
						"http://www.wmxfx.com/images/AD/index/main4.jpg?v=150306" 
                };
                  
                return true;  
            } catch (Exception e) {  
                e.printStackTrace();  
                return false;  
            }  
        }  
       
        @Override  
        protected void onPostExecute(Boolean result) {  
            super.onPostExecute(result);  
            if (result) {  
                initUI(context);//成功之后初始化页面  
            }  
        }  
    }*/
	
	/** 
     * ImageLoader 图片组件初始化 
     *  
     * @param context 
     */  
    public static void initImageLoader(Context context) {  
        // This configuration tuning is custom. You can tune every option, you  
        // may tune some of them,  
        // or you can create default configuration by  
        // ImageLoaderConfiguration.createDefault(this);  
        // method.  
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(context).threadPriority(Thread.NORM_PRIORITY - 2).denyCacheImageMultipleSizesInMemory().discCacheFileNameGenerator(new Md5FileNameGenerator()).tasksProcessingOrder(QueueProcessingType.LIFO).writeDebugLogs() // Remove  
                                                                                                                                                                                                                                                                                                // for  
                                                                                                                                                                                                                                                                                                // release  
                                                                                                                                                                                                                                                                                                // app  
                .build();  
        // Initialize ImageLoader with configuration.  
        ImageLoader.getInstance().init(config);  
    }
}


























