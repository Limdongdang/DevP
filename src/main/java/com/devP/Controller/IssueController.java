package com.devP.Controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.devP.Service.IssueService;
import com.devP.Service.UserService;
import com.devP.VO.IssueVO;

@Controller
public class IssueController {
	private String url = "/issue";
	@Autowired
	private IssueService issueService;
	@Autowired
    private MailController mailController;
	
	//�̽� ��� ������
	@RequestMapping(value="/issue.do", method= RequestMethod.GET)
    public String issueView(){
        return "issue";
    }
	
	//�̽� ���
	@RequestMapping(value="/issue.do", method= RequestMethod.POST)
    public String issueInsert(@ModelAttribute IssueVO issue, HttpSession session){
		//���� ���̵� ���� ���
		issue.setUserId(session.getAttribute("id").toString());
		//�̽� ���� �ʱ� ����
		issue.setStatus("������");
		//�̸��� �˸� ���� �����ؾ� ��
		
		//---------------------
		issueService.insertIssue(issue);
        return "main";
    }
	//�̽� ���
	@RequestMapping(value="/list.do", method= RequestMethod.GET)
    public String getIssuelist(@RequestParam int projectId, Model model){
		model.addAttribute("issueList", issueService.getIssuelist(projectId));
		String[] statusArray = {"������", "������", "�Ϸ�"};
		model.addAttribute("statusarr", statusArray);
        return "issueList";
    }
}
