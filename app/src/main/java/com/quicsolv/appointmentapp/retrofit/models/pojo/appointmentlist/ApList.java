package com.quicsolv.appointmentapp.retrofit.models.pojo.appointmentlist;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**********************************************************************
 * Created by   -  Tushar Patil
 * Organization -  QuicSolv Technologies Pvt.Ltd
 * Date         -  23 Apr 2018
 ***********************************************************************/

public class ApList {
    @SerializedName("1")
    @Expose
    private List<_1> _1 = null;
    @SerializedName("2")
    @Expose
    private List<_2> _2 = null;
    @SerializedName("3")
    @Expose
    private List<_3> _3 = null;

    public List<_1> get1() {
        return _1;
    }

    public void set1(List<_1> _1) {
        this._1 = _1;
    }

    public List<_2> get2() {
        return _2;
    }

    public void set2(List<_2> _2) {
        this._2 = _2;
    }

    public List<_3> get3() {
        return _3;
    }

    public void set3(List<_3> _3) {
        this._3 = _3;
    }


}
