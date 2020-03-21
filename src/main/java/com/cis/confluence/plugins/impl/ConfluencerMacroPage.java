package com.cis.confluence.plugins.impl;

import com.atlassian.confluence.content.render.xhtml.ConversionContext;
import com.atlassian.confluence.macro.Macro;
import com.atlassian.confluence.macro.MacroExecutionException;
import com.cis.confluence.plugins.utils.ConfluencerManager;
import org.apache.log4j.Logger;

import java.util.Map;

public class ConfluencerMacroPage implements Macro {

    private static Logger logger = Logger.getLogger(ConfluencerMacroPage.class);

    @Override
    public String execute(Map<String, String> map, String s, ConversionContext conversionContext) throws MacroExecutionException {
        return getPage();
    }

    @Override
    public BodyType getBodyType() {
        return BodyType.PLAIN_TEXT;
    }

    @Override
    public OutputType getOutputType() {
        return OutputType.INLINE;
    }

    public String getPage() {
        final StringBuilder sb = new StringBuilder();

        sb.append("<h1 style=\"color: #50394c; text-align: left;\">").append("CiS :: Confluencer Module").append("</h1>");
        sb.append("<br><br>");
        sb.append("<h1 style=\"color: #50394c; font-size: 40px; text-align: center; margin-bottom: 50px;\">").append("¡¡¡ CiS :: Confluencer 2020 !!!").append("</h1>");

        if (null != ConfluencerManager.getFirst()) {
            sb.append("<table class=\"aui aui-table-sortable\">");
            sb.append(getHeader());
            sb.append("<tbody>");
            sb.append(getFirst());
            sb.append("</tbody>");
            sb.append("</table>");
        }

        sb.append("<u><h3 style=\"color: #50394c; text-align: center; margin-top: 50px; margin-bottom: 30px;\">").append("¡¡¡ Ranking List of Confluencers !!!").append("</h3></u>");

        if (ConfluencerManager.sortedListWithoutFirst().size() > 0) {
            sb.append("<table class=\"aui aui-table-sortable\">");
            sb.append(getHeader());
            sb.append("<tbody>");
            sb.append(getOthers());
            sb.append("</tbody>");
            sb.append("</table>");
        }
        return sb.toString();
    }



    private String getHeader(){
        StringBuilder sb = new StringBuilder();
        sb.append("<thead >").
            append("<tr class=\"aui-page-panel-inline col-sm-12\">").
            append("<td class=\"aui-page-panel-nav col-1\" style=\"text-align: center; \">").append("N&#8791").append("</td>").
            append("<td class=\"aui-page-panel-nav col-2\" style=\"text-align: center; \">").append("</td>").
            append("<td class=\"aui-page-panel-nav col-sm-3\" style=\"text-align: center; \">").append("Confluencer").append("</td>").
            append("<td class=\"aui-page-panel-nav col-1\" style=\"text-align: center; \">").append("Spaces").append("</td>").
            append("<td class=\"aui-page-panel-nav col-1\" style=\"text-align: center; \">").append("Pages").append("</td>").
            append("<td class=\"aui-page-panel-nav col-1\" style=\"text-align: center; \">").append("Blogs").append("</td>").
            append("<td class=\"aui-page-panel-nav col-1\" style=\"text-align: center; \">").append("Comments").append("</td>").
            append("<td class=\"aui-page-panel-nav col-1\" style=\"text-align: center; \">").append("Likes").append("</td>").
            append("<td class=\"aui-page-panel-nav col-1\" style=\"text-align: center; \">").append("Total Score").append("</td>").
            append("</tr>");
        sb.append("</thead>");
        return sb.toString();
    }

