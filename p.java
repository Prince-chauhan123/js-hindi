package com.upc_group.upcpro.fragments.calculators;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.p003v7.widget.RecyclerView;
import android.text.SpannableString;
import android.text.style.RelativeSizeSpan;
import android.text.style.SubscriptSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.upc_group.upcpro.C0431R;
import com.upc_group.upcpro.adapters.CalculatorListAdapter;
import com.upc_group.upcpro.adapters.CalculatorListAdapter.AdapterListener;
import com.upc_group.upcpro.adapters.CalculatorListAdapter.Field;
import com.upc_group.upcpro.adapters.CalculatorListAdapter.Section;
import com.upc_group.upcpro.adapters.CalculatorListAdapter.ValueAdapter;
import com.upc_group.upcpro.adapters.CustomManager;
import com.upc_group.upcpro.fragments.BaseFragment;
import com.upc_group.upcpro.utils.Utils;
import com.upc_group.upcpro.utils.Utils.UnitChangeListener;
import java.util.ArrayList;

public class CarbonOxygenFragment extends BaseFragment implements ValueAdapter, AdapterListener, UnitChangeListener {

    /* renamed from: C */
    private double f54C;
    private double CCH4;
    private double CH4;
    private double CH4Factor;

    /* renamed from: CO */
    private double f55CO;
    private double CO2;

    /* renamed from: CP */
    private double f56CP;
    private double DewPoint;
    private double EMF;

    /* renamed from: H2 */
    private double f57H2;
    private double H2O;

    /* renamed from: Kc */
    private double f58Kc;
    private double Kcb;

    /* renamed from: Kh */
    private double f59Kh;

    /* renamed from: Ko */
    private double f60Ko;

    /* renamed from: Kw */
    private double f61Kw;
    private double SootLimit;

    /* renamed from: ac */
    private double f62ac;
    private double alloyFactor;
    private String inputType = "O2";
    private boolean isMetric;
    private CalculatorListAdapter mListAdapter;
    private double pO2;
    private double temperature;

    public void didSelectSelector() {
    }

    @Nullable
    public View onCreateView(LayoutInflater layoutInflater, @Nullable ViewGroup viewGroup, @Nullable Bundle bundle) {
        return layoutInflater.inflate(C0431R.layout.fragment_calculator, viewGroup, false);
    }

    public void onViewCreated(View view, @Nullable Bundle bundle) {
        super.onViewCreated(view, bundle);
        if (this.isFirstAppear) {
            this.isMetric = Utils.isMetric(this.mActivity);
            setupDefaultValues(false);
        }
        this.mListAdapter = new CalculatorListAdapter(getContext());
        this.mListAdapter.setValueAdapter(this);
        this.mListAdapter.setAdapterListener(this);
        CustomManager customManager = new CustomManager(this.mActivity);
        customManager.setAutoMeasureEnabled(true);
        customManager.setItemPrefetchEnabled(false);
        RecyclerView recyclerView = (RecyclerView) view.findViewById(C0431R.C0433id.recyclerView);
        recyclerView.setLayoutManager(customManager);
        recyclerView.setAdapter(this.mListAdapter);
        recyclerView.setFocusable(false);
        Utils.addAutoClearFocus(this.mActivity, recyclerView);
        Utils.addUnitChangeListener(this);
    }

    public void onResume() {
        super.onResume();
        prepareFields();
    }

    private void setupDefaultValues(boolean z) {
        double d = 0.0d;
        this.f54C = 0.0d;
        this.CCH4 = 0.0d;
        this.CH4 = 0.0d;
        this.f61Kw = 0.0d;
        this.f59Kh = 0.0d;
        this.Kcb = 0.0d;
        this.f60Ko = 0.0d;
        this.f58Kc = 0.0d;
        this.pO2 = 0.0d;
        this.f62ac = 0.0d;
        this.SootLimit = 0.0d;
        this.DewPoint = 0.0d;
        this.H2O = 0.0d;
        this.CO2 = 0.0d;
        this.f56CP = 0.0d;
        this.EMF = 0.0d;
        this.temperature = 0.0d;
        this.f55CO = z ? 0.0d : 20.0d;
        this.f57H2 = z ? 0.0d : 40.0d;
        this.CH4Factor = z ? 0.0d : 100.0d;
        if (!z) {
            d = 100.0d;
        }
        this.alloyFactor = d;
    }

