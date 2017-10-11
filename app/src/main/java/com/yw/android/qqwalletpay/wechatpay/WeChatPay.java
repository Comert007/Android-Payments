package com.yw.android.qqwalletpay.wechatpay;

import android.app.Activity;
import android.widget.Toast;

import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

/**
 * Created by feng on 2017/10/9.
 */

public class WeChatPay implements IWXAPIEventHandler {


    /**
     * <pre>
     * PayReq req = new PayReq();
     * //req.appId = "wxf8b4f85f3a794e77";  // 测试用appId
     * req.appId			= json.getString("appid");
     * req.partnerId		= json.getString("partnerid");  // 商家id
     * req.prepayId		= json.getString("prepayid");  // 支付款 id
     * req.nonceStr		= json.getString("noncestr");
     * req.timeStamp		= json.getString("timestamp");
     * req.packageValue	= json.getString("package");
     * req.sign			= json.getString("sign");
     * req.extData			= "app data"; // optional
     * Toast.makeText(PayActivity.this, "正常调起支付", Toast.LENGTH_SHORT).show();
     * // 在支付之前，如果应用没有注册到微信，应该先调用IWXMsg.registerApp将应用注册到微信
     * api.sendReq(req);
     * </pre>
     */


    private Activity context;
    private String appId;
    private String partnerId;
    private String prepayId;
    private String nonceStr;
    private String timeStamp;
    private String packageValue;
    private String sign;


    private IWXAPI mWXApi;

    public void wechatPay(){

        mWXApi = WXAPIFactory.createWXAPI(context, this.appId);
        mWXApi.handleIntent(context.getIntent(), this);
        if (!mWXApi.isWXAppInstalled()) {
            Toast.makeText(context.getApplicationContext(),"请确认是否安装微信", Toast.LENGTH_SHORT).show();
            return;
        }
        PayReq request = new PayReq();

        request.appId = this.appId;
        request.partnerId = this.partnerId;
        request.prepayId= this.prepayId;
        request.packageValue = this.packageValue != null ? this.packageValue : "Sign=WXPay";
        request.nonceStr= this.nonceStr;
        request.timeStamp= this.timeStamp;
        request.sign = this.sign;

        mWXApi.sendReq(request);
    }

    @Override
    public void onReq(BaseReq baseReq) {

    }

    @Override
    public void onResp(BaseResp baseResp) {

    }


    public static class  Builder {

        private Activity context;
        private String appId;
        private String partnerId;
        private String prepayId;
        private String nonceStr;
        private String timeStamp;
        private String packageValue;
        private String sign;

        public Builder setContext(Activity context) {
            this.context = context;
            return this;
        }

        public Builder setAppId(String appId) {
            this.appId = appId;
            return this;
        }

        public Builder setPartnerId(String partnerId) {
            this.partnerId = partnerId;
            return this;
        }

        public Builder setPrepayId(String prepayId) {
            this.prepayId = prepayId;
            return this;
        }

        public Builder setNonceStr(String nonceStr) {
            this.nonceStr = nonceStr;
            return this;
        }

        public Builder setTimeStamp(String timeStamp) {
            this.timeStamp = timeStamp;
            return this;
        }

        public Builder setPackageValue(String packageValue) {
            this.packageValue = packageValue;
            return this;
        }

        public Builder setSign(String sign) {
            this.sign = sign;
            return this;
        }


        public WeChatPay create(){
            WeChatPay pay = new WeChatPay();
            pay.context = this.context;
            pay.appId = this.appId;
            pay.nonceStr = this.nonceStr;
            pay.packageValue = this.packageValue;
            pay.partnerId = this.partnerId;
            pay.prepayId = this.prepayId;
            pay.sign = this.sign;
            pay.timeStamp = this.timeStamp;

            return pay;
        }
    }

}
