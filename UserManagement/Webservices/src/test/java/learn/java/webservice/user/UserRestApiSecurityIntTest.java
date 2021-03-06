package learn.java.webservice.user;

import org.easymock.EasyMock;
import org.easymock.IMocksControl;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.context.support.WithSecurityContextTestExecutionListener;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import org.springframework.test.context.web.ServletTestExecutionListener;
import org.springframework.test.context.web.WebAppConfiguration;

import learn.java.dao.user.UserEntityDao;
import learn.java.dto.user.LearnUser;
import learn.java.webservice.WebservicesApplication;
import learn.java.webservice.user.UserRestApi;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes={ WebservicesApplication.class})
@TestExecutionListeners(listeners={ServletTestExecutionListener.class,
		DependencyInjectionTestExecutionListener.class,
		DirtiesContextTestExecutionListener.class,
		TransactionalTestExecutionListener.class,
		WithSecurityContextTestExecutionListener.class
})
public class UserRestApiSecurityIntTest {

	@Autowired
	private UserRestApiImpl userRestApi;
	private LearnUser user;
	
	private final IMocksControl control = EasyMock.createControl();
	private final UserService userService = control.createMock(UserService.class);

	
	@Before
	public void before() {
		user = new LearnUser();
		userRestApi.setUserService(userService);
	}
	
	@After
	public void after() {
		SecurityContextHolder.clearContext();
	}
	
	@Ignore
	@Test(expected = AuthenticationCredentialsNotFoundException.class)
	public void addUserUnauthenticated() {
		userRestApi.createUser(user);
	}

	@Test
	@WithMockUser(username="AdminUser", roles={"ADMIN","USER"})
	public void addUserAuthenticated() {
		userRestApi.createUser(user);
	}

	@Test
	@WithMockUser(username="AdminUser", roles={"ADMIN","USER"})
	public void getAllUsers(){
		userRestApi.getUsers();
	}
	
	
	
}