    private void prepareFields() {
        ArrayList arrayList = new ArrayList();
        ArrayList arrayList2 = new ArrayList();
        ArrayList arrayList3 = new ArrayList();
        Section section = new Section("units");
        section.setValue(Boolean.valueOf(this.isMetric));
        Field field = new Field("O2", Utils.subScript(getString(C0431R.string.O2_Probe)), "");
        Field field2 = new Field("CO", Utils.subScript("CO/CO2"), "");
        Field field3 = new Field("dewPoint", getString(C0431R.string.Dew_point), "");
        arrayList3.add(field);
        arrayList3.add(field2);
        arrayList3.add(field3);
        Section section2 = new Section("inputs");
        section2.setFields(arrayList3);
        Section section3 = new Section("fields");
        Field field4 = new Field("temperature", getString(C0431R.string.Temperature), this.isMetric ? "°C" : "°F");
        Field field5 = new Field("EMF", "EMF", "mV", Boolean.valueOf(this.inputType.equalsIgnoreCase("O2")));
        Field field6 = new Field("CO", "CO", "%");
        Field field7 = new Field("H2", Utils.subScript("H2"), "%");
        Field field8 = new Field("CH4", Utils.subScript("CH4"), "%");
        Field field9 = new Field("CH4Factor", Utils.subScript(getString(C0431R.string.CH4_Factor)), "%");
        Field field10 = new Field("alloyFactor", getString(C0431R.string.Alloy_Factor), "%");
        Section section4 = section2;
        ArrayList arrayList4 = arrayList;
        Section section5 = section;
        Field field11 = new Field("CP", getString(C0431R.string.Carbon_potential), "%", Boolean.valueOf(false));
        field11.setEndString(" *");
        field11.setUseScientificNotation(true);
        Section section6 = section3;
        Field field12 = new Field("CO2", Utils.subScript("CO2"), "%", Boolean.valueOf(this.inputType.equalsIgnoreCase("CO")));
        field12.setUseScientificNotation(true);
        ArrayList arrayList5 = arrayList2;
        Field field13 = new Field("H2O", Utils.subScript("H2O"), "%", Boolean.valueOf(false));
        field13.setUseScientificNotation(true);
        Field field14 = field13;
        Field field15 = new Field("DewPoint", getString(C0431R.string.Dew_point), this.isMetric ? "°C" : "°F", Boolean.valueOf(this.inputType.equalsIgnoreCase("dewPoint")));
        field15.setUseScientificNotation(true);
        Field field16 = field15;
        Field field17 = new Field("SootLimit", getString(C0431R.string.Soot_limit), "%C", Boolean.valueOf(false));
        field17.setUseScientificNotation(true);
        Field field18 = field17;
        Field field19 = new Field("C", "C", "%", Boolean.valueOf(false));
        field19.setUseScientificNotation(true);
        Field field20 = field19;
        Field field21 = new Field("CCH4", Utils.subScript("C-CH4"), "%", Boolean.valueOf(false));
        field21.setUseScientificNotation(true);
        SpannableString spannableString = new SpannableString("aC");
        spannableString.setSpan(new SubscriptSpan(), 1, 2, 0);
        Field field22 = field21;
        spannableString.setSpan(new RelativeSizeSpan(0.6f), 1, 2, 0);
        Field field23 = field12;
        Field field24 = new Field("ac", spannableString, "", Boolean.valueOf(false));
        field24.setUseScientificNotation(true);
        Field field25 = field24;
        Field field26 = new Field("pO2", Utils.subScript("pO2"), "atm", Boolean.valueOf(false));
        field26.setUseScientificNotation(true);
        Field field27 = field26;
        Field field28 = new Field("Kcb", Utils.subScript(getString(C0431R.string.CO_CO2_Ratio)), "", Boolean.valueOf(false));
        field28.setUseScientificNotation(true);
        Field field29 = new Field("Kh", Utils.subScript(getString(C0431R.string.H2_H20_Ratio)), "", Boolean.valueOf(false));
        field29.setUseScientificNotation(true);
        field4.setMergeUnitAndTile(true);
        field5.setMergeUnitAndTile(true);
        field6.setMergeUnitAndTile(true);
        field7.setMergeUnitAndTile(true);
        field8.setMergeUnitAndTile(true);
        field9.setMergeUnitAndTile(true);
        field10.setMergeUnitAndTile(true);
        field11.setMergeUnitAndTile(true);
        Field field30 = field23;
        field30.setMergeUnitAndTile(true);
        Field field31 = field14;
        field31.setMergeUnitAndTile(true);
        Field field32 = field16;
        field32.setMergeUnitAndTile(true);
        Field field33 = field18;
        field33.setMergeUnitAndTile(true);
        Field field34 = field20;
        field34.setMergeUnitAndTile(true);
        Field field35 = field34;
        Field field36 = field22;
        field36.setMergeUnitAndTile(true);
        Field field37 = field36;
        Field field38 = field25;
        field38.setMergeUnitAndTile(true);
        Field field39 = field38;
        Field field40 = field27;
        field40.setMergeUnitAndTile(true);
        field28.setMergeUnitAndTile(true);
        field29.setMergeUnitAndTile(true);
        ArrayList arrayList6 = arrayList5;
        arrayList6.add(field4);
        arrayList6.add(field5);
        arrayList6.add(field6);
        arrayList6.add(field7);
        arrayList6.add(field8);
        arrayList6.add(field9);
        arrayList6.add(field10);
        arrayList6.add(field11);
        arrayList6.add(field30);
        arrayList6.add(field31);
        arrayList6.add(field32);
        arrayList6.add(field33);
        arrayList6.add(field35);
        arrayList6.add(field37);
        arrayList6.add(field39);
        arrayList6.add(field40);
        arrayList6.add(field28);
        arrayList6.add(field29);
        Section section7 = section6;
        section7.setFields(arrayList6);
        Section section8 = new Section("reset");
        Section section9 = new Section("text");
        ArrayList arrayList7 = new ArrayList();
        arrayList7.add(new Field("CPextraInfo", "", ""));
        section9.setFields(arrayList7);
        ArrayList arrayList8 = arrayList4;
        arrayList8.add(section5);
        arrayList8.add(section4);
        arrayList8.add(section7);
        arrayList8.add(section8);
        arrayList8.add(section9);
        this.mListAdapter.setSections(arrayList8, this.inputType);
    }

