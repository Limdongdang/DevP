package com.devP.Controller;

import java.util.ArrayList;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
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
	
	@Autowired
    private JavaMailSender mailSender;

    private String from = "daggggg2@naver.com";	
	
	//�̽� ��� ������
	@RequestMapping(value="/issue.do", method= RequestMethod.GET)
    public String issueView(HttpSession session, Model model){
		model.addAttribute("username", session.getAttribute("name"));
        return "issue";
    }
	
	//�̽� ���
	@RequestMapping(value="/issue.do", method= RequestMethod.POST)
    public String issueInsert(@ModelAttribute IssueVO issue, HttpSession session){
		String emails = issue.getSendingEmail();
		//���� ���̵� ���� ���
		issue.setUserId(session.getAttribute("id").toString());
		//�̽� ���� �ʱ� ����
		issue.setStatus("������");
		
		// �����ڸ� ��ǥ(,)�� �����Ͽ� ���ڿ��� ������, �̸��� �ּҵ��� ArrayList�� ����
        ArrayList<String> emailList = new ArrayList<>();
        String[] emailArray = emails.split(",");
        for (String email : emailArray) {
            emailList.add(email);
        }
		//�̸��� �˸� ����
		try {
			mailController.sendMail(from, emailList, issue.getUserId() + "(��)�� �̽� �˸��� ���½��ϴ�", issue.getContent());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//---------------------
		issueService.insertIssue(issue);
        return "main";
    }
	//�̽� ���
	@RequestMapping(value="/list.do", method= RequestMethod.GET)
    public String getIssuelist(@RequestParam int projectId, Model model){
		model.addAttribute("issueList", issueService.getIssuelist(projectId));
		String[] statusArray = {"������", "������", "�ذ�"};
		model.addAttribute("statusarr", statusArray);
        return "issueList";
    }
	//�̽� ����
	@RequestMapping(value="/delete.do", method= RequestMethod.POST)
    public String deleteIssue(@RequestBody IssueVO issue){
		System.out.println(issue.getIssueId());
		try {
			issueService.deleteIssue(issue.getIssueId());
		} catch (Exception e) {
			// TODO: handle exception
		}
        return "redirect:/list.do?projectId= " + issue.getProjectId();
	}
	//�̽� �� - �߰� �۾� ��� �߰� �ؾ� ��
	@RequestMapping(value="/detail.do", method= RequestMethod.GET)
    public String getIssuedetail(@RequestParam int issueId, Model model){
		try {
			//�̽� ��ȸ�� �ø��� -�߰� �۾� ���� ���� ���̵� �ߺ� ��ȸ ����
			issueService.countupIssue(issueId);
			model.addAttribute("issue", issueService.getIssue(issueId));
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e);
		}
		System.out.println(model);
        return "issueDetail";
	}
	//�̽� �ذ�
	@RequestMapping(value="/solve.do", method= RequestMethod.POST)
    public String solveIssue(@RequestBody IssueVO issue){
		issue.setStatus("�ذ�");
		try {
			issueService.changeIssueStatus(issue);
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e);
		}
        return "redirect:/list.do?projectId= " + issue.getProjectId();
	}
}
