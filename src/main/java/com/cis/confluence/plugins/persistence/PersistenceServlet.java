package com.cis.confluence.plugins.persistence;

import com.cis.confluence.plugins.dto.EventUser;
import com.cis.confluence.plugins.utils.ConfluencerManager;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class PersistenceServlet extends HttpServlet {


    private final ConfluencerPersistence confluencerPersistence;

    public PersistenceServlet(ConfluencerPersistence confluencerPersistence) {
        this.confluencerPersistence = confluencerPersistence;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        final PrintWriter w = resp.getWriter();

        for (EventUser user: confluencerPersistence.getAll()) {
            w.printf("<li><%2$s> %s </%2$s></li>", user.toString());
            System.out.println("----------------eventuserService :::: " + user.toString());
            ConfluencerManager.addUser(user.getEmail(),user.getName(), user.getFullName(), user.getKey().getStringValue(), user.getIcon());
        }

        w.write("</ol>");
        w.write("<script language='javascript'>document.forms[0].elements[0].focus();</script>");
        System.out.println("-------------------- DO GET :::::");
        w.close();
//
//
//
//
//
//        ConfluencerManager.setList(userService.getAll());
//        System.out.println("-------------------- get ::: ");
//        resp.getWriter().write("Todo servlet, doGet");
//        resp.getWriter().close();
//        //super.doGet(req, resp);
//
//        ao.executeInTransaction(new TransactionCallback<Void>() // (1)
//        {
//            @Override
//            public Void doInTransaction()
//            {
//                for (Todo todo : ao.find(Todo.class)) // (2)
//                {
//                    w.printf("<li><%2$s> %s </%2$s></li>", todo.getDescription(), todo.isComplete() ? "strike" : "strong");
//                }
//                return null;
//            }
//        });

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        if (req.getParameterNames().hasMoreElements()) {
//            System.out.println(" ------------------- post :::: " + req.getParameterNames().nextElement().toString());
//        }
//
//                userService.saveAll(ConfluencerManager.getList());
//        resp.getWriter().write("Todo servlet, doPost");
//        resp.getWriter().close();
        System.out.println("-------------------- DO POST :::::");
        ConfluencerManager.getList().forEach(user ->{
            confluencerPersistence.saveAll(ConfluencerManager.getList());
        });

        resp.sendRedirect(req.getContextPath() + "/plugins/servlet/confluencer/users");

    }


    //        @Override
//        protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
//            try {
//                final PrintWriter w = resp.getWriter();
//                w.write("<h1>Client-Atlas Mapping</h1>");
//                w.write("<form method=\"post\">");
//                w.write("ClientUserID:<br>");
//                w.write("<input type=\"text\" name=\"ClientUserId\" size=\"25\"/><br>");
//                w.write("AtlasSecret:<br>");
//                w.write("<input type=\"text\" name=\"AtlasSecret\" size=\"25\"/><br>");
//                w.write("AtlasRequestToken:<br>");
//                w.write("<input type=\"text\" name=\"AtlasRequestToken\" size=\"25\"/><br>");
//                w.write("AtlasAccessToken:<br>");
//                w.write("<input type=\"text\" name=\"AtlasAccessToken\" size=\"25\"/><br>");
//                w.write("  ");
//                w.write("<input type=\"submit\" name=\"submit\" value=\"Add\"/>");
//                w.write("</form>");
//
//                w.write("<table>");
//                w.write("<tr>");
//                w.write("<th>ClientUserId</th>");
//                w.write("<th>AtlasSecret</th>");
//                w.write("<th>AtlasRequestToken</th>");
//                w.write("<th>AtlasAccessToken</th>");
//                w.write("</tr>");
//                for (ClientAtlasUser user : userService.all()) {
//                    w.write("<tr>");
//                    w.printf("<td> %s </td>", user.getClientUserId());
//                    w.printf("<td> %s </td>", user.getAtlasSecret());
//                    w.printf("<td> %s </td>", user.getAtlasRequestToken());
//                    w.printf("<td> %s </td>", user.getAtlasAccessToken());
//                    w.write("</tr>");
//                }
//
//                w.write("</table>");
//                w.write("<script language='javascript'>document.forms[0].elements[0].focus();</script>");
//
//                w.close();
//            } catch (IOException e) {
//                LOG.error(e.getMessage());
//            }
//        }
//
//        @Override
//        protected void doPost(HttpServletRequest req, HttpServletResponse resp) {
//            try {
//                Map<String, String> properties = new HashMap<>();
//                properties.put(CLIENT_ID, req.getParameter("ClientUserId"));
//                properties.put(SECRET, req.getParameter("AtlasSecret"));
//                properties.put(REQUEST_TOKEN, req.getParameter("AtlasRequestToken"));
//                properties.put(ACCESS_TOKEN, req.getParameter("AtlasAccessToken"));
//                userService.add(properties);
//
//                resp.sendRedirect(req.getContextPath() + "/plugins/servlet/user/mapping");
//            } catch (IOException e) {
//                LOG.error(e.getMessage());
//            }
//        }

}