    private void recalculate() {
        double d;
        double d2 = this.temperature;
        if (!this.isMetric) {
            d2 = (d2 - 32.0d) / 1.8d;
        }
        double d3 = 273.15d + d2;
        if (this.inputType.equalsIgnoreCase("O2")) {
            this.pO2 = Math.pow(10.0d, ((-this.EMF) / ((0.0496d * d2) + 13.5482d)) - 0.6792d);
            this.f58Kc = 1.0d / Math.pow(10.0d, (((-this.EMF) + 1462.6048d) / (d3 * 0.0992d)) - 4.8658d);
            this.CO2 = this.f55CO / this.f58Kc;
            this.H2O = this.f57H2 / Math.exp(7.5762d - (((1292.28d - this.EMF) / d3) * 23.213d));
            this.DewPoint = H2OtoDPT(this.H2O);
        } else if (this.inputType.equalsIgnoreCase("CO")) {
            this.pO2 = Math.pow(10.0d, ((Math.log10(this.f55CO / this.CO2) + (14744.0d / d3)) - 4.526d) * -2.0d);
            this.EMF = -0.0496d * d3 * Math.log10(this.pO2 / 0.209d);
            this.H2O = this.f57H2 / Math.exp(7.5762d - (((1292.28d - this.EMF) / d3) * 23.213d));
            this.DewPoint = H2OtoDPT(this.H2O);
        } else if (this.inputType.equalsIgnoreCase("dewPoint")) {
            double d4 = this.DewPoint;
            if (!this.isMetric) {
                d4 = (this.DewPoint - 32.0d) / 1.8d;
            }
            this.H2O = 50.0d;
            double d5 = this.H2O / 2.0d;
            double d6 = Double.NaN;
            for (int i = 0; i < 100; i++) {
                d6 = H2OtoDPT(this.H2O);
                if (Math.abs(d6 - d4) < 0.005d) {
                    break;
                }
                if (d6 > d4) {
                    this.H2O -= d5;
                } else {
                    this.H2O += d5;
                }
                d5 /= 2.0d;
            }
            this.DewPoint = d6;
            this.pO2 = Math.pow(10.0d, ((Math.log10(this.f57H2 / this.H2O) + (13027.0d / d3)) - 2.951d) * -2.0d);
            this.EMF = -0.0496d * d3 * Math.log10(this.pO2 / 0.209d);
            this.f58Kc = 1.0d / Math.pow(10.0d, (((-this.EMF) + 1462.6048d) / (d3 * 0.0992d)) - 4.8658d);
            this.CO2 = this.f55CO / this.f58Kc;
        }
        double d7 = this.f55CO / 100.0d;
        double d8 = this.CO2 / 100.0d;
        double d9 = this.CH4 / 100.0d;
        double d10 = this.f57H2 / 100.0d;
        double d11 = d2;
        double d12 = this.H2O / 100.0d;
        double d13 = 0.0d;
        if (this.CH4 > 0.0d) {
            d = 10.0d;
            d13 = Math.pow(10.0d, (Math.log10((d9 / d10) / d10) - (4791.0d / d3)) + 5.789d);
        } else {
            d = 10.0d;
        }
        double pow = Math.pow(d, (Math.log10((d7 * d7) / d8) + (8817.0d / d3)) - 9.071d);
        double pow2 = Math.pow(10.0d, (Math.log10((d7 * d10) / d12) + (7100.0d / d3)) - 7.496d);
        double pow3 = 0.0196d * Math.pow(d10, 1.5d) * Math.exp(-17600.0d / d3);
        double pow4 = 184.0d * Math.pow(d8 / d7, -0.3d) * d8 * Math.exp(-22400.0d / d3);
        double exp = (((475000.0d * Math.exp(-27150.0d / d3)) * d12) / Math.sqrt(d10)) / (1.0d + (((5600000.0d * Math.exp(-12900.0d / d3)) * d12) / Math.sqrt(d10)));
        double d14 = ((this.CH4Factor / 100.0d) * pow3 * d13) + (pow4 * pow);
        double d15 = ((exp * pow2) - ((this.CH4Factor / 100.0d) * pow3)) - pow4;
        double d16 = -d15;
        double d17 = (d15 * d15) - ((4.0d * d14) * (-exp));
        double d18 = pow;
        double max = Math.max(d18, 1.0d / Math.max(((Math.sqrt(d17) + d16) / 2.0d) / d14, ((d16 - Math.sqrt(d17)) / 2.0d) / d14));
        this.f62ac = max;
        double d19 = d11;
        this.f56CP = m8cp(d19, d18);
        this.f54C = 0.01d * this.alloyFactor * this.f56CP;
        this.CCH4 = this.f54C;
        this.CCH4 = m8cp(d19, max);
        this.CCH4 = this.CCH4 * 0.01d * this.alloyFactor;
        this.SootLimit = 21.5d / (Math.exp((5300.0d / d3) - 1.976d) + 5.215d);
        this.Kcb = this.f55CO / this.CO2;
        this.f60Ko = Math.pow(10.0d, (((-this.EMF) + 1292.2784d) / (d3 * 0.0992d)) - 3.2903d);
        this.f59Kh = this.f57H2 / this.H2O;
        if (!this.isMetric) {
            this.DewPoint = (this.DewPoint * 1.8d) + 32.0d;
        }
        this.mListAdapter.reloadFields();
    }

