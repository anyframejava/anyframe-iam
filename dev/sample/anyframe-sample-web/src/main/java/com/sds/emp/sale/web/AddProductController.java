package com.sds.emp.sale.web;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.context.SecurityContextHolder;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import anyframe.core.idgen.IIdGenerationService;
import anyframe.web.springmvc.common.controller.AnyframeFormController;

import com.sds.emp.sale.services.CategoryService;
import com.sds.emp.sale.services.ProductService;
import com.sds.emp.sale.services.ProductVO;
import com.sds.emp.sale.services.SearchVO;

/**
 * controller class for create product.
 * @author Heewon Jung
 */
public class AddProductController extends AnyframeFormController {

    private ProductService productService = null;

    private IIdGenerationService idService = null;

    private CategoryService categoryService = null;

    public void setCategoryService(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    public void setProductService(ProductService productService) {
        this.productService = productService;
    }

    public void setIdGenerationServiceProduct(IIdGenerationService idService) {
        this.idService = idService;
    }

    public ModelAndView process(HttpServletRequest request,
            HttpServletResponse response) throws Exception {

        ProductVO productVO = new ProductVO();
        bind(request, productVO);
        
        String userId =
            SecurityContextHolder.getContext().getAuthentication().getName();

        productVO.setSellerId(userId);
        System.out.println(productVO.toString());
        MultipartHttpServletRequest multipartRequest =
            (MultipartHttpServletRequest) request;
        MultipartFile picturefile = multipartRequest.getFile("realImageFile");
        String pictureName = "";
        String picturefileName = "";
        String pictureExt = "";

        if (picturefile != null
            && !picturefile.getOriginalFilename().equals("")) {
            pictureName = picturefile.getOriginalFilename();

            if (pictureName.toLowerCase().endsWith("jpg")) {
                pictureExt = "jpg";
            } else if (pictureName.toLowerCase().endsWith("gif")) {
                pictureExt = "gif";
            }

            picturefileName =
                idService.getNextStringId()
                    + (!pictureExt.equals("") ? "." + pictureExt : pictureExt);
            String destDir = getServletContext().getRealPath("/upload/");
            File dirPath = new File(destDir);
            if (!dirPath.exists()) {
                dirPath.mkdirs();
            }
            File destination =
                File.createTempFile("file", picturefileName, dirPath);

            FileCopyUtils.copy(picturefile.getInputStream(), new FileOutputStream(
                destination));

            productVO.setImageFile(destination.getAbsolutePath());
        }

        String prodNo = productService.addProduct(productVO);

        productVO.setProdNo(prodNo);
        request.setAttribute("prodNo", prodNo);

        request.setAttribute("productVO", productVO);
        request.setAttribute("picture", productVO.getImageFile());

        return new ModelAndView(this.getSuccess_add());
    }

    protected Object formBackingObject(HttpServletRequest request)
            throws Exception {
        SearchVO searchVO = new SearchVO();
        searchVO.setSearchUseYn("Y");
        ArrayList categoryList =
            (ArrayList) categoryService.getDropDownCategoryList();

        request.setAttribute("categoryList", categoryList);
        return new ProductVO();
    }
}
