package com.yw.android.qqwalletpay;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.tencent.mobileqq.openpay.api.IOpenApi;
import com.tencent.mobileqq.openpay.api.OpenApiFactory;
import com.tencent.mobileqq.openpay.constants.OpenConstants;
import com.tencent.mobileqq.openpay.data.pay.PayApi;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

public class MainActivity extends AppCompatActivity {

    String getOrderNoUrl = "http://iv.qq.com/wallet/monitor/cgi/token";

    String tokenId;
    String callbackScheme = "qwallet100619284";
    IOpenApi openApi;

    int paySerial = 1;

    final String APP_ID = "100619284";
    // 签名步骤建议不要在app上执行，要放在服务器上执行
    // appkey建议不要保存app
    final String APP_KEY = "d139ae6fb0175e5659dce2a7c1fe84d5";
    final String BARGAINOR_ID = "2001";

    private Button btnQQWallet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnQQWallet = (Button) findViewById(R.id.btn_qq_wallet);
        btnQQWallet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBtnGetOrderNo();
            }
        });

        openApi = OpenApiFactory.getInstance(this, APP_ID);
    }


    public void onBtnIsMqqInstalled() {
        boolean isInstalled = openApi.isMobileQQInstalled();
        Toast.makeText(this, "IsMqqInstalled:" + isInstalled, Toast.LENGTH_LONG).show();
    }

    public void onBtnIsMqqSupportPay() {
        boolean isSupport = openApi.isMobileQQSupportApi(OpenConstants.API_NAME_PAY);
        Toast.makeText(this, "IsMqqSupportPay:" + isSupport, Toast.LENGTH_LONG).show();
    }

    public void onBtnGetOrderNo() {
        GetOrderNoTask task = new GetOrderNoTask();
        task.execute();
    }
    /**
     * 响应购买按钮.
     */
    public void onBtnMqqPay() {
        if (TextUtils.isEmpty(tokenId)) {
            Toast.makeText(this, "tokenId is null.", Toast.LENGTH_LONG).show();
        }

        PayApi api = new PayApi();
        api.appId = APP_ID;

        api.serialNumber = "" + paySerial++;
        api.callbackScheme = callbackScheme;

        api.tokenId = tokenId;
        api.pubAcc = "";
        api.pubAccHint = "";
        api.nonce = String.valueOf(System.currentTimeMillis());
        api.timeStamp = System.currentTimeMillis() / 1000;
        api.bargainorId = BARGAINOR_ID;

        try {
            signApi(api);
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }

        if (api.checkParams()) {
            openApi.execApi(api);
            Log.d("tag","调起支付");
        }
    }

    /**
     * 签名步骤建议不要在app上执行，要放在服务器上执行.
     */
    public void signApi(PayApi api) throws Exception {
        // 按key排序
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("appId=").append(api.appId);
        stringBuilder.append("&bargainorId=").append(api.bargainorId);
        stringBuilder.append("&nonce=").append(api.nonce);
        stringBuilder.append("&pubAcc=").append("");
        stringBuilder.append("&tokenId=").append(api.tokenId);

        byte[] byteKey = (APP_KEY+"&").getBytes("UTF-8");
        // 根据给定的字节数组构造一个密钥,第二参数指定一个密钥算法的名称
        SecretKey secretKey = new SecretKeySpec(byteKey, "HmacSHA1");
        // 生成一个指定 Mac 算法 的 Mac 对象
        Mac mac = Mac.getInstance("HmacSHA1");
        // 用给定密钥初始化 Mac 对象
        mac.init(secretKey);
        byte[] byteSrc = stringBuilder.toString().getBytes("UTF-8");
        // 完成 Mac 操作
        byte[] dst = mac.doFinal(byteSrc);
        // Base64
        api.sig = Base64.encodeToString(dst, Base64.NO_WRAP);
        api.sigType = "HMAC-SHA1";

    }

    private class GetOrderNoResult {
        int retCode;
        String tokenId;
    }

    private class GetOrderNoTask extends AsyncTask<Void, Void, GetOrderNoResult> {

        private ProgressDialog dialog;

        @Override
        protected void onPreExecute() {
            dialog = ProgressDialog.show(MainActivity.this, "GetOrderNo", "GetOrderNo...");
        }

        @Override
        protected void onPostExecute(GetOrderNoResult result) {
            if (dialog != null) {
                dialog.dismiss();
            }

//            Dialog alertDialog = new AlertDialog.Builder(MainActivity.this).
//                    setTitle("GetOrderNoResult").
//                    setMessage("retCode:" + result.retCode + " orderNo:" + result.tokenId).
//                    create();
//            alertDialog.show();

            if (result.retCode == 0) {
                MainActivity.this.tokenId = result.tokenId;
            }

            Log.d("tag","retCode:" + result.retCode + " orderNo:" + result.tokenId);
            onBtnMqqPay();
        }

        @Override
        protected GetOrderNoResult doInBackground(Void... params) {
            GetOrderNoResult result = new GetOrderNoResult();

            // 将URL与参数拼接
            HttpGet getMethod = new HttpGet(getOrderNoUrl);

            HttpClient httpClient = new DefaultHttpClient();

            try {
                HttpResponse response = httpClient.execute(getMethod);
                String retContent = EntityUtils.toString(response.getEntity(), "utf-8");

                Log.i("tag", "HttpResponse:" + retContent);

                JSONObject json = new JSONObject(retContent);
                result.retCode = json.getInt("ret");
                result.tokenId = json.getString("token");
            } catch (ClientProtocolException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return result;
        }
    }
}