    /* renamed from: f */
    private double m9f(double d, double d2, double d3) {
        return ((((2300.0d / (d2 + 273.15d)) - 2.21d) + (0.15d * d)) + Math.log10(d)) - Math.log10(d3);
    }

    /* renamed from: cp */
    private double m8cp(double d, double d2) {
        double f;
        double d3 = 0.01d;
        int i = 0;
        do {
            f = m9f(d3, d, d2);
            d3 -= f / ((m9f(d3 + 0.001d, d, d2) - m9f(d3 - 0.001d, d, d2)) / 0.002d);
            if (d3 <= 0.0d) {
                return 0.0d;
            }
            i++;
            if (i >= 200) {
                return 0.0d;
            }
        } while (Math.abs(f) > 0.001d);
        return d3;
    }

    private double H2OtoDPT(double d) {
        double d2 = d / 22120.0d;
        return ((((Math.pow(d2, 0.65d) * 0.0129281d) * Math.sin(Math.log(((100.0d / d) * 0.904104d) * 221.2d) * 0.803d)) + (10.65669d / ((-Math.log(d2)) + 6.5509d))) - 0.625124d) * 374.15d;
    }

    private void recalculateUnits() {
        if (this.isMetric) {
            this.temperature = Utils.fahrenheitToCelsius(this.temperature);
            if (this.inputType.equalsIgnoreCase("dewPoint")) {
                this.DewPoint = Utils.fahrenheitToCelsius(this.DewPoint);
                return;
            }
            return;
        }
        this.temperature = Utils.celsiusToFahrenheit(this.temperature);
        if (this.inputType.equalsIgnoreCase("dewPoint")) {
            this.DewPoint = Utils.celsiusToFahrenheit(this.DewPoint);
        }
    }

