package www.patient.jykj_zxyl.base.base_utils;

import com.github.promeg.pinyinhelper.Pinyin;

import java.util.Comparator;

import www.patient.jykj_zxyl.base.base_bean.AllDepartmentBean;

/**
 * Created by G on 2020/10/8 19:55
 */
public class LetterComparatorUtil implements Comparator<AllDepartmentBean.HospitalDepartmentListBean> {

    @Override
    public int compare(AllDepartmentBean.HospitalDepartmentListBean l, AllDepartmentBean.HospitalDepartmentListBean r) {
        if (l == null || r == null) {
            return 0;
        }

        String lhsSortLetters = Pinyin.toPinyin(l.getDepartmentName().charAt(0)).substring(0,1);
        String rhsSortLetters = Pinyin.toPinyin(r.getDepartmentName().charAt(0)).substring(0,1);
        if (lhsSortLetters == null || rhsSortLetters == null) {
            return 0;
        }
        return lhsSortLetters.compareTo(rhsSortLetters);
    }
}
