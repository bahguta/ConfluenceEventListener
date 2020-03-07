package com.cis.confluence.plugins.impl;

import com.atlassian.confluence.content.render.xhtml.ConversionContext;
import com.atlassian.confluence.event.events.user.UserProfilePictureUpdateEvent;
import com.atlassian.confluence.macro.Macro;
import com.atlassian.confluence.macro.MacroExecutionException;
import com.atlassian.confluence.user.UserAccessor;
import com.atlassian.confluence.user.UserProfilePictureManager;
import com.atlassian.confluence.user.actions.AbstractUserProfileAction;
import com.atlassian.confluence.user.actions.ProfilePictureInfo;
import com.atlassian.confluence.user.avatar.ConfluenceAvatarOwner;
import com.cis.confluence.plugins.utils.ConfluencerManager;

import java.io.File;
import java.util.Arrays;
import java.util.Map;
import java.util.Set;

//@ConfluenceComponent
//@Named("ConfluencerMacroPage")
public class ConfluencerMacroPage implements Macro {

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

    public String getPage(){
        StringBuilder sb = new StringBuilder();

        sb.append("<h1 style=\"color: #50394c; text-align: center;\">").append("CiS Confluencer Module").append("</h1>");
        sb.append("<h3 style=\"color: #50394c;\">").append("Confluencer of year 2020 Ranking List !").append("</h3>");
        sb.append("<br>");
        sb.append("<table>").
                append("<thead style=\"margin: 0px; display: table-header-group; color: #6b5b95; font-weight: bold;\">").
                append("<br>").
                append("<tr>").
                append("<td>").append("</td>").
                append("<td>").append("Full Name").append("</td>").
                append("<td>").append("Spaces Created").append("</td>").
                append("<td>").append("Pages Created").append("</td>").
                append("<td>").append("Blogs Created").append("</td>").
                append("<td>").append("Comments Created").append("</td>").
                append("<td>").append("Likes Created").append("</td>").
                append("</tr>").
                append("</thead>").
                append("<br>").
                append("<tbody>");

        ConfluencerManager.getSortedList();

        ConfluencerManager.getList().forEach( u -> {
            sb.append("<tr>").
                    append("<td>").append("<img src=\""+ u.getIcon() +"\"  width=\"40\" height=\"40\" />").append("</td>").
                    append("<td>").append("<p style=\"color: #bc5a45;\">").append(u.getFullName()).append("</p>").append("</td>").
                    append("<td>").append("<p style=\"color: #5b9aa0; text-align: center;\">").append(u.getSpace()).append("</p>").append("</td>").
                    append("<td>").append("<p style=\"color: #5b9aa0; text-align: center;\">").append(u.getPage()).append("</p>").append("</td>").
                    append("<td>").append("<p style=\"color: #5b9aa0; text-align: center;\">").append(u.getBlog()).append("</p>").append("</td>").
                    append("<td>").append("<p style=\"color: #5b9aa0; text-align: center;\">").append(u.getComment()).append("</p>").append("</td>").
                    append("<td>").append("<p style=\"color: #5b9aa0; text-align: center;\">").append(u.getLike()).append("</p>").append("</td>").
                    append("</tr>");
        });

        sb.append("</tbody>").append("</table>");

        return sb.toString();
    }
}