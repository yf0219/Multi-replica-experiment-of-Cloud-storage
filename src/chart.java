import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.DateAxis;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.NumberTickUnit;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.jfree.ui.RectangleInsets;

import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * 创建JFreeChart Line Chart（折线图） 
 */
public class chart {
    public static void main(String[] args) {
        // 步骤1：创建CategoryDataset对象（准备数据）  
        XYSeriesCollection dataset = createDataset();
        // 步骤2：根据Dataset 生成JFreeChart对象，以及做相应的设置  
        JFreeChart freeChart = createChart(dataset);
        // 步骤3：将JFreeChart对象输出到文件，Servlet输出流等  
        saveAsFile(freeChart, "D://jfreechart/Replicas Generation1.png", 500, 300);
    }

    // 创建DefaultCategoryDataset对象
    public static XYSeriesCollection createDataset() {
        XYSeries series1 = new XYSeries("Our Scheme");
        XYSeries series2 = new XYSeries("Li[]");


        series1.add(10, 3.2);
        series1.add(20, 3.4);
        series1.add(30, 3.7);
        series1.add(40, 3.7);
        series1.add(50, 3.9);
        series1.add(60, 4.0);
        series1.add(70, 4.0);
        series1.add(80, 4.1);
        series1.add(90, 4.3);
        series1.add(100, 4.3);
        series2.add(10, 3.3);
        series2.add(20, 3.4);
        series2.add(30, 3.6);
        series2.add(40, 3.7);
        series2.add(50, 3.8);
        series2.add(60, 4.0);
        series2.add(70, 4.1);
        series2.add(80, 4.2);
        series2.add(90, 4.3);
        series2.add(100, 4.4);
        XYSeriesCollection dataset = new XYSeriesCollection();
        dataset.addSeries(series1);
        dataset.addSeries(series2);

        return dataset;
    }
    // 根据CategoryDataset生成JFreeChart对象  
    public static JFreeChart createChart(XYSeriesCollection lineDataset) {
        JFreeChart jfreechart = ChartFactory.createTimeSeriesChart(
                "Replicas Generation",       // 标题
                "Number of copies",       // categoryAxisLabel （category轴，横轴，X轴的标签）
                "Time",  // valueAxisLabel（value轴，纵轴，Y轴的标签）
                (XYDataset) lineDataset,// dataset
                true,       // legend  
                true,       // tooltips  
                true);      // URLs  


        // 配置字体（解决中文乱码的通用方法）  
        Font xfont = new Font("宋体", Font.PLAIN, 16); // X轴  
        Font yfont = new Font("宋体", Font.PLAIN, 16); // Y轴  
        Font kfont = new Font("宋体", Font.PLAIN, 14); // 底部  
        Font titleFont = new Font("隶书", Font.BOLD, 20); // 图片标题  

        jfreechart.setBackgroundPaint(Color.white);
        XYPlot xyplot = (XYPlot) jfreechart.getPlot(); // 获得 plot：XYPlot！  
        NumberAxis domainAxiss = new NumberAxis("Number of copies");
        domainAxiss.setRange(0, 110);
        domainAxiss.setTickUnit(new NumberTickUnit(10));

        xyplot.getDomainAxis().setLabelFont(xfont);
        xyplot.getRangeAxis().setLabelFont(yfont);
        jfreechart.getLegend().setItemFont(kfont);
        jfreechart.getTitle().setFont(titleFont);

        //设置时间格式，同时也解决了乱码问题  
        DateAxis dateaxis = (DateAxis) xyplot.getDomainAxis();


        xyplot.setDomainAxis(dateaxis);

        // Set the range for the x-axis

        XYPlot plot = (XYPlot) jfreechart.getPlot();
        plot.setDomainAxis(domainAxiss);
        ValueAxis domainAxis = plot.getDomainAxis();
        domainAxis.setRange(0, 110);

// Set the range for the y-axis
        ValueAxis rangeAxis = plot.getRangeAxis();
        rangeAxis.setRange(1, 5);



        // 以下的设置可以由用户定制，也可以省略  

        XYLineAndShapeRenderer xylineandshaperenderer = (XYLineAndShapeRenderer) plot.getRenderer();
        // 设置网格背景颜色  
        plot.setBackgroundPaint(Color.white);
        // 设置网格竖线颜色  
        plot.setDomainGridlinePaint(Color.pink);
        // 设置网格横线颜色  
        plot.setRangeGridlinePaint(Color.pink);
        // 设置曲线图与xy轴的距离  
        plot.setAxisOffset(new RectangleInsets(0D, 0D, 0D, 10D));
        // 设置曲线是否显示数据点  
        xylineandshaperenderer.setBaseShapesVisible(true);
        // 设置曲线显示各数据点的值  
//      XYItemRenderer xyitem = plot.getRenderer();  
//      xyitem.setBaseItemLabelsVisible(true);  
//      xyitem.setBasePositiveItemLabelPosition(new ItemLabelPosition(  
//              ItemLabelAnchor.OUTSIDE12, TextAnchor.BASELINE_LEFT));  
//      xyitem.setBaseItemLabelGenerator(new StandardXYItemLabelGenerator());  
//      xyitem.setBaseItemLabelFont(new Font("Dialog", 1, 14));  
//      plot.setRenderer(xyitem);  

        return jfreechart;
    }

    // 保存为文件  
    public static void saveAsFile(JFreeChart chart, String outputPath,
                                  int weight, int height) {
        FileOutputStream out = null;
        try {
            File outFile = new File(outputPath);
            if (!outFile.getParentFile().exists()) {
                outFile.getParentFile().mkdirs();
            }
            out = new FileOutputStream(outputPath);
            // 保存为PNG文件  
            ChartUtilities.writeChartAsPNG(out, chart, 600, 350);
            // 保存为JPEG文件  
            //ChartUtilities.writeChartAsJPEG(out, chart, 500, 400);  
            out.flush();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (out != null) {
                try {
                    out.close();
                } catch (IOException e) {
                    // do nothing  
                }
            }
        }
    }

}