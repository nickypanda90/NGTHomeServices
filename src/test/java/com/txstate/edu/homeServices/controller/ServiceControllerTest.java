package com.txstate.edu.homeServices.controller;

import com.txstate.edu.homeServices.model.CustomerRegistration;
import com.txstate.edu.homeServices.object.ServiceOrder;
import com.txstate.edu.homeServices.object.ServicePayment;
import com.txstate.edu.homeServices.service.EmailService;
import com.txstate.edu.homeServices.service.OrderService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.springframework.mail.SimpleMailMessage;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import java.util.ArrayList;
import java.util.List;

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

    @Mock
    private ServiceOrder serviceOrder;

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

        Mockito.doAnswer(new Answer<Object>()
        {
            @Override
            public Object answer(InvocationOnMock invocation) throws Throwable
            {
                SimpleMailMessage registrationmsg = (SimpleMailMessage) invocation.getArguments()[0];
                assertEquals(registrationmsg.getFrom(), "support@demo.com");
                return null;
            }
        }).when(emailService).sendEmail(Mockito.any(SimpleMailMessage.class));


        assertEquals(controller.savePaymentInfo(servicePayment,request), orderConfirm);

        // Verify if the methods/mocks were called with correct arguments and how many number of times
        verify(orderService, times(1)).savePaymentInfo(servicePayment);
        verify(emailService, times(1)).sendEmail(any(SimpleMailMessage.class));

    }

    @Test
    public void updateServiceStatus(){
        ServiceOrder serviceOrder = Mockito.mock(ServiceOrder.class);
        when(orderService.updateServiceStatus(serviceOrder)).thenReturn(serviceOrder);

        assertEquals(controller.updateServiceStatus(serviceOrder),serviceOrder);

        verify(orderService, times(1)).updateServiceStatus(serviceOrder);
    }

    @Test
    public void updateServiceStatusorder(){
        ServiceOrder serviceOrder = Mockito.mock(ServiceOrder.class);
        when(orderService.updateServiceStatus(serviceOrder)).thenReturn(serviceOrder);

        assertEquals(controller.updateServiceStatus(serviceOrder),serviceOrder);

        verify(orderService, times(1)).updateServiceStatus(serviceOrder);
    }

    @Test
    public void createService(){
        HttpSession httpSession = Mockito.mock(HttpSession.class);
        CustomerRegistration customerRegistration = Mockito.mock(CustomerRegistration.class);

        when(request.getSession()).thenReturn(httpSession);
        when(httpSession.getAttribute("USER_DETAILS_EXPANDED")).thenReturn(customerRegistration);
        when(serviceOrder.getCustomerId()).thenReturn(1);
        Mockito.doAnswer(new Answer<Object>()
        {
            @Override
            public Object answer(InvocationOnMock invocation) throws Throwable
            {
                Integer customerId = (Integer) invocation.getArguments()[0];
                assertEquals(customerId, 1);
                return null;
            }
        }).when(serviceOrder).setBusinessId(1);
        controller.createService(serviceOrder, request);

        Mockito.doAnswer(new Answer<Object>()
        {
            @Override
            public Object answer(InvocationOnMock invocation) throws Throwable
            {
                Integer customerId = (Integer) invocation.getArguments()[0];
                assertEquals(customerId, 1);
                return null;
            }
        }).when(serviceOrder).setCustomerId(1);
        controller.createService(serviceOrder, request);

    }

    @Test
    public void isFirstTimeUser() throws Exception {
        int customerId = 1;
        when(orderService.isFirstTimeUser(customerId)).thenReturn(false);
        assertEquals(orderService.isFirstTimeUser(customerId), false);
    }

    @Test
    public void getServices() throws Exception {
        int customerId=1;
        List<ServiceOrder> serviceOrderList = new ArrayList<>();
        ServiceOrder serviceOrder = Mockito.mock(ServiceOrder.class);
        serviceOrderList.add(serviceOrder);

        when(orderService.fetchOrders(customerId)).thenReturn(serviceOrderList);
    }
}
