/*
 * Copyright (c) 2017-2018 THL A29 Limited, a Tencent company. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package www.patient.jykj_zxyl.util.tencenUtil.models;

import www.patient.jykj_zxyl.util.tencenUtil.common.AbstractModel;
import www.patient.jykj_zxyl.util.tencenUtil.common.AbstractModel;
import com.google.gson.annotations.SerializedName;
import com.google.gson.annotations.Expose;
import java.util.HashMap;

public class QuotaInvoiceOCRResponse extends AbstractModel {

    /**
    * 发票号码
    */
    @SerializedName("InvoiceNum")
    @Expose
    private String InvoiceNum;

    /**
    * 发票代码
    */
    @SerializedName("InvoiceCode")
    @Expose
    private String InvoiceCode;

    /**
    * 大写金额
    */
    @SerializedName("Rate")
    @Expose
    private String Rate;

    /**
    * 唯一请求 ID，每次请求都会返回。定位问题时需要提供该次请求的 RequestId。
    */
    @SerializedName("RequestId")
    @Expose
    private String RequestId;

    /**
     * Get 发票号码 
     * @return InvoiceNum 发票号码
     */
    public String getInvoiceNum() {
        return this.InvoiceNum;
    }

    /**
     * Set 发票号码
     * @param InvoiceNum 发票号码
     */
    public void setInvoiceNum(String InvoiceNum) {
        this.InvoiceNum = InvoiceNum;
    }

    /**
     * Get 发票代码 
     * @return InvoiceCode 发票代码
     */
    public String getInvoiceCode() {
        return this.InvoiceCode;
    }

    /**
     * Set 发票代码
     * @param InvoiceCode 发票代码
     */
    public void setInvoiceCode(String InvoiceCode) {
        this.InvoiceCode = InvoiceCode;
    }

    /**
     * Get 大写金额 
     * @return Rate 大写金额
     */
    public String getRate() {
        return this.Rate;
    }

    /**
     * Set 大写金额
     * @param Rate 大写金额
     */
    public void setRate(String Rate) {
        this.Rate = Rate;
    }

    /**
     * Get 唯一请求 ID，每次请求都会返回。定位问题时需要提供该次请求的 RequestId。 
     * @return RequestId 唯一请求 ID，每次请求都会返回。定位问题时需要提供该次请求的 RequestId。
     */
    public String getRequestId() {
        return this.RequestId;
    }

    /**
     * Set 唯一请求 ID，每次请求都会返回。定位问题时需要提供该次请求的 RequestId。
     * @param RequestId 唯一请求 ID，每次请求都会返回。定位问题时需要提供该次请求的 RequestId。
     */
    public void setRequestId(String RequestId) {
        this.RequestId = RequestId;
    }

    /**
     * Internal implementation, normal users should not use it.
     */
    public void toMap(HashMap<String, String> map, String prefix) {
        this.setParamSimple(map, prefix + "InvoiceNum", this.InvoiceNum);
        this.setParamSimple(map, prefix + "InvoiceCode", this.InvoiceCode);
        this.setParamSimple(map, prefix + "Rate", this.Rate);
        this.setParamSimple(map, prefix + "RequestId", this.RequestId);

    }
}