    /* Code decompiled incorrectly, please refer to instructions dump. */
    public java.lang.Object valueFor(java.lang.String r3) {
        /*
            r2 = this;
            int r0 = r3.hashCode()
            r1 = 0
            switch(r0) {
                case -1896897414: goto L_0x00f5;
                case -688347430: goto L_0x00ea;
                case 67: goto L_0x00df;
                case 2156: goto L_0x00d5;
                case 2157: goto L_0x00cb;
                case 2282: goto L_0x00c1;
                case 2424: goto L_0x00b6;
                case 2429: goto L_0x00ab;
                case 2436: goto L_0x00a0;
                case 2444: goto L_0x0095;
                case 3106: goto L_0x0089;
                case 66671: goto L_0x007d;
                case 66886: goto L_0x0072;
                case 68766: goto L_0x0067;
                case 70821: goto L_0x005c;
                case 75242: goto L_0x0050;
                case 110131: goto L_0x0044;
                case 2062668: goto L_0x0038;
                case 321701236: goto L_0x002d;
                case 1068500753: goto L_0x0021;
                case 1090165406: goto L_0x0015;
                case 1179738938: goto L_0x000a;
                default: goto L_0x0008;
            }
        L_0x0008:
            goto L_0x0100
        L_0x000a:
            java.lang.String r0 = "DewPoint"
            boolean r3 = r3.equals(r0)
            if (r3 == 0) goto L_0x0100
            r3 = 7
            goto L_0x0101
        L_0x0015:
            java.lang.String r0 = "CH4Factor"
            boolean r3 = r3.equals(r0)
            if (r3 == 0) goto L_0x0100
            r3 = 17
            goto L_0x0101
        L_0x0021:
            java.lang.String r0 = "CPextraInfo"
            boolean r3 = r3.equals(r0)
            if (r3 == 0) goto L_0x0100
            r3 = 21
            goto L_0x0101
        L_0x002d:
            java.lang.String r0 = "temperature"
            boolean r3 = r3.equals(r0)
            if (r3 == 0) goto L_0x0100
            r3 = r1
            goto L_0x0101
        L_0x0038:
            java.lang.String r0 = "CCH4"
            boolean r3 = r3.equals(r0)
            if (r3 == 0) goto L_0x0100
            r3 = 18
            goto L_0x0101
        L_0x0044:
            java.lang.String r0 = "pO2"
            boolean r3 = r3.equals(r0)
            if (r3 == 0) goto L_0x0100
            r3 = 10
            goto L_0x0101
        L_0x0050:
            java.lang.String r0 = "Kcb"
            boolean r3 = r3.equals(r0)
            if (r3 == 0) goto L_0x0100
            r3 = 12
            goto L_0x0101
        L_0x005c:
            java.lang.String r0 = "H2O"
            boolean r3 = r3.equals(r0)
            if (r3 == 0) goto L_0x0100
            r3 = 6
            goto L_0x0101
        L_0x0067:
            java.lang.String r0 = "EMF"
            boolean r3 = r3.equals(r0)
            if (r3 == 0) goto L_0x0100
            r3 = 1
            goto L_0x0101
        L_0x0072:
            java.lang.String r0 = "CO2"
            boolean r3 = r3.equals(r0)
            if (r3 == 0) goto L_0x0100
            r3 = 5
            goto L_0x0101
        L_0x007d:
            java.lang.String r0 = "CH4"
            boolean r3 = r3.equals(r0)
            if (r3 == 0) goto L_0x0100
            r3 = 16
            goto L_0x0101
        L_0x0089:
            java.lang.String r0 = "ac"
            boolean r3 = r3.equals(r0)
            if (r3 == 0) goto L_0x0100
            r3 = 9
            goto L_0x0101
        L_0x0095:
            java.lang.String r0 = "Kw"
            boolean r3 = r3.equals(r0)
            if (r3 == 0) goto L_0x0100
            r3 = 14
            goto L_0x0101
        L_0x00a0:
            java.lang.String r0 = "Ko"
            boolean r3 = r3.equals(r0)
            if (r3 == 0) goto L_0x0100
            r3 = 15
            goto L_0x0101
        L_0x00ab:
            java.lang.String r0 = "Kh"
            boolean r3 = r3.equals(r0)
            if (r3 == 0) goto L_0x0100
            r3 = 13
            goto L_0x0101
        L_0x00b6:
            java.lang.String r0 = "Kc"
            boolean r3 = r3.equals(r0)
            if (r3 == 0) goto L_0x0100
            r3 = 11
            goto L_0x0101
        L_0x00c1:
            java.lang.String r0 = "H2"
            boolean r3 = r3.equals(r0)
            if (r3 == 0) goto L_0x0100
            r3 = 3
            goto L_0x0101
        L_0x00cb:
            java.lang.String r0 = "CP"
            boolean r3 = r3.equals(r0)
            if (r3 == 0) goto L_0x0100
            r3 = 4
            goto L_0x0101
        L_0x00d5:
            java.lang.String r0 = "CO"
            boolean r3 = r3.equals(r0)
            if (r3 == 0) goto L_0x0100
            r3 = 2
            goto L_0x0101
        L_0x00df:
            java.lang.String r0 = "C"
            boolean r3 = r3.equals(r0)
            if (r3 == 0) goto L_0x0100
            r3 = 19
            goto L_0x0101
        L_0x00ea:
            java.lang.String r0 = "SootLimit"
            boolean r3 = r3.equals(r0)
            if (r3 == 0) goto L_0x0100
            r3 = 8
            goto L_0x0101
        L_0x00f5:
            java.lang.String r0 = "alloyFactor"
            boolean r3 = r3.equals(r0)
            if (r3 == 0) goto L_0x0100
            r3 = 20
            goto L_0x0101
        L_0x0100:
            r3 = -1
        L_0x0101:
            switch(r3) {
                case 0: goto L_0x0198;
                case 1: goto L_0x0191;
                case 2: goto L_0x018a;
                case 3: goto L_0x0183;
                case 4: goto L_0x017c;
                case 5: goto L_0x0175;
                case 6: goto L_0x016e;
                case 7: goto L_0x0167;
                case 8: goto L_0x0160;
                case 9: goto L_0x0159;
                case 10: goto L_0x0152;
                case 11: goto L_0x014b;
                case 12: goto L_0x0144;
                case 13: goto L_0x013d;
                case 14: goto L_0x0136;
                case 15: goto L_0x012f;
                case 16: goto L_0x0128;
                case 17: goto L_0x0121;
                case 18: goto L_0x011a;
                case 19: goto L_0x0113;
                case 20: goto L_0x010c;
                case 21: goto L_0x0109;
                default: goto L_0x0104;
            }
        L_0x0104:
            java.lang.Integer r3 = java.lang.Integer.valueOf(r1)
            return r3
        L_0x0109:
            java.lang.String r3 = "* DIN 17 022 Part 3"
            return r3
        L_0x010c:
            double r0 = r2.alloyFactor
            java.lang.Double r3 = java.lang.Double.valueOf(r0)
            return r3
        L_0x0113:
            double r0 = r2.f54C
            java.lang.Double r3 = java.lang.Double.valueOf(r0)
            return r3
        L_0x011a:
            double r0 = r2.CCH4
            java.lang.Double r3 = java.lang.Double.valueOf(r0)
            return r3
        L_0x0121:
            double r0 = r2.CH4Factor
            java.lang.Double r3 = java.lang.Double.valueOf(r0)
            return r3
        L_0x0128:
            double r0 = r2.CH4
            java.lang.Double r3 = java.lang.Double.valueOf(r0)
            return r3
        L_0x012f:
            double r0 = r2.f60Ko
            java.lang.Double r3 = java.lang.Double.valueOf(r0)
            return r3
        L_0x0136:
            double r0 = r2.f61Kw
            java.lang.Double r3 = java.lang.Double.valueOf(r0)
            return r3
        L_0x013d:
            double r0 = r2.f59Kh
            java.lang.Double r3 = java.lang.Double.valueOf(r0)
            return r3
        L_0x0144:
            double r0 = r2.Kcb
            java.lang.Double r3 = java.lang.Double.valueOf(r0)
            return r3
        L_0x014b:
            double r0 = r2.f58Kc
            java.lang.Double r3 = java.lang.Double.valueOf(r0)
            return r3
        L_0x0152:
            double r0 = r2.pO2
            java.lang.Double r3 = java.lang.Double.valueOf(r0)
            return r3
        L_0x0159:
            double r0 = r2.f62ac
            java.lang.Double r3 = java.lang.Double.valueOf(r0)
            return r3
        L_0x0160:
            double r0 = r2.SootLimit
            java.lang.Double r3 = java.lang.Double.valueOf(r0)
            return r3
        L_0x0167:
            double r0 = r2.DewPoint
            java.lang.Double r3 = java.lang.Double.valueOf(r0)
            return r3
        L_0x016e:
            double r0 = r2.H2O
            java.lang.Double r3 = java.lang.Double.valueOf(r0)
            return r3
        L_0x0175:
            double r0 = r2.CO2
            java.lang.Double r3 = java.lang.Double.valueOf(r0)
            return r3
        L_0x017c:
            double r0 = r2.f56CP
            java.lang.Double r3 = java.lang.Double.valueOf(r0)
            return r3
        L_0x0183:
            double r0 = r2.f57H2
            java.lang.Double r3 = java.lang.Double.valueOf(r0)
            return r3
        L_0x018a:
            double r0 = r2.f55CO
            java.lang.Double r3 = java.lang.Double.valueOf(r0)
            return r3
        L_0x0191:
            double r0 = r2.EMF
            java.lang.Double r3 = java.lang.Double.valueOf(r0)
            return r3
        L_0x0198:
            double r0 = r2.temperature
            java.lang.Double r3 = java.lang.Double.valueOf(r0)
            return r3
        */
        throw new UnsupportedOperationException("Method not decompiled: com.upc_group.upcpro.fragments.calculators.CarbonOxygenFragment.valueFor(java.lang.String):java.lang.Object");
    }

