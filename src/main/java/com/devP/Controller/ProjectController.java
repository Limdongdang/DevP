package com.devP.Controller;

import com.devP.Service.IssueService;
import com.devP.Service.LeaderService;
import com.devP.Service.ProjectService;
import com.devP.Service.TaskService;
import com.devP.VO.MemberVO;
import com.devP.VO.ProjectGroupVO;
import com.devP.VO.ProjectVO;
import com.devP.VO.TaskVO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Controller
@SessionAttributes("project")
@RequestMapping("project")
public class ProjectController {


    @Autowired
    private IssueService issueService;

    @Autowired
    private ProjectService projectService;

    @Autowired
    private TaskService taskService;

    @Autowired
    private LeaderService leaderService;

    @Autowired
    private MailController mailController;

    @Autowired
    private HttpSession session;

    @ModelAttribute("colorMap")
    public Map<String, String> setColorMap(){
        Map<String,String> colorMap = new HashMap<>();

        colorMap.put("1","F5A9A9");
        colorMap.put("2","F5D0A9");
        colorMap.put("3","F2F5A9");
        colorMap.put("4","BCF5A9");
        colorMap.put("5","BCF5A9");
        colorMap.put("6","A9D0F5");
        colorMap.put("7","A9BCF5");
        colorMap.put("8","A9A9F5");
        colorMap.put("9","D0A9F5");
        colorMap.put("10","F6CEEC");

        return colorMap;
    }




    //프로젝트 추가 화면
    @RequestMapping(value = "/add/view.do", method = RequestMethod.GET)
    public String addProjectView() {
        if(projectService.insertProjectView() == 200) {
            return "insertProject";
        }else if(projectService.insertProjectView() == 405){
            return "redirect:/user/login/view.do";
        }else{
            return "redirect:/";
        }
    }
    @RequestMapping(value = "/color/set.do", method = RequestMethod.GET)
    public String setColorProject(@RequestParam String projectColor, MemberVO vo){
        System.out.println(projectColor);
        vo.setColor("#"+projectColor);
        vo.setUserId(session.getAttribute("id").toString());
        vo.setProjectId(Integer.parseInt(session.getAttribute("projectId").toString()));
        int result = projectService.setProjectColor(vo);

        int projectId = Integer.parseInt(session.getAttribute("projectId").toString());
        System.out.println(projectId);
        System.out.println(vo.getColor());

        return "redirect:/project/detail.do?projectId="+projectId;
//        return "main";

    }

    //프로젝트 상세
    @RequestMapping(value="/detail.do", method= RequestMethod.GET)
    public String detailProject(@RequestParam int projectId, ProjectVO vo, MemberVO member, Model model){
        model.addAttribute("menuId", "projectMenu");
        if(session.getAttribute("projectId")!=null) session.removeAttribute("projectId");
        session.setAttribute("projectId", projectId);

        vo.setProjectId(projectId); member.setProjectId(projectId);
        member.setUserId(session.getAttribute("id").toString());

        int result = projectService.getProjectDetail(vo, member,model);
//        vo.setProjectId(projectId);
//        member.setProjectId(projectId);
//        member.setUserId(session.getAttribute("id").toString());

        if(result == 200)  return "projectDetail";
        else return "redirect:/";
    }

    @RequestMapping(value = "/add.do", method = RequestMethod.POST)
    public String addProject(@ModelAttribute ProjectVO vo, MemberVO vo2, ProjectGroupVO vo3) throws Exception {
        int result = projectService.insertProject(vo, vo2, vo3);
        if(result == 200) {
            int projectId = projectService.getProjectId(vo);
            session.setAttribute("projectId",projectId);
            return "redirect: /project/task/add/view.do";
        }
        else if(result == 405) return "redirect: /project/insertProject.do";
        return null;
    }
    @RequestMapping(value = "/task/add/view.do", method = RequestMethod.GET)
    public String addTaskView(TaskVO vo, Model model){
        vo.setProjectId(Integer.parseInt(session.getAttribute("projectId").toString()));
        List<String> nameList = projectService.getMemberNames(vo.getProjectId());

        model.addAttribute("categoryMap", taskService.setCategoryMap());
        model.addAttribute("statusMap", taskService.setStatusMap());
        int result = leaderService.getTaskDatas(vo, model);

        if(result == 200) return "insertTask";
        else {
            session.removeAttribute("projectId");
            return "redirect:/list.do";
        }
    }

    @RequestMapping(value = "/task/add.do", method = RequestMethod.POST)
    public ResponseEntity<String> addTask(TaskVO vo, Model model) throws Exception {
        try {
            vo.setProjectId(Integer.parseInt(session.getAttribute("projectId").toString()));
            System.out.println(vo.getProjectId());
            int result = leaderService.addTask(vo);

            return ResponseEntity.ok("Task insert successfully");

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred");

        }
    }


    //프로젝트 목록
    @RequestMapping(value = "/list/view.do", method = RequestMethod.GET)
    public String listProjectView(Model model) {
        if(session.getAttribute("projectId")!= null){session.removeAttribute("projectId");}
        if(projectService.getProjectList(model) == 200){
            return "projectList";
        } else if (projectService.getProjectList(model) == 405) {
            return "login";
        }
        return null;
    }

    @RequestMapping(value = "/list/complete/view.do", method = RequestMethod.GET)
    public String completeListProjectView(Model model) {
        int result = projectService.getCompleteProjectList(model);
        if(result == 200){
            return "completeProjectList";
        } else if (result == 405) {
            return "mainTemp";
        }
        return null;
    }

    @RequestMapping(value="/myTask.do", method = RequestMethod.GET)
    public String myTaskView(ProjectVO project, MemberVO member, TaskVO task, Model model) throws Exception {
        model.addAttribute("menuId","taskMenu");
        int result = projectService.showTaskView(project, member, task, model);
        if(result == 200) return "task";
        else return "man";
    }


    @RequestMapping(value="/member/list.do", method = RequestMethod.GET)
    public String manageMemberView(MemberVO vo, HttpSession session, Model model) {
        model.addAttribute("menuId","memberMenu");
        int result = projectService.showProjectMemberList(vo, model);

        if (result == 200) return "member";
        else return "mainTemp";
    }
}
