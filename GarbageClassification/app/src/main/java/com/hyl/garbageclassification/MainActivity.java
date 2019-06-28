package com.hyl.garbageclassification;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.bumptech.glide.Glide;
import com.google.common.base.Joiner;
import com.hyl.garbageclassification.common.BaseActivity;
import com.hyl.garbageclassification.common.PermissionList;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;
import com.youth.banner.listener.OnBannerListener;
import com.youth.banner.loader.ImageLoader;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * @author yunlai.hao
 * @date 2019/6/28
 */
public class MainActivity extends BaseActivity implements OnBannerListener {
    private static final String TAG = "MainActivity";
    private Banner banner;
    private ArrayList<File> listPath;
    private ArrayList<String> listTitle;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initPermission();
    }

    /**
     * 初始化权限
     */
    private void initPermission() {
        List<String> permissionList = PermissionList.getPermissionList();
        List<String> applyForPermissionList = new ArrayList<>();

        for (String permission : permissionList){
            if (ContextCompat.checkSelfPermission(this, permission) == PackageManager.PERMISSION_DENIED) {
                applyForPermissionList.add(permission);
            }
        }

        if (applyForPermissionList.size() > 0){
            String[] applyForPermissions = Joiner.on(",").join(applyForPermissionList).split(",");
            ActivityCompat.requestPermissions(this, applyForPermissions, 1);
        }

    }

    @SuppressLint("SdCardPath")
    private void initView() {
        banner = findViewById(R.id.banner);

        //放图片地址的集合
        listPath = new ArrayList<>();
        //放标题的集合
        listTitle = new ArrayList<>();

        listPath.add(new File("/sdcard/Android/Image/image_1.jpg"));
        listPath.add(new File("/sdcard/Android/Image/image_2.jpg"));
        listPath.add(new File("/sdcard/Android/Image/image_3.jpg"));
        listPath.add(new File("/sdcard/Android/Image/image_4.jpg"));
        listTitle.add("好好学习");
        listTitle.add("天天向上");
        listTitle.add("热爱劳动");
        listTitle.add("不搞对象");

        //设置内置样式，共有六种可以点入方法内逐一体验使用。
        banner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR_TITLE_INSIDE);
        //设置图片加载器，图片加载器在下方
        banner.setImageLoader(new GarbageClassificationLoader());
        //设置图片网址或地址的集合
        banner.setImages(listPath);
        //设置轮播的动画效果，内含多种特效，可点入方法内查找后内逐一体验
        banner.setBannerAnimation(Transformer.Default);
        //设置轮播图的标题集合
        banner.setBannerTitles(listTitle);
        //设置轮播间隔时间
        banner.setDelayTime(3000);
        //设置是否为自动轮播，默认是“是”。
        banner.isAutoPlay(true);
        //设置指示器的位置，小点点，左中右。
        banner.setIndicatorGravity(BannerConfig.CENTER)
                //以上内容都可写成链式布局，这是轮播图的监听。比较重要。方法在下面。
                .setOnBannerListener(this)
                //必须最后调用的方法，启动轮播图。
                .start();
    }

    @Override
    public void OnBannerClick(int position) {
        Log.i(TAG, "你点了第"+position+"张轮播图");
    }


    /**
     * 自定义的图片加载器
     */
    private class GarbageClassificationLoader extends ImageLoader {
        @Override
        public void displayImage(Context context, Object path, ImageView imageView) {
            Glide.with(context).load(path).into(imageView);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1){
            for (int i =0; i < permissions.length ; i++){
                switch (permissions[i]){
                    case "android.permission.READ_EXTERNAL_STORAGE":
                        if (grantResults[i] == PackageManager.PERMISSION_GRANTED) {
                            initView();
                        } else {
                            finish();
                        }
                        break;
                    default:
                        break;
                }
            }
        }
    }
}
