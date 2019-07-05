package ky.util;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.labels.ItemLabelAnchor;
import org.jfree.chart.labels.ItemLabelPosition;
import org.jfree.chart.labels.StandardCategoryItemLabelGenerator;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.BarRenderer3D;
import org.jfree.chart.renderer.category.LineAndShapeRenderer;
import org.jfree.chart.title.TextTitle;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.ui.HorizontalAlignment;
import org.jfree.ui.RectangleEdge;
import org.jfree.ui.TextAnchor;

/**
 * @author zj
 * @ClassName DrawTool
 * @description 绘制图表类
 * @date 2014年3月11日16:02:34
 */
public class DrawTool {

    /**
     * @return JFreeChart 图形对象
     * @MethodName drawBarChart
     * @description 根据数据绘制柱状图
     * @author zj
     * @date 2014年3月11日16:03:20
     */
    public static JFreeChart drawBarChart(String title, String Xname,
                                          String Yname, DefaultCategoryDataset dataset) {
        JFreeChart chart = ChartFactory.createBarChart3D(title, Xname, Yname,
                dataset, PlotOrientation.VERTICAL, true, false, false);
        // 设置标题字体样式
        TextTitle textTitle = chart.getTitle();
        textTitle.setFont(new Font("黑体", Font.PLAIN, 20));
        // 设置柱状体颜色
        CategoryPlot categoryplot = (CategoryPlot) chart.getPlot();
        categoryplot.getRenderer().setSeriesPaint(0, new Color(10, 230, 230));
        NumberAxis numberaxis = (NumberAxis) categoryplot.getRangeAxis();
        CategoryAxis domainAxis = categoryplot.getDomainAxis();
        // 设置横轴坐标上的字体样式
        domainAxis.setTickLabelFont(new Font("黑体", Font.PLAIN, 10));
        // 设置横轴的标题字体样式
        domainAxis.setLabelFont(new Font("黑体", Font.PLAIN, 16));
        // 设置纵轴轴坐标上的字体样式
        numberaxis.setTickLabelFont(new Font("sans-serif", Font.PLAIN, 14));
        // 设置纵轴的标题字体样式
        numberaxis.setLabelFont(new Font("黑体", Font.PLAIN, 16));
        // 设置图片最底部字体样式
        if (chart.getLegend() != null) {
            chart.getLegend().setItemFont(new Font("宋体", Font.PLAIN, 16));
        }
        // 设置柱状体的数据显示
        BarRenderer3D renderer = (BarRenderer3D) categoryplot.getRenderer();
        renderer
                .setItemLabelGenerator(new StandardCategoryItemLabelGenerator());
        renderer.setSeriesItemLabelsVisible(0, true);
        // 设置柱状体的数据显示样式
        renderer.setBasePositiveItemLabelPosition(new ItemLabelPosition(
                ItemLabelAnchor.OUTSIDE12, TextAnchor.BASELINE_LEFT));
        renderer.setItemLabelAnchorOffset(10D);
        return chart;
    }

    /**
     * @return JFreeChart 图形对象
     * @MethodName drawLineChart
     * @description 根据数据绘制折线图
     * @author zj
     * @date 2014年3月11日16:03:20
     */
    public static JFreeChart drawLineChart(String title, String Xname,
                                           String Yname, DefaultCategoryDataset dataset) {
        // 创建图形对象
        JFreeChart chart = ChartFactory.createLineChart(title, Xname, Yname,
                dataset, PlotOrientation.VERTICAL, true, true, false);
        // 设置图例的字体显示，防止中文乱码
        chart.getLegend().setItemFont(new Font("黑体", Font.PLAIN, 10));
        // 设置标题并且设置其字体，防止中文乱码
        TextTitle textTitle = chart.getTitle();
        textTitle.setFont(new Font("黑色", Font.PLAIN, 16));
        // 设置图表子标题
        chart.addSubtitle(new TextTitle("按月份"));
        // 创建一个标题对象，用于放置产生图形日前
        TextTitle tt = new TextTitle("日期:"
                + new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
        // 设置该标题的字体，防止中文乱码
        tt.setFont(new Font("黑体", 0, 12));
        // 设置该标题的位置为产生的图形下面
        tt.setPosition(RectangleEdge.BOTTOM);
        // 设置图片为右对齐
        tt.setHorizontalAlignment(HorizontalAlignment.RIGHT);
        // 将该标题添加到图表
        chart.addSubtitle(tt);
        // 获取图表区域对象
        CategoryPlot categoryplot = chart.getCategoryPlot();
        // 设置边线不可见
        //categoryplot.setRangeGridlinesVisible(true);
        // 设置横轴坐标的字体，防止中文乱码
        categoryplot.getDomainAxis().setTickLabelFont(new Font("黑体", 0, 10));
        // 设置横轴标题的字体，防止中文乱码
        categoryplot.getDomainAxis().setLabelFont(
                new Font("黑体", Font.PLAIN, 10));
        // 设置竖轴的字体，防止中文乱码
        categoryplot.getRangeAxis().setLabelFont(new Font("黑体", 0, 10));
        // 获取显示线条的对象
        LineAndShapeRenderer lasp = (LineAndShapeRenderer) categoryplot
                .getRenderer();
        // 设置拐点是否可见/是否显示拐点
        lasp.setBaseShapesVisible(true);
        // 设置拐点不同用不同的形状
        lasp.setDrawOutlines(true);
        // 设置线条是否被显示填充颜色
        lasp.setUseFillPaint(true);
        // 设置拐点颜色
        lasp.setBaseFillPaint(Color.yellow);
        // 设置折线加粗
        lasp.setSeriesStroke(0, new BasicStroke(3F));
        lasp.setSeriesOutlineStroke(0, new BasicStroke(2.0F));
        // 设置折线拐点
        lasp.setSeriesShape(0, new java.awt.geom.Ellipse2D.Double(-5D, -5D,
                10D, 10D));
        return chart;
    }
}
