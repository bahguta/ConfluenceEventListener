//package com.cis.confluence.plugins.utils;
//
//import com.atlassian.confluence.api.model.people.User;
//import com.atlassian.confluence.content.render.xhtml.ConversionContext;
//import com.atlassian.confluence.macro.DefaultImagePlaceholder;
//import com.atlassian.confluence.macro.EditorImagePlaceholder;
//import com.atlassian.confluence.macro.ImagePlaceholder;
//import com.atlassian.confluence.macro.Macro;
//import com.atlassian.confluence.macro.MacroExecutionException;
//import com.atlassian.confluence.pages.thumbnail.Dimensions;
//import com.atlassian.confluence.setup.settings.SettingsManager;
//import com.atlassian.confluence.user.UserAccessor;
//import com.atlassian.confluence.user.actions.ProfilePictureInfo;
//import org.springframework.web.util.HtmlUtils;
//
//import java.util.Map;
//
//public class ProfilePictureMacro implements Macro, EditorImagePlaceholder
//{
//    private static final String PARAM_USER = "User";
//
//    private UserAccessor userAccessor;
//    private SettingsManager settingsManager;
//
//    @Override
//    public ImagePlaceholder getImagePlaceholder(Map<String, String> params, ConversionContext ctx)
//    {
//        String url = null;
//        if (params.containsKey(PARAM_USER))
//        {
//            ProfilePictureInfo picture = userAccessor.getUserProfilePicture(params.get(PARAM_USER));
//            url = (picture == null) ? null : picture.getDownloadPath();
//        }
//        return new DefaultImagePlaceholder(url, new Dimensions(48, 48), false);
//    }
//
//    @Override
//    public String execute(Map<String, String> params, String body, ConversionContext ctx) throws MacroExecutionException
//    {
//        String url;
//        if (params.containsKey(PARAM_USER))
//        {
//            ProfilePictureInfo picture = userAccessor.getUserProfilePicture((com.atlassian.user.User) User.fromUsername("admin"));
//            url = (picture == null) ? null : settingsManager.getGlobalSettings().getBaseUrl() + picture.getDownloadPath();
//        }
//        else
//        {
//            throw new MacroExecutionException("No user parameter specified");
//        }
//        String username = HtmlUtils.htmlEscape(params.get(PARAM_USER));
//        return String.format("<a class=\"userLogoLink\" data-username=\"%s\" href=\"%s\" title=\"\">" +
//                "<img class=\"userLogo logo\" src=\"%s\" alt=\"User icon: %s\" title=\"\">" +
//                "</a>", username, settingsManager.getGlobalSettings().getBaseUrl() + "/display/~" + username, url, username);
//
//    }
//
//    @Override
//    public BodyType getBodyType()
//    {
//        return BodyType.NONE;
//    }
//
//    @Override
//    public OutputType getOutputType()
//    {
//        return OutputType.INLINE;
//    }
//
//    public void setUserAccessor(UserAccessor userAccessor)
//    {
//        this.userAccessor = userAccessor;
//    }
//
//    public void setSettingsManager(SettingsManager settingsManager)
//    {
//        this.settingsManager = settingsManager;
//    }
//
//}
