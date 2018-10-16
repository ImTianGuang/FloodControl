package com.tian.cloud.service.controller;

import com.google.common.base.Charsets;
import com.tian.cloud.service.controller.request.AccountCheckReq;
import com.tian.cloud.service.controller.request.AccountUpdateReq;
import com.tian.cloud.service.controller.request.ChangePassReq;
import com.tian.cloud.service.controller.request.CommonSearchReq;
import com.tian.cloud.service.controller.response.*;
import com.tian.cloud.service.dao.entity.CommonType;
import com.tian.cloud.service.dao.entity.Company;
import com.tian.cloud.service.dao.entity.FloodUser;
import com.tian.cloud.service.dao.entity.Message;
import com.tian.cloud.service.enums.UploadType;
import com.tian.cloud.service.model.auth.AccountCheckResult;
import com.tian.cloud.service.service.*;
import com.tian.cloud.service.util.DateUtil;
import com.tian.cloud.service.util.FileUtils;
import com.tian.cloud.service.util.ParamCheckUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.util.URLEncoder;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLDecoder;
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

    @RequestMapping("accountList")
    @ResponseBody
    public PageResponse<FloodUser> accountList(String token) {
        List<FloodUser> users = authService.accountList(token);
        return PageResponse.success(users, FloodUser::getId);
    }

    @RequestMapping("updateAccountList")
    @ResponseBody
    public BaseResponse<Boolean> updateAccountList(@RequestBody AccountUpdateReq reqs) {
        authService.saveOrUpdate(reqs);
        return BaseResponse.success(true);
    }

    @RequestMapping("checkAccount")
    @ResponseBody
    public BaseResponse<AccountCheckResult> checkAccount(@RequestBody AccountCheckReq checkReq) {
        AccountCheckResult token = authService.checkAccount(checkReq);
        return BaseResponse.success(token);
    }

    @RequestMapping("changePassword")
    @ResponseBody
    public BaseResponse<Boolean> changePassword(@RequestBody ChangePassReq req) {
        boolean result = authService.changePassword(req.getToken(), req.getNewPass());
        return BaseResponse.success(result);
    }

    @RequestMapping("addAccount")
    @ResponseBody
    public BaseResponse<Boolean> addAccount(String token, String name, String pass, boolean isSuper) {
        boolean result = authService.addAccount(token, name, pass, isSuper);
        return BaseResponse.success(result);
    }

    @RequestMapping("delAccount")
    @ResponseBody
    public BaseResponse<Boolean> delAccount(String token, String name) {
        boolean result = authService.delAccount(token, name);
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

    @RequestMapping("exportMessage")
    public BaseResponse<Boolean> exportMessage(@RequestBody CommonSearchReq request) {
        if (request.getStartDateStr() != null) {
            request.setStartTime(DateUtil.str2Date(request.getStartDateStr(), DateUtil.FMT_YYYY_MM1).getTime());
        }
        if (request.getEndDateStr() != null) {
            request.setEndTime(DateUtil.str2Date(request.getEndDateStr(), DateUtil.FMT_YYYY_MM1).getTime());
        }
        exportService.exportMessage(request);
        return BaseResponse.success(null);
    }

    @RequestMapping("uploadUrl")
    @ResponseBody
    public BaseResponse<String> uploadUrl(int uploadType, Integer refId) throws Exception {
        UploadType uploadType1 = UploadType.toEnum(uploadType);
        ParamCheckUtil.assertTrue(uploadType1 != null, "系统异常");
        ParamCheckUtil.assertTrue(refId != null, "系统异常");

        String ext = uploadService.encryptUploadExtra(uploadType1, refId);
        ext = URLEncoder.QUERY.encode(ext, Charsets.UTF_8);
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
    public String doUpload(@RequestParam("file") MultipartFile file, String extraData) throws Exception{
        log.info("extraData:{}", extraData);
        uploadService.upload(file, extraData);
        return "上传成功";
    }

    @RequestMapping("fileUpload")
    @ResponseBody
    public String fileUpload(@RequestParam("file") MultipartFile file, @Param("uploadType") Integer uploadType, @Param("refId") Integer refId) throws Exception{
        log.info("uploadType:{},refId:{}", uploadType, refId);
        uploadService.upload(file, uploadType, refId);
        return "上传成功";
    }

    @RequestMapping("downloadUrl")
    @ResponseBody
    public BaseResponse<String> downloadUrl (HttpServletRequest request, HttpServletResponse response, int uploadType, Integer refId) throws Exception {
        UploadType type = UploadType.toEnum(uploadType);
        ParamCheckUtil.assertTrue(type != null, "参数错误:type");
        ParamCheckUtil.assertTrue(refId != null, "参数错误:refId");
        String ext = uploadService.encryptDownloadExtra(type, refId);
        return BaseResponse.success("/manage/download?ext=" + URLEncoder.QUERY.encode(ext, Charsets.UTF_8));
    }

    @RequestMapping("download")
    public Object download(HttpServletRequest request, HttpServletResponse response, String ext) throws Exception {
        File file = uploadService.getFileByExt(ext);
        if(file.exists()){
            OutputStream os = new BufferedOutputStream(response.getOutputStream());
            try {
                response.setContentType("application/octet-stream");

                String fileName = FileUtils.getFileName(file.getAbsolutePath());
                response.setHeader("Content-disposition", "attachment; filename="
                        + new String(fileName.getBytes("utf-8"), "ISO8859-1")); // 指定下载的文件名
                os.write(org.apache.commons.io.FileUtils.readFileToByteArray(file));
                os.flush();
            } finally {
                if(os != null){
                    os.close();
                }
            }
        }
        return null;
    }

    @RequestMapping("h5/companySelect")
    public ModelAndView companySelect() throws Exception{

        ModelAndView modelAndView = new ModelAndView();
        List<Company> companies = companyService.selectAll();
        modelAndView.setViewName("companySelect");
        modelAndView.addObject("companyList", companies);
        return modelAndView;
    }

    @RequestMapping("h5/floodReport")
    public ModelAndView floodReport(Integer companyId) throws Exception{

        ModelAndView modelAndView = new ModelAndView();
        CompanySituationTypes companySituationTypes = companyService.situationTypesOfCompany(companyId);
        modelAndView.setViewName("afterfloodReport");
        modelAndView.addObject("situationTypeList", companySituationTypes.getSituationTypeList());
        modelAndView.addObject("solutionTypeList", companySituationTypes.getSolutionTypeList());
        return modelAndView;
    }
}