    private String getFirst(){
        StringBuilder sb = new StringBuilder();
        if (null != ConfluencerManager.getFirst()) {
            sb.append("<tr class=\"aui-page-panel-inline\" style=\"text-align: center; \">").
                    append("<td class=\"aui-page-panel-nav\" style=\"text-align: center; padding-top: 30px;\">").append("<p>").append(1).append("</p>").append("</td>").
                    append("<td class=\"aui-page-panel-content user-hover user-avatar jprt_user-hover-new jprt_user-hover\">").append("<img  style=\"border-radius: 25%; border: 1px solid black; margin: 10px;\" src=\"" + ConfluencerManager.getFirst().getIcon().getPath() + "\"   width=\"50\" height=\"50\" />").append("</td>").
                    append("<td class=\"aui-page-panel-content\" >").append("<p style=\"text-align: left; padding-top: 30px;\">").append(ConfluencerManager.getFirst().getFullName()).append("</p>").append("</td>").
                    append("<td class=\"aui-page-panel-content\" style=\"text-align: center; padding-top: 30px;\">").append("<p>").append(ConfluencerManager.getFirst().getSpace()).append("</p>").append("</td>").
                    append("<td class=\"aui-page-panel-content\" style=\"text-align: center; padding-top: 30px;\">").append("<p>").append(ConfluencerManager.getFirst().getPage()).append("</p>").append("</td>").
                    append("<td class=\"aui-page-panel-content\" style=\"text-align: center; padding-top: 30px;\">").append("<p>").append(ConfluencerManager.getFirst().getBlog()).append("</p>").append("</td>").
                    append("<td class=\"aui-page-panel-content\" style=\"text-align: center; padding-top: 30px;\">").append("<p>").append(ConfluencerManager.getFirst().getComment()).append("</p>").append("</td>").
                    append("<td class=\"aui-page-panel-content\" style=\"text-align: center; padding-top: 30px;\">").append("<p>").append(ConfluencerManager.getFirst().getLike()).append("</p>").append("</td>").
                    append("<td class=\"aui-page-panel-content\" style=\"text-align: center; padding-top: 30px;\">").append("<p>").append(ConfluencerManager.getFirst().totalScore()).append("</p>").append("</td>").
                    append("</tr>");
        }
        return sb.toString();
    }

    private String getOthers(){
        final int[] cont = {1};
        StringBuilder sb = new StringBuilder();
        ConfluencerManager.sortedListWithoutFirst().forEach(u -> {
            cont[0]++;
            sb.append("<tr class=\"aui-page-panel-inline\" >").
                    append("<td class=\"aui-page-panel-nav\" style=\"text-align: center; padding-top: 30px;\">").append("<p>").append(cont[0]).append("</p>").append("</td>").
                    append("<td class=\"aui-page-panel-content\">").append("<img  style=\"border-radius: 25%; border: 1px solid black; margin: 10px;\" src=\"" + u.getIcon().getPath() + "\" width=\"50\" height=\"50\" />").append("</td>").
                    append("<td class=\"aui-page-panel-content\">").append("<p style=\"color: #bc5a45; text-align: left; padding-top: 30px;\">").append(u.getFullName()).append("</p>").append("</td>").
                    append("<td class=\"aui-page-panel-content\" style=\"text-align: center; padding-top: 30px;\">").append("<p>").append(u.getSpace()).append("</p>").append("</td>").
                    append("<td class=\"aui-page-panel-content\" style=\"text-align: center; padding-top: 30px;\">").append("<p>").append(u.getPage()).append("</p>").append("</td>").
                    append("<td class=\"aui-page-panel-content\" style=\"text-align: center; padding-top: 30px;\">").append("<p>").append(u.getBlog()).append("</p>").append("</td>").
                    append("<td class=\"aui-page-panel-content\" style=\"text-align: center; padding-top: 30px;\">").append("<p>").append(u.getComment()).append("</p>").append("</td>").
                    append("<td class=\"aui-page-panel-content\" style=\"text-align: center; padding-top: 30px;\">").append("<p>").append(u.getLike()).append("</p>").append("</td>").
                    append("<td class=\"aui-page-panel-content\" style=\"text-align: center; padding-top: 30px;\">").append("<p style=\"color: red;\">").append(u.totalScore()).append("</p>").append("</td>").
                    append("</tr>");
        });
        return sb.toString();
    }

}