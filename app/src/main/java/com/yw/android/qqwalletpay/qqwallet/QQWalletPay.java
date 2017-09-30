package com.yw.android.qqwalletpay.qqwallet;

import com.tencent.mobileqq.openpay.data.pay.PayApi;

/**
 * Created by feng on 2017/9/30.
 * qq钱包支付
 */

public class QQWalletPay {


    /**
     *  PayApi api = new PayApi();
     api.appId = APP_ID; // 在http://open.qq.com注册的AppId,参与支付签名，签名关键字key为appId
     api.serialNumber = ...; // 支付序号,用于标识此次支付
     api.callbackScheme = ...; // QQ钱包支付结果回调给urlscheme为callbackScheme的activity.，参看后续的“支付回调结果处理”
     api.tokenId = ...; // QQ钱包支付生成的token_id
     api.pubAcc = ...; // 手Q公众帐号id.参与支付签名，签名关键字key为pubAcc
     api.pubAccHint = ...; // 支付完成页面，展示给用户的提示语：提醒关注公众帐号
     api.nonce = ...; // 随机字段串，每次支付时都要不一样.参与支付签名，签名关键字key为nonce
     api.timeStamp = ...; // 时间戳，为1970年1月1日00:00到请求发起时间的秒数
     api.bargainorId = ...; // 商户号.参与支付签名，签名关键字key为bargainorId
     api.sig = ...; // 商户Server下发的数字签名，生成的签名串，参看“数字签名”
     api.sigType = "HMAC-SHA1"; // 签名时，使用的加密方式，默认为"HMAC-SHA1"
     */


    public static class Builder{

        private String appId;
        private String serialNumber;
        private String callbackScheme;
        private String tokenId;
        private String pubAcc;
        private String pubAccHint;
        private String nonce;
        private String timeStamp;
        private String bargainorId;
        private String sig;
        private String sigType="HMAC-SHA1";

        public Builder setAppId(String appId) {
            this.appId = appId;
            return this;
        }

        public Builder setSerialNumber(String serialNumber) {
            this.serialNumber = serialNumber;
            return this;
        }

        public Builder setCallbackScheme(String callbackScheme) {
            this.callbackScheme = callbackScheme;
            return this;
        }

        public Builder setTokenId(String tokenId) {
            this.tokenId = tokenId;
            return this;
        }

        public Builder setPubAcc(String pubAcc) {
            this.pubAcc = pubAcc;
            return this;
        }

        public Builder setPubAccHint(String pubAccHint) {
            this.pubAccHint = pubAccHint;
            return this;
        }

        public Builder setNonce(String nonce) {
            this.nonce = nonce;
            return this;
        }

        public Builder setTimeStamp(String timeStamp) {
            this.timeStamp = timeStamp;
            return this;
        }

        public Builder setBargainorId(String bargainorId) {
            this.bargainorId = bargainorId;
            return this;
        }

        public Builder setSig(String sig) {
            this.sig = sig;
            return this;
        }

        public Builder setSigType(String sigType) {
            this.sigType = sigType;
            return this;
        }


        /**
         * private String appId;
         private String serialNumber;
         private String callbackScheme;
         private String tokenId;
         private String pubAcc;
         private String pubAccHint;
         private String nonce;
         private String timeStamp;
         private String bargainorId;
         private String sig;
         private String sigType="HMAC-SHA1";
         * @return
         */
        public PayApi create(){
            PayApi api = new PayApi();
            api.serialNumber = this.serialNumber;
            api.callbackScheme = this.callbackScheme;
            api.tokenId = this.tokenId;
            api.pubAcc = this.pubAcc;
            api.pubAccHint = this.pubAccHint;
            api.nonce = this.nonce;
            api.timeStamp = Long.parseLong(this.timeStamp);
            api.bargainorId = this.bargainorId;
            api.sig = this.sig;
            api.appId = this.appId;
            api.sigType = this.sigType;

            return api;
        }
    }
}
