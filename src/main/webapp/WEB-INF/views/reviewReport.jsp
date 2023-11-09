<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page contentType="text/html;charset=UTF-8" language="java"%>

<%@ include file="/WEB-INF/views/include/headerBase.jsp"%>
<%@ include file="/WEB-INF/views/include/headerLink.jsp"%>
<link rel="stylesheet" href="/resources/css/base.css">
<link rel="stylesheet" href="/resources/css/leaderDetail.css">
<link rel="stylesheet" href="/resources/css/member.css">
<%@ include file="/WEB-INF/views/include/headerTop.jsp"%>
<!-- 컨텐츠 시작 -->
<div class="mv-100 container">
	<h2 class="pTitle">리뷰 리포트</h2>
	<%--    <ul class="menu">--%>
	<%--        <li>--%>
	<%--            <button class="btn black" type="button" onclick="location.href='/leader/task/view.do'">일정 및 업무 수정</button>--%>
	<%--        </li>--%>
	<%--        <li>--%>
	<%--            <button class="btn black" type="button" onclick="location.href='/leader/member/view.do'">멤버 추가 및 삭제</button>--%>
	<%--        </li>--%>
	<%--        <li>--%>
	<%--            <button class="btn black" type="button" onclick="completeProject('${project.projectId}','${project.projectName}')">프로젝트 완료</button>--%>
	<%--        </li>--%>
	<%--        <li>--%>
	<%--            <button class="btn black" type="button" onclick="location.href='/leader/project/delete/view.do?projectId=${project.projectId}'">프로젝트 삭제</button>--%>
	<%--        </li>--%>
	<%--    </ul>--%>
	<div class="leaderWrapper">
		<div class="contentsWrapper">

			<%--            <table class="btnTable none">--%>
			<%--                <tr>--%>
			<%--                    <td>--%>
			<%--                        <div class="leaderBtn"  onclick="location.href='/leader/task/view.do'">--%>
			<%--                            <p class="label">일정 및 업무 수정</p>--%>
			<%--                        </div>--%>
			<%--                    </td>--%>
			<%--                </tr>--%>
			<%--                <tr>--%>
			<%--                    <td>--%>
			<%--                        <div class="leaderBtn"  onclick="location.href='manageMember.do'">--%>
			<%--                            <p class="label">멤버 추가 및 삭제</p>--%>
			<%--                        </div>--%>
			<%--                    </td>--%>
			<%--                </tr>--%>
			<%--                <tr>--%>
			<%--                    <td>--%>
			<%--                        <div class="leaderBtn" onclick="completeProject('${project.projectId}','${project.projectName}')">--%>
			<%--                            <p class="label">프로젝트 완료</p>--%>
			<%--                        </div>--%>
			<%--                    </td>--%>
			<%--                </tr>--%>
			<%--                <tr>--%>
			<%--                    <td>--%>
			<%--                        <div class="leaderBtn" onclick="location.href='/leader/project/delete/view.do?projectId=${project.projectId}'">--%>
			<%--                            <p class="label">프로젝트 삭제</p>--%>
			<%--                        </div>--%>
			<%--                    </td>--%>
			<%--                </tr>--%>
			<%--            </table>--%>

			<div class="contentsBox">
				<div class="projectProgress">
					<span class="progressLabel">프로젝트</span>
					<div class="">${project.projectName}</div>
				</div>
				<hr>
				<div class="projectProgress">
					<span class="progressLabel">기간</span>
					<%--                    <div class="">${projectData.startDate} ~ ${projectData.endDate}</div>--%>
					<div>${project.startDate}~${project.endDate}</div>
				</div>
				<hr>
				<div class="projectProgress">
					<div class="member-wrapper">
						<p class="progressLabel">멤버리뷰</p>
						<c:forEach items="${myReview}" var="review">
							<div class="member-item">
								<div class="item">
									${review.writeMemberId} : ${review.evaluation } <br>
								</div>
							</div>
						</c:forEach>
					</div>
				</div>
				<div class="projectProgress">
					<span class="progressLabel">업무 진행 내역</span> <img
						src="/resources/image/wbsImage.png">
				</div>
				<div class="projectProgress">
					<span class="progressLabel">업무 기여도</span><br> <img
						src="/resources/image/업무기여도.png">
					<div>이세진님은 '개화' 프로젝트에서 33.3% 업무 기여도를 보입니다.</div>
					<div>그 중 '늦춰진 업무 3건(60%)', '이슈 등록된 업무 4건(80%)', '리더가 일정을 조정한
						업무 2건(40%)' 입니다.</div>
					<div>일정이 늦춰지는 것을 알리지 않은 업무 1건(스와이퍼 구조 해결)이 있습니다.</div>

				</div>

			</div>
		</div>
	</div>
</div>
</body>
<%--<script = "text/javascript">--%>
<script>
	function completeProject(projectId, projectName) {
		if (confirm("[" + projectName + "]프로젝트를 완료하시겠습니까?")) {
			$
					.ajax({
						type : 'GET',
						url : '/leader/project/complete.do',
						data : {
							projectId : projectId
						},
						success : function(response) {
							if (response === "success") {
								alert("완료되었습니다");
								window.location.href = "/project/list/view.do";
							} else {
								alert("오류가 발생했습니다. 다시 시도해주세요");
								window.location.href = "/leader/detail.do?projectId=${projectId}"
							}

						},
						error : function(error) {
							console.log("에러: " + error);
							alert("오류가 발생했습니다. 다시 시도해주세요");
							window.location.href = "/leader/detail.do?projectId=${projectId}"
						}
					});
		}
	}
</script>

<!-- 컨텐츠 종료 -->
<%@ include file="/WEB-INF/views/include/footer.jsp"%>
