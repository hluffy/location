package location.dk.com.location;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText xloc;
    private EditText yloc;
    private EditText info;
    private Button savesave;
    //声明AMapLocationClient类对象
    public AMapLocationClient mLocationClient = null;
    //声明定位回调监听器
    public AMapLocationListener mLocationListener = null;
    public AMapLocationClientOption mLocationOption = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bindView();
    }

    private void bindView(){
        xloc = (EditText)findViewById(R.id.xloc);
        yloc = (EditText)findViewById(R.id.yloc);
        info = (EditText)findViewById(R.id.info);
        savesave = (Button)findViewById(R.id.save);

        savesave.setOnClickListener(this);

        mLocationListener = new AMapLocationListener() {
            @Override
            public void onLocationChanged(AMapLocation amapLocation) {
                // TODO Auto-generated method stub
                Log.i("sss","sss");
                if (amapLocation != null) {
                    if (amapLocation.getErrorCode() == 0) {
                        //可在其中解析amapLocation获取相应内容。
                        xloc.setText(String.valueOf(amapLocation.getLongitude()));
                        yloc.setText(String.valueOf(amapLocation.getLatitude()));
                        info.setText(amapLocation.getCity()+" "+amapLocation.getDistrict()+" "+amapLocation.getStreet()+" "+amapLocation.getStreetNum());



                    }else {

                        //定位失败时，可通过ErrCode（错误码）信息来确定失败的原因，errInfo是错误信息，详见错误码表。
                        Log.e("AmapError","location Error, ErrCode:"
                                + amapLocation.getErrorCode() + ", errInfo:"
                                + amapLocation.getErrorInfo());
                    }

                    mLocationClient.stopLocation();

                }

            }
        };



        mLocationOption = new AMapLocationClientOption();
        //设置定位模式为AMapLocationMode.Hight_Accuracy，高精度模式。
        mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
        //获取一次定位结果：
        //该方法默认为false。
//        mLocationOption.setOnceLocation(true);
        //获取最近3s内精度最高的一次定位结果：
        //设置setOnceLocationLatest(boolean b)接口为true，启动定位时SDK会返回最近3s内精度最高的一次定位结果。如果设置其为true，setOnceLocation(boolean b)接口也会被设置为true，反之不会，默认为false。
        mLocationOption.setOnceLocationLatest(true);
        mLocationOption.setOnceLocationLatest(true);
        //设置是否返回地址信息（默认返回地址信息）
        mLocationOption.setNeedAddress(true);
        //设置是否强制刷新WIFI，默认为true，强制刷新。
        mLocationOption.setWifiActiveScan(false);
        //设置是否允许模拟位置,默认为true，允许模拟位置
        mLocationOption.setMockEnable(false);
        //单位是毫秒，默认30000毫秒，建议超时时间不要低于8000毫秒。
        mLocationOption.setHttpTimeOut(20000);
    }

    @Override
    public void onClick(View view) {
        //初始化定位
        mLocationClient = new AMapLocationClient(getApplicationContext());

        //设置定位回调监听
        mLocationClient.setLocationListener(mLocationListener);
        mLocationClient.setLocationOption(mLocationOption);
        // TODO Auto-generated method stub
        //给定位客户端对象设置定位参数
        Log.i("test","test");


        //启动定位
        mLocationClient.startLocation();
    }

}
