package demo.easyexcel.read.listener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.metadata.CellExtra;
import com.alibaba.excel.read.listener.ReadListener;
import com.alibaba.fastjson.JSON;

import org.junit.Assert;

import demo.easyexcel.read.dto.DemoExtraDto;

/**
 * DemoExtraListener
 * 读取单元格的批注
 *
 * @author Wenzhou
 * @since 2023/3/18 2:22
 */
public class DemoExtraListener implements ReadListener<DemoExtraDto> {
    @Override
    public void invoke(DemoExtraDto demoExtraData, AnalysisContext analysisContext) {

    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {

    }

    @Override
    public void extra(CellExtra extra, AnalysisContext context) {
        System.out.println("读取到了一条额外信息" + JSON.toJSONString(extra));
        switch (extra.getType()) {
            case COMMENT:
                System.out.println("额外信息是批注，在rowIndex:"
                        + extra.getRowIndex()
                        + ",columnIndex:" + extra.getColumnIndex()
                        + ",内容是:" + extra.getText());
                break;
            case HYPERLINK:
                if ("Sheet1!A1".equals(extra.getText())) {
                    System.out.println("额外信息是超链接，在rowIndex:"
                            + extra.getRowIndex()
                            + ",columnIndex:" + extra.getColumnIndex()
                            + ",内容是:" + extra.getText());
                } else if ("Sheet2!A1".equals(extra.getText())) {
                    System.out.println("额外信息是超链接，而且覆盖了一个区间，在firstRowIndex:"
                            + extra.getFirstRowIndex()
                            + ",firstColumnIndex:" + extra.getFirstColumnIndex()
                            + ",lastRowIndex:" + extra.getLastRowIndex()
                            + ",lastColumnIndex:" + extra.getLastColumnIndex()
                            + ",内容是:" + extra.getText());
                } else {
                    Assert.fail("Unknown hyperlink!");
                }
                break;
            case MERGE:
                System.out.println("额外信息是合并单元格，而且覆盖了一个区间，在firstRowIndex:"
                        + extra.getFirstRowIndex()
                        + ",firstColumnIndex:" + extra.getFirstColumnIndex()
                        + ",lastRowIndex:" + extra.getLastRowIndex()
                        + ",lastColumnIndex:" + extra.getLastColumnIndex());
                break;
            default:
        }
    }
}
