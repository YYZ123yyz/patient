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

public class ShipInvoiceInfo extends AbstractModel {

    /**
    * 识别出的字段名称（关键字）。
    */
    @SerializedName("Name")
    @Expose
    private String Name;

    /**
    * 识别出的字段名称对应的值，也就是字段Name对应的字符串结果。
    */
    @SerializedName("Value")
    @Expose
    private String Value;

    /**
    * 文本行在旋转纠正之后的图像中的像素坐标。
    */
    @SerializedName("Rect")
    @Expose
    private Rect Rect;

    /**
     * Get 识别出的字段名称（关键字）。 
     * @return Name 识别出的字段名称（关键字）。
     */
    public String getName() {
        return this.Name;
    }

    /**
     * Set 识别出的字段名称（关键字）。
     * @param Name 识别出的字段名称（关键字）。
     */
    public void setName(String Name) {
        this.Name = Name;
    }

    /**
     * Get 识别出的字段名称对应的值，也就是字段Name对应的字符串结果。 
     * @return Value 识别出的字段名称对应的值，也就是字段Name对应的字符串结果。
     */
    public String getValue() {
        return this.Value;
    }

    /**
     * Set 识别出的字段名称对应的值，也就是字段Name对应的字符串结果。
     * @param Value 识别出的字段名称对应的值，也就是字段Name对应的字符串结果。
     */
    public void setValue(String Value) {
        this.Value = Value;
    }

    /**
     * Get 文本行在旋转纠正之后的图像中的像素坐标。 
     * @return Rect 文本行在旋转纠正之后的图像中的像素坐标。
     */
    public Rect getRect() {
        return this.Rect;
    }

    /**
     * Set 文本行在旋转纠正之后的图像中的像素坐标。
     * @param Rect 文本行在旋转纠正之后的图像中的像素坐标。
     */
    public void setRect(Rect Rect) {
        this.Rect = Rect;
    }

    /**
     * Internal implementation, normal users should not use it.
     */
    public void toMap(HashMap<String, String> map, String prefix) {
        this.setParamSimple(map, prefix + "Name", this.Name);
        this.setParamSimple(map, prefix + "Value", this.Value);
        this.setParamObj(map, prefix + "Rect.", this.Rect);

    }
}
