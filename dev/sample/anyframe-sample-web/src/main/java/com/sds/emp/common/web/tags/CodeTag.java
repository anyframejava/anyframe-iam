package com.sds.emp.common.web.tags;

import java.util.ArrayList;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.taglibs.standard.tag.el.core.ExpressionUtil;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.WebApplicationContext;

import com.sds.emp.code.services.CodeService;
import com.sds.emp.code.services.CodeVO;
import com.sds.emp.common.EmpUtil;
import com.sds.emp.purchase.services.DeliveryCompanyService;
import com.sds.emp.purchase.services.DeliveryCompanyVO;
import com.sds.emp.sale.services.CategoryService;
import com.sds.emp.sale.services.CategoryVO;

public class CodeTag extends TagSupport {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    private String name;

    private String mcode;

    private String title;

    private String selected = "";

    private String mode;

    private String sort;

    private String usrstr;

    public ArrayList findList(String mcode) throws Exception {
        ArrayList resultArr;

        ApplicationContext ctx =
            (ApplicationContext) pageContext.getServletContext().getAttribute(
                WebApplicationContext.ROOT_WEB_APPLICATION_CONTEXT_ATTRIBUTE);

        if (mcode.equals("DLVYCOMP")) {
            DeliveryCompanyService deliveryCompanyService =
                (DeliveryCompanyService) ctx.getBean("deliveryCompanyService");
            resultArr =
                (ArrayList) deliveryCompanyService
                    .getDropDownDeliveryCompanyList();
        } else if (mcode.equals("CATEGORY")) {
            CategoryService categoryService =
                (CategoryService) ctx.getBean("categoryService");
            resultArr = (ArrayList) categoryService.getDropDownCategoryList();
        } else {
            CodeService codeService = (CodeService) ctx.getBean("codeService");
            resultArr = (ArrayList) codeService.getCodeList(mcode);
        }

        return resultArr;
    }

