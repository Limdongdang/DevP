package com.devP.Mapper.Impl;

import com.devP.Controller.MailController;
import com.devP.Mapper.Repository.IssueDAOMybatis;
import com.devP.Service.IssueService;
import com.devP.Service.MailService;
import com.devP.VO.IssueVO;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("issueService")
public class IssueServiceImpl implements IssueService {
	@Autowired
	private IssueDAOMybatis issueDAO;
	@Autowired
	private HttpSession session;
	
	
    private String from = "daggggg2@naver.com";

	@Autowired
    private MailService mailService;
	
	@Override
	public int insertIssue(IssueVO issue){
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
		try {
			mailService.sendMail(from, emailList, issue.getUserId() + "(��)�� �̽� �˸��� ���½��ϴ�", issue.getContent());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return issueDAO.insertIssue(issue);
	}
	
	@Override
	public List<IssueVO> getIssuelist(int projectId) {
		return issueDAO.getIssuelist(projectId);
	}
}