    public boolean canUpdateValue(String str, String str2) {
        boolean z = true;
        if (str.equalsIgnoreCase("DewPoint")) {
            try {
                if (Double.parseDouble(str2) > ((double) (this.isMetric ? 60 : 140))) {
                    z = false;
                }
                return z;
            } catch (Exception unused) {
            }
        }
        return true;
    }

    public int imeOptions(String str) {
        boolean z = this.inputType.equalsIgnoreCase("o2") ? str.equalsIgnoreCase("alloyfactor") : this.inputType.equalsIgnoreCase("CO") ? str.equalsIgnoreCase("co2") : this.inputType.equalsIgnoreCase("dewPoint") ? str.equalsIgnoreCase("dewpoint") : false;
        return z ? 6 : 5;
    }

    public void valueDidChange(String str, String str2) {
        char c = 65535;
        try {
            switch (str.hashCode()) {
                case -1896897414:
                    if (str.equals("alloyFactor")) {
                        c = 20;
                        break;
                    }
                    break;
                case -688347430:
                    if (str.equals("SootLimit")) {
                        c = 8;
                        break;
                    }
                    break;
                case 67:
                    if (str.equals("C")) {
                        c = 19;
                        break;
                    }
                    break;
                case 2156:
                    if (str.equals("CO")) {
                        c = 2;
                        break;
                    }
                    break;
                case 2157:
                    if (str.equals("CP")) {
                        c = 4;
                        break;
                    }
                    break;
                case 2282:
                    if (str.equals("H2")) {
                        c = 3;
                        break;
                    }
                    break;
                case 2424:
                    if (str.equals("Kc")) {
                        c = 11;
                        break;
                    }
                    break;
                case 2429:
                    if (str.equals("Kh")) {
                        c = 13;
                        break;
                    }
                    break;
                case 2436:
                    if (str.equals("Ko")) {
                        c = 15;
                        break;
                    }
                    break;
                case 2444:
                    if (str.equals("Kw")) {
                        c = 14;
                        break;
                    }
                    break;
                case 3106:
                    if (str.equals("ac")) {
                        c = 9;
                        break;
                    }
                    break;
                case 66671:
                    if (str.equals("CH4")) {
                        c = 16;
                        break;
                    }
                    break;
                case 66886:
                    if (str.equals("CO2")) {
                        c = 5;
                        break;
                    }
                    break;
                case 68766:
                    if (str.equals("EMF")) {
                        c = 1;
                        break;
                    }
                    break;
                case 70821:
                    if (str.equals("H2O")) {
                        c = 6;
                        break;
                    }
                    break;
                case 75242:
                    if (str.equals("Kcb")) {
                        c = 12;
                        break;
                    }
                    break;
                case 110131:
                    if (str.equals("pO2")) {
                        c = 10;
                        break;
                    }
                    break;
                case 2062668:
                    if (str.equals("CCH4")) {
                        c = 18;
                        break;
                    }
                    break;
                case 321701236:
                    if (str.equals("temperature")) {
                        c = 0;
                        break;
                    }
                    break;
                case 1090165406:
                    if (str.equals("CH4Factor")) {
                        c = 17;
                        break;
                    }
                    break;
                case 1179738938:
                    if (str.equals("DewPoint")) {
                        c = 7;
                        break;
                    }
                    break;
            }
            switch (c) {
                case 0:
                    this.temperature = Double.valueOf(str2).doubleValue();
                    break;
                case 1:
                    this.EMF = Double.valueOf(str2).doubleValue();
                    break;
                case 2:
                    this.f55CO = Double.valueOf(str2).doubleValue();
                    break;
                case 3:
                    this.f57H2 = Double.valueOf(str2).doubleValue();
                    break;
                case 4:
                    this.f56CP = Double.valueOf(str2).doubleValue();
                    break;
                case 5:
                    this.CO2 = Double.valueOf(str2).doubleValue();
                    break;
                case 6:
                    this.H2O = Double.valueOf(str2).doubleValue();
                    break;
                case 7:
                    this.DewPoint = Double.valueOf(str2).doubleValue();
                    break;
                case 8:
                    this.SootLimit = Double.valueOf(str2).doubleValue();
                    break;
                case 9:
                    this.f62ac = Double.valueOf(str2).doubleValue();
                    break;
                case 10:
                    this.pO2 = Double.valueOf(str2).doubleValue();
                    break;
                case 11:
                    this.f58Kc = Double.valueOf(str2).doubleValue();
                    break;
                case 12:
                    this.Kcb = Double.valueOf(str2).doubleValue();
                    break;
                case 13:
                    this.f59Kh = Double.valueOf(str2).doubleValue();
                    break;
                case 14:
                    this.f61Kw = Double.valueOf(str2).doubleValue();
                    break;
                case 15:
                    this.f60Ko = Double.valueOf(str2).doubleValue();
                    break;
                case 16:
                    this.CH4 = Double.valueOf(str2).doubleValue();
                    break;
                case 17:
                    this.CH4Factor = Double.valueOf(str2).doubleValue();
                    break;
                case 18:
                    this.CCH4 = Double.valueOf(str2).doubleValue();
                    break;
                case 19:
                    this.f54C = Double.valueOf(str2).doubleValue();
                    break;
                case 20:
                    this.alloyFactor = Double.valueOf(str2).doubleValue();
                    break;
            }
            recalculate();
        } catch (Exception unused) {
        }
    }

    public void unitsDidChange(boolean z) {
        this.isMetric = z;
        recalculateUnits();
        prepareFields();
        recalculate();
    }

    public void didReset() {
        setupDefaultValues(true);
        this.mListAdapter.reloadFields();
    }

    public void didSelectInput(String str) {
        this.inputType = str;
        prepareFields();
    }

    public void didChangeUnits(boolean z) {
        if (z != this.isMetric) {
            this.isMetric = z;
            try {
                recalculateUnits();
                recalculate();
            } catch (Exception unused) {
            }
        }
    }
}
