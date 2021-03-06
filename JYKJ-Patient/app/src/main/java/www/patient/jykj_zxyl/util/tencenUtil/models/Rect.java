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

import com.google.gson.annotations.SerializedName;
import com.google.gson.annotations.Expose;
import java.util.HashMap;

import www.patient.jykj_zxyl.util.tencenUtil.common.AbstractModel;
import www.patient.jykj_zxyl.util.tencenUtil.common.AbstractModel;

public class Rect extends AbstractModel {

    /**
    * 左上角x
    */
    @SerializedName("X")
    @Expose
    private Integer X;

    /**
    * 左上角y
    */
    @SerializedName("Y")
    @Expose
    private Integer Y;

    /**
    * 宽度
    */
    @SerializedName("Width")
    @Expose
    private Integer Width;

    /**
    * 高度
    */
    @SerializedName("Height")
    @Expose
    private Integer Height;

    /**
     * Get 左上角x 
     * @return X 左上角x
     */
    public Integer getX() {
        return this.X;
    }

    /**
     * Set 左上角x
     * @param X 左上角x
     */
    public void setX(Integer X) {
        this.X = X;
    }

    /**
     * Get 左上角y 
     * @return Y 左上角y
     */
    public Integer getY() {
        return this.Y;
    }

    /**
     * Set 左上角y
     * @param Y 左上角y
     */
    public void setY(Integer Y) {
        this.Y = Y;
    }

    /**
     * Get 宽度 
     * @return Width 宽度
     */
    public Integer getWidth() {
        return this.Width;
    }

    /**
     * Set 宽度
     * @param Width 宽度
     */
    public void setWidth(Integer Width) {
        this.Width = Width;
    }

    /**
     * Get 高度 
     * @return Height 高度
     */
    public Integer getHeight() {
        return this.Height;
    }

    /**
     * Set 高度
     * @param Height 高度
     */
    public void setHeight(Integer Height) {
        this.Height = Height;
    }

    /**
     * Internal implementation, normal users should not use it.
     */
    public void toMap(HashMap<String, String> map, String prefix) {
        this.setParamSimple(map, prefix + "X", this.X);
        this.setParamSimple(map, prefix + "Y", this.Y);
        this.setParamSimple(map, prefix + "Width", this.Width);
        this.setParamSimple(map, prefix + "Height", this.Height);

    }
}

