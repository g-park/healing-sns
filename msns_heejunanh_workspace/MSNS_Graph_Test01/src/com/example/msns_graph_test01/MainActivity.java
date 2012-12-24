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

		// ǥ���� ��ġ��
		List<double[]> values = new ArrayList<double[]>();
		values.add(new double[] { 14230, 12300, 14240, 15244, 15900, 19200,
				22030, 21200, 19500, 15500, 12600, 14000 });

		/** �׷��� ����� ���� �׷��� �Ӽ� ������ü */
		XYMultipleSeriesRenderer renderer = new XYMultipleSeriesRenderer();

		// ��� ǥ�� ����� ���� ũ��
		renderer.setChartTitle("2011�⵵ �Ǹŷ�");
		renderer.setChartTitleTextSize(20);

		// �з��� ���� �̸�
		String[] titles = new String[] { "���� �Ǹŷ�" };

		// �׸��� ǥ���ϴµ� ���� ����
		int[] colors = new int[] { Color.YELLOW };

		// �з��� ���� ũ�� �� �� ���� ����
		renderer.setLegendTextSize(15);
		int length = colors.length;
		for (int i = 0; i < length; i++) {
			SimpleSeriesRenderer r = new SimpleSeriesRenderer();
			r.setColor(colors[i]);
			renderer.addSeriesRenderer(r);
		}

		// X,Y�� �׸��̸��� ���� ũ��
		renderer.setXTitle("��");
		renderer.setYTitle("�Ǹŷ�");
		renderer.setAxisTitleTextSize(12);

		// ��ġ�� ���� ũ�� / X�� �ּ�,�ִ밪 / Y�� �ּ�,�ִ밪
		renderer.setLabelsTextSize(10);
		renderer.setXAxisMin(0.5);
		renderer.setXAxisMax(12.5);
		renderer.setYAxisMin(0);
		renderer.setYAxisMax(24000);

		// X,Y�� ���� ����
		renderer.setAxesColor(Color.WHITE);
		// �������, X,Y�� ����, ��ġ���� ���� ����
		renderer.setLabelsColor(Color.CYAN);

		// X���� ǥ�� ����
		renderer.setXLabels(12);
		// Y���� ǥ�� ����
		renderer.setYLabels(5);

		// X,Y�� ���Ĺ���
		renderer.setXLabelsAlign(Align.LEFT);
		renderer.setYLabelsAlign(Align.LEFT);
		// X,Y�� ��ũ�� ���� ON/OFF
		renderer.setPanEnabled(false, false);
		// ZOOM��� ON/OFF
		renderer.setZoomEnabled(false, false);
		// ZOOM ����
		renderer.setZoomRate(1.0f);
		// ���밣 ����
		renderer.setBarSpacing(0.5f);

		// ���� ���� ����
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

		// �׷��� ��ü ����
		GraphicalView gv = ChartFactory.getBarChartView(this, dataset,
				renderer, Type.STACKED);

		// �׷����� LinearLayout�� �߰�
		LinearLayout llBody = (LinearLayout) findViewById(R.id.llBody);
		llBody.addView(gv);
	}
}
