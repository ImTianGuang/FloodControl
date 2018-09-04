package com.tian.cloud.service.controller;

import com.tian.cloud.service.controller.response.BaseResponse;
import com.tian.cloud.service.controller.response.CompanyInfo;
import com.tian.cloud.service.controller.response.FloodSituationInfo;
import com.tian.cloud.service.dao.entity.CommonType;
import com.tian.cloud.service.dao.entity.Company;
import com.tian.cloud.service.dao.entity.Message;
import com.tian.cloud.service.service.*;
import com.tian.cloud.service.util.excel.ExcelExportUtil;
import com.tian.cloud.service.util.excel.MySheet;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;
import java.util.List;

/**
 * @author tianguang
 * 2018/8/20 下午1:41
 **/
@RestController
@RequestMapping("/manage/")
@Slf4j
public class ManageController {

    @Resource
    private CompanyService companyService;

    @Resource
    private CommonTypeService commonTypeService;

    @Resource
    private SituationService situationService;

    @Resource
    private MessageService messageService;

    @Resource
    private ExportService exportService;

    @RequestMapping("updateCompanyList")
    @ResponseBody
    public BaseResponse<Boolean> updateCompanyList(@RequestBody Company company) {
        log.info("companyList:{}", company);
        companyService.saveOrUpdate(company);
        return BaseResponse.success(true);
    }

    // addOrUpdate company
    @RequestMapping("updateCompany")
    @ResponseBody
    public BaseResponse<Boolean> saveOrUpdateCompanyInfo(@RequestBody CompanyInfo companyInfo) {
        companyService.saveCompanyInfo(companyInfo);
        return BaseResponse.success(true);
    }

    @RequestMapping("updateCommonType")
    @ResponseBody
    public BaseResponse<Boolean> saveOrUpdateCommonType(@RequestBody List<CommonType> commonTypeList) {
        log.info("commonType:{}", commonTypeList);
        commonTypeService.saveOrUpdate(commonTypeList);
        return BaseResponse.success(true);
    }

    @RequestMapping("updateMessage")
    @ResponseBody
    public BaseResponse<Void> saveOrUpdateMessage(@RequestBody Message message) {
        messageService.saveOrUpdate(message);
        return BaseResponse.success(null);
    }

    @RequestMapping("updateSituation")
    @ResponseBody
    public BaseResponse<Boolean> saveOrUpdateFloodSituation(@RequestBody FloodSituationInfo situationInfo) {
        log.info("updateFlood:{}", situationInfo);
        situationService.saveOrUpdate(situationInfo);
        return BaseResponse.success(true);
    }

    // export company
    @RequestMapping("exportCompany")
    @ResponseBody
    public BaseResponse<Boolean> exportCompany(HttpServletRequest request, HttpServletResponse response) throws Exception{
        List<MySheet> mySheets = exportService.getAllUserSheetList();
        Workbook workbook = ExcelExportUtil.exportWorkbook(mySheets);
        OutputStream outputstream = response.getOutputStream();

        //清空输出流
        response.reset();
        //设置响应头和下载保存的文件名
        workbook.write(outputstream);
        response.setHeader("content-disposition","attachment;filename=test.xls");
        //定义输出类型
        response.setContentType("APPLICATION/xls");


        outputstream.close();
        response.flushBuffer();
        return BaseResponse.success(null);
    }
    // export message flood-situation

}
