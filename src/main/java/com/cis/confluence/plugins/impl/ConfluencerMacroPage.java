package com.cis.confluence.plugins.impl;

import com.atlassian.confluence.content.render.xhtml.ConversionContext;
import com.atlassian.confluence.macro.Macro;
import com.atlassian.confluence.macro.MacroExecutionException;
import com.cis.confluence.plugins.utils.ConfluencerManager;
import org.apache.log4j.Logger;

import java.util.Map;

public class ConfluencerMacroPage implements Macro {

    private static Logger logger = Logger.getLogger(ConfluencerManager.class);

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
        final int[] cont = {1};

        //sb.append("#requireResource(\"confluence.web.resources:aui-forms\")");

        sb.append("<h1 style=\"color: #50394c; text-align: left;\">").append("CiS :: Confluencer Module").append("</h1>");
        sb.append("<br><br>");
        sb.append("<h1 style=\"color: #50394c; text-align: center; margin-bottom: 50px;\">").append("¡¡¡ CiS :: Confluencer 2020 !!!").append("</h1>");
        sb.append("<u><h3 style=\"color: #50394c; text-align: center; margin-bottom: 50px;\">").append("¡¡¡ Ranking List of Confluencers !!!").append("</h3></u>");

        sb.append("<div  class=\"aui-page-panel\">");
        sb.append("<table class=\"aui aui-table-sortable\">").
                append("<thead >").
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
                append("</tr>").
                append("</thead>").
                append("<br>").
                append("<tbody>");

        //mark the first user with diferent color, ehs the Confluencer at the moment
        if (null != ConfluencerManager.getFirst()) {
            sb.append("<tr class=\"aui-page-panel-inline\" style=\"text-align: center;\">").
                    append("<td class=\"aui-page-panel-nav\" style=\"text-align: center;\">").append("<p>").append(cont[0]).append("</p>").append("</td>").
                    append("<td class=\"aui-page-panel-content user-hover user-avatar jprt_user-hover-new jprt_user-hover\">").append("<img  style=\"border-radius: 25%; border: 1px solid black; margin: 10px;\" src=\"" + ConfluencerManager.getFirst().getIcon().getPath() + "\"   width=\"50\" height=\"50\" />").append("</td>").
                    append("<td class=\"aui-page-panel-content\" >").append("<p style=\"text-align: left; margin: 10px;\">").append(ConfluencerManager.getFirst().getFullName()).append("</p>").append("</td>").
                    append("<td class=\"aui-page-panel-content\" style=\"text-align: center; margin: 10px;\">").append("<p>").append(ConfluencerManager.getFirst().getSpace()).append("</p>").append("</td>").
                    append("<td class=\"aui-page-panel-content\" style=\"text-align: center; margin: 10px;\">").append("<p>").append(ConfluencerManager.getFirst().getPage()).append("</p>").append("</td>").
                    append("<td class=\"aui-page-panel-content\" style=\"text-align: center; margin: 10px;\">").append("<p>").append(ConfluencerManager.getFirst().getBlog()).append("</p>").append("</td>").
                    append("<td class=\"aui-page-panel-content\" style=\"text-align: center; margin: 10px;\">").append("<p>").append(ConfluencerManager.getFirst().getComment()).append("</p>").append("</td>").
                    append("<td class=\"aui-page-panel-content\" style=\"text-align: center; margin: 10px;\">").append("<p>").append(ConfluencerManager.getFirst().getLike()).append("</p>").append("</td>").
                    append("<td class=\"aui-page-panel-content\" style=\"text-align: center; margin: 10px;\">").append("<p>").append(ConfluencerManager.getFirst().totalScore()).append("</p>").append("</td>").
                    append("</tr>");

            //show other users
            ConfluencerManager.sortedListWithoutFirst().forEach(u -> {
                cont[0]++;
                sb.append("<tr class=\"aui-page-panel-inline\" >").
                        append("<td class=\"aui-page-panel-nav\" style=\"text-align: center;\">").append("<p>").append(cont[0]).append("</p>").append("</td>").
                        append("<td class=\"aui-page-panel-content\">").append("<img  style=\"border-radius: 25%; border: 1px solid black; margin: 10px;\" src=\"" + u.getIcon().getPath() + "\" width=\"50\" height=\"50\" />").append("</td>").
                        append("<td class=\"aui-page-panel-content\">").append("<p style=\"color: #bc5a45; text-align: left; margin: 10px;\">").append(u.getFullName()).append("</p>").append("</td>").
                        append("<td class=\"aui-page-panel-content\" style=\"text-align: center; margin: 10px;\">").append("<p>").append(u.getSpace()).append("</p>").append("</td>").
                        append("<td class=\"aui-page-panel-content\" style=\"text-align: center; margin: 10px;\">").append("<p>").append(u.getPage()).append("</p>").append("</td>").
                        append("<td class=\"aui-page-panel-content\" style=\"text-align: center; margin: 10px;\">").append("<p>").append(u.getBlog()).append("</p>").append("</td>").
                        append("<td class=\"aui-page-panel-content\" style=\"text-align: center; margin: 10px;\">").append("<p>").append(u.getComment()).append("</p>").append("</td>").
                        append("<td class=\"aui-page-panel-content\" style=\"text-align: center; margin: 10px;\">").append("<p>").append(u.getLike()).append("</p>").append("</td>").
                        append("<td class=\"aui-page-panel-content\" style=\"text-align: center; margin: 10px;\">").append("<p style=\"color: red;\">").append(u.totalScore()).append("</p>").append("</td>").
                        append("</tr>");
            });
        }

