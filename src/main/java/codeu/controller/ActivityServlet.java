package codeu.controller;

import codeu.model.data.Activity;
import codeu.model.store.basic.ActivityStore;
import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/** Servlet responsible for the activity feed page. */
public class ActivityServlet extends HttpServlet {

  /** Store class that gives access to Activities. */
  private ActivityStore activityStore;

  /** Set up state for handling activityfeed requests. */
  @Override
  public void init() throws ServletException {
    super.init();
    setActivityStore(ActivityStore.getInstance());
  }

  /**
   * Sets the ActivityStore used by this servlet. This function provides a common setup method for
   * use by the test framework or the servlet's init() function.
   */
  void setActivityStore(ActivityStore activityStore) {
    this.activityStore = activityStore;
  }

  /**
   * This function fires when a user navigates to the activityFeed page. It fetches all the data
   * from the database and displays it in order of the newest to oldest based on timestamp.
   */
  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response)
      throws IOException, ServletException {
    List<Activity> activities = activityStore.getAllActivities();

    request.setAttribute("activities", activities);

    request.getRequestDispatcher("/WEB-INF/view/activity.jsp").forward(request, response);
  }
}
