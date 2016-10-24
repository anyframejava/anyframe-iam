Anyframe IAM, release 1.0.0 (2010.01)
---------------------------------------------------------
http://www.anyframejava.org

1. Anyframe IAM 소개

IAM은 J2EE 기반의 엔터프라이즈 어플리케이션을 위한 보안 솔루션(사용자 인증 및 권한관리)을 제공하는 Identify & Access Management Framework 이다. 

J2EE 기반의 엔터프라이즈 어플리케이션은 사용자 인증 및 권한관리에 대한 다양한 요구사항을 가지고 있으며,

각각의 엔터프라이즈 어플리케이션에 대하여 이러한 기능들을 매번 새로이 개발하는 것은 많은 추가 비용과 리소스를 부담하게 한다. 

IAM은 Spring Security 를 기반으로 하여 Anyframe 또는 Spring Framework 기반으로 작성된 엔터프라이즈 어플리케이션에 대하여 인증 및 권한관리 기능을 제공함으로써, 

개발 생산성을 향상시키고 사용자 인증 및 권한관리에 능동적이고 유연한 형태의 어플리케이션을 개발할 수 있도록 지원하는 프레임워크이다.

Anyframe IAM은 다음과 같은 특징을 지닌다.

1) Spring Security 기반 IAM Core: 엔터프라이즈 어플리케이션에 설치되어 사용자 인증 및 권한관리를 제어하는 IAM Core 는 Spring Security 기반으로 작성되어 있으며, DB 기반으로 구축된다. Anyframe 또는 Spring 기반의 어플리케이션에 적용시 기존 어플리케이션의 변경 영향을 최소할 수 있다.

2) 독립적인 형태의 IAM Admin Console: IAM Core 의 동작은 DB의 데이타를 기반으로 하여 구성되며, 이때 DB의 내용을 관리하기 위한 툴이 IAM Admin Console이다. 기존 엔터프라이즈 어플리케이션과 별도로 동작하므로 어플리케이션과의 독립적인 형태로 관리 기능을 구성할 수 있다.

3) Spring Security 의 강력한 기능을 계승: Spring Security 의 강력한 기능을 계승하고 확장하였으며, 유연하고 확장성 있는 형태의 사용자 인증 및 권한관리 기능을 제공한다.

4) Cross Browser 지원: IAM Admin 은 Cross Browser 지원하여 Internet Explorer 뿐만 아니라 Firefox, Chrome, Safari 등과 같은 브라우저에서도 동일하게 동작하도록 구현되어 있다.

5) 관심 영역의 분리: Servlet Filter 와 AOP를 통한 가로채기(Interception)을 사용하여 보안을 부여하며, Spring 의 Ioc와 Lifecycle서비스 기반으로 동작하여 일반적인 업무 코드 영역과 분리되어 동작한다.

Anyframe IAM 관련 문서는 포탈 사이트의 문서 메뉴를 통해 확인할 수 있으며 보다 자세한 내용에 대해서는 HTML 형태로 구성된 매뉴얼을 참조하도록 한다.

* Anyframe 포탈 사이트 : http://www.anyframejava.org
* Anyframe IAM  매뉴얼 : 

2. 배포 파일 구조(zip)

1) Anyframe-iam-x.x.x-bin.zip
   - lib				: anyframe.iam.core.x.x.x.jar 파일 포함
   						  anyframe.iam.service.x.x.x.jar 파일 포함
   						  anyframe.iam.web.x.x.x.war 파일 포함
   						  anyframe.iam.sample.x.x.x.war 파일 포함
   - db					: HSQL DB 구동 파일과 초기화 script 포함
   - licenses			: Anyframe IAM을 통해 배포되는 3rd party 라이브러리들에 대한 라이센스 본문과 정리된 목록 포함
   - 기타				: Anyframe IAM 소개 및 기본 사항(readme.txt), 버전 별 변경 사항(changelog.txt), Anyframe IAM 라이센스(license.txt)

2) Anyframe-iam-x.x.x-src.zip
   - src				: Anyframe IAM 기능을 제공하는 세부 프로젝트 별 소스 코드 포함(core, service, web)
   - docs				: Anyframe IAM API 매뉴얼 포함
   - licenses			: Anyframe IAM을 통해 배포되는 3rd party 라이브러리들에 대한 라이센스 본문과 정리된 목록 포함
   - 기타				: Anyframe IAM 소개 및 기본 사항(readme.txt), 버전 별 변경 사항(changelog.txt), Anyframe IAM 라이센스(license.txt)

* maven 프로젝트
: anyframe repository를 맨 먼저 바라보도록 설정해야 한다 - 사용자의 maven 설정파일(settings.xml) 이나 소스 프로젝트의 pom.xml 에 아래의 문장을 추가하도록 한다

	<repositories>
	    <!-- 첫번째로 -->
            <repository>
                <id>anyframe</id>
                <name>repository for Anyframe</name>                 
                <url>http://dev.anyframejava.org/artifactory/anyframe-repository</url>
                <snapshots>
                    <enabled>true</enabled>
                </snapshots>
             </repository>
	    ..
	</repositories>

* 그 외 추가적인 Anyframe IAM CORE 및 ADMIN 설치 절차는 Anyframe 포탈 사이트를 참조하도록 한다.

3. 라이센스 정책

Anyframe IAM 프로젝트는 라이센스 정책으로 Apache Licence (http://www.apache.org), Version 2.0을 채택하고 있다.
