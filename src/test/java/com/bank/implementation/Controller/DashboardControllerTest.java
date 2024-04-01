package com.bank.implementation.Controller;

import com.bank.implementation.Controller.DashboardController;
import com.bank.implementation.Model.DashboardData;
import com.bank.implementation.Service.DashboardService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class DashboardControllerTest {

    @Mock
    private DashboardService dashboardService;

    @InjectMocks
    private DashboardController dashboardController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetDashboardData() {
        DashboardData expectedDashboardData = new DashboardData(/* initialize DashboardData here */);

        when(dashboardService.generateDashboardData()).thenReturn(expectedDashboardData);

        ResponseEntity<DashboardData> response = dashboardController.getDashboardData();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedDashboardData, response.getBody());
        verify(dashboardService, times(1)).generateDashboardData();
    }
}
