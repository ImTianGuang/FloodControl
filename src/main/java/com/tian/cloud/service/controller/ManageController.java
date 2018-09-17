package com.tian.cloud.service.controller;

import com.tian.cloud.service.controller.request.AccountCheckReq;
import com.tian.cloud.service.controller.request.CommonSearchReq;
import com.tian.cloud.service.controller.response.BaseResponse;
import com.tian.cloud.service.controller.response.CompanyInfo;
import com.tian.cloud.service.controller.response.FloodSituationInfo;
import com.tian.cloud.service.dao.entity.CommonType;
import com.tian.cloud.service.dao.entity.Company;
import com.tian.cloud.service.dao.entity.Message;
import com.tian.cloud.service.enums.UploadType;
import com.tian.cloud.service.service.*;
import com.tian.cloud.service.util.DateUtil;
import com.tian.cloud.service.util.ParamCheckUtil;
import com.tian.cloud.service.util.excel.ExcelExportUtil;
import com.tian.cloud.service.util.excel.MySheet;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Param;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

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

    @Resource
    private AuthService authService;

    @Resource
    private UploadService uploadService;

    @RequestMapping("checkAccount")
    @ResponseBody
    public BaseResponse<String> checkAccount(@RequestBody AccountCheckReq checkReq) {
        String token = authService.checkAccount(checkReq);
        return BaseResponse.success(token);
    }

    @RequestMapping("checkToken")
    @ResponseBody
    public BaseResponse<Boolean> checkToken(String token) {
        boolean result = authService.checkToken(token);
        return BaseResponse.success(result);
    }

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
    public BaseResponse<Company> saveOrUpdateCompanyInfo(@RequestBody CompanyInfo companyInfo) {
        Company company = companyService.saveOrUpdateCompanyInfo(companyInfo);
        return BaseResponse.success(company);
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

    @RequestMapping("deleteSituation")
    @ResponseBody
    public BaseResponse<Boolean> deleteSituation(int situationId) {
        log.info("deleteFlood:{}", situationId);
        situationService.deleteById(situationId);
        return BaseResponse.success(true);
    }

    @RequestMapping("exportCompany")
    @ResponseBody
    public BaseResponse<Boolean> exportCompany(HttpServletRequest request, HttpServletResponse response, String emails) throws Exception{

        exportService.exportAll(emails);
        return BaseResponse.success(null);
    }

    @RequestMapping("exportFlood")
    public BaseResponse<Boolean> exportFlood(@RequestBody CommonSearchReq request) {
        if (request.getStartDateStr() != null) {
            request.setStartTime(DateUtil.str2Date(request.getStartDateStr(), DateUtil.FMT_YYYY_MM1).getTime());
        }
        if (request.getEndDateStr() != null) {
            request.setEndTime(DateUtil.str2Date(request.getEndDateStr(), DateUtil.FMT_YYYY_MM1).getTime());
        }
        exportService.exportFlood(request);
        return BaseResponse.success(null);
    }

    @RequestMapping("uploadUrl")
    @ResponseBody
    public BaseResponse<String> uploadUrl(int uploadType, Integer refId) throws Exception {
        UploadType uploadType1 = UploadType.toEnum(uploadType);
        ParamCheckUtil.assertTrue(uploadType1 != null, "系统异常");
        ParamCheckUtil.assertTrue(refId != null, "系统异常");

        String ext = uploadService.encryptExtra(uploadType1, refId);
        return BaseResponse.success("/manage/upload?ext=" + ext + "&title=" + uploadType1.getMsg());
    }

    @RequestMapping("upload")
    public ModelAndView upLoadFlood(@RequestParam(value = "ext") String ext, @RequestParam("title") String title) throws Exception{

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("upLoad");
        modelAndView.addObject("ext", ext);
        modelAndView.addObject("title", title);
        modelAndView.addObject("uploadPath", "/manage/doUpload");
        return modelAndView;
    }

    @RequestMapping("doUpload")
    @ResponseBody
    public BaseResponse<Boolean> doUpload(@RequestParam("file") MultipartFile file, String extraData) throws Exception{
        log.info("extraData:{}", extraData);
        uploadService.upload(file, extraData);
        return BaseResponse.success(true);
    }

    @RequestMapping("download")
    @ResponseBody
    public Object download (HttpServletRequest request, HttpServletResponse response, String path) {
        return null;
    }
}
