package com.yw.android.qqwalletpay.qqwallet;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.tencent.mobileqq.openpay.api.IOpenApi;
import com.tencent.mobileqq.openpay.api.IOpenApiListener;
import com.tencent.mobileqq.openpay.api.OpenApiFactory;
import com.tencent.mobileqq.openpay.data.base.BaseResponse;
import com.tencent.mobileqq.openpay.data.pay.PayResponse;
import com.yw.android.qqwalletpay.R;

/**
 * Created by feng on 2017/10/9.
 */

public class WeChatCallbackActivity extends AppCompatActivity implements IOpenApiListener{

    private IOpenApi openApi;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_callback);

        openApi = OpenApiFactory.getInstance(this, "app id");
        openApi.handleIntent(getIntent(), this);
    }


    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        openApi.handleIntent(intent, this);
    }

    @Override
    public void onOpenResponse(BaseResponse response) {
        String message;
        if (response == null) {
            // 不能识别的intent
            return;
        } else {
            if (response instanceof PayResponse) {
                // 支付回调响应
                PayResponse payResponse = (PayResponse) response;

                message = " apiName:" + payResponse.apiName
                        + " serialnumber:" + payResponse.serialNumber
                        + " isSucess:" + payResponse.isSuccess()
                        + " retCode:" + payResponse.retCode
                        + " retMsg:" + payResponse.retMsg
                        + " transactionId:" + payResponse.transactionId;
                if (payResponse.isSuccess()) {
                    // 支付成功，这个支付结果不能作为发货的依据
                }
            } else {
                // 不能识别的响应
            }
        }
    }


}
