import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class SignInServlet extends HttpServlet {
    private AccountService accountService;
    private UsersDataSet dataSet;
    public SignInServlet(AccountService accountService) {
        this.accountService = accountService;
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String login = req.getParameter("login");
        String pass = req.getParameter("password");

        if (login == null || pass == null) {
            resp.setContentType("text/html;charset=utf-8");
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }
        try {
            dataSet = accountService.getUserByLogin(login);
        } catch (DBException e) {

        }
        if (dataSet == null || !dataSet.getPass().equals(pass) || !dataSet.getName().equals(login)) {
            resp.setContentType("text/html;charset=utf-8");
            resp.getWriter().println("Unauthorized");
            resp.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }
        else {
            accountService.addSession(req.getSession().getId(), dataSet);
            resp.setContentType("text/html;charset=utf-8");
            resp.getWriter().println("Authorized");
            resp.setStatus(HttpServletResponse.SC_OK);
        }
    }
}
