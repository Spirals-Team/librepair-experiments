package pl.sda.jira.project.model;

import fr.inria.spirals.npefix.resi.CallChecker;
import fr.inria.spirals.npefix.resi.context.MethodContext;
import fr.inria.spirals.npefix.resi.exception.AbnormalExecutionError;
import fr.inria.spirals.npefix.resi.exception.ForceReturn;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FakeProjectRepository implements ProjectRepository {
    private Map<Long, Project> projects = new HashMap<>();

    @Override
    public void addProject(Project project) {
        MethodContext _bcornu_methode_context1 = new MethodContext(void.class, 11, 189, 297);
        try {
            CallChecker.varInit(this, "this", 11, 189, 297);
            CallChecker.varInit(project, "project", 11, 189, 297);
            CallChecker.varInit(this.projects, "projects", 11, 189, 297);
            if (CallChecker.beforeDeref(project, Project.class, 12, 266, 272)) {
                if (CallChecker.beforeDeref(projects, Map.class, 12, 253, 260)) {
                    project = CallChecker.beforeCalled(project, Project.class, 12, 266, 272);
                    projects = CallChecker.beforeCalled(projects, Map.class, 12, 253, 260);
                    CallChecker.isCalled(projects, Map.class, 12, 253, 260).put(CallChecker.isCalled(project, Project.class, 12, 266, 272).getId(), project);
                }
            }
        } catch (ForceReturn _bcornu_return_t) {
            _bcornu_return_t.getDecision().getValue();
            return ;
        } finally {
            _bcornu_methode_context1.methodEnd();
        }
    }

    @Override
    public void removedProject(Long id) {
        MethodContext _bcornu_methode_context2 = new MethodContext(void.class, 16, 304, 389);
        try {
            CallChecker.varInit(this, "this", 16, 304, 389);
            CallChecker.varInit(id, "id", 16, 304, 389);
            CallChecker.varInit(this.projects, "projects", 16, 304, 389);
            if (CallChecker.beforeDeref(projects, Map.class, 17, 364, 371)) {
                projects = CallChecker.beforeCalled(projects, Map.class, 17, 364, 371);
                CallChecker.isCalled(projects, Map.class, 17, 364, 371).remove(id);
            }
        } catch (ForceReturn _bcornu_return_t) {
            _bcornu_return_t.getDecision().getValue();
            return ;
        } finally {
            _bcornu_methode_context2.methodEnd();
        }
    }

    @Override
    public List<Project> getAllProjects() {
        MethodContext _bcornu_methode_context3 = new MethodContext(List.class, 21, 396, 512);
        try {
            CallChecker.varInit(this, "this", 21, 396, 512);
            CallChecker.varInit(this.projects, "projects", 21, 396, 512);
            if (CallChecker.beforeDeref(projects, Map.class, 22, 488, 495)) {
                projects = CallChecker.beforeCalled(projects, Map.class, 22, 488, 495);
                return new ArrayList<Project>(CallChecker.isCalled(projects, Map.class, 22, 488, 495).values());
            }else
                throw new AbnormalExecutionError();
            
        } catch (ForceReturn _bcornu_return_t) {
            return ((List<Project>) (_bcornu_return_t.getDecision().getValue()));
        } finally {
            _bcornu_methode_context3.methodEnd();
        }
    }

    @Override
    public boolean checkIfTeamHasProject(Long teamid) {
        MethodContext _bcornu_methode_context4 = new MethodContext(boolean.class, 26, 519, 768);
        try {
            CallChecker.varInit(this, "this", 26, 519, 768);
            CallChecker.varInit(teamid, "teamid", 26, 519, 768);
            CallChecker.varInit(this.projects, "projects", 26, 519, 768);
            projects = CallChecker.beforeCalled(projects, Map.class, 27, 616, 623);
            for (Project project : CallChecker.isCalled(projects, Map.class, 27, 616, 623).values()) {
                if (CallChecker.beforeDeref(project, Project.class, 28, 651, 657)) {
                    if (CallChecker.beforeDeref(CallChecker.isCalled(project, Project.class, 28, 651, 657).getTeam(), Team.class, 28, 651, 667)) {
                        if ((CallChecker.isCalled(CallChecker.isCalled(project, Project.class, 28, 651, 657).getTeam(), Team.class, 28, 651, 667).getId()) == teamid) {
                            return true;
                        }
                    }else
                        throw new AbnormalExecutionError();
                    
                }else
                    throw new AbnormalExecutionError();
                
            }
            return false;
        } catch (ForceReturn _bcornu_return_t) {
            return ((Boolean) (_bcornu_return_t.getDecision().getValue()));
        } finally {
            _bcornu_methode_context4.methodEnd();
        }
    }
}

