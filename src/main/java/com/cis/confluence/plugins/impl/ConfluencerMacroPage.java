package com.cis.confluence.plugins.impl;

import com.atlassian.confluence.content.render.xhtml.ConversionContext;
import com.atlassian.confluence.event.events.user.UserProfilePictureUpdateEvent;
import com.atlassian.confluence.macro.Macro;
import com.atlassian.confluence.macro.MacroExecutionException;
import com.atlassian.confluence.themes.events.FaviconChangedEvent;
import com.atlassian.confluence.user.*;
import com.atlassian.confluence.user.actions.AbstractUserProfileAction;
import com.atlassian.confluence.user.actions.ProfilePictureInfo;
import com.atlassian.confluence.user.avatar.ConfluenceAvatarOwner;
import com.atlassian.confluence.vcache.VCacheRequestContextOperations;
import com.atlassian.plugin.spring.scanner.annotation.imports.ComponentImport;
import com.atlassian.plugin.spring.scanner.annotation.imports.ConfluenceImport;
import com.atlassian.user.User;
import com.cis.confluence.plugins.utils.ConfluencerManager;

import javax.inject.Inject;
import javax.inject.Named;
import java.io.File;
import java.util.Arrays;
import java.util.Map;
import java.util.Set;

//@Named("ConfluencerMacroPage")
public class ConfluencerMacroPage implements Macro {

    private UserDetailsManager userDetailsManager;

    public ConfluencerMacroPage() {
    }

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

        sb.append("<h1 style=\"color: #50394c; text-align: left;\">").append("CiS :: Confluencer Module").append("</h1>");
        sb.append("<br><br>");
        sb.append("<h2 style=\"color: #50394c; text-align: center; margin-bottom: 50px;\">").append("¡¡¡ CiS :: Confluencer 2020 !!!").append("</h2>");
        sb.append("<h3 style=\"color: #50394c; text-align: center; margin-bottom: 50px;\">").append("Ranking List of Confluencers !!! Year 2020 !!!").append("</h3>");

        sb.append("<table style=\"text-align: center; width: 100%; border-radius: 10px; border:4px solid #cde2f5;\">").
                append("<thead style=\"font-size: 18px; border-bottom:4px solid #cde2f5; width: 100%; margin: 20px; background: bisque; display: table-header-group; color: #6b5b95; font-weight: bold;\">").
                append("<tr>").
                append("<td style=\"text-align: center;\">").append("N&#8791").append("</td>").
                append("<td style=\"text-align: center;\">").append("</td>").
                append("<td style=\"text-align: left;\">").append("Confluencer").append("</td>").
                append("<td style=\"text-align: center;\">").append("Spaces").append("</td>").
                append("<td style=\"text-align: center;\">").append("Pages").append("</td>").
                append("<td style=\"text-align: center;\">").append("Blogs").append("</td>").
                append("<td style=\"text-align: center;\">").append("Comments").append("</td>").
                append("<td style=\"text-align: center;\">").append("Likes").append("</td>").
                append("<td style=\"text-align: center;\">").append("Total Score").append("</td>").
                append("</tr>").
                append("</thead>").
                append("<br>").
                append("<tbody>");

        //mark the first user with diferent color
        if (null != ConfluencerManager.getFirst()) {
            sb.append("<tr style=\"font-size: 15px; width: 100%; color: red; background: palegreen; text-align: center;\">").
                    append("<td>").append("<p>").append(cont[0]).append("</p>").append("</td>").
                    append("<td>").append("<img src=\"" + ConfluencerManager.getFirst().getIcon() + "\"  width=\"40\" height=\"40\" />").append("</td>").
                    append("<td>").append("<p style=\"text-align: left;\">").append(ConfluencerManager.getFirst().getFullName()).append("</p>").append("</td>").
                    append("<td>").append("<p>").append(ConfluencerManager.getFirst().getSpace()).append("</p>").append("</td>").
                    append("<td>").append("<p>").append(ConfluencerManager.getFirst().getPage()).append("</p>").append("</td>").
                    append("<td>").append("<p>").append(ConfluencerManager.getFirst().getBlog()).append("</p>").append("</td>").
                    append("<td>").append("<p>").append(ConfluencerManager.getFirst().getComment()).append("</p>").append("</td>").
                    append("<td>").append("<p>").append(ConfluencerManager.getFirst().getLike()).append("</p>").append("</td>").
                    append("<td>").append("<p>").append(ConfluencerManager.getFirst().totalScore()).append("</p>").append("</td>").
                    append("</tr>");

            //show other users
            ConfluencerManager.sortedListWithoutFirst().forEach(u -> {
                cont[0]++;
                sb.append("<tr style=\"font-size: 13px; width: 100%; text-align: center; color: #5b9aa0;\">").
                        append("<td>").append("<p>").append(cont[0]).append("</p>").append("</td>").
                        append("<td>").append("<img src=\"" + u.getIcon() + "\"  width=\"40\" height=\"40\" />").append("</td>").
                        append("<td>").append("<p style=\"color: #bc5a45; text-align: left;\">").append(u.getFullName()).append("</p>").append("</td>").
                        append("<td>").append("<p>").append(u.getSpace()).append("</p>").append("</td>").
                        append("<td>").append("<p>").append(u.getPage()).append("</p>").append("</td>").
                        append("<td>").append("<p>").append(u.getBlog()).append("</p>").append("</td>").
                        append("<td>").append("<p>").append(u.getComment()).append("</p>").append("</td>").
                        append("<td>").append("<p>").append(u.getLike()).append("</p>").append("</td>").
                        append("<td>").append("<p style=\"color: red;\">").append(u.totalScore()).append("</p>").append("</td>").
                        append("</tr>");
            });
        }

        sb.append("</tbody>").append("</table>");

        return sb.toString();
    }


}