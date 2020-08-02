package com.txstate.edu.homeServices.controller;

import com.txstate.edu.homeServices.model.CustomerRegistration;
import com.txstate.edu.homeServices.object.ServicePayment;
import com.txstate.edu.homeServices.repository.ServicePaymentRepository;
import com.txstate.edu.homeServices.service.EmailService;
import com.txstate.edu.homeServices.service.OrderService;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mail.SimpleMailMessage;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


class ServiceControllerTest {

    @Mock
    private OrderService orderService;

    @Mock
    private EmailService emailService;

    @Mock
    private ServicePayment servicePayment;

    @Mock
    private HttpServletRequest request;

    @InjectMocks
    private ServiceController controller;


    @BeforeEach
    public void setup(){

        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void savePaymentInfoTest() {
        ServicePayment orderConfirm = Mockito.mock(ServicePayment.class);
        HttpSession httpSession = Mockito.mock(HttpSession.class);
        CustomerRegistration customerRegistration = Mockito.mock(CustomerRegistration.class);

        when(orderService.savePaymentInfo(servicePayment)).thenReturn(orderConfirm);
        when(request.getSession()).thenReturn(httpSession);
        when(httpSession.getAttribute("USER_DETAILS_EXPANDED")).thenReturn(customerRegistration);

        assertEquals(controller.savePaymentInfo(servicePayment,request), orderConfirm);

        // Verify if the methods/mocks were called with correct arguments and how many number of times
        verify(orderService, times(1)).savePaymentInfo(servicePayment);
        verify(emailService, times(1)).sendEmail(any(SimpleMailMessage.class));

    }
}