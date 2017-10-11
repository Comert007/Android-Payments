package com.yw.android.qqwalletpay.alipay;

import android.app.Activity;
import android.os.Handler;

/**
 * Created by feng on 2017/10/9.
 */

public class Alipay {

    /**
     * ali pay sdk flag
     */
    private static final int SDK_PAY_FLAG = 1;
    private static final int SDK_CHECK_FLAG = 2;

    private Activity context;

    //支付宝支付的配置

    // 商户网站唯一订单号
    private String outTradeNo;
    // 商品名称
    private String subject;
    // 商品详情
    private String body;
    // 商品金额
    private String price;
    // 服务器异步通知页面路径
    private String callbackUrl;

    private Handler handler;




    public static class Builder{
        //上下文
        private Activity contetxt;
        // 商户网站唯一订单号
        private String outTradeNo;
        // 商品名称
        private String subject;
        // 商品详情
        private String body;
        // 商品金额
        private String price;
        // 服务器异步通知页面路径
        private String callbackUrl;
        public Builder() {
            super();
        }

        public Builder with(Activity contetxt){
            this.contetxt = contetxt;
            return this;
        }


        /**
         * 设置唯一订单号
         * @param outTradeNo
         * @return
         */
        public Builder setOutTradeNo(String outTradeNo){
            this.outTradeNo = outTradeNo;
            return this;
        }

        /**
         * 设置订单标题
         * @param subject
         * @return
         */
        public Builder setSubject(String subject){
            this.subject = subject;
            return this;
        }

        /**
         * 设置订单内容
         * @param body
         * @return
         */
        public Builder setBody(String body){
            this.body = body;
            return this;
        }

        /**
         * 设置订单价格
         * @param price
         * @return
         */
        public Builder setPrice(String price){
            this.price = price;
            return this;
        }

        /**
         * 设置回调
         * @param callbackUrl
         * @return
         */
        public Builder setCallbackUrl(String callbackUrl){
            this.callbackUrl = callbackUrl;
            return this;
        }

        public Alipay create(){
            Alipay Alipay = new Alipay();
            Alipay.context = this.contetxt;
            Alipay.outTradeNo = this.outTradeNo;
            Alipay.subject = this.subject;
            Alipay.body = this.body;
            Alipay.price = this.price;
            Alipay.callbackUrl = this.callbackUrl;

            return Alipay;
        }

    }
}
