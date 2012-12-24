package com.example.msns_graph_test01;

import java.util.ArrayList;
import java.util.List;

import org.achartengine.ChartFactory;
import org.achartengine.GraphicalView;
import org.achartengine.chart.BarChart.Type;
import org.achartengine.model.CategorySeries;
import org.achartengine.model.XYMultipleSeriesDataset;
import org.achartengine.renderer.SimpleSeriesRenderer;
import org.achartengine.renderer.XYMultipleSeriesRenderer;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.Paint.Align;
import android.os.Bundle;
import android.widget.LinearLayout;

public class MainActivity extends Activity {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		// 표시할 수치값
		List<double[]> values = new ArrayList<double[]>();
		values.add(new double[] { 14230, 12300, 14240, 15244, 15900, 19200,
				22030, 21200, 19500, 15500, 12600, 14000 });

		/** 그래프 출력을 위한 그래픽 속성 지정객체 */
		XYMultipleSeriesRenderer renderer = new XYMultipleSeriesRenderer();

		// 상단 표시 제목과 글자 크기
		renderer.setChartTitle("2011년도 판매량");
		renderer.setChartTitleTextSize(20);

		// 분류에 대한 이름
		String[] titles = new String[] { "월별 판매량" };

		// 항목을 표시하는데 사용될 색상값
		int[] colors = new int[] { Color.YELLOW };

		// 분류명 글자 크기 및 각 색상 지정
		renderer.setLegendTextSize(15);
		int length = colors.length;
		for (int i = 0; i < length; i++) {
			SimpleSeriesRenderer r = new SimpleSeriesRenderer();
			r.setColor(colors[i]);
			renderer.addSeriesRenderer(r);
		}

		// X,Y축 항목이름과 글자 크기
		renderer.setXTitle("월");
		renderer.setYTitle("판매량");
		renderer.setAxisTitleTextSize(12);

		// 수치값 글자 크기 / X축 최소,최대값 / Y축 최소,최대값
		renderer.setLabelsTextSize(10);
		renderer.setXAxisMin(0.5);
		renderer.setXAxisMax(12.5);
		renderer.setYAxisMin(0);
		renderer.setYAxisMax(24000);

		// X,Y축 라인 색상
		renderer.setAxesColor(Color.WHITE);
		// 상단제목, X,Y축 제목, 수치값의 글자 색상
		renderer.setLabelsColor(Color.CYAN);

		// X축의 표시 간격
		renderer.setXLabels(12);
		// Y축의 표시 간격
		renderer.setYLabels(5);

		// X,Y축 정렬방향
		renderer.setXLabelsAlign(Align.LEFT);
		renderer.setYLabelsAlign(Align.LEFT);
		// X,Y축 스크롤 여부 ON/OFF
		renderer.setPanEnabled(false, false);
		// ZOOM기능 ON/OFF
		renderer.setZoomEnabled(false, false);
		// ZOOM 비율
		renderer.setZoomRate(1.0f);
		// 막대간 간격
		renderer.setBarSpacing(0.5f);

		// 설정 정보 설정
		XYMultipleSeriesDataset dataset = new XYMultipleSeriesDataset();
		for (int i = 0; i < titles.length; i++) {
			CategorySeries series = new CategorySeries(titles[i]);
			double[] v = values.get(i);
			int seriesLength = v.length;
			for (int k = 0; k < seriesLength; k++) {
				series.add(v[k]);
			}
			dataset.addSeries(series.toXYSeries());
		}

		// 그래프 객체 생성
		GraphicalView gv = ChartFactory.getBarChartView(this, dataset,
				renderer, Type.STACKED);

		// 그래프를 LinearLayout에 추가
		LinearLayout llBody = (LinearLayout) findViewById(R.id.llBody);
		llBody.addView(gv);
	}
}