    public int doStartTag() throws JspException {
        try {
            JspWriter out = pageContext.getOut();

            StringBuffer str = new StringBuffer();

            str.append("<select ");

            str.append("name='" + name + "' ");

            str.append(usrstr + " ");

            str.append(">");

            if (title != null && !"".equals(title)) {
                str.append("<option value=''>" + title + "</option>");
            }

            ArrayList codeList = findList(mcode);
            if (codeList == null) {
                codeList = new ArrayList();
            }
            if (mcode.equals("DLVYCOMP")) {
                for (int i = 0; i < codeList.size(); i++) {
                    str.append("<option value='");
                    DeliveryCompanyVO deliveryCompanyVO =
                        (DeliveryCompanyVO) codeList.get(i);

                    if (mode != null && mode.equals("1")) {
                        str.append(EmpUtil.null2str(deliveryCompanyVO
                            .getDlvyCompName())
                            + "' ");
                        if (selected.equals(EmpUtil.null2str(deliveryCompanyVO
                            .getDlvyCompName()))) {
                            str.append("selected ");
                        }
                        str.append(">");
                        str.append(EmpUtil.null2str(deliveryCompanyVO
                            .getDlvyCompName())
                            + "</option>");
                    } else if (mode != null && mode.equals("2")) {
                        str.append(EmpUtil.null2str(deliveryCompanyVO
                            .getDlvyCompNo())
                            + "' ");
                        if (selected.equals(EmpUtil.null2str(deliveryCompanyVO
                            .getDlvyCompNo()))) {
                            str.append("selected ");
                        }
                        str.append(">");
                        str.append(EmpUtil.null2str(deliveryCompanyVO
                            .getDlvyCompNo())
                            + "</option>");
                    } else {
                        str.append(EmpUtil.null2str(deliveryCompanyVO
                            .getDlvyCompNo())
                            + "' ");
                        if (selected.equals(EmpUtil.null2str(deliveryCompanyVO
                            .getDlvyCompNo()))) {
                            str.append("selected ");
                        }
                        str.append(">");
                        str.append(EmpUtil.null2str(deliveryCompanyVO
                            .getDlvyCompName())
                            + "</option>");
                    }
                }
            } else if (mcode.equals("CATEGORY")) {
                for (int i = 0; i < codeList.size(); i++) {
                    str.append("<option value='");
                    CategoryVO categoryVO = (CategoryVO) codeList.get(i);
                    if (mode != null && mode.equals("1")) {
                        str.append(EmpUtil.null2str(categoryVO
                            .getCategoryName())
                            + "' ");
                        if (selected.equals(EmpUtil.null2str(categoryVO
                            .getCategoryName()))) {
                            str.append("selected ");
                        }
                        str.append(">");
                        str.append(EmpUtil.null2str(categoryVO
                            .getCategoryName())
                            + "</option>");
                    } else if (mode != null && mode.equals("2")) {
                        str.append(EmpUtil.null2str(categoryVO.getCategoryNo())
                            + "' ");
                        if (selected.equals(EmpUtil.null2str(categoryVO
                            .getCategoryNo()))) {
                            str.append("selected ");
                        }
                        str.append(">");
                        str.append(EmpUtil.null2str(categoryVO.getCategoryNo())
                            + "</option>");
                    } else {
                        str.append(EmpUtil.null2str(categoryVO.getCategoryNo())
                            + "' ");
                        if (selected.equals(EmpUtil.null2str(categoryVO
                            .getCategoryNo()))) {
                            str.append("selected ");
                        }
                        str.append(">");
                        str.append(EmpUtil.null2str(categoryVO
                            .getCategoryName())
                            + "</option>");
                    }
                }
            } else {
                for (int i = 0; i < codeList.size(); i++) {
                    str.append("<option value='");
                    CodeVO codeVO = (CodeVO) codeList.get(i);
                    if (mode != null && mode.equals("1")) {
                        str.append(EmpUtil.null2str(codeVO.getCodeName())
                            + "' ");
                        if (selected.equals(EmpUtil.null2str(codeVO
                            .getCodeName()))) {
                            str.append("selected ");
                        }
                        str.append(">");
                        str.append(EmpUtil.null2str(codeVO.getCodeName())
                            + "</option>");
                    } else if (mode != null && mode.equals("2")) {
                        str.append(EmpUtil.null2str(codeVO.getCode()) + "' ");
                        if (selected.equals(EmpUtil.null2str(codeVO.getCode()))) {
                            str.append("selected ");
                        }
                        str.append(">");
                        str.append(EmpUtil.null2str(codeVO.getCode())
                            + "</option>");
                    } else {
                        str.append(EmpUtil.null2str(codeVO.getCode()) + "' ");
                        if (selected.equals(EmpUtil.null2str(codeVO.getCode()))) {
                            str.append("selected ");
                        }
                        str.append(">");
                        str.append(EmpUtil.null2str(codeVO.getCodeName())
                            + "</option>");
                    }
                }
            }

            str.append("</select>");

            out.print(str.toString());

        } catch (Exception ex) {
            throw new JspTagException(ex.getMessage());
        }
        return SKIP_BODY;
    }

    /**
     * @return Returns the mcode.
     */
    public String getMcode() {
        return mcode;
    }

    /**
     * @param mcode
     *        The mcode to set.
     */
    public void setMcode(String mcode) {
        this.mcode = mcode;
    }

    /**
     * @return Returns the mode.
     */
    public String getMode() {
        return mode;
    }

    /**
     * @param mode
     *        The mode to set.
     */
    public void setMode(String mode) {
        this.mode = mode;
    }

    /**
     * @return Returns the selected.
     */
    public String getSelected() {
        return selected;
    }

    /**
     * @param selected
     *        The selected to set.
     */
    public void setSelected(String selected) {
        try {

            String value =
                (String) ExpressionUtil.evalNotNull("code", "selected",
                    selected, String.class, this, pageContext);

            this.selected = value;
        } catch (Exception ex) {

            try {
                this.selected = selected;
            } catch (Exception e) {
                this.selected = "";
            }
        }
    }

    /**
     * @return Returns the sort.
     */
    public String getSort() {
        return sort;
    }

    /**
     * @param sort
     *        The sort to set.
     */
    public void setSort(String sort) {
        this.sort = sort;
    }

    /**
     * @return Returns the title.
     */
    public String getTitle() {
        return title;
    }

    /**
     * @param title
     *        The title to set.
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * @return Returns the usrstr.
     */
    public String getUsrstr() {
        return usrstr;
    }

    /**
     * @param usrstr
     *        The usrstr to set.
     */
    public void setUsrstr(String usrstr) {
        this.usrstr = usrstr;
    }

    /**
     * @return Returns the name.
     */
    public String getName() {
        return name;
    }

    /**
     * @param name
     *        The name to set.
     */
    public void setName(String name) {
        this.name = name;
    }

}