        sb.append("</tbody>").append("</table>");
        sb.append("</div>");





//        sb.append("<h1 style=\"color: #50394c; text-align: left;\">").append("CiS :: Confluencer Module").append("</h1>");
//        sb.append("<br><br>");
//        sb.append("<h1 style=\"color: #50394c; text-align: center; margin-bottom: 50px;\">").append("¡¡¡ CiS :: Confluencer 2020 !!!").append("</h1>");
//        sb.append("<u><h3 style=\"color: #50394c; text-align: center; margin-bottom: 50px;\">").append("¡¡¡ Ranking List of Confluencers !!!").append("</h3></u>");
//
//        sb.append("<table  style=\"text-align: center; width: 100%; border-radius: 10px; border:4px solid #cde2f5;\">").
//                append("<thead style=\"font-size: 20px; border-bottom:4px solid #cde2f5; width: 100%;  margin-top: 20px; margin-bottom: 20px; background: bisque; display: table-header-group; color: #6b5b95; font-weight: bold;\">").
//                append("<tr>").
//                append("<td style=\"text-align: center;\">").append("N&#8791").append("</td>").
//                append("<td style=\"text-align: center;\">").append("</td>").
//                append("<td style=\"text-align: left; margin: 10px;\">").append("Confluencer").append("</td>").
//                append("<td style=\"text-align: center;\">").append("Spaces").append("</td>").
//                append("<td style=\"text-align: center;\">").append("Pages").append("</td>").
//                append("<td style=\"text-align: center;\">").append("Blogs").append("</td>").
//                append("<td style=\"text-align: center;\">").append("Comments").append("</td>").
//                append("<td style=\"text-align: center;\">").append("Likes").append("</td>").
//                append("<td style=\"text-align: center;\">").append("Total Score").append("</td>").
//                append("</tr>").
//                append("</thead>").
//                append("<br>").
//                append("<tbody>");
//
//        //mark the first user with diferent color, ehs the Confluencer at the moment
//        if (null != ConfluencerManager.getFirst()) {
//            sb.append("<tr style=\"font-size: 17px; width: 100%; color: blue; background: grey; text-align: center;\">").
//                    append("<td>").append("<p>").append(cont[0]).append("</p>").append("</td>").
//                    append("<td>").append("<img  style=\"border-radius: 25%; border: 1px solid black; margin: 10px;\" src=\"" + ConfluencerManager.getFirst().getIcon().getPath() + "\"  width=\"50\" height=\"50\" />").append("</td>").
//                    append("<td>").append("<p style=\"text-align: left; margin: 10px;\">").append(ConfluencerManager.getFirst().getFullName()).append("</p>").append("</td>").
//                    append("<td>").append("<p>").append(ConfluencerManager.getFirst().getSpace()).append("</p>").append("</td>").
//                    append("<td>").append("<p>").append(ConfluencerManager.getFirst().getPage()).append("</p>").append("</td>").
//                    append("<td>").append("<p>").append(ConfluencerManager.getFirst().getBlog()).append("</p>").append("</td>").
//                    append("<td>").append("<p>").append(ConfluencerManager.getFirst().getComment()).append("</p>").append("</td>").
//                    append("<td>").append("<p>").append(ConfluencerManager.getFirst().getLike()).append("</p>").append("</td>").
//                    append("<td>").append("<p>").append(ConfluencerManager.getFirst().totalScore()).append("</p>").append("</td>").
//                    append("</tr>");
//
//            //show other users
//            ConfluencerManager.sortedListWithoutFirst().forEach(u -> {
//                cont[0]++;
//                sb.append("<tr style=\"font-size: 15px; width: 100%; text-align: center; color: #5b9aa0;\">").
//                        append("<td>").append("<p>").append(cont[0]).append("</p>").append("</td>").
//                        append("<td>").append("<img  style=\"border-radius: 25%; border: 1px solid black; margin: 10px;\" src=\"" + u.getIcon().getPath() + "\" width=\"50\" height=\"50\" />").append("</td>").
//                        append("<td>").append("<p style=\"color: #bc5a45; text-align: left; margin: 10px;\">").append(u.getFullName()).append("</p>").append("</td>").
//                        append("<td>").append("<p>").append(u.getSpace()).append("</p>").append("</td>").
//                        append("<td>").append("<p>").append(u.getPage()).append("</p>").append("</td>").
//                        append("<td>").append("<p>").append(u.getBlog()).append("</p>").append("</td>").
//                        append("<td>").append("<p>").append(u.getComment()).append("</p>").append("</td>").
//                        append("<td>").append("<p>").append(u.getLike()).append("</p>").append("</td>").
//                        append("<td>").append("<p style=\"color: red;\">").append(u.totalScore()).append("</p>").append("</td>").
//                        append("</tr>");
//            });
//        }
//
//        sb.append("</tbody>").append("</table>");


        return sb.toString();
    }

